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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cirakas
 */
@Entity
@Table(name = "alerts", uniqueConstraints = {
@UniqueConstraint(columnNames = {"id"})})
public class Alerts implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "power", nullable = false)
    private double power;
    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    @Column(name = "deleteFlag")
    private Boolean deleteFlag;
    @JoinColumn(name = "device_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Devices deviceId;

    /**
     *
     */
    public Alerts() {
    }

    /**
     *
     * @param id
     */
    public Alerts(Long id) {
	this.id = id;
    }

    /**
     *
     * @param id
     * @param power
     */
    public Alerts(Long id, double power) {
	this.id = id;
	this.power = power;
    }

    /**
     *
     * @return
     */
    public Long getId() {
	return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
	this.id = id;
    }

    /**
     *
     * @return
     */
    public double getPower() {
	return power;
    }

    /**
     *
     * @param power
     */
    public void setPower(double power) {
	this.power = power;
    }

    /**
     *
     * @return
     */
    public Date getTime() {
	return time;
    }

    /**
     *
     * @param time
     */
    public void setTime(Date time) {
	this.time = time;
    }

    /**
     *
     * @return
     */
    public Boolean isDeleteFlag() {
	return deleteFlag;
    }

    /**
     *
     * @param deleteFlag
     */
    public void setDeleteFlag(Boolean deleteFlag) {
	this.deleteFlag = deleteFlag;
    }

    /**
     *
     * @return
     */
    public Devices getDeviceId() {
	return deviceId;
    }

    /**
     *
     * @param deviceId
     */
    public void setDeviceId(Devices deviceId) {
	this.deviceId = deviceId;
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
	if (!(object instanceof Alerts)) {
	    return false;
	}
	Alerts other = (Alerts) object;
	if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "com.ilids.entity.Alerts[ id=" + id + " ]";
    }
    
}
