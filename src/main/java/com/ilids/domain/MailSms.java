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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cirakas
 */
@Entity
@Table(name = "mail_sms")
@NamedQueries({
    @NamedQuery(name = "MailSms.findAll", query = "SELECT m FROM MailSms m"),
    @NamedQuery(name = "MailSms.findById", query = "SELECT m FROM MailSms m WHERE m.id = :id"),
    @NamedQuery(name = "MailSms.findByMail", query = "SELECT m FROM MailSms m WHERE m.mail = :mail"),
    @NamedQuery(name = "MailSms.findBySms", query = "SELECT m FROM MailSms m WHERE m.sms = :sms")})
public class MailSms implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "mail")
    private String mail;
    @Size(max = 15)
    @Column(name = "sms")
    private String sms;

    public MailSms() {
    }

    public MailSms(Long id) {
	this.id = id;
    }

    public MailSms(Long id, String mail) {
	this.id = id;
	this.mail = mail;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getMail() {
	return mail;
    }

    public void setMail(String mail) {
	this.mail = mail;
    }

    public String getSms() {
	return sms;
    }

    public void setSms(String sms) {
	this.sms = sms;
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
	if (!(object instanceof MailSms)) {
	    return false;
	}
	MailSms other = (MailSms) object;
	if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "com.ilids.domain.MailSms[ id=" + id + " ]";
    }
    
}
