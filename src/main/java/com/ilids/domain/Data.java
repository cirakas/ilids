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
@Table(name = "data", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id"})})
@NamedQueries({
    @NamedQuery(name = "Data.findAll", query = "SELECT d FROM Data d"),
    @NamedQuery(name = "Data.findById", query = "SELECT d FROM Data d WHERE d.id = :id"),
    @NamedQuery(name = "Data.findByData", query = "SELECT d FROM Data d WHERE d.data = :data"),
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
    @Column(name = "data", nullable = false)
    private double data;
    @Basic(optional = false)
    @NotNull
    @Column(name = "time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    @JoinColumn(name = "device_id", referencedColumnName = "slave_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Devices deviceId;
    
    @JoinColumn(name = "address_map", referencedColumnName = "off_set", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AddressMap addressMap;

    /**
     *
     */
    public Data() {
    }

    /**
     *
     * @param id
     */
    public Data(Long id) {
	this.id = id;
    }

    /**
     *
     * @param id
     * @param data
     * @param voltage
     * @param current
     * @param time
     */
    public Data(Long id, double data, double voltage, double current, Date time) {
	this.id = id;
	this.data = data;
	this.time = time;
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
    public double getData() {
	return data;
    }

    /**
     *
     * @param data
     */
    public void setData(double data) {
	this.data = data;
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
    
    /**
     *
     * @return
     */
    public AddressMap getAddressMap() {
        return addressMap;
    }

    /**
     *
     * @param addressMap
     */
    public void setAddressMap(AddressMap addressMap) {
        this.addressMap = addressMap;
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
