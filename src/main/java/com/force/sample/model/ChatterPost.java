package com.force.sample.model;

public class ChatterPost {

    public enum TO_DO_REASON {LIKE,MENTION};
    
    private String id;
    private String title;
    private String body;
    private String author;
    private TO_DO_REASON reason;
    private boolean done;
    
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
    
    @Override
    public String toString() {
        return "{id=" + id + ", title=" + title + ", author=" + author + ", reason=" + reason + ", completed=" + done + "}";
    }
    
}
