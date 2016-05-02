/*
 * Created by Julio Suriano Siu on 2016.04.10  * 
 * Copyright © 2016 Julio Suriano Siu. All rights reserved. * 
 */
package com.mycompany.managers;

import com.mycompany.entitypackage.User;
import com.mycompany.sessionbeanpackage.UserFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named(value = "profileViewManager")
@SessionScoped
/**
 *
 * @author jsuriano
 */
public class ProfileViewManager implements Serializable {

    // Instance Variable (Property)
    private User user;
    
  /**
   * The instance variable 'userFacade' is annotated with the @EJB annotation.
   * This means that the GlassFish application server, at runtime, will inject in
   * this instance variable a reference to the @Stateless session bean UserFacade.
   */  
  @EJB
  private UserFacade userFacade;

  public ProfileViewManager() {

  }

  public String viewProfile() {
    return "StaffProfile";
  }

  /**
   * @return the user
   */
  public User getUser() {
    return user;
  }

  public User getLoggedInUser() {
    return userFacade.find(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id"));
  }

  /**
   * @param user the user to set
   */
  public void setUser(User user) {
    this.user = user;
  }
  
  public String getTitle() {
        return getLoggedInUser().getTitle();
    }
    
    public void setTitle(String title) {
        User u = userFacade.find(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id"));
        u.setTitle(title);
        userFacade.edit(u);
    }
    
    public String getSchool() {
        return getLoggedInUser().getSchool();
    }
    
    public void setSchool(String school) {
        User u = userFacade.find(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id"));
        u.setSchool(school);
        userFacade.edit(u);
    }
    
    public String getCity() {
        return getLoggedInUser().getCity();
    }
    
    public void setCity(String city) {
        User u = userFacade.find(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id"));
        u.setCity(city);
        userFacade.edit(u);
    }
    
    public String getState() {
        return getLoggedInUser().getState();
    }
    
    public void setState(String state) {
        User u = userFacade.find(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id"));
        u.setState(state);
        userFacade.edit(u);
    }
    
    public int getZipcode() {
        return getLoggedInUser().getZipcode();
    }
    
    public void setZipcode(int zipcode) {
        User u = userFacade.find(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id"));
        u.setZipcode(zipcode);
        userFacade.edit(u);
    }
  
}