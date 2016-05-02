/*
 * Created by Franki Yeung on 2016.05.02  * 
 * Copyright © 2016 Franki Yeung. All rights reserved. * 
 */
package com.mycompany.entitypackage;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ftyyeung
 */
@Entity
@Table(name = "GroupRecruit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupRecruit.findAll", query = "SELECT g FROM GroupRecruit g"),
    @NamedQuery(name = "GroupRecruit.findById", query = "SELECT g FROM GroupRecruit g WHERE g.id = :id"),
    @NamedQuery(name = "GroupRecruit.findByAdmin", query = "SELECT g FROM GroupRecruit g WHERE g.admin = :admin")})
public class GroupRecruit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "admin")
    private Boolean admin;
    @JoinColumn(name = "recruit_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Recruit recruitId;
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Group1 groupId;

    public GroupRecruit() {
    }

    public GroupRecruit(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Recruit getRecruitId() {
        return recruitId;
    }

    public void setRecruitId(Recruit recruitId) {
        this.recruitId = recruitId;
    }

    public Group1 getGroupId() {
        return groupId;
    }

    public void setGroupId(Group1 groupId) {
        this.groupId = groupId;
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
        if (!(object instanceof GroupRecruit)) {
            return false;
        }
        GroupRecruit other = (GroupRecruit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entitypackage.GroupRecruit[ id=" + id + " ]";
    }
    
}
