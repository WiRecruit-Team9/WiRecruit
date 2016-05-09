/*
 * Created by Franki Yeung on 2016.05.02  * 
 * Copyright © 2016 Franki Yeung. All rights reserved. * 
 */
package com.mycompany.sessionbeanpackage;

import com.mycompany.entitypackage.Event;
import com.mycompany.entitypackage.Group1;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ftyyeung
 */
// EventFacade is an EJB style POJO (Plain Old Java Object) session bean, 
// which is annotated to be @Stateless.
@Stateless
public class EventFacade extends AbstractFacade<Event> {

    @PersistenceContext(unitName = "WiRecruitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventFacade() {
        super(Event.class);
    }
    
    // The following findEventsByGroup method is added to the generated code.
    
    public List<Event> findEventsByGroup(Group1 searchGroup){
        if (em.createQuery("SELECT e FROM Event e WHERE e.groupId = :groupID "
                + "ORDER BY e.id DESC")
                .setParameter("groupID", searchGroup)
                .getResultList().isEmpty()) {
            return null;
        }
        else {
            return (List<Event>) (em.createQuery("SELECT e FROM Event e WHERE e.groupId = :groupID "
                + "ORDER BY e.id DESC")
                .setParameter("groupID", searchGroup)
                .getResultList());        
        }
    }
    
}
