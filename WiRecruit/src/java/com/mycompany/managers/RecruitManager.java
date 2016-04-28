/*
 * Created by Julio Suriano Siu on 2016.04.10  * 
 * Copyright © 2016 Julio Suriano Siu. All rights reserved. * 
 */
package com.mycompany.managers;

import com.mycompany.entitypackage.RecruitPhoto;
import com.mycompany.entitypackage.Recruit;
import com.mycompany.entitypackage.User;
import com.mycompany.jsfpackage.util.JsfUtil;
import com.mycompany.jsfpackage.util.JsfUtil.PersistAction;
import com.mycompany.sessionbeanpackage.UserFacade;
import com.mycompany.sessionbeanpackage.RecruitPhotoFacade;
import com.mycompany.sessionbeanpackage.RecruitFacade;
import com.mycompany.managers.ProfileViewManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
 
@Named(value = "recruitManager")
@SessionScoped
/**
 *
 * @author jsuriano
 */
public class RecruitManager implements Serializable {
    private String firstName;
    private String lastName;
    private String school;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private int zipcode;
    private int year;
    private float gpa;
    private String email;
    private long phone;
    private int skillLevel;
    private String position;
    private String secondaryPosition;
    private int height;
    private int weight;
    private String commitment;
    private String notes;
    private Recruit currentRecruitID;
    private Recruit selected;

    
    private String statusMessage = "";
    
    private final String[] listOfStates = Constants.STATES;
    private final int[] listOfSkillLevels = Constants.SKILL_LEVELS;
    private final String[] listOfPositions = Constants.POSITIONS;
    
    private List<Recruit> listOfRecruits = null;
    
    @EJB
    private com.mycompany.sessionbeanpackage.RecruitFacade ejbFacade;
    
    @EJB
    private RecruitFacade recruitFacade;
    
    
    @EJB
    private RecruitPhotoFacade recruitPhotoFacade;
    
    @EJB
    private UserFacade userFacade;
    
    public List<Recruit> getItems() {
        int user_id = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
        User user = userFacade.find(user_id);
        commitment = user.getSchool();
        
        if (listOfRecruits == null) {
            listOfRecruits = getFacade().findRecruitsByCommitment(commitment);
        }
        return listOfRecruits;
    }
    
    public Recruit getSelected() {
        return selected;
    }

    public void setSelected(Recruit selected) {
        this.selected = selected;
    }
    
    protected void initializeEmbeddableKey() {
    }

    public Recruit prepareCreate() {
        selected = new Recruit();
        initializeEmbeddableKey();
        return selected;
    }
    
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RecruitCreated"));
        if (!JsfUtil.isValidationFailed()) {
            listOfRecruits = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RecruitUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RecruitDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            listOfRecruits = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public Recruit getRecuit(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Recruit> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Recruit> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
    public Recruit getCurrentRecruitID() {
        return currentRecruitID;
    }

    public void setCurrentRecruitID(Recruit currentRecruitID) {
        this.currentRecruitID = currentRecruitID;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getSchool() {
        return school;
    }
    
    public void setSchool(String school) {
        this.school = school;
    }
    
    public String getAddress1() {
        return address1;
    }
    
    public void setAddress1(String address1) {
        this.address1 = address1;
    }
    
    public String getAddress2() {
        return address2;
    }
    
    public void setAddress2(String address2) {
        this.address2 = address2;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public int getZipcode() {
        return zipcode;
    }
    
    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }
    
    public int getYear() {
        return year;
    }
    
    public void setYear(int year) {
        this.year =year;
    }
    
    public float getGpa() {
        return gpa;
    }
    
    public void setGpa(float gpa) {
        this.gpa = gpa;
    }
        
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public long getPhone() {
        return phone;
    }
    
    public void setPhone(long phone) {
        this.phone = phone;
    }
    
    public int getSkillLevel() {
        return skillLevel;
    }
    
    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }
    
    public String getPosition() {
        return position;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    public String getSecondaryPosition() {
        return secondaryPosition;
    }
    
    public void setSecondaryPosition(String secondaryPosition) {
        this.secondaryPosition = secondaryPosition;
    }
    
    public int getHeight() {
        return height;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public int getWeight() {
        return weight;
    }
    
    public void setWeight(int weight) {
        this.weight = weight;
    }
    
    public String getCommitment() {
        return commitment;
    }
    
    public void setCommitment(String commitment) {
        this.commitment = commitment;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String getStatusMessage() {
        return statusMessage;
    }
    
    public String[] getListOfStates() {
        return listOfStates;
    }
    
    public int[] getListOfSkillLevels() {
        return listOfSkillLevels;
    }
    
    public String[] getListOfPositions() {
        return listOfPositions;
    }
    
    public List getListOfRecruits() {
        return listOfRecruits;
    }
    
    protected void setEmbeddableKeys() {
    }
    
    private RecruitFacade getFacade() {
        return ejbFacade;
    }

    
    public String createRecruit() {
        int user_id = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
        User user = userFacade.find(user_id);
        
        commitment = user.getSchool();
        if (statusMessage.isEmpty()) {
            try {
                
                Recruit recruit = new Recruit();
                recruit.setFirstName(firstName);
                recruit.setLastName(lastName);  
                recruit.setEmail(email);
                recruit.setPhone(phone);
                recruit.setSchool(school);
                recruit.setCity(city);
                recruit.setState(state);
                recruit.setZipcode(zipcode);
                recruit.setAddress1(address1);
                recruit.setAddress2(address2);
                recruit.setPosition(position);
                recruit.setSecondaryPosition(secondaryPosition);
                recruit.setSkillLevel(skillLevel);
                recruit.setGpa(gpa);
                recruit.setCommitment(commitment);
                recruit.setHeight(height);
                recruit.setWeight(weight);
                recruit.setYear(year);
                recruit.setNotes(notes);
                
                recruitFacade.create(recruit);                
            } catch (EJBException e) {
                statusMessage = "Something went wrong while creating the recruit!";
                return "";
            }
            AccountManager.appendFeed(user.getFirstName() + " " + user.getLastName() + " added "
                + " " + firstName + " " + lastName + " to the recruit book");
            
            return "RecruitBook?faces-redirect=true";
        }
        return "";
    }
    
    public String updateRecruit() {
        if (statusMessage.isEmpty()) {
            try {
                
            } catch (EJBException e) {
                statusMessage = "Something went wrong while editing the recruit!";
                return "";
            }
            return "RecruitProfile?faces-redirect=true";
        }
        return "";
    }
    
    public String deleteRecruit() {
        return "";
    }
    
    public String userPhoto() {
        /*String user_name = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("username");
        Recruit recruit = recruitFacade.findByUsername(user_name);
        List<RecruitPhoto> photoList = recruitPhotoFacade.findPhotosByRecruitID(user.getId());
        if (photoList.isEmpty()) {
            return "defaultUserPhoto.png";
        }
        return photoList.get(0).getThumbnailName();*/
        return "";
    }
    
    public String getListOfRecruitsByCommitment()
    {
        int user_id = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
        User user = userFacade.find(user_id);
    
        commitment = user.getSchool();
        listOfRecruits = recruitFacade.findRecruitsByCommitment(commitment);
        return "RecruitBook?faces-redirect=true";
    }
    
    public String viewRecruit()
    {
        return "RecruitProfile?faces-redirect=true";
    }
    
    public String addRecruit()
    {
        return "CreateRecruit?faces-redirect=true";
    }
    
     

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }
}
