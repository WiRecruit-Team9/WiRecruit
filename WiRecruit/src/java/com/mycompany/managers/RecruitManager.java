/*
 * Created by Julio Suriano Siu on 2016.04.10  * 
 * Copyright © 2016 Julio Suriano Siu. All rights reserved. * 
 */
package com.mycompany.managers;

import com.mycompany.entitypackage.Comment;
import com.mycompany.entitypackage.RecruitPhoto;
import com.mycompany.entitypackage.Recruit;
import com.mycompany.entitypackage.Event;
import com.mycompany.entitypackage.Upvote;
import com.mycompany.entitypackage.User;
import com.mycompany.jsfpackage.util.JsfUtil;
import com.mycompany.jsfpackage.util.JsfUtil.PersistAction;
import com.mycompany.sessionbeanpackage.CommentFacade;
import com.mycompany.sessionbeanpackage.UserFacade;
import com.mycompany.sessionbeanpackage.RecruitPhotoFacade;
import com.mycompany.sessionbeanpackage.UpvoteFacade;
import com.mycompany.sessionbeanpackage.RecruitFacade;
import com.mycompany.sessionbeanpackage.EventFacade;
import com.mycompany.sessionbeanpackage.GroupUserFacade;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.model.map.GeocodeResult;
 
/**
 *
 * @author ftyyeung
 */
@Named(value = "recruitManager")
@SessionScoped
/**
 *
 * @author jsuriano
 */
public class RecruitManager implements Serializable {
    private String firstName;       // First Name of current recruit
    private String lastName;        // Last Name of current recruit
    private String school;          // High School Name of current recruit
    private String address1;        // Address 1 field of current recruit
    private String address2;        // Address 2 field of current recruit
    private String city;            // City field of current recruit
    private String state;           // State field of current recruit
    private int zipcode;            // Zipcode of current recruit
    private String year;            // Graduation year of the current recruit
    private float gpa;              // GPA field of the current recruit
    private String email;           // E-mail address of the current recruit
    private String recruitedYear;   // Year the current recruit is added
    private String phone;           // Phone Number of current Recruit
    private int skillLevel;         // Skill level field of current recruit
    private String position;        // primary position field of current recruit
    private String secondaryPosition;   // Secondary position field of current recruit
    private int height;             // Height of current recruit in inches
    private int weight;             // weight of current recruit in pounds
    private String commitment;      // The school recruiting the current recruit
    private String notes;           // Any notes on the current recruit
    private Recruit currentRecruitID;   // The ID of the current recruit
    private Recruit selected;       // The current recruit object
    private MapModel geoModel;      // A new gmap object
    private String centerGeocode = "40, 40"; // A String that denotes the coordinates of a location
    private String searchedRecruitName; // The name to search recruits by
    private String weather; // String representation of weather for a current location
    private String like;    // Number of likes for current user
    private String statusMessage = "";  //current error message, empty if no error
    private String text;    // Text to be created as comment entry
    
    private final String[] listOfSchoolYear = Constants.SCHOOLYEAR; // List of recruitment years
    private final String[] listOfStates = Constants.STATES; // List of states
    private final int[] listOfSkillLevels = Constants.SKILL_LEVELS; // List of skill levels
    private final String[] listOfPositions = Constants.POSITIONS;   // list of different positions
    
    private List<Recruit> listOfRecruits = null;    // List of recruits for current school
    private List<Recruit> matchedRecruits = new ArrayList();    // list of recruits that matches search
    
