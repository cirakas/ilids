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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author cirakas
 */
@Entity
@Table(name = "user_settings", uniqueConstraints = {
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
    private User user;

    /**
     *
     */
    public UserSettings() {
    }

    /**
     *
     * @param id
     */
    public UserSettings(Long id) {
	this.id = id;
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
    public Boolean getSentMail() {
	return sentMail;
    }

    /**
     *
     * @param sentMail
     */
    public void setSentMail(Boolean sentMail) {
	this.sentMail = sentMail;
    }

    /**
     *
     * @return
     */
    public Boolean getSentSms() {
	return sentSms;
    }

    /**
     *
     * @param sentSms
     */
    public void setSentSms(Boolean sentSms) {
	this.sentSms = sentSms;
    }

    /**
     *
     * @return
     */
    public Charts getChart() {
	return chart;
    }

    /**
     *
     * @param chart
     */
    public void setChart(Charts chart) {
	this.chart = chart;
    }

    /**
     *
     * @return
     */
    public User getUser() {
	return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(User user) {
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
