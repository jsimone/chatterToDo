package com.force.sample.service;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import org.codehaus.jackson.map.ObjectMapper;

import com.force.sample.model.ChatterPost;
import com.force.sdk.oauth.context.ForceSecurityContextHolder;
import com.force.sdk.oauth.context.SecurityContext;

public class ChatterService {
    
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
    
    public List<ChatterPost> getFeed() throws IOException {

        ArrayList<ChatterPost> posts = new ArrayList<ChatterPost>();
        
        //URLConnection urlConn = buildConn("/data/v22.0/chatter/feeds/news/me");
        URLConnection urlConn = buildConn("/data/v22.0/chatter/feeds/user-profile/", true);
        BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
        
        StringBuilder jsonReturn = new StringBuilder();
        String inputLine;
        
        while((inputLine = in.readLine()) != null) {
            jsonReturn.append(inputLine);
        }
        
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> mappedResult = 
            mapper.readValue(jsonReturn.toString().getBytes(), HashMap.class);
        
        System.out.println(jsonReturn.toString());
        System.out.println("-------------About to print out feed items: ");
        
        HashMap<String, Object> feedItems = (HashMap<String, Object>) mappedResult.get("feedItems");
        ArrayList<HashMap<String, Object>> items = (ArrayList<HashMap<String, Object>>) feedItems.get("items");
        
        String userId = getUserId();
        for(HashMap<String, Object> item : items) {
            System.out.println("item id: " + item.get("id") + ", item title: " + item.get("title") + ", likes: " + item.get("likes"));
            if(didILike((HashMap<String, Object>)item.get("likes"), userId)) {
                ChatterPost post = buildChatterPost(item, ChatterPost.TO_DO_REASON.LIKE);
                posts.add(post);
            }
        }
        
        return posts;
    }
    
    private ChatterPost buildChatterPost(HashMap<String, Object> item, ChatterPost.TO_DO_REASON toDoReason) {
        ChatterPost post = new ChatterPost();
        
        post.setId((String)item.get("id"));
        post.setTitle((String)item.get("title"));
        post.setReason(toDoReason);
        
        return post;
    }
    
    private boolean didILike(HashMap<String, Object> likes, String userId) {
        int total = (Integer)likes.get("total");
        
        if(total < 1) {
            return false;
        }
        
        ArrayList<HashMap<String, Object>> likesList = 
            (ArrayList<HashMap<String, Object>>) likes.get("likes");
        
        for(HashMap<String, Object> like : likesList) {
            HashMap<String, Object> user = (HashMap<String, Object>)like.get("user");
            String likeUserId = (String)user.get("id");
            if(userId.equals(likeUserId)) {
                return true;
            }
        }
        
        return false;
        
    }
    
}
