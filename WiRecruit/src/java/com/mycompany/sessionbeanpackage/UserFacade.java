/*
 * Created by Julio Suriano Siu on 2016.04.06  * 
 * Copyright © 2016 Julio Suriano Siu. All rights reserved. * 
 */
package com.mycompany.sessionbeanpackage;

import com.mycompany.entitypackage.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jsuriano
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "WiRecruitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }
    
    public User getUser(int id) {
        return em.find(User.class, id);
    }

    public User findByUsername(String username) {
        if (em.createQuery("SELECT u FROM User u WHERE u.username = :uname")
                .setParameter("uname", username)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (User) (em.createQuery("SELECT u FROM User u WHERE u.username = :uname")
                .setParameter("uname", username)
                .getSingleResult());    
        }
    }
    
    public void deleteUser(int id){
        
        User user = em.find(User.class, id);
        em.remove(user);
    }
    
    public List findUserByUniversity(String search){
        if (em.createQuery("SELECT r FROM User r WHERE r.school = :commit")
                .setParameter("commit", search)
                .getResultList().isEmpty()) {
            return null;
        }
        else {
            return (List) (em.createQuery("SELECT r FROM User r WHERE r.school = :commit")
                .setParameter("commit", search)
                .getResultList());        
        }
    }
    
}
