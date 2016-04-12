/*
 * Created by Julio Suriano Siu on 2016.04.06  * 
 * Copyright © 2016 Julio Suriano Siu. All rights reserved. * 
 */
package com.mycompany.sessionbeanpackage;

import com.mycompany.entitypackage.RecruitPhoto;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jsuriano
 */
@Stateless
public class RecruitPhotoFacade extends AbstractFacade<RecruitPhoto> {

    @PersistenceContext(unitName = "WiRecruitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RecruitPhotoFacade() {
        super(RecruitPhoto.class);
    }
    
    public List<RecruitPhoto> findPhotosByRecruitID(Integer recruitID) {
        return (List<RecruitPhoto>) em.createNamedQuery("RecruitPhoto.findPhotosByRecruitId")
                .setParameter("recruitId", recruitID)
                .getResultList();
    }
}
