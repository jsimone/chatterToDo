package com.force.sample.model;

import java.net.URL;

import javax.persistence.*;

@Entity
public class ChatterPost {

    public enum TO_DO_REASON {LIKE,MENTION};
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO) 
    private int localId;
    private String id;
    private String feedOwnerUserId;
    private String title;
    private String body;
    private String author;
    private TO_DO_REASON reason;
    private boolean done;
    private URL link;
    
    public int getLocalId() {
        return localId;
    }
    public void setLocalId(int localId) {
        this.localId = localId;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getFeedOwnerUserId() {
        return feedOwnerUserId;
    }
    public void setFeedOwnerUserId(String userId) {
        this.feedOwnerUserId = userId;
    }
    public TO_DO_REASON getReason() {
        return reason;
    }
    public void setReason(TO_DO_REASON reason) {
        this.reason = reason;
    }
    public boolean isDone() {
        return done;
    }
    public void setDone(boolean done) {
        this.done = done;
    }
    public URL getLink() {
        return link;
    }
    public void setLink(URL link) {
        this.link = link;
    }
    @Override
    public String toString() {
        return "{id=" + id + ", title=" + title + ", author=" + author + ", reason=" + reason + ", completed=" + done + "}";
    }
    
    /**
     * It's important that we only consider values that come from the chatter api 
     * in the comparison methods since these are mainly used to determine whether or not to store
     * the post in the database.
     * 
     * For this reason we'll need to exclude reason and done from this algorithm
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ChatterPost other = (ChatterPost)obj;
        if (author == null) {
            if (other.author != null) return false;
        } else if (!author.equals(other.author)) return false;
        if (body == null) {
            if (other.body != null) return false;
        } else if (!body.equals(other.body)) return false;
        if (feedOwnerUserId == null) {
            if (other.feedOwnerUserId != null) return false;
        } else if (!feedOwnerUserId.equals(other.feedOwnerUserId)) return false;
        if (id == null) {
            if (other.id != null) return false;
        } else if (!id.equals(other.id)) return false;
        if (title == null) {
            if (other.title != null) return false;
        } else if (!title.equals(other.title)) return false;
        return true;
    }
 
    /**
     * It's important that we only consider values that come from the chatter api 
     * in the comparison methods since these are mainly used to determine whether or not to store
     * the post in the database.
     * 
     * For this reason we'll need to exclude reason and done from this algorithm
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((author == null) ? 0 : author.hashCode());
        result = prime * result + ((body == null) ? 0 : body.hashCode());
        result = prime * result + ((feedOwnerUserId == null) ? 0 : feedOwnerUserId.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }
    
}
