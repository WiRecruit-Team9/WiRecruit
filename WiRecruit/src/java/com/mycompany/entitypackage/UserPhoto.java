/*
 * Created by Julio Suriano Siu on 2016.04.12  * 
 * Copyright © 2016 Julio Suriano Siu. All rights reserved. * 
 */
package com.mycompany.entitypackage;

import com.mycompany.managers.Constants;  // Added

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jsuriano
 */
@Entity
@Table(name = "UserPhoto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserPhoto.findAll", query = "SELECT u FROM UserPhoto u"),
    @NamedQuery(name = "UserPhoto.findById", query = "SELECT u FROM UserPhoto u WHERE u.id = :id"),
    @NamedQuery(name = "UserPhoto.findPhotosByUserId", query = "SELECT p FROM UserPhoto p WHERE p.userId.id = :userId"),
    @NamedQuery(name = "UserPhoto.findByExtension", query = "SELECT u FROM UserPhoto u WHERE u.extension = :extension")})
public class UserPhoto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "extension")
    private String extension;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private User userId;

    public UserPhoto() {
    }

    public UserPhoto(Integer id) {
        this.id = id;
    }

    public UserPhoto(Integer id, String extension) {
        this.id = id;
        this.extension = extension;
    }
    
    public UserPhoto(String extension, User id) {
        this.extension = extension;
        userId = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserPhoto)) {
            return false;
        }
        UserPhoto other = (UserPhoto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entitypackage.UserPhoto[ id=" + id + " ]";
    }
    
    public String getFilePath() {
        return Constants.USER_DIRECTORY + getFilename();
    }

    public String getFilename() {
        return getId() + "." + getExtension();
    }
    
    public String getThumbnailName() {
        return getId() + "_thumbnail." + getExtension();
    }
    
    public String getThumbnailFilePath() {
        return Constants.USER_DIRECTORY + getThumbnailName();
    }
    
}
