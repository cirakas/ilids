/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ilids.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author cirakas
 */
@Entity
@Table(name = "devices", catalog = "ilids", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Devices.findAll", query = "SELECT d FROM Devices d"),
    @NamedQuery(name = "Devices.findById", query = "SELECT d FROM Devices d WHERE d.id = :id"),
    @NamedQuery(name = "Devices.findByName", query = "SELECT d FROM Devices d WHERE d.name = :name"),
    @NamedQuery(name = "Devices.findByCode", query = "SELECT d FROM Devices d WHERE d.code = :code"),
    @NamedQuery(name = "Devices.findByCreatedDate", query = "SELECT d FROM Devices d WHERE d.createdDate = :createdDate")})
public class Devices implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
    @Size(max = 45)
    @Column(name = "name", length = 45)
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "code", nullable = false, length = 45)
    private String code;
    @Column(name = "created_date")
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deviceId", fetch = FetchType.LAZY)
    private Collection<Alerts> alertsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deviceId", fetch = FetchType.LAZY)
    private Collection<Data> dataCollection;

    public Devices() {
    }

    public Devices(Long id) {
	this.id = id;
    }

    public Devices(Long id, String code) {
	this.id = id;
	this.code = code;
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

    public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
    }

    public Date getCreatedDate() {
	return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
    }

    @XmlTransient
    public Collection<Alerts> getAlertsCollection() {
	return alertsCollection;
    }

    public void setAlertsCollection(Collection<Alerts> alertsCollection) {
	this.alertsCollection = alertsCollection;
    }

    @XmlTransient
    public Collection<Data> getDataCollection() {
	return dataCollection;
    }

    public void setDataCollection(Collection<Data> dataCollection) {
	this.dataCollection = dataCollection;
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
	if (!(object instanceof Devices)) {
	    return false;
	}
	Devices other = (Devices) object;
	if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "com.ilids.entity.Devices[ id=" + id + " ]";
    }
    
}
