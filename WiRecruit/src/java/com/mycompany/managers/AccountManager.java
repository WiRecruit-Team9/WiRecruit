/*
 * Created by Julio Suriano Siu on 2016.04.10  * 
 * Copyright © 2016 Julio Suriano Siu. All rights reserved. * 
 */
package com.mycompany.managers;

import com.mycompany.entitypackage.Event;
import com.mycompany.entitypackage.UserPhoto;
import com.mycompany.entitypackage.User;
import com.mycompany.sessionbeanpackage.UserPhotoFacade;
import com.mycompany.sessionbeanpackage.UserFacade;
import com.mycompany.sessionbeanpackage.EventFacade;
import com.mycompany.sessionbeanpackage.GroupUserFacade;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import javax.faces.application.FacesMessage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.primefaces.event.FlowEvent;
 
@Named(value = "accountManager")
@SessionScoped
/**
 *
 * @author jsuriano
 */
public class AccountManager implements Serializable {
    
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String title;
    private String school;
    private String city;
    private String state;
    private int zipcode;
    private int security_question;
    private String security_answer;
    private String statusMessage = "";
    private final String[] listOfStates = Constants.STATES;
    private Map<String, Object> security_questions;
    private final String[] listOfSchools = Constants.SCHOOLS;
    private final String[] listOfTitles = Constants.TITLES;
    private User selected;
    private List<User> listOfStaff = null;   
    private static ArrayList<String> feed = new ArrayList<String>();
    private List<Event> eventFeed = null;
    
    @EJB
    private UserFacade userFacade;
    
    @EJB
    private UserPhotoFacade userPhotoFacade;
    
    @EJB
    private EventFacade eventFacade;
    
    @EJB
    private GroupUserFacade groupUserFacade;
    