    /**
     * The instance variable 'recruitFacade' is annotated with the @EJB annotation.
     * This means that the GlassFish application server, at runtime, will inject
     * in this instance variable a reference to the @Stateless session bean
     * UserFacade.
     */
    @EJB
    private RecruitFacade recruitFacade;
    /**
     * The instance variable 'eventFacade' is annotated with the @EJB annotation.
     * This means that the GlassFish application server, at runtime, will inject
     * in this instance variable a reference to the @Stateless session bean
     * UserFacade.
     */
    @EJB
    private EventFacade eventFacade;
    /**
     * The instance variable 'recruitPhotoFacade' is annotated with the @EJB annotation.
     * This means that the GlassFish application server, at runtime, will inject
     * in this instance variable a reference to the @Stateless session bean
     * UserFacade.
     */
    @EJB
    private RecruitPhotoFacade recruitPhotoFacade;
    /**
     * The instance variable 'userFacade' is annotated with the @EJB annotation.
     * This means that the GlassFish application server, at runtime, will inject
     * in this instance variable a reference to the @Stateless session bean
     * UserFacade.
     */
    @EJB
    private UserFacade userFacade;
    /**
     * The instance variable 'groupUserFacade' is annotated with the @EJB annotation.
     * This means that the GlassFish application server, at runtime, will inject
     * in this instance variable a reference to the @Stateless session bean
     * UserFacade.
     */
    @EJB
    private GroupUserFacade groupUserFacade;
    /**
     * The instance variable 'commentFacade' is annotated with the @EJB annotation.
     * This means that the GlassFish application server, at runtime, will inject
     * in this instance variable a reference to the @Stateless session bean
     * UserFacade.
     */
    @EJB
    private CommentFacade commentFacade;
    /**
     * The instance variable 'upvoteFacade' is annotated with the @EJB annotation.
     * This means that the GlassFish application server, at runtime, will inject
     * in this instance variable a reference to the @Stateless session bean
     * UserFacade.
     */
    @EJB
    private UpvoteFacade upvoteFacade;
    
    /**
     *  Empty Constructor for RecruitManager
     */
    public RecruitManager()
    {
        
    }
    
    /**
     *  Initialization Method for after constructor is called
     */
    @PostConstruct
    public void init() {
        geoModel = new DefaultMapModel(); // Creates a new gmap instance
    }
    
    /**
     *  Gets the list of recruits that matches the current user's group
     * 
     * @return a list of Recruit objects that belongs in the user's group
     */
    public List<Recruit> getItems() {
        // Get the current user object
        int user_id = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
        User user = userFacade.find(user_id);
        
        // Set current group to the user's group
        commitment = user.getSchool();
        
        // Attempt to find a list of recruits using current group
        if (listOfRecruits == null) {
            listOfRecruits = recruitFacade.findRecruitsByCommitment(commitment);
        }
        return listOfRecruits;
    }

    /**
     * Getter method to retrieve gmap
     * 
     * @return a MapModel object 
     */
    public MapModel getGeoModel() {
        return geoModel;
    }

    /**
     * Setter method for GeoModel
     * 
     * @param geoModel is the new gmap instance to be set
     */
    public void setGeoModel(MapModel geoModel) {
        this.geoModel = geoModel;
    }

    /**
     * @return the Number of likes for current recruit
     */
    public int getNumLikes() {
        // A list of upvote objects that contains all of the current recruit's upvotes
        List<Upvote> upvotes = upvoteFacade.searchUpvoteByRecruit(selected);
        
        if (upvotes == null) {
            return 0;
        }
        else {
            return upvotes.size();
        }
    }

    /**
     * @return the recruitedYear
     */
    public String getRecruitedYear() {
        return recruitedYear;
    }

    /**
     * @param recruitedYear the new recruitedYear to be set
     */
    public void setRecruitedYear(String recruitedYear) {
        this.recruitedYear = recruitedYear;
    }
    
    /**
     * @return the selected field
     */
    public Recruit getSelected() {
        return selected;
    }

    /**
     * @param selected the new selected to be set
     */
    public void setSelected(Recruit selected) {
        this.selected = selected;
    }
    
    protected void initializeEmbeddableKey() {
    }
    
