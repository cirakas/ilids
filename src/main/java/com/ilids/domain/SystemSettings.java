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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cirakas
 */
@Entity
@Table(name = "system_settings", catalog = "ilids", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SystemSettings.findAll", query = "SELECT s FROM SystemSettings s"),
    @NamedQuery(name = "SystemSettings.findById", query = "SELECT s FROM SystemSettings s WHERE s.id = :id"),
    @NamedQuery(name = "SystemSettings.findByMdv", query = "SELECT s FROM SystemSettings s WHERE s.mdv = :mdv"),
    @NamedQuery(name = "SystemSettings.findByRatesPerUnit", query = "SELECT s FROM SystemSettings s WHERE s.ratesPerUnit = :ratesPerUnit"),
    @NamedQuery(name = "SystemSettings.findByTimeZone", query = "SELECT s FROM SystemSettings s WHERE s.timeZone = :timeZone"),
    @NamedQuery(name = "SystemSettings.findBySystemClock", query = "SELECT s FROM SystemSettings s WHERE s.systemClock = :systemClock")})
public class SystemSettings implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "mdv", precision = 22)
    private Double mdv;
    @Column(name = "rates_per_unit", precision = 22)
    private Double ratesPerUnit;
    @Size(max = 45)
    @Column(name = "time_zone", length = 45)
    private String timeZone;
    @Column(name = "system_clock")
    @Temporal(TemporalType.TIMESTAMP)
    private Date systemClock;

    public SystemSettings() {
    }

    public SystemSettings(Long id) {
	this.id = id;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Double getMdv() {
	return mdv;
    }

    public void setMdv(Double mdv) {
	this.mdv = mdv;
    }

    public Double getRatesPerUnit() {
	return ratesPerUnit;
    }

    public void setRatesPerUnit(Double ratesPerUnit) {
	this.ratesPerUnit = ratesPerUnit;
    }

    public String getTimeZone() {
	return timeZone;
    }

    public void setTimeZone(String timeZone) {
	this.timeZone = timeZone;
    }

    public Date getSystemClock() {
	return systemClock;
    }

    public void setSystemClock(Date systemClock) {
	this.systemClock = systemClock;
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
	if (!(object instanceof SystemSettings)) {
	    return false;
	}
	SystemSettings other = (SystemSettings) object;
	if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "com.ilids.entity.SystemSettings[ id=" + id + " ]";
    }
    
}
