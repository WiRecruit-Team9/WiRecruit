/*
 * Created by Julio Suriano Siu on 2016.04.06  * 
 * Copyright © 2016 Julio Suriano Siu. All rights reserved. * 
 */
package com.mycompany.sessionbeanpackage;

import com.mycompany.entitypackage.UserPhoto;
import javax.ejb.Stateless;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jsuriano
 */
@Stateless
public class UserPhotoFacade extends AbstractFacade<UserPhoto> {

    @PersistenceContext(unitName = "WiRecruitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserPhotoFacade() {
        super(UserPhoto.class);
    }
    
    public List<UserPhoto> findPhotosByUserID(Integer userID) {
        return (List<UserPhoto>) em.createNamedQuery("UserPhoto.findPhotosByUserId")
                .setParameter("userId", userID)
                .getResultList();
    }
    
}
