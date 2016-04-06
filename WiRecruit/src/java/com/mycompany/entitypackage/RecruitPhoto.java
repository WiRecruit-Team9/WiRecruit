/*
 * Created by Julio Suriano Siu on 2016.04.06  * 
 * Copyright © 2016 Julio Suriano Siu. All rights reserved. * 
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jsuriano
 */
@Entity
@Table(name = "RecruitPhoto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RecruitPhoto.findAll", query = "SELECT r FROM RecruitPhoto r"),
    @NamedQuery(name = "RecruitPhoto.findById", query = "SELECT r FROM RecruitPhoto r WHERE r.id = :id"),
    @NamedQuery(name = "RecruitPhoto.findByExtension", query = "SELECT r FROM RecruitPhoto r WHERE r.extension = :extension")})
public class RecruitPhoto implements Serializable {

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
    @JoinColumn(name = "recruit_id", referencedColumnName = "id")
    @ManyToOne
    private Recruit recruitId;

    public RecruitPhoto() {
    }

    public RecruitPhoto(Integer id) {
        this.id = id;
    }

    public RecruitPhoto(Integer id, String extension) {
        this.id = id;
        this.extension = extension;
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

    public Recruit getRecruitId() {
        return recruitId;
    }

    public void setRecruitId(Recruit recruitId) {
        this.recruitId = recruitId;
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
        if (!(object instanceof RecruitPhoto)) {
            return false;
        }
        RecruitPhoto other = (RecruitPhoto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entitypackage.RecruitPhoto[ id=" + id + " ]";
    }
    
}
