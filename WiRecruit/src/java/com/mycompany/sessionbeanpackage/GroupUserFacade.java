/*
 * Created by Franki Yeung on 2016.05.02  * 
 * Copyright © 2016 Franki Yeung. All rights reserved. * 
 */
package com.mycompany.sessionbeanpackage;

import com.mycompany.entitypackage.GroupUser;
import com.mycompany.entitypackage.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ftyyeung
 */
// GroupUserFacade is an EJB style POJO (Plain Old Java Object) session bean, 
// which is annotated to be @Stateless.
@Stateless
public class GroupUserFacade extends AbstractFacade<GroupUser> {

    @PersistenceContext(unitName = "WiRecruitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GroupUserFacade() {
        super(GroupUser.class);
    }
    
    // The following selectGroupFromUser method is added to the generated code.
    
    public GroupUser selectGroupFromUser(User id)
    {
        if (em.createQuery("SELECT g FROM GroupUser g WHERE g.userId = :pass")
                .setParameter("pass", id)
                .getResultList().isEmpty()) {
            return null;
        }
        else {
            return (GroupUser)(em.createQuery("SELECT g FROM GroupUser g WHERE g.userId = :pass")
                .setParameter("pass", id)
                .getSingleResult());
        }
    }
}
