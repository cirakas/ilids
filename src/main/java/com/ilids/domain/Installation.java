/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ilids.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cirakas
 */
@Entity
@Table(name = "installation", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email"}),
    @UniqueConstraint(columnNames = {"id"}),
    @UniqueConstraint(columnNames = {"name"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Installation.findAll", query = "SELECT i FROM Installation i"),
    @NamedQuery(name = "Installation.findById", query = "SELECT i FROM Installation i WHERE i.id = :id"),
    @NamedQuery(name = "Installation.findByName", query = "SELECT i FROM Installation i WHERE i.name = :name"),
    @NamedQuery(name = "Installation.findByAddress", query = "SELECT i FROM Installation i WHERE i.address = :address"),
    @NamedQuery(name = "Installation.findByEmail", query = "SELECT i FROM Installation i WHERE i.email = :email"),
    @NamedQuery(name = "Installation.findByPhone", query = "SELECT i FROM Installation i WHERE i.phone = :phone"),
    @NamedQuery(name = "Installation.findByInstDate", query = "SELECT i FROM Installation i WHERE i.instDate = :instDate"),
    @NamedQuery(name = "Installation.findByUpdateDate", query = "SELECT i FROM Installation i WHERE i.updateDate = :updateDate"),
    @NamedQuery(name = "Installation.findByEnabled", query = "SELECT i FROM Installation i WHERE i.enabled = :enabled"),
    @NamedQuery(name = "Installation.findByDeleted", query = "SELECT i FROM Installation i WHERE i.deleted = :deleted"),
    @NamedQuery(name = "Installation.findByMobileApp", query = "SELECT i FROM Installation i WHERE i.mobileApp = :mobileApp"),
    @NamedQuery(name = "Installation.findByBillingPeriod", query = "SELECT i FROM Installation i WHERE i.billingPeriod = :billingPeriod"),
    @NamedQuery(name = "Installation.findByBillingDate", query = "SELECT i FROM Installation i WHERE i.billingDate = :billingDate")})
public class Installation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
    @Size(max = 225)
    @Column(name = "name", length = 225)
    private String name;
    @Size(max = 1000)
    @Column(name = "address", length = 1000)
    private String address;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 45)
    @Column(name = "email", length = 45)
    private String email;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 45)
    @Column(name = "phone", length = 45)
    private String phone;
    @Column(name = "inst_date")
    @Temporal(TemporalType.DATE)
    private Date instDate;
    @Column(name = "update_date")
    @Temporal(TemporalType.DATE)
    private Date updateDate;
    @Column(name = "enabled")
    private Boolean enabled;
    @Column(name = "deleted")
    private Boolean deleted;
    @Column(name = "mobile_app")
    private Boolean mobileApp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "billing_period", nullable = false)
    private long billingPeriod;
    @Column(name = "billing_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date billingDate;

    public Installation() {
    }

    public Installation(Long id) {
	this.id = id;
    }

    public Installation(Long id, long billingPeriod) {
	this.id = id;
	this.billingPeriod = billingPeriod;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
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

    public Date getInstDate() {
	return instDate;
    }

    public void setInstDate(Date instDate) {
	this.instDate = instDate;
    }

    public Date getUpdateDate() {
	return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
	this.updateDate = updateDate;
    }

    public Boolean getEnabled() {
	return enabled;
    }

    public void setEnabled(Boolean enabled) {
	this.enabled = enabled;
    }

    public Boolean getDeleted() {
	return deleted;
    }

    public void setDeleted(Boolean deleted) {
	this.deleted = deleted;
    }

    public Boolean getMobileApp() {
	return mobileApp;
    }

    public void setMobileApp(Boolean mobileApp) {
	this.mobileApp = mobileApp;
    }

    public long getBillingPeriod() {
	return billingPeriod;
    }

    public void setBillingPeriod(long billingPeriod) {
	this.billingPeriod = billingPeriod;
    }

    public Date getBillingDate() {
	return billingDate;
    }

    public void setBillingDate(Date billingDate) {
	this.billingDate = billingDate;
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
	if (!(object instanceof Installation)) {
	    return false;
	}
	Installation other = (Installation) object;
	if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "com.ilids.entity.Installation[ id=" + id + " ]";
    }
    
}
