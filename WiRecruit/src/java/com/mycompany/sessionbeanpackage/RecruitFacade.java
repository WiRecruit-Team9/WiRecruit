/*
 * Created by Franki Yeung on 2016.05.02  * 
 * Copyright © 2016 Franki Yeung. All rights reserved. * 
 */
package com.mycompany.sessionbeanpackage;

import com.mycompany.entitypackage.Recruit;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ftyyeung
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
    
    public void deleteRecruit(int id){
        Recruit recruit = em.find(Recruit.class, id);
        em.remove(recruit);
    }
    
    public Recruit getRecruit(int id) {
        return em.find(Recruit.class, id);
    }
    
    public List findRecruitsByCommitment(String search){
        if (em.createQuery("SELECT r FROM Recruit r WHERE r.commitment = :commit")
                .setParameter("commit", search)
                .getResultList().isEmpty()) {
            return null;
        }
        else {
            return (List) (em.createQuery("SELECT r FROM Recruit r WHERE r.commitment = :commit")
                .setParameter("commit", search)
                .getResultList());        
        }
    }
    
    public List<Recruit> searchRecruitByName(String name) {
        List<Recruit> recruits = new ArrayList<>();
        
        if (name.trim().length() != 0) {
            recruits = em.createQuery("SELECT r FROM Recruit r WHERE r.firstName LIKE '%" + name + "%' "
                    + "OR r.lastName LIKE '%" + name + "%'", Recruit.class)
                .getResultList();
        }

        return recruits;
    }
}
