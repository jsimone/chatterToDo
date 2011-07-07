package com.force.sample.service;

import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.force.sample.dao.ChatterPostDao;
import com.force.sample.model.ChatterPost;
import com.force.sdk.oauth.context.ForceSecurityContextHolder;
import com.force.sdk.oauth.context.SecurityContext;

@Service
public class ChatterService {
    
    @Autowired
    private ChatterPostDao chatterPostDao;
    
    private URLConnection buildConn(String destination, boolean includeUsername) throws IOException {
        SecurityContext sc = ForceSecurityContextHolder.get(false);
        //String endpoint = sc.getEndPoint();
        String token = sc.getSessionId();
        String endpoint = "https://vmf01.t.salesforce.com/services";
        String url = endpoint + destination + (includeUsername ? sc.getUserId() : "");
        
        URL restUrl = new URL(url);
        URLConnection urlConn = restUrl.openConnection();
        urlConn.addRequestProperty("Authorization", "OAuth " + token);
        
        return urlConn;
    }
    
    private String getUserId() {
        SecurityContext sc = ForceSecurityContextHolder.get(false);
        return sc.getUserId();
    }
    
    private String getEndpoint() {
        return "https://vmf01.t.salesforce.com";
    }
    
    public List<ChatterPost> getToDoFeed() throws IOException {
        List<ChatterPost> posts = getStoredPostsForUser();
        getLikes(posts);
        getMentions(posts);
        return posts;
    }
    
    public List<ChatterPost> getMentions(List<ChatterPost> posts) throws IOException {
        
        URLConnection urlConn = buildConn("/data/v22.0/chatter/feeds/to/", true);
        BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
        
        StringBuilder jsonReturn = new StringBuilder();
        String inputLine;
        
        while((inputLine = in.readLine()) != null) {
            jsonReturn.append(inputLine);
        }
        
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readValue(jsonReturn.toString().getBytes(), JsonNode.class);
        
        JsonNode itemsNode = rootNode.path("feedItems").path("items");
        Iterator<JsonNode> itemsIter = itemsNode.getElements();
        while(itemsIter.hasNext()) {
            JsonNode itemNode = itemsIter.next();
            ChatterPost post = buildChatterPost(itemNode, ChatterPost.TO_DO_REASON.MENTION);
            if(!posts.contains(post)) {                
                posts.add(post);
                chatterPostDao.savePost(post);
            }
        }
        
        return posts;
    }
    
    public List<ChatterPost> getLikes(List<ChatterPost> posts) throws IOException {

        URLConnection urlConn = buildConn("/data/v22.0/chatter/feeds/news/me ", false);
        BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
        
        StringBuilder jsonReturn = new StringBuilder();
        String inputLine;
        
        while((inputLine = in.readLine()) != null) {
            jsonReturn.append(inputLine);
        }
        
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readValue(jsonReturn.toString().getBytes(), JsonNode.class);

        JsonNode itemsNode = rootNode.path("feedItems").path("items");
        Iterator<JsonNode> itemsIter = itemsNode.getElements();
        String userId = getUserId();
        while(itemsIter.hasNext()) {
            JsonNode itemNode = itemsIter.next();
            if(didILike(itemNode.get("likes"), userId)) {
                ChatterPost post = buildChatterPost(itemNode, ChatterPost.TO_DO_REASON.LIKE);
                if(!posts.contains(post)) {
                    posts.add(post);
                    chatterPostDao.savePost(post);
                }
            }
        }
        
//        System.out.println(jsonReturn.toString());
//        System.out.println("-------------About to print out feed items: ");
        
        return posts;
    }
    
    private ChatterPost buildChatterPost(JsonNode item, ChatterPost.TO_DO_REASON toDoReason) {
        ChatterPost post = new ChatterPost();
        
        post.setId(item.path("id").getTextValue());
        post.setTitle(item.path("title").getTextValue());
        post.setAuthorName(item.path("user").path("name").getTextValue());
        post.setAuthorId(item.path("user").path("id").getTextValue());
        post.setFeedOwnerUserId(getUserId());
        post.setBody(item.path("body").path("text").getTextValue());
        post.setReason(toDoReason);
        
        try {
            post.setAuthorLink(new URL(buildLink(item.path("user").path("id").getTextValue())));
            post.setPostLink(new URL(buildLink(item.path("user").path("id").getTextValue(), post.getId())));
        } catch (MalformedURLException e) {
            post.setAuthorLink(null);
            post.setPostLink(null);
        }

        return post;
    }
    
    private boolean didILike(JsonNode likesNode, String userId) {
        int total = likesNode.path("total").getIntValue();
        
        if(total < 1) {
            return false;
        }
        
        Iterator<JsonNode> likesIter = likesNode.path("likes").getElements();
        
        while(likesIter.hasNext()) {
            JsonNode likeNode = likesIter.next();
            String likeUserId = likeNode.path("user").path("id").getTextValue();
            if(userId.equals(likeUserId)) {
                return true;
            }
        }
        
        return false;
        
    }
    
    private String buildLink(String userId, String postId) {
        return buildLink(userId) + "&ChatterFeedItemId=" + postId;
    }
    
    private String buildLink(String userId) {
        return getEndpoint() + "/_ui/core/userprofile/UserProfilePage?u=" + userId;
    }
    
    private List<ChatterPost> getStoredPostsForUser() {
        return chatterPostDao.getPostsForUser(getUserId());
    }
    
    @Transactional
    public void setPostToDone(Integer postId) {
        ChatterPost post = chatterPostDao.getPost(postId, getUserId());
        post.setDone(true);
        chatterPostDao.savePost(post);
    }
    
    @Transactional
    public void setPostToNotDone(Integer postId) {
        ChatterPost post = chatterPostDao.getPost(postId, getUserId());
        post.setDone(false);
        chatterPostDao.savePost(post);
    }    
}
