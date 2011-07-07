package com.force.sample.dao;

import java.util.List;

import javax.persistence.*;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.force.sample.model.ChatterPost;

/**
 * 
 * JPA implementation of the Data Access Object that interacts with ChatterPosts
 *
 * @author John Simone
 */
@Repository
public class ChatterPostDaoImpl implements ChatterPostDao {

    //Spring will provide the entity manager based on the 
    //database configuration in the application context
    @PersistenceContext
    private EntityManager entityManager;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ChatterPost getPost(int postId, String userId) {
        Query query =  entityManager.createQuery("from ChatterPost cp where cp.feedOwnerUserId = ?1 and cp.localId = ?2", ChatterPost.class);
        query.setParameter(1, userId);
        query.setParameter(2, postId);
        return (ChatterPost) query.getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void savePost(ChatterPost post) {
        entityManager.persist(post);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings(value="unchecked")
    public List<ChatterPost> getPostsForUser(String userId) {
        Query query = entityManager.createQuery("from ChatterPost cp where cp.feedOwnerUserId = ?1", ChatterPost.class);
        query.setParameter(1, userId);
        return query.getResultList();
    }

}
