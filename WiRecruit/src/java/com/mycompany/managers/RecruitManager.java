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
import com.mycompany.sessionbeanpackage.Group1Facade;
import com.mycompany.sessionbeanpackage.UpvoteFacade;
import com.mycompany.sessionbeanpackage.RecruitFacade;
import com.mycompany.sessionbeanpackage.EventFacade;
import com.mycompany.sessionbeanpackage.GroupUserFacade;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
    private String year;
    private float gpa;
    private String email;
    private String recruitedYear;
    private String phone;
    private int skillLevel;
    private String position;
    private String secondaryPosition;
    private int height;
    private int weight;
    private String commitment;
    private String notes;
    private Recruit currentRecruitID;
    private Recruit selected;
    private MapModel geoModel;
    private List<String> listOfEmails = null;
    private String userSchool;
    private String centerGeocode = "40, 40";
    private String searchedRecruitName;
    private String weather;
    private int selectedNumLikes = 0;
    private String like = "Like";
    private String statusMessage = "";
    private String text;
    
    private final String[] listOfSchoolYear = Constants.SCHOOLYEAR;
    private final String[] listOfStates = Constants.STATES;
    private final int[] listOfSkillLevels = Constants.SKILL_LEVELS;
    private final String[] listOfPositions = Constants.POSITIONS;
    
    private List<Recruit> listOfRecruits = null;
    private List<Recruit> matchedRecruits = new ArrayList();
    private List<Comment> comments = new ArrayList();
    
    @EJB
    private com.mycompany.sessionbeanpackage.RecruitFacade ejbFacade;
    @EJB
    private RecruitFacade recruitFacade;
    @EJB
    private EventFacade eventFacade;
    @EJB
    private RecruitPhotoFacade recruitPhotoFacade;
    @EJB
    private UserFacade userFacade;
    @EJB
    private Group1Facade groupFacade;
    @EJB
    private GroupUserFacade groupUserFacade;
    @EJB
    private CommentFacade commentFacade;
    
    @EJB
    private UpvoteFacade upvoteFacade;
    
    public RecruitManager()
    {
        
    }
    
    @PostConstruct
    public void init() {
        geoModel = new DefaultMapModel();
    }
    
    public List<Recruit> getItems() {
        int user_id = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
        User user = userFacade.find(user_id);
        commitment = user.getSchool();
        
        if (listOfRecruits == null) {
            listOfRecruits = getFacade().findRecruitsByCommitment(commitment);
        }
        return listOfRecruits;
    }

    public MapModel getGeoModel() {
        return geoModel;
    }

    public void setGeoModel(MapModel geoModel) {
        this.geoModel = geoModel;
    }

    public int getSelectedNumLikes() {
        return selectedNumLikes;
    }

    public void setSelectedNumLikes(int selectedNumLikes) {
        this.selectedNumLikes = selectedNumLikes;
    }

    public String getRecruitedYear() {
        return recruitedYear;
    }

    public void setRecruitedYear(String recruitedYear) {
        this.recruitedYear = recruitedYear;
    }
    
    public Recruit getSelected() {
        return selected;
    }

    public void setSelected(Recruit selected) {
        this.selected = selected;
    }
    
    protected void initializeEmbeddableKey() {
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

    public String destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RecruitDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            listOfRecruits = null;    // Invalidate list of items to trigger re-query.
        }
        return "RecruitBook?faces-redirect=true"; 
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
    
    public String getYear() {
        return year;
    }
    
    public void setYear(String year) {
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
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
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
    
    public String[] getListOfSchoolYear() {
        return listOfSchoolYear;
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

    public String getCenterGeocode() {
        return centerGeocode;
    }

    public void setCenterGeocode(String centerGeocode) {
        this.centerGeocode = centerGeocode;
    }

    public String getSearchedRecruitName() {
        return searchedRecruitName;
    }

    public void setSearchedRecruitName(String searchedRecruitName) {
        this.searchedRecruitName = searchedRecruitName;
    }

    public List<Recruit> getMatchedRecruits() {
        return matchedRecruits;
    }

    public void setMatchedRecruits(List<Recruit> matchedRecruits) {
        this.matchedRecruits = matchedRecruits;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Comment> getComments() {
        return commentFacade.getAllCommentsForRecruit(selected.getId());
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    
    public String createRecruit() throws UnsupportedEncodingException {
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
                recruit.setRecruitedYear(recruitedYear);
                recruit.setSkillLevel(skillLevel);
                recruit.setGpa(gpa);
                recruit.setCommitment(commitment);
                recruit.setHeight(height);
                recruit.setWeight(weight);
                recruit.setYear(year);
                recruit.setNotes(notes);
                
                recruitFacade.create(recruit);   
                selected = recruit;
            } catch (EJBException e) {
                statusMessage = "Something went wrong while creating the recruit!";
                return "";
            }   
            
            try {
                
                Event event = new Event();
                event.setDescription("created a new recruit");
                event.setRecruitId(selected);
                event.setUserId(user);
                event.setGroupId(groupUserFacade.selectGroupFromUser(user).getGroupId());
                event.setType(0);
                
                eventFacade.create(event);               
            } catch (EJBException e) {
                statusMessage = "Something went wrong while creating the event!";
                return getListOfRecruitsByCommitment();
            }
           
            sendEmail();
            
            reset();
                        
            return getListOfRecruitsByCommitment();
        }
        return "";
    }
    
    public void reset()
    {
        firstName = lastName = email = phone = school = city = state = address1 = 
                    position = secondaryPosition = address2 = notes = year = "";
            
            commitment = "";
            
            zipcode = skillLevel = height = weight = 0;
            gpa = 0;
            recruitedYear = "";
    }
    
    public String deleteRecruit() {
        return "";
    }
    
    public String recruitPhoto(int id) {
        List<RecruitPhoto> photoList = recruitPhotoFacade.findPhotosByRecruitID(id);
        if (photoList.isEmpty()) {
            return "defaultUserPhoto.png";
        }
        return photoList.get(0).getThumbnailName();
        //return "";
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
    
    public void sendEmail() throws UnsupportedEncodingException
    {
        int user_id = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
        User currentUser = userFacade.find(user_id);
        userSchool = currentUser.getState();
        listOfEmails = userFacade.findUserByUniversity(userSchool);
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
    
    public String message(User user)
    {
        return user.getFirstName()+" "+user.getLastName()+ " just added a new Recruit to the Recruit Book!";              
    }
    
    
    private boolean skip;
     
    public boolean isSkip() {
        return skip;
    }
 
    public void setSkip(boolean skip) {
        this.skip = skip;
    }
     
    public String onFlowProcess(FlowEvent event) {
        if(skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        }
        else {
            return event.getNewStep();
        }
    }
    
    public void buttonAction(ActionEvent actionEvent) {
        addMessage(getFirstName() + getLastName() + "has been added to the Recruit Book");
    }
     
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public String getSelectedPosition() {
        return selected.getPosition();
    }
    
    public void setSelectedPosition(String position) {
        selected.setPosition(position);
        recruitFacade.edit(selected);
    }
    
    public String getSelectedSecondaryPosition() {
        return selected.getSecondaryPosition();
    }
    
    public void setSelectedSecondaryPosition(String secondaryPosition) {
        selected.setSecondaryPosition(secondaryPosition);
        recruitFacade.edit(selected);
    }
    
    public int getSelectedHeight() {
        return selected.getHeight();
    }
    
    public void setSelectedHeight(int height) {
        selected.setHeight(height);
        recruitFacade.edit(selected);
    }
    
    public int getSelectedWeight() {
        return selected.getWeight();
    }
    
    public void setSelectedWeight(int weight) {
        selected.setWeight(weight);
        recruitFacade.edit(selected);
    }
    
    public String getSelectedSchool() {
        return selected.getSchool();
    }
    
    public void setSelectedSchool(String school) {
        selected.setSchool(school);
        recruitFacade.edit(selected);
    }
    
    public float getSelectedGpa(){
        return selected.getGpa();
    }
    
    public void setSelectedGpa(float gpa) {
        selected.setGpa(gpa);
        recruitFacade.edit(selected);
    }
    
    public String getSelectedEmail() {
        return selected.getEmail();
    }
    
    public void setSelectedEmail(String email) {
        selected.setEmail(email);
        recruitFacade.edit(selected);
    }
    
    public String getSelectedNotes() {
        return selected.getNotes();
    }
    
    public void setSelectedNotes(String notes) {
        if (notes.trim().isEmpty()) {
            notes = "               ";
        }
        
        selected.setNotes(notes);
        recruitFacade.edit(selected);
    }
    
    public String getSelectedAddress1() {
        return selected.getAddress1();
    }
    
    public void setSelectedAddress1(String address1) {
        selected.setAddress1(address1);
        recruitFacade.edit(selected);
    }
    
    public String getSelectedAddress2() {
        return selected.getAddress2();
    }
    
    public void setSelectedAddress2(String address2) {
        if (address2.trim().isEmpty()) {
            address2 = "               ";
        }
        
        selected.setAddress2(address2);
        recruitFacade.edit(selected);
    }
    
    public String getSelectedCity() {
        return selected.getCity();
    }
    
    public void setSelectedCity(String city) {
        selected.setCity(city);
        recruitFacade.edit(selected);
    }
    
    public String getSelectedState() {
        return selected.getState();
    }
    
    public void setSelectedState(String state) {
        selected.setState(state);
        recruitFacade.edit(selected);
    }
    
    public int getSelectedZipcode() {
        return selected.getZipcode();
    }
    
    public void setSelectedZipcode(int zipcode) {
        selected.setZipcode(zipcode);
        recruitFacade.edit(selected);
    }
    
    public void onLoad() {
        geocode();
        loadWeather();
    }
    
    public void geocode() {
        RequestContext.getCurrentInstance().execute("geocode()");
    }
    
    public void onGeocode(GeocodeEvent event) {
        List<GeocodeResult> results = event.getResults();
         
        if (results != null && !results.isEmpty()) {
            LatLng center = results.get(0).getLatLng();
            centerGeocode = center.getLat() + "," + center.getLng();
        }
    }
    
    public void searchedRecruits() {
        matchedRecruits = recruitFacade.searchRecruitByName(searchedRecruitName);
    }
    
    public void onRecruitRowSelect() {
        //System.out.println(selectedGroup);
        ConfigurableNavigationHandler configurableNavigationHandler
                = (ConfigurableNavigationHandler) FacesContext.
                getCurrentInstance().getApplication().getNavigationHandler();

        configurableNavigationHandler.performNavigation("RecruitProfile?faces-redirect=true");

        searchedRecruitName = "";
        matchedRecruits.clear();
    }
    
    public void loadWeather() {
        RequestContext.getCurrentInstance().execute("loadWeather()");
    }
    
    public void likeUnlike()
    {
        int user_id = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
        User currentUser = userFacade.find(user_id);
        if (upvoteFacade.findUpVoteByUserRecruit(currentUser, selected) != null)
        {
            upvoteFacade.remove(upvoteFacade.findUpVoteByUserRecruit(currentUser, selected));
            //return "Dashboard";
            if (upvoteFacade.searchUpvoteByRecruit(selected) != null)
                setSelectedNumLikes(upvoteFacade.searchUpvoteByRecruit(selected).size());
            else
                setSelectedNumLikes(0);
            
            like = "Like";
        }
        else
        {
            try {
                Upvote upvote = new Upvote();
                upvote.setRecruitId(selected);
                upvote.setUserId(currentUser);
                
                upvoteFacade.create(upvote);  
                
                setSelectedNumLikes(upvoteFacade.searchUpvoteByRecruit(selected).size());
                
                like = "Unlike";
            } catch (EJBException e) {
                statusMessage = "Something went wrong while creating the Upvote!";
            }
        }
    }
    
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

            text = "";
        }
    }
}
