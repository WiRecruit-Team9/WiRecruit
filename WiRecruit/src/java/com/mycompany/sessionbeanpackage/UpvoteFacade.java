/*
 * Created by Franki Yeung on 2016.05.02  * 
 * Copyright © 2016 Franki Yeung. All rights reserved. * 
 */
package com.mycompany.sessionbeanpackage;

import com.mycompany.entitypackage.Recruit;
import com.mycompany.entitypackage.Upvote;
import com.mycompany.entitypackage.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ftyyeung
 */
@Stateless
public class UpvoteFacade extends AbstractFacade<Upvote> {

    @PersistenceContext(unitName = "WiRecruitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UpvoteFacade() {
        super(Upvote.class);
    }
    
    public Upvote findUpVoteByUserRecruit(User id, Recruit selected)
    {
        if (em.createQuery("SELECT g FROM Upvote g WHERE g.userId = :pass AND g.recruitId = :rec")
                .setParameter("pass", id).setParameter("rec", selected)
                .getResultList().isEmpty()) {
            return null;
        }
        else {
            return (Upvote)(em.createQuery("SELECT g FROM Upvote g WHERE g.userId = :pass AND g.recruitId = :rec")
                .setParameter("pass", id).setParameter("rec", selected)
                .getSingleResult());
        }
    }
    
    public List<Upvote> searchUpvoteByRecruit(Recruit selected) {
        if (em.createQuery("SELECT g FROM Upvote g WHERE g.recruitId = :rec")
                .setParameter("rec", selected)
                .getResultList().isEmpty()) {
            return null;
        }
        else {
            return (List<Upvote>)(em.createQuery("SELECT g FROM Upvote g WHERE g.recruitId = :rec")
                .setParameter("rec", selected)
                .getResultList());
        }
    }
}
