/*
 * Created by Franki Yeung on 2016.05.02  * 
 * Copyright © 2016 Franki Yeung. All rights reserved. * 
 */
package com.mycompany.sessionbeanpackage;

import com.mycompany.entitypackage.Group1;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ftyyeung
 */
@Stateless
public class Group1Facade extends AbstractFacade<Group1> {

    @PersistenceContext(unitName = "WiRecruitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Group1Facade() {
        super(Group1.class);
    }
    
    public Group1 findGroupByPasscode(int search){
        if (em.createQuery("SELECT g FROM Group1 g WHERE g.passcode = :pass")
                .setParameter("pass", search)
                .getResultList().isEmpty()) {
            return null;
        }
        else {
            return (Group1)(em.createQuery("SELECT g FROM Group1 g WHERE g.passcode = :pass")
                .setParameter("pass", search)
                .getResultList()).get(0);        
        }
    }
}
