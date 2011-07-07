package com.force.sample.dao;

import java.util.List;

import com.force.sample.model.ChatterPost;

public interface ChatterPostDao {

    public ChatterPost getPost(int postId, String userId);
    public ChatterPost savePost(ChatterPost post);
    
    public List<ChatterPost> getPostsForUser(String userId);
    
}
