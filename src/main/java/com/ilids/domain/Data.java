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
@Table(name = "data", catalog = "ilids", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Data.findAll", query = "SELECT d FROM Data d"),
    @NamedQuery(name = "Data.findById", query = "SELECT d FROM Data d WHERE d.id = :id"),
    @NamedQuery(name = "Data.findByPower", query = "SELECT d FROM Data d WHERE d.power = :power"),
    @NamedQuery(name = "Data.findByVoltage", query = "SELECT d FROM Data d WHERE d.voltage = :voltage"),
    @NamedQuery(name = "Data.findByCurrent", query = "SELECT d FROM Data d WHERE d.current = :current"),
    @NamedQuery(name = "Data.findByTime", query = "SELECT d FROM Data d WHERE d.time = :time")})
public class Data implements Serializable {
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "voltage", nullable = false)
    private double voltage;
    @Basic(optional = false)
    @NotNull
    @Column(name = "current", nullable = false)
    private double current;
    @Basic(optional = false)
    @NotNull
    @Column(name = "time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    @JoinColumn(name = "device_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Devices deviceId;

    public Data() {
    }

    public Data(Long id) {
	this.id = id;
    }

    public Data(Long id, double power, double voltage, double current, Date time) {
	this.id = id;
	this.power = power;
	this.voltage = voltage;
	this.current = current;
	this.time = time;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public double getPower() {
	return power;
    }

    public void setPower(double power) {
	this.power = power;
    }

    public double getVoltage() {
	return voltage;
    }

    public void setVoltage(double voltage) {
	this.voltage = voltage;
    }

    public double getCurrent() {
	return current;
    }

    public void setCurrent(double current) {
	this.current = current;
    }

    public Date getTime() {
	return time;
    }

    public void setTime(Date time) {
	this.time = time;
    }

    public Devices getDeviceId() {
	return deviceId;
    }

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
	if (!(object instanceof Data)) {
	    return false;
	}
	Data other = (Data) object;
	if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "com.ilids.entity.Data[ id=" + id + " ]";
    }
    
}