    public AccountManager() {
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
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getSchool() {
        return school;
    }
    
    public void setSchool(String school) {
        this.school = school;
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
    
    public int getSecurity_question() {
        return security_question;
    }

    public void setSecurity_question(int security_question) {
        this.security_question = security_question;
    }
    
    public String getSecurity_answer() {
        return security_answer;
    }
    
    public void setSecurity_answer(String security_answer) {
        this.security_answer = security_answer;
    }
    
    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
    
    public String[] getListOfStates() {
        return listOfStates;
    }
    
    public String[] getListOfTitles() {
        return listOfTitles;
    }

    public List<Event> getEventFeed() {
        return eventFeed;
    }

    public void setEventFeed(List<Event> eventFeed) {
        this.eventFeed = eventFeed;
    }
    
    public Map<String, Object> getSecurity_questions() {
        if (security_questions == null) {
            security_questions = new LinkedHashMap<>();
            for (int i = 0; i < Constants.QUESTIONS.length; i++) {
                security_questions.put(Constants.QUESTIONS[i], i);
            }
        }
        return security_questions;
    }

    public List<User> getListOfStaff() {
        return listOfStaff;
    }

    public void setListOfStaff(List<User> listOfStaff) {
        this.listOfStaff = listOfStaff;
    }

    
    
    public String[] getListOfSchools() {
        return listOfSchools;
    }  
    
    public User getSelected() {
        if (selected == null) {
            selected = userFacade.find(FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("user_id"));
        }
        return selected;
    }

    public void setSelected(User selected) {
        this.selected = selected;
    }
    
    public String createAccount() throws UnsupportedEncodingException {
        
        // Check to see if a user already exists with the username given.
        User aUser = userFacade.findByUsername(username);
        
        if (aUser != null) {
            username = "";
            statusMessage = "Username already exists! Please select a different one!";
            return "";
        }

        if (statusMessage.isEmpty()) {
            try {
                User user = new User();
                user.setFirstName(firstName);
                user.setLastName(lastName);  
                user.setEmail(email);
                user.setUsername(username);                
                user.setPassword(password);
                user.setTitle(title);
                user.setSchool(school);
                user.setCity(city);
                user.setState(state);
                user.setZipcode(zipcode);
                user.setSecurityQuestion(security_question);
                user.setSecurityAnswer(security_answer);
                
                userFacade.create(user); 
                
            } catch (EJBException e) {
                statusMessage = "Something went wrong while creating your account!";
                return "";
            }
            initializeSessionMap();
            sendEmail();
            save();
            return "StaffProfile?faces-redirect=true";
        }
        return "";
    }
    
    public String updateAccount() throws UnsupportedEncodingException {
        if (statusMessage.isEmpty()) {
            int user_id = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
            User editUser = userFacade.getUser(user_id);
            try {
                editUser.setFirstName(this.selected.getFirstName());
                editUser.setLastName(this.selected.getLastName());
                editUser.setEmail(this.selected.getEmail());
                editUser.setPassword(this.selected.getPassword());
                editUser.setTitle(this.selected.getTitle());
                editUser.setCity(this.selected.getCity());
                editUser.setState(this.selected.getState());
                editUser.setZipcode(this.selected.getZipcode());               
                
                userFacade.edit(editUser);
            } catch (EJBException e) {
                username = "";
                statusMessage = "Something went wrong while editing your profile!";
                return "";
            }
            return "StaffProfile?faces-redirect=true";
        }
        return "";
    }
    
    public String deleteAccount() {
        if (statusMessage.isEmpty()) {
            int user_id = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
            try {
                userFacade.deleteUser(user_id);
                                
            } catch (EJBException e) {
                username = "";
                statusMessage = "Something went wrong while deleting your account!";
                return "";
            }
            
            return "/index.xhtml?faces-redirect=true";
        }
        return "";
    }
    
    public void validateInformation(ComponentSystemEvent event) {
        FacesContext fc = FacesContext.getCurrentInstance();

        UIComponent components = event.getComponent();
        // Get password
        UIInput uiInputPassword = (UIInput) components.findComponent("password");
        String pwd = uiInputPassword.getLocalValue() == null ? ""
                : uiInputPassword.getLocalValue().toString();

        // Get confirm password
        UIInput uiInputConfirmPassword = (UIInput) components.findComponent("confirmPassword");
        String confirmPassword = uiInputConfirmPassword.getLocalValue() == null ? ""
                : uiInputConfirmPassword.getLocalValue().toString();

        if (pwd.isEmpty() || confirmPassword.isEmpty()) {
            // Do not take any action. 
            // The required="true" in the XHTML file will catch this and produce an error message.
            return;
        }

        if (!pwd.equals(confirmPassword)) {
            statusMessage = "Passwords must match!";
        } else {
            statusMessage = "";
        }   
    }
    
    public void initializeSessionMap() {
        User user = userFacade.findByUsername(getUsername());
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("username", username);
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("user_id", user.getId());
    }

    private boolean correctPasswordEntered(UIComponent components) {
        UIInput uiInputVerifyPassword = (UIInput) components.findComponent("verifyPassword");
        String verifyPassword = uiInputVerifyPassword.getLocalValue() == null ? ""
                : uiInputVerifyPassword.getLocalValue().toString();
        if (verifyPassword.isEmpty()) {
            statusMessage = "";
            return false;
        } else {
            if (verifyPassword.equals(password)) {
                return true;
            } else {
                statusMessage = "Invalid password entered!";
                return false;
            }
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        firstName = lastName = email = username = password = statusMessage = "";
        title = school = city = state = security_answer = "";
        zipcode = security_question = 0;
        
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true";
    }
     
    public String userPhoto() {
        String user_name = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("username");
        User user = userFacade.findByUsername(user_name);
        List<UserPhoto> photoList = userPhotoFacade.findPhotosByUserID(user.getId());
        if (photoList.isEmpty()) {
            return "defaultUserPhoto.png";
        }
        return photoList.get(0).getThumbnailName();
    }
    
    public String findPhoto(String userNamePhoto) {
        User user = userFacade.findByUsername(userNamePhoto);
        List<UserPhoto> photoList = userPhotoFacade.findPhotosByUserID(user.getId());
        if (photoList.isEmpty()) {
            return "defaultUserPhoto.png";
        }
        return photoList.get(0).getThumbnailName();
    }
    
    public String getListOfStaffByUniversity()
    {
        int user_id = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
        User user = userFacade.find(user_id);
    
        school = user.getSchool();
        listOfStaff = userFacade.findUserByUniversity(school);
        System.out.println();
        return "StaffBook";
    }

    public ArrayList<String> getFeed() {
        return feed;
    }

    public void setFeed(ArrayList<String> feed) {
        this.feed = feed;
    }
    
    public static void appendFeed(String newFeed)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        
        //feed.add(0, newFeed + " at " + dateFormat.format(date));
        System.out.println("Work work work work work");
    }
    
    public String updateFeed() {
        int user_id = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
        User user = userFacade.find(user_id);
        
        eventFeed = eventFacade.findEventsByGroup(groupUserFacade.selectGroupFromUser(user).getGroupId());
        
        return "Dashboard?faces-redirect=true";
    }
    
    public void sendEmail() throws UnsupportedEncodingException
    {
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
        
        try {
                Message msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress("wicruit@gmail.com", "Wicruit"));
                msg.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(currentUser.getEmail(), currentUser.getFirstName()));
                msg.setSubject("Welcome To Wicruit!");
                msg.setText(body);
                Transport.send(msg, user, pass);

            } catch (AddressException e) {
                // ...
            } catch (MessagingException e) {
                // ...
            }
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
    
    public void save() {  
        User user = new User();

        FacesMessage msg = new FacesMessage("Successful", "Welcome :" + user.getFirstName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public String message(User newUser)
    {
        return "Congratulations "+ newUser.getFirstName()+ "!\n\n"
                + "Your account have been successfully created!\n" 
                + "Your account name is: "+ newUser.getUsername() +"\n\n" 
                + "You can now have all the information about your recruits in one place. No more scrammbling around looking for information on your recruit or losing vital information \n\n"
                + "Thank you for choosing us, \n"
                + "Wicruit Support Team";               
    }
    
    public String cancel() {
//        firstName = "";
//        lastName = "";
//        email = "";
//        username = "";
//        password = "";
//        title = "";
//        school = "";
//        city = "";
//        state = "";
//        zipcode = 0;
//        security_question = 0;
//        security_answer = "";
//        statusMessage = "";
        
        return "index";
    }
}
