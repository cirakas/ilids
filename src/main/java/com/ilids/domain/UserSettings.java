/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ilids.domain;

import java.io.Serializable;
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
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cirakas
 */
@Entity
@Table(name = "user_settings", catalog = "ilids", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id"})})
public class UserSettings implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "sent_mail")
    private Boolean sentMail;
    @Column(name = "sent_sms")
    private Boolean sentSms;
    @JoinColumn(name = "chart", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Charts chart;
    @JoinColumn(name = "user", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;

    public UserSettings() {
    }

    public UserSettings(Long id) {
	this.id = id;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Boolean getSentMail() {
	return sentMail;
    }

    public void setSentMail(Boolean sentMail) {
	this.sentMail = sentMail;
    }

    public Boolean getSentSms() {
	return sentSms;
    }

    public void setSentSms(Boolean sentSms) {
	this.sentSms = sentSms;
    }

    public Charts getChart() {
	return chart;
    }

    public void setChart(Charts chart) {
	this.chart = chart;
    }

    public Users getUser() {
	return user;
    }

    public void setUser(Users user) {
	this.user = user;
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
	if (!(object instanceof UserSettings)) {
	    return false;
	}
	UserSettings other = (UserSettings) object;
	if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "com.ilids.entity.UserSettings[ id=" + id + " ]";
    }
    
}
