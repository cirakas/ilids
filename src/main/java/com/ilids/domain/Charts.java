/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ilids.domain;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
@Table(name = "charts",uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Charts.findAll", query = "SELECT c FROM Charts c"),
    @NamedQuery(name = "Charts.findById", query = "SELECT c FROM Charts c WHERE c.id = :id"),
    @NamedQuery(name = "Charts.findByName", query = "SELECT c FROM Charts c WHERE c.name = :name"),
    @NamedQuery(name = "Charts.findByChartKey", query = "SELECT c FROM Charts c WHERE c.chartKey = :chartKey")})
public class Charts implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @Size(max = 45)
    @Column(name = "chart_key", length = 45)
    private String chartKey;
    @OneToMany(mappedBy = "chart", fetch = FetchType.LAZY)
    private Collection<UserSettings> userSettingsCollection;

    /**
     *
     */
    public Charts() {
    }

    /**
     *
     * @param id
     */
    public Charts(Long id) {
	this.id = id;
    }

    /**
     *
     * @param id
     * @param name
     */
    public Charts(Long id, String name) {
	this.id = id;
	this.name = name;
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
    public String getName() {
	return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     *
     * @return
     */
    public String getChartKey() {
	return chartKey;
    }

    /**
     *
     * @param chartKey
     */
    public void setChartKey(String chartKey) {
	this.chartKey = chartKey;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public Collection<UserSettings> getUserSettingsCollection() {
	return userSettingsCollection;
    }

    /**
     *
     * @param userSettingsCollection
     */
    public void setUserSettingsCollection(Collection<UserSettings> userSettingsCollection) {
	this.userSettingsCollection = userSettingsCollection;
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
	if (!(object instanceof Charts)) {
	    return false;
	}
	Charts other = (Charts) object;
	if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "com.ilids.entity.Charts[ id=" + id + " ]";
    }
    
}
