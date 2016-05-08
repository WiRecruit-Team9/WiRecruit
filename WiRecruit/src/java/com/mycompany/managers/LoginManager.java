/*
 * Created by Julio Suriano Siu on 2016.04.10  * 
 * Copyright © 2016 Julio Suriano Siu. All rights reserved. * 
 */
package com.mycompany.managers;

import com.mycompany.entitypackage.Event;
import com.mycompany.entitypackage.User;
import com.mycompany.sessionbeanpackage.EventFacade;
import com.mycompany.sessionbeanpackage.UserFacade;
import com.mycompany.sessionbeanpackage.GroupUserFacade;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "loginManager")
@SessionScoped
/**
 *
 * @author jsuriano
 */
public class LoginManager implements Serializable {

    private String username;
    private String password;
    private String errorMessage;

    /**
     * The instance variable 'userFacade' is annotated with the @EJB annotation.
     * This means that the GlassFish application server, at runtime, will inject
     * in this instance variable a reference to the @Stateless session bean
     * UserFacade.
     */
    @EJB
    private UserFacade userFacade;
    private EventFacade eventFacade;
    private GroupUserFacade groupUserFacade;

    /**
     * Creates a new instance of LoginManager
     */
    public LoginManager() {
    }

    /**
     * @return the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the user to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public String createUser() {
        return "CreateAccount?faces-redirect=true";
    }

    public String resetPassword() {
        return "EnterUsername?faces-redirect=true";
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String loginUser() {
        User user = userFacade.findByUsername(getUsername());
        if (user == null) {
            errorMessage = "Invalid username or password!";
            return "";
        } else {
            if (user.getUsername().equals(getUsername()) && user.getPassword().equals(getPassword())) {
                errorMessage = "";
                initializeSessionMap(user);

                return "Dashboard?faces-redirect=true";
            }
            errorMessage = "Invalid username or password!";
            return "";
        }
    }

    public void initializeSessionMap(User user) {
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("first_name", user.getFirstName());
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("last_name", user.getLastName());
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("username", username);
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("user_id", user.getId());
    }

}
