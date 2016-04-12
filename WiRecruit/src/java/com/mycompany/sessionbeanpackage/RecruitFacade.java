/*
 * Created by Julio Suriano Siu on 2016.04.06  * 
 * Copyright © 2016 Julio Suriano Siu. All rights reserved. * 
 */
package com.mycompany.sessionbeanpackage;

import com.mycompany.entitypackage.Recruit;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jsuriano
 */
@Stateless
public class RecruitFacade extends AbstractFacade<Recruit> {

    @PersistenceContext(unitName = "WiRecruitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RecruitFacade() {
        super(Recruit.class);
    }
    
    public Recruit getRecruit(int id) {
        return em.find(Recruit.class, id);
    }

    public void deleteRecruit(int id){
        Recruit recruit = em.find(Recruit.class, id);
        em.remove(recruit);
    }
    
}
