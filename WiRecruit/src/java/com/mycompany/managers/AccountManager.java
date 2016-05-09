/*
 * Created by Julio Suriano Siu on 2016.04.10  * 
 * Copyright © 2016 Julio Suriano Siu. All rights reserved. * 
 */
package com.mycompany.managers;

import com.mycompany.entitypackage.Event;
import com.mycompany.entitypackage.Group1;
import com.mycompany.entitypackage.GroupUser;
import com.mycompany.entitypackage.UserPhoto;
import com.mycompany.entitypackage.User;
import com.mycompany.sessionbeanpackage.UserPhotoFacade;
import com.mycompany.sessionbeanpackage.UserFacade;
import com.mycompany.sessionbeanpackage.EventFacade;
import com.mycompany.sessionbeanpackage.GroupUserFacade;
import com.mycompany.sessionbeanpackage.Group1Facade;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
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
import java.util.Properties;
import javax.faces.application.FacesMessage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

/**
 *
 * @author Peter Cho
 */
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
    private List<Event> eventFeed = null;

    private boolean groupExist = false;
    private String joinStatusMessage;
    private String joinPasscode;
    private String createGroupTitle;
    private int createGroupPasscode;
    private String currentGroupTitle;

    /**
     * The instance variable 'userFacade' is annotated with the @EJB annotation.
     * This means that the GlassFish application server, at runtime, will inject
     * in this instance variable a reference to the @Stateless session bean
     * UserFacade.
     */
    @EJB
    private UserFacade userFacade;

    /**
     * The instance variable 'userPhotoFacade' is annotated with the @EJB annotation.
     * This means that the GlassFish application server, at runtime, will inject
     * in this instance variable a reference to the @Stateless session bean
     * UserPhotoFacade.
     */
    @EJB
    private UserPhotoFacade userPhotoFacade;

    /**
     * The instance variable 'eventFacade' is annotated with the @EJB annotation.
     * This means that the GlassFish application server, at runtime, will inject
     * in this instance variable a reference to the @Stateless session bean
     * EventFacade.
     */
    @EJB
    private EventFacade eventFacade;

    /**
     * The instance variable 'groupUserFacade' is annotated with the @EJB annotation.
     * This means that the GlassFish application server, at runtime, will inject
     * in this instance variable a reference to the @Stateless session bean
     * GroupUserFacade.
     */
    @EJB
    private GroupUserFacade groupUserFacade;

    /**
     * The instance variable 'groupFacade' is annotated with the @EJB annotation.
     * This means that the GlassFish application server, at runtime, will inject
     * in this instance variable a reference to the @Stateless session bean
     * Group1Facade.
     */
    @EJB
    private Group1Facade groupFacade;

    /**
     *Creates a new instance of AccountManager
     */
    public AccountManager() {
    }

    /**
     *
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return the currentGroupTitle
     */
    public String getCurrentGroupTitle() {
        return currentGroupTitle;
    }

    /**
     *
     * @param currentGroupTitle the currentGroupTitle to set
     */
    public void setCurrentGroupTitle(String currentGroupTitle) {
        this.currentGroupTitle = currentGroupTitle;
    }

    /**
     *
     * @return the joinStatusMessage
     */
    public String getJoinStatusMessage() {
        return joinStatusMessage;
    }

    /**
     *
     * @param joinStatusMessage the joinStatusMessage to set
     */
    public void setJoinStatusMessage(String joinStatusMessage) {
        this.joinStatusMessage = joinStatusMessage;
    }

    /**
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return the school
     */
    public String getSchool() {
        return school;
    }

    /**
     *
     * @param school the school to set
     */
    public void setSchool(String school) {
        this.school = school;
    }

    /**
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     *
     * @param state the state to set
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
     * @param zipcode the zipcode to set
     */
    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    /**
     *
     * @return the security_question
     */
    public int getSecurity_question() {
        return security_question;
    }

    /**
     *
     * @param security_question the security_question to set
     */
    public void setSecurity_question(int security_question) {
        this.security_question = security_question;
    }

    /**
     *
     * @return the security_answer
     */
    public String getSecurity_answer() {
        return security_answer;
    }

    /**
     *
     * @param security_answer the security_answer to set
     */
    public void setSecurity_answer(String security_answer) {
        this.security_answer = security_answer;
    }

    /**
     *
     * @return the statusMessage
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     *
     * @param statusMessage the statusMessage to set
     */
    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    /**
     *
     * @return the listOfStates
     */
    public String[] getListOfStates() {
        return listOfStates;
    }

    /**
     *
     * @return the listOfTitles
     */
    public String[] getListOfTitles() {
        return listOfTitles;
    }

    /**
     *
     * @return the eventFeed
     */
    public List<Event> getEventFeed() {
        return eventFeed;
    }

    /**
     *
     * @param eventFeed the eventFeed to set
     */
    public void setEventFeed(List<Event> eventFeed) {
        this.eventFeed = eventFeed;
    }

    /**
     *
     * @return the list of security questions
     */
    public Map<String, Object> getSecurity_questions() {
        if (security_questions == null) {
            security_questions = new LinkedHashMap<>();
            for (int i = 0; i < Constants.QUESTIONS.length; i++) {
                security_questions.put(Constants.QUESTIONS[i], i);
            }
        }
        return security_questions;
    }

    /**
     *
     * @return the listOfStaff
     */
    public List<User> getListOfStaff() {
        return listOfStaff;
    }

    /**
     *
     * @param listOfStaff the listOfStaff to set
     */
    public void setListOfStaff(List<User> listOfStaff) {
        this.listOfStaff = listOfStaff;
    }

    /**
     *
     * @return the listOfSchools
     */
    public String[] getListOfSchools() {
        return listOfSchools;
    }

    /**
     *
     * @return the selected
     */
    public User getSelected() {
        if (selected == null) {
            selected = userFacade.find(FacesContext.getCurrentInstance().
                    getExternalContext().getSessionMap().get("user_id"));
        }
        return selected;
    }

    /**
     *
     * @param selected the selected to set
     */
    public void setSelected(User selected) {
        this.selected = selected;
    }

    /**
     *
     * @return the groupExist
     */
    public boolean isGroupExist() {
        return groupExist;
    }

    /**
     *
     * @param groupExist the groupExist to set
     */
    public void setGroupExist(boolean groupExist) {
        this.groupExist = groupExist;
    }

    /**
     *
     * @return the joinPasscode
     */
    public String getJoinPasscode() {
        return joinPasscode;
    }

    /**
     *
     * @param joinPasscode the joinPasscode to set
     */
    public void setJoinPasscode(String joinPasscode) {
        this.joinPasscode = joinPasscode;
    }

    /**
     *
     * @return the createGroupTitle
     */
    public String getCreateGroupTitle() {
        return createGroupTitle;
    }

    /**
     *
     * @param createGroupTitle the createGroupTitle to set
     */
    public void setCreateGroupTitle(String createGroupTitle) {
        this.createGroupTitle = createGroupTitle;
    }

    /**
     *
     * @return the createGroupPasscode
     */
    public int getCreateGroupPasscode() {
        return createGroupPasscode;
    }

    /**
     *
     * @param createGroupPasscode the createGroupPasscode to set
     */
    public void setCreateGroupPasscode(int createGroupPasscode) {
        this.createGroupPasscode = createGroupPasscode;
    }

    /**
     *
     * @return the link to the staff profile page on successful account creation
     * @throws UnsupportedEncodingException
     */
    public String createAccount() throws UnsupportedEncodingException {

        // Check to see if a user already exists with the username given.
        User aUser = userFacade.findByUsername(username);

        if (aUser != null) {
            username = "";
            statusMessage = "Username already exists! Please select a different one!";
            return "";
        }
        else {
            statusMessage = "";
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
                if (!groupExist)
                {
                    user.setSchool(Integer.toString(createGroupPasscode));
                }
                else
                {
                    user.setSchool(joinPasscode);
                }
                user.setCity(city);
                user.setState(state);
                user.setZipcode(zipcode);
                user.setSecurityQuestion(security_question);
                user.setSecurityAnswer(security_answer);

                userFacade.create(user);

            } catch (EJBException e) {
                username = "";
                statusMessage = "Something went wrong while creating your account!";
                return "";
            }

            if (!groupExist) {
                try {
                    Group1 group = new Group1();
                    group.setPasscode(createGroupPasscode);
                    group.setTitle(createGroupTitle);

                    groupFacade.create(group);

                } catch (EJBException e) {
                    username = "";
                    statusMessage = "Something went wrong while creating your group!";
                    return "";
                }
                try{
                    GroupUser relation = new GroupUser();
                    relation.setGroupId(groupFacade.findGroupByPasscode(this.createGroupPasscode));
                    relation.setUserId(userFacade.findByUsername(username));
                    
                    groupUserFacade.create(relation);
                    
                }catch (EJBException e) {
                    username = "";
                    statusMessage = "Something went wrong while creating your groupUser!";
                    return "";
                }
            }
            else
            {
                try{
                    GroupUser relation = new GroupUser();
                    relation.setGroupId(groupFacade.findGroupByPasscode(Integer.parseInt(joinPasscode)));
                    relation.setUserId(userFacade.findByUsername(username));
                    
                    groupUserFacade.create(relation);
                    
                }catch (EJBException e) {
                    username = "";
                    statusMessage = "Something went wrong while creating your groupUser!";
                    return "";
                }
            }

            initializeSessionMap();
            sendEmail();
            save();
            reset();
            return "StaffProfile?faces-redirect=true";
        }
        return "";
    }

    /**
     *
     * @return the path to index.xhtml on successful account deletion
     */
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

    /**
     *
     * @param event the ComponentSystemEvent
     */
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
            statusMessage = "Passwords match!";
        }
    }

    /**
     * initialize the session map
     */
    public void initializeSessionMap() {
        User user = userFacade.findByUsername(getUsername());
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("username", username);
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("user_id", user.getId());
    }

    /**
     *
     * @return the path to index.html
     */
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        firstName = lastName = email = username = password = statusMessage = "";
        title = school = city = state = security_answer = "";
        zipcode = security_question = 0;

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true";
    }

    /**
     *
     * @return the userPhoto
     */
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

    /**
     *
     * @param userNamePhoto the userPhoto to set to
     * @return
     */
    public String findPhoto(String userNamePhoto) {
        User user = userFacade.findByUsername(userNamePhoto);
        List<UserPhoto> photoList = userPhotoFacade.findPhotosByUserID(user.getId());
        if (photoList.isEmpty()) {
            return "defaultUserPhoto.png";
        }
        return photoList.get(0).getThumbnailName();
    }

    /**
     *
     * @return path to StaffBook page
     */
    public String getListOfStaffByUniversity() {
        int user_id = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
        User user = userFacade.find(user_id);

        school = user.getSchool();
        listOfStaff = userFacade.findUserByUniversity(school);
        System.out.println();
        return "StaffBook";
    }

    /**
     *
     * @return path to Dashboard page
     */
    public String updateFeed() {
        int user_id = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
        User user = userFacade.find(user_id);

        eventFeed = eventFacade.findEventsByGroup(groupUserFacade.selectGroupFromUser(user).getGroupId());

        return "Dashboard?faces-redirect=true";
    }

    /**
     * send an email
     * @throws UnsupportedEncodingException
     */
    public void sendEmail() throws UnsupportedEncodingException {
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

    /**
     *
     * @return path to CreateAccount page
     */
    public String createGroup() {
        Random gen = new Random();
        this.groupExist = false;
        //check if group exists
        this.createGroupPasscode = gen.nextInt(9000) + 1000;

        
        while (groupFacade.findGroupByPasscode(this.createGroupPasscode) != null) {
            this.createGroupPasscode = gen.nextInt(9000) + 1000;
        }

        return "CreateAccount?faces-redirect=true";
    }
    
    /**
     *
     * @return path to CreateAccount page
     */
    public String joinGroup() {
        groupExist = true;
        if (groupFacade.findGroupByPasscode(Integer.parseInt(joinPasscode)) != null)
        {
            joinStatusMessage = "";
            return "CreateAccount?faces-redirect=true";
        }
        else
        {
            joinStatusMessage = "Incorrect passcode.";
        }
        return "JoinGroup?faces-redirect=true";
    }

    /**
     * saves the new user
     */
    public void save() {
        User user = new User();

        FacesMessage msg = new FacesMessage("Successful", "Welcome :" + user.getFirstName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     *
     * @param newUser new user to send email to
     * @return
     */
    public String message(User newUser) {
        return "Congratulations " + newUser.getFirstName() + "!\n\n"
                + "Your account have been successfully created!\n"
                + "Your account name is: " + newUser.getUsername() + "\n\n"
                + "You can now have all the information about your recruits in one place. No more scrammbling around looking for information on your recruit or losing vital information \n\n"
                + "Thank you for choosing us, \n"
                + "Wicruit Support Team";
    }
    
    /**
     *
     * @return the groupTitle
     */
    public String updateGroupTitle(){
        currentGroupTitle = groupFacade.findGroupByPasscode(Integer.parseInt(school)).getTitle();
        return currentGroupTitle;
    }

    /**
     *
     * @return path to index page
     */
    public String cancel() {
        reset();
        
        return "index?faces-redirect=true";
    }
    
    /**
     * reset all fields
     */
    public void reset() {
        createGroupTitle = "";
        joinPasscode = "";
        firstName = "";
        lastName = "";
        email = "";
        username = "";
        password = "";
        title = "";
        school = "";
        city = "";
        state = "";
        zipcode = 0;
        security_question = 0;
        security_answer = "";
        statusMessage = "";
        joinStatusMessage = "";
    }
}
