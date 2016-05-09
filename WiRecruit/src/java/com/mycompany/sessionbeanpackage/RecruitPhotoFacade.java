/*
 * Created by Franki Yeung on 2016.05.02  * 
 * Copyright © 2016 Franki Yeung. All rights reserved. * 
 */
package com.mycompany.sessionbeanpackage;

import com.mycompany.entitypackage.RecruitPhoto;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ftyyeung
 */
// RecruitPhotoFacade is an EJB style POJO (Plain Old Java Object) session bean, 
// which is annotated to be @Stateless.
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
    
    // The following findPhotosByRecruitID method is added to the generated code.
    
    public List<RecruitPhoto> findPhotosByRecruitID(Integer recruitID) {
        return (List<RecruitPhoto>) em.createNamedQuery("RecruitPhoto.findPhotosByRecruitId")
                .setParameter("recruitId", recruitID)
                .getResultList();
    }
}
