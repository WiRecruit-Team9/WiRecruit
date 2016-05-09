/*
 * Created by Peter Cho on 2016.05.03  * 
 * Copyright © 2016 Peter Cho. All rights reserved. * 
 */
package com.mycompany.sessionbeanpackage;

import com.mycompany.entitypackage.Comment;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Peter Cho
 */
// CommentFacade is an EJB style POJO (Plain Old Java Object) session bean, 
// which is annotated to be @Stateless.
@Stateless
public class CommentFacade extends AbstractFacade<Comment> {

    @PersistenceContext(unitName = "WiRecruitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CommentFacade() {
        super(Comment.class);
    }
    
    // The following getAllCommentsForRecruit method is added to the generated code.
    
    public List<Comment> getAllCommentsForRecruit(int id) {
        return em.createQuery("SELECT c FROM Comment c WHERE c.recruitId.id = :id").
                setParameter("id", id).getResultList();
    }
}
