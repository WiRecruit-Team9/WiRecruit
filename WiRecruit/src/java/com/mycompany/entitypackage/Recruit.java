/*
 * Created by Julio Suriano Siu on 2016.04.12  * 
 * Copyright © 2016 Julio Suriano Siu. All rights reserved. * 
 */
package com.mycompany.entitypackage;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jsuriano
 */
@Entity
@Table(name = "Recruit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recruit.findAll", query = "SELECT r FROM Recruit r"),
    @NamedQuery(name = "Recruit.findById", query = "SELECT r FROM Recruit r WHERE r.id = :id"),
    @NamedQuery(name = "Recruit.findByFirstName", query = "SELECT r FROM Recruit r WHERE r.firstName = :firstName"),
    @NamedQuery(name = "Recruit.findByLastName", query = "SELECT r FROM Recruit r WHERE r.lastName = :lastName"),
    @NamedQuery(name = "Recruit.findBySchool", query = "SELECT r FROM Recruit r WHERE r.school = :school"),
    @NamedQuery(name = "Recruit.findByAddress1", query = "SELECT r FROM Recruit r WHERE r.address1 = :address1"),
    @NamedQuery(name = "Recruit.findByAddress2", query = "SELECT r FROM Recruit r WHERE r.address2 = :address2"),
    @NamedQuery(name = "Recruit.findByCity", query = "SELECT r FROM Recruit r WHERE r.city = :city"),
    @NamedQuery(name = "Recruit.findByState", query = "SELECT r FROM Recruit r WHERE r.state = :state"),
    @NamedQuery(name = "Recruit.findByZipcode", query = "SELECT r FROM Recruit r WHERE r.zipcode = :zipcode"),
    @NamedQuery(name = "Recruit.findByYear", query = "SELECT r FROM Recruit r WHERE r.year = :year"),
    @NamedQuery(name = "Recruit.findByHeight", query = "SELECT r FROM Recruit r WHERE r.height = :height"),
    @NamedQuery(name = "Recruit.findByWeight", query = "SELECT r FROM Recruit r WHERE r.weight = :weight"),
    @NamedQuery(name = "Recruit.findByGpa", query = "SELECT r FROM Recruit r WHERE r.gpa = :gpa"),
    @NamedQuery(name = "Recruit.findByPhone", query = "SELECT r FROM Recruit r WHERE r.phone = :phone"),
    @NamedQuery(name = "Recruit.findByEmail", query = "SELECT r FROM Recruit r WHERE r.email = :email"),
    @NamedQuery(name = "Recruit.findBySkillLevel", query = "SELECT r FROM Recruit r WHERE r.skillLevel = :skillLevel"),
    @NamedQuery(name = "Recruit.findByPosition", query = "SELECT r FROM Recruit r WHERE r.position = :position"),
    @NamedQuery(name = "Recruit.findByCommitment", query = "SELECT r FROM Recruit r WHERE r.commitment = :commitment")})
public class Recruit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "first_name")
    private String firstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "last_name")
    private String lastName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "school")
    private String school;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "address1")
    private String address1;
    @Size(max = 255)
    @Column(name = "address2")
    private String address2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "city")
    private String city;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "state")
    private String state;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zipcode")
    private int zipcode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "year")
    private int year;
    @Basic(optional = false)
    @NotNull
    @Column(name = "height")
    private int height;
    @Basic(optional = false)
    @NotNull
    @Column(name = "weight")
    private int weight;
    @Basic(optional = false)
    @NotNull
    @Column(name = "gpa")
    private float gpa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "phone")
    private long phone;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Column(name = "skill_level")
    private int skillLevel;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "position")
    private String position;
    @Size(max = 255)
    @Column(name = "commitment")
    private String commitment;
    @Lob
    @Size(max = 65535)
    @Column(name = "notes")
    private String notes;
    @OneToMany(mappedBy = "recruitId")
    private Collection<RecruitPhoto> recruitPhotoCollection;
    @OneToMany(mappedBy = "recruitId")
    private Collection<Upvote> upvoteCollection;

    public Recruit() {
    }

    public Recruit(Integer id) {
        this.id = id;
    }

    public Recruit(Integer id, String firstName, String lastName, String school, String address1, String city, String state, int zipcode, int year, int height, int weight, float gpa, int phone, String email, int skillLevel, String position) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.school = school;
        this.address1 = address1;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.year = year;
        this.height = height;
        this.weight = weight;
        this.gpa = gpa;
        this.phone = phone;
        this.email = email;
        this.skillLevel = skillLevel;
        this.position = position;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        this.year = year;
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

    public float getGpa() {
        return gpa;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @XmlTransient
    public Collection<RecruitPhoto> getRecruitPhotoCollection() {
        return recruitPhotoCollection;
    }

    public void setRecruitPhotoCollection(Collection<RecruitPhoto> recruitPhotoCollection) {
        this.recruitPhotoCollection = recruitPhotoCollection;
    }

    @XmlTransient
    public Collection<Upvote> getUpvoteCollection() {
        return upvoteCollection;
    }

    public void setUpvoteCollection(Collection<Upvote> upvoteCollection) {
        this.upvoteCollection = upvoteCollection;
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
        if (!(object instanceof Recruit)) {
            return false;
        }
        Recruit other = (Recruit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entitypackage.Recruit[ id=" + id + " ]";
    }
    
}
