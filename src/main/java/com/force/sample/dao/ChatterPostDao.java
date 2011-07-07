package com.force.sample.dao;

import java.util.List;

import com.force.sample.model.ChatterPost;

/**
 * 
 * Describes the interface for the Data Access Object that interacts with ChatterPost
 * objects in the database.
 *
 * @author John Simone
 */
public interface ChatterPostDao {

    /**
     * Retrieve a post by post id and user id. The post id is unique and could be used
     * on it's own. The inclusion of user id ensures that a user does not alter posts
     * that do not belong to them.
     * 
     * @param postId - The post id to look up
     * @param userId - The id of the current user
     * @return The requested ChatterPost
     */
    ChatterPost getPost(int postId, String userId);
    
    /**
     * Persist the post.
     * 
     * @param post - the ChatterPost to save
     * @return
     */
    void savePost(ChatterPost post);
    
    /**
     * Get all of the posts that belong to a specific user's to do list.
     * 
     * @param userId - the id of the current user
     * @return a list of ChatterPosts
     */
    List<ChatterPost> getPostsForUser(String userId);
    
}
