package com.force.sample.dao;

import java.util.List;

import javax.persistence.*;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.force.sample.model.ChatterPost;

@Repository
public class ChatterPostDaoImpl implements ChatterPostDao {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public ChatterPost getPost(int postId) {
        return entityManager.find(ChatterPost.class, postId);
    }

    @Override
    @Transactional
    public ChatterPost savePost(ChatterPost post) {
        //entityManager.merge(post);
        entityManager.persist(post);
        return null;
    }

    @Override
    @SuppressWarnings(value="unchecked")
    public List<ChatterPost> getPostsForUser(String userId) {
        Query query = entityManager.createQuery("from ChatterPost cp where cp.feedOwnerUserId = '" + userId + "'", ChatterPost.class);
        return query.getResultList();
    }

}