    /**
     *  Create new recruit with persist
     */
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RecruitCreated"));
        if (!JsfUtil.isValidationFailed()) {
            listOfRecruits = null;    // Invalidate list of items to trigger re-query.
        }
    }

    /**
     *  Update new recruit with persist
     */
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RecruitUpdated"));
    }

    /**
     *  Destroy a recruit entry with persist 
     * @return
     */
    public String destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RecruitDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            listOfRecruits = null;    // Invalidate list of items to trigger re-query.
        }
        return "RecruitBook?faces-redirect=true"; 
    }
    
    /**
     * @param id the ID of the recruit
     * @return a recruit object matching the ID
     */
    public Recruit getRecuit(java.lang.Integer id) {
        return recruitFacade.find(id);
    }

    /**
     * @return a list of recruit when selecting many
     */
    public List<Recruit> getItemsAvailableSelectMany() {
        return recruitFacade.findAll();
    }

    /**
     * @return a list of recruits when selecting one
     */
    public List<Recruit> getItemsAvailableSelectOne() {
        return recruitFacade.findAll();
    }
    
    /**
     * @return the currentRecruitID
     */
    public Recruit getCurrentRecruitID() {
        return currentRecruitID;
    }

    /**
     * @param currentRecruitID the currentRecruitID to be set
     */
    public void setCurrentRecruitID(Recruit currentRecruitID) {
        this.currentRecruitID = currentRecruitID;
    }
    
    /**
     * @return the first name of the recruit
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * @param firstName the firstName to be set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    /**
     * @return the lastName of the recruit
     */
    public String getLastName() {
        return lastName;
    }
    
    /**
     * @param lastName the lastName to be set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    /**
     * @return the school
     */
    public String getSchool() {
        return school;
    }
    
    /**
     * @param school the school to be set
     */
    public void setSchool(String school) {
        this.school = school;
    }
    
    /**
     * @return the address1
     */
    public String getAddress1() {
        return address1;
    }
    
    /**
     * @param address1 the address1 to be set
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }
    
    /**
     * @return the address2
     */
    public String getAddress2() {
        return address2;
    }
    
    /**
     * @param address2 the address2 to be set
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }
    
    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }
    
    /**
     * @param city the city to be set
     */
    public void setCity(String city) {
        this.city = city;
    }
    
    /**
     * @return the state
     */
    public String getState() {
        return state;
    }
    
    /**
     *
     * @param state the state to be set
     */
    public void setState(String state) {
        this.state = state;
    }
    
    /**
     *
     * @return the zipcode
     */
    public int getZipcode() {
        return zipcode;
    }
    
    /**
     *
     * @param zipcode the zipcode to be set
     */
    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }
    
    /**
     *
     * @return the year of the current recruit
     */
    public String getYear() {
        return year;
    }
    
    /**
     *
     * @param year the year to be set
     */
    public void setYear(String year) {
        this.year =year;
    }
    
    /**
     *
     * @return the GPA of the current recruit
     */
    public float getGpa() {
        return gpa;
    }
    
    /**
     *
     * @param gpa the gpa to be set
     */
    public void setGpa(float gpa) {
        this.gpa = gpa;
    }
        
    /**
     *
     * @return the email of the current recruit
     */
    public String getEmail() {
        return email;
    }
    
    /**
     *
     * @param email the email to be set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     *
     * @return the phone number of the current recruit
     */
    public String getPhone() {
        return phone;
    }
    
    /**
     *
     * @param phone the phone number to be set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    /**
     *
     * @return the skill level
     */
    public int getSkillLevel() {
        return skillLevel;
    }
    
    /**
     *
     * @param skillLevel the skill level to be set
     */
    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }
    
    /**
     *
     * @return the position
     */
    public String getPosition() {
        return position;
    }
    
    /**
     *
     * @param position the position to be set
     */
    public void setPosition(String position) {
        this.position = position;
    }
    
    /**
     *
     * @return the secondary position
     */
    public String getSecondaryPosition() {
        return secondaryPosition;
    }
    
    /**
     *
     * @param secondaryPosition the secondary position to be set
     */
    public void setSecondaryPosition(String secondaryPosition) {
        this.secondaryPosition = secondaryPosition;
    }
    
    /**
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }
    
    /**
     *
     * @param height the height to be set
     */
    public void setHeight(int height) {
        this.height = height;
    }
    
    /**
     *
     * @return the weight
     */
    public int getWeight() {
        return weight;
    }
    
    /**
     *
     * @param weight the weight to be set
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }
    
    /**
     *
     * @return the commitment
     */
    public String getCommitment() {
        return commitment;
    }
    
    /**
     *
     * @param commitment the commitment to be set
     */
    public void setCommitment(String commitment) {
        this.commitment = commitment;
    }
    
    /**
     *
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }
    
    /**
     *
     * @param notes the notes to be set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    /**
     *
     * @return the current statusMessage
     */
    public String getStatusMessage() {
        return statusMessage;
    }
    
    /**
     *
     * @return the array of states
     */
    public String[] getListOfStates() {
        return listOfStates;
    }
    
    /**
     *
     * @return the array of school year
     */
    public String[] getListOfSchoolYear() {
        return listOfSchoolYear;
    }
    
    /**
     *
     * @return the array of skill levels
     */
    public int[] getListOfSkillLevels() {
        return listOfSkillLevels;
    }
    
    /**
     *
     * @return the array of position names
     */
    public String[] getListOfPositions() {
        return listOfPositions;
    }
    
    /**
     *
     * @return the list of recruits
     */
    public List getListOfRecruits() {
        return listOfRecruits;
    }
    
    /**
     *
     */
    protected void setEmbeddableKeys() {
    }

    /**
     *
     * @return the centerGeocode
     */
    public String getCenterGeocode() {
        return centerGeocode;
    }

    /**
     *
     * @param centerGeocode the centerGeocode to be set
     */
    public void setCenterGeocode(String centerGeocode) {
        this.centerGeocode = centerGeocode;
    }

    /**
     *
     * @return the searchedRecruitName
     */
    public String getSearchedRecruitName() {
        return searchedRecruitName;
    }

    /**
     *
     * @param searchedRecruitName the name to be set
     */
    public void setSearchedRecruitName(String searchedRecruitName) {
        this.searchedRecruitName = searchedRecruitName;
    }

    /**
     *
     * @return the list of matchedRecruits
     */
    public List<Recruit> getMatchedRecruits() {
        return matchedRecruits;
    }

    /**
     *
     * @param matchedRecruits the list of matchedRecruits to be set
     */
    public void setMatchedRecruits(List<Recruit> matchedRecruits) {
        this.matchedRecruits = matchedRecruits;
    }

    /**
     *
     * @return the weather
     */
    public String getWeather() {
        return weather;
    }

    /**
     *
     * @param weather the weather to be set
     */
    public void setWeather(String weather) {
        this.weather = weather;
    }

    /**
     *  Check if the current user has liked or unliked the selected recruit
     * 
     * @return the like or unlike depending on what the user has selected
     */
    public String getLike() {
        int user_id = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
        User currentUser = userFacade.find(user_id);
        if (upvoteFacade.findUpVoteByUserRecruit(currentUser, selected) != null)
        {
            like = "Unlike";
        }
        else {
            like = "Like";
        }
        
        return like;
    }

    /**
     *  
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @param text the text to be set for comments
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     *  Find the list of all comments for one recruit
     * @return the list of comments
     */
    public List<Comment> getComments() {
        // send query to get a list of comments for selected recruit
        return commentFacade.getAllCommentsForRecruit(selected.getId());
    }
    
    /**
     * Creates a new recruit object and enter it into the Recruit table
     * 
     * @return a link to the appropriate page depending if creation succeeded
     * @throws UnsupportedEncodingException
     */
    public String createRecruit() throws UnsupportedEncodingException {
        // Gets an instance of the current user
        int user_id = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
        User user = userFacade.find(user_id);
        
        // Get the group the current user belongs to
        commitment = user.getSchool();
        
        //Clear the statusMessage
        statusMessage = "";
        
        // Attempt to create a new recruit if there is no error
        if (statusMessage.isEmpty()) {
            try {
                
                // Create a new recruit Object with user specified fields
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
                recruit.setRecruitedYear(recruitedYear);
                recruit.setSkillLevel(skillLevel);
                recruit.setGpa(gpa);
                recruit.setCommitment(commitment);
                recruit.setHeight(height);
                recruit.setWeight(weight);
                recruit.setYear(year);
                recruit.setNotes(notes);
                
                // Create the recruit entry in the table
                recruitFacade.create(recruit); 
                // Set the selected recruit to this recruit
                selected = recruit;
            } catch (EJBException e) {
                statusMessage = "Something went wrong while creating the recruit!";
                return "";
            }   
            
            try {
                // Grabs the current time of host machine and format it 
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                
                // Create a new event and add a created new recruit descriptor, Sets the time as well
                Event event = new Event();
                event.setDescription("created a new recruit, " + selected.getFirstName() + " " + selected.getLastName() + " on " +date);
                event.setRecruitId(selected);
                event.setUserId(user);
                event.setGroupId(groupUserFacade.selectGroupFromUser(user).getGroupId());
                event.setType(0);
                
                // Create the event in the database
                eventFacade.create(event);               
            } catch (EJBException e) {
                statusMessage = "Something went wrong while creating the event!";
                return getListOfRecruitsByCommitment();
            }
           // Send an email notifying that a new recruit is created
            sendEmail();
            // reset all fields
            reset();
            // Update the list of recruits and return user to recruitBook page
            return getListOfRecruitsByCommitment();
        }
        return "";
    }
    
    /**
     * Reset method to clear all fields
     */
    public void reset()
    {
        firstName = lastName = email = phone = school = city = state = address1 = 
                    position = secondaryPosition = address2 = notes = year = "";
            
            commitment = "";
            
            zipcode = skillLevel = height = weight = 0;
            gpa = 0;
            recruitedYear = "";
    }
    
    /**
     *
     * @param id the ID of the recruit
     * @return  the string containing the path to the recruit's photo
     */
    public String recruitPhoto(int id) {
        List<RecruitPhoto> photoList = recruitPhotoFacade.findPhotosByRecruitID(id);
        if (photoList.isEmpty()) {
            return "defaultUserPhoto.png";
        }
        return photoList.get(0).getThumbnailName();
    }
    
    /**
     * Updates the current list of recruits and redirect the page
     * @return a link redirecting the page to recruitBook
     */
    public String getListOfRecruitsByCommitment()
    {
        // Gets current user's information
        int user_id = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
        User user = userFacade.find(user_id);
    
        // Get the group of the current user
        commitment = user.getSchool();
        // Update the list of recruits for current group
        listOfRecruits = recruitFacade.findRecruitsByCommitment(commitment);
        return "RecruitBook?faces-redirect=true";
    }
    
    /**
     *
     * @return a redirect link to view current selected recruit
     */
    public String viewRecruit()
    {
        text = "";
        return "RecruitProfile?faces-redirect=true";
    }
    
    /**
     *
     * @return a redirect link to adding recruit
     */
    public String addRecruit()
    {
        return "CreateRecruit?faces-redirect=true";
    }
    
     

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    recruitFacade.edit(selected);
                } else {
                    recruitFacade.remove(selected);
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
    
    /**
     *  Sends an email from wicruit@gmail.com to current user using javamail
     * @throws UnsupportedEncodingException
     */
    public void sendEmail() throws UnsupportedEncodingException
    {
        // Create a new mail with properties set
        int user_id = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
        User currentUser = userFacade.find(user_id);
        String body = message(currentUser);
        String host = "smtp.gmail.com";
        String user = "wicruit@gmail.com";
        String pass = "testaccforwicruit";
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", user);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props, null);
        
        // Attempt to send mail using javamail
        try {
                Message msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress("wicruit@gmail.com", "Wicruit"));
                msg.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(currentUser.getEmail(), currentUser.getFirstName()));
                msg.setSubject("A new Recruit was added!!");
                msg.setText(body);
                Transport.send(msg, user, pass);

            } catch (AddressException e) {
                // ...
            } catch (MessagingException e) {
                // ...
            }
    }
    
    /**
     *  Creates an automated message for email use
     * @param user the user logged in
     * @return a string formatted for email body
     */
    public String message(User user)
    {
        return user.getFirstName()+" "+user.getLastName()+ " just added a new Recruit to the Recruit Book!";              
    }
    
    /**
     *
     * @param actionEvent
     */
    public void buttonAction(ActionEvent actionEvent) {
        addMessage(getFirstName() + getLastName() + "has been added to the Recruit Book");
    }
     
    /**
     *
     * @param summary
     */
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    /**
     *
     * @return the position of current selected recruit
     */
    public String getSelectedPosition() {
        return selected.getPosition();
    }
    
    /**
     * Method created for ajax inline edit
     * @param position the position to be set for current recruit
     */
    public void setSelectedPosition(String position) {
        selected.setPosition(position);
        recruitFacade.edit(selected);
    }
    
    /**
     *  
     * @return the secondary position of the selected recruit
     */
    public String getSelectedSecondaryPosition() {
        return selected.getSecondaryPosition();
    }
    
    /**
     *Method created for ajax inline edit
     * @param secondaryPosition
     */
    public void setSelectedSecondaryPosition(String secondaryPosition) {
        selected.setSecondaryPosition(secondaryPosition);
        recruitFacade.edit(selected);
    }
    
    /**
     *
     * @return the height of the selected recruit
     */
    public int getSelectedHeight() {
        return selected.getHeight();
    }
    
    /**
     *  Method for ajax inline edit
     * @param height the height to be set
     */
    public void setSelectedHeight(int height) {
        selected.setHeight(height);
        recruitFacade.edit(selected);
    }
    
    /**
     *
     * @return the weight of the selected recruit
     */
    public int getSelectedWeight() {
        return selected.getWeight();
    }
    
    /**
     * Method created for ajax inline edit
     * @param weight the weight to be set
     */
    public void setSelectedWeight(int weight) {
        selected.setWeight(weight);
        recruitFacade.edit(selected);
    }
    
    /**
     *
     * @return the school of the selected recruit
     */
    public String getSelectedSchool() {
        return selected.getSchool();
    }
    
    /**
     *Method created for ajax inline edit
     * @param school the school to be set
     */
    public void setSelectedSchool(String school) {
        selected.setSchool(school);
        recruitFacade.edit(selected);
    }
    
    /**
     *
     * @return the gpa of the selected recruit
     */
    public float getSelectedGpa(){
        return selected.getGpa();
    }
    
    /**
     *Method created for ajax inline edit
     * @param gpa the gpa to be set
     */
    public void setSelectedGpa(float gpa) {
        selected.setGpa(gpa);
        recruitFacade.edit(selected);
    }
    
    /**
     *
     * @return the email of the selected recruit
     */
    public String getSelectedEmail() {
        return selected.getEmail();
    }
    
    /**
     *Method created for ajax inline edit
     * @param email the email to be set
     */
    public void setSelectedEmail(String email) {
        selected.setEmail(email);
        recruitFacade.edit(selected);
    }
    
    /**
     *
     * @return the notes of the selected recruit
     */
    public String getSelectedNotes() {
        return selected.getNotes();
    }
    
    /**
     *Method created for ajax inline edit
     * @param notes the notes to be set
     */
    public void setSelectedNotes(String notes) {
        if (notes.trim().isEmpty()) {
            notes = "               ";
        }
        
        selected.setNotes(notes);
        recruitFacade.edit(selected);
    }
    
    /**
     *
     * @return the address1 of the selected recruit
     */
    public String getSelectedAddress1() {
        return selected.getAddress1();
    }
    
    /**
     *Method created for ajax inline edit
     * @param address1 the address 1 to be set
     */
    public void setSelectedAddress1(String address1) {
        selected.setAddress1(address1);
        recruitFacade.edit(selected);
    }
    
    /**
     *
     * @return the address2 of the selected recruit
     */
    public String getSelectedAddress2() {
        return selected.getAddress2();
    }
    
    /**
     *Method created for ajax inline edit
     * @param address2 the address2 to be set
     */
    public void setSelectedAddress2(String address2) {
        if (address2.trim().isEmpty()) {
            address2 = "               ";
        }
        
        selected.setAddress2(address2);
        recruitFacade.edit(selected);
    }
    
    /**
     *
     * @return the city of the selected recruit
     */
    public String getSelectedCity() {
        return selected.getCity();
    }
    
    /**
     *Method created for ajax inline edit
     * @param city the city to be set
     */
    public void setSelectedCity(String city) {
        selected.setCity(city);
        recruitFacade.edit(selected);
    }
    
    /**
     *
     * @return the state of the selected recruit
     */
    public String getSelectedState() {
        return selected.getState();
    }
    
    /**
     *Method created for ajax inline edit
     * @param state the state to be set
     */
    public void setSelectedState(String state) {
        selected.setState(state);
        recruitFacade.edit(selected);
    }
    
    /**
     *
     * @return the zipcode of the selected recruit
     */
    public int getSelectedZipcode() {
        return selected.getZipcode();
    }
    
    /**
     *Method created for ajax inline edit
     * @param zipcode the zipcode to be set
     */
    public void setSelectedZipcode(int zipcode) {
        selected.setZipcode(zipcode);
        recruitFacade.edit(selected);
    }
    
    /**
     *  Update gmap and weather information
     */
    public void onLoad() {
        geocode();
        loadWeather();
    }
    
    /**
     *  Calls a geocoding javascript function
     */
    public void geocode() {
        RequestContext.getCurrentInstance().execute("geocode()");
    }
    
    /**
     *  Set a location based on first entry of geocode result
     * @param event the event created by calling geocoding
     */
    public void onGeocode(GeocodeEvent event) {
        List<GeocodeResult> results = event.getResults();
         
        if (results != null && !results.isEmpty()) {
            LatLng center = results.get(0).getLatLng();
            centerGeocode = center.getLat() + "," + center.getLng();
        }
    }
    
    /**
     *  Find a list of recruit objects whose name matches searchedRecruitName and group matches current user's group
     */
    public void searchedRecruits() {
        int user_id = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
        User currentUser = userFacade.find(user_id);
        
        matchedRecruits = recruitFacade.searchRecruitByName(searchedRecruitName, currentUser.getSchool());
    }
    
    /**
     * Handler for when a recruit is selected from search
     */
    public void onRecruitRowSelect() {
        text = "";
        
        ConfigurableNavigationHandler configurableNavigationHandler
                = (ConfigurableNavigationHandler) FacesContext.
                getCurrentInstance().getApplication().getNavigationHandler();

        configurableNavigationHandler.performNavigation("RecruitProfile?faces-redirect=true");

        searchedRecruitName = "";
        matchedRecruits.clear();
    }
    
    /**
     * Method to call weather update method in javascript
     */
    public void loadWeather() {
        RequestContext.getCurrentInstance().execute("loadWeather()");
    }
    
    /**
     *  Handles likes and unlike interaction when button is clicked on a profile
     */
    public void likeUnlike()
    {
        // Get the current user information
        int user_id = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
        User currentUser = userFacade.find(user_id);
        //Attempt to find if current user has already voted on selected recruit
        if (upvoteFacade.findUpVoteByUserRecruit(currentUser, selected) != null)
        {
            //Remove the entry from the database
            upvoteFacade.remove(upvoteFacade.findUpVoteByUserRecruit(currentUser, selected));
            
            //Create a new event recording the user unliking the recruit
            try {
                
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                
                //Create a new event recording user unliking the recruit with time
                Event event = new Event();
                event.setDescription("unliked recruit " + selected.getFirstName() + " " + selected.getLastName() + " on " +date);
                event.setRecruitId(selected);
                event.setUserId(currentUser);
                event.setGroupId(groupUserFacade.selectGroupFromUser(currentUser).getGroupId());
                event.setType(0);
                
                eventFacade.create(event);               
            } catch (EJBException e) {
                statusMessage = "Something went wrong while creating the event!";
            }
            // Update the button text
            like = "Like";
        }
        else
        {
            // Create a new upvote entry to record user liking the recruit
            try {
                Upvote upvote = new Upvote();
                upvote.setRecruitId(selected);
                upvote.setUserId(currentUser);
                
                upvoteFacade.create(upvote);  
                
                like = "Unlike";
            } catch (EJBException e) {
                statusMessage = "Something went wrong while creating the Upvote!";
            }
            
            //Create a new event recording the user liking the recruit
            try {
                
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                
                //Create a new event recording user liking the recruit with time
                Event event = new Event();
                event.setDescription("liked recruit " + selected.getFirstName() + " " + selected.getLastName() + " on " +date);
                event.setRecruitId(selected);
                event.setUserId(currentUser);
                event.setGroupId(groupUserFacade.selectGroupFromUser(currentUser).getGroupId());
                event.setType(0);
                
                eventFacade.create(event);               
            } catch (EJBException e) {
                statusMessage = "Something went wrong while creating the event!";
            }
        }
    }
    
    /**
     * Creates a new comment entry in the database when the user presses submit button
     */
    public void submitComment() {
        String cleanedText = text.replace("&nbsp;","");
        cleanedText = cleanedText.replace("<div>","");
        cleanedText = cleanedText.replace("<br>","");
        cleanedText = cleanedText.replace("</div>","");
        cleanedText = cleanedText.replace("<span class=\"Apple-tab-span\" style=\"white-space:pre\">	</span>", "");
        if (!cleanedText.trim().isEmpty()) {
            int user_id = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
            User currentUser = userFacade.find(user_id);

            Comment comment = new Comment();
            comment.setCommentText(text);
            comment.setRecruitId(selected);
            comment.setUserId(currentUser);
            
            commentFacade.create(comment);
            
            // Create a new event recording that this user has commented on selected recruit
            try {
                
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                
                Event event = new Event();
                event.setDescription("commented on recruit " + selected.getFirstName() + " " + selected.getLastName() + " on " +date);
                event.setRecruitId(selected);
                event.setUserId(currentUser);
                event.setGroupId(groupUserFacade.selectGroupFromUser(currentUser).getGroupId());
                event.setType(0);
                
                eventFacade.create(event);               
            } catch (EJBException e) {
                statusMessage = "Something went wrong while creating the event!";
            }
            
            text = "";
        }
    }
}
