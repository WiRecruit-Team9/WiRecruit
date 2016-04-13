/*
 * Created by Julio Suriano Siu on 2016.04.10  * 
 * Copyright © 2016 Julio Suriano Siu. All rights reserved. * 
 */
package com.mycompany.managers;

import com.mycompany.entitypackage.RecruitPhoto;
import com.mycompany.entitypackage.Recruit;
import com.mycompany.entitypackage.User;
import com.mycompany.sessionbeanpackage.UserFacade;
import com.mycompany.sessionbeanpackage.RecruitPhotoFacade;
import com.mycompany.sessionbeanpackage.RecruitFacade;
import com.mycompany.managers.ProfileViewManager;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
    
    private String statusMessage = "";
    
    private final String[] listOfStates = Constants.STATES;
    private final int[] listOfSkillLevels = Constants.SKILL_LEVELS;
    private final String[] listOfPositions = Constants.POSITIONS;
    
    @EJB
    private RecruitFacade recruitFacade;
    
    @EJB
    private RecruitPhotoFacade recruitPhotoFacade;
    
    @EJB
    private UserFacade userFacade;
    
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
                
                System.out.println("Test");
                
                recruitFacade.create(recruit);                
            } catch (EJBException e) {
                statusMessage = "Something went wrong while creating the recruit!";
                return "";
            }
            return "Recruits";
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
            return "RecruitProfile";
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
}
