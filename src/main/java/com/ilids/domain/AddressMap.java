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
 * @author cirakaspvt
 */
@Entity
@Table(name = "address_map")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AddressMap.findAll", query = "SELECT a FROM AddressMap a"),
    @NamedQuery(name = "AddressMap.findById", query = "SELECT a FROM AddressMap a WHERE a.id = :id"),
    @NamedQuery(name = "AddressMap.findByOffSet", query = "SELECT a FROM AddressMap a WHERE a.offSet = :offSet"),
    @NamedQuery(name = "AddressMap.findByParamName", query = "SELECT a FROM AddressMap a WHERE a.paramName = :paramName"),
    @NamedQuery(name = "AddressMap.findByWordLength", query = "SELECT a FROM AddressMap a WHERE a.wordLength = :wordLength"),
    @NamedQuery(name = "AddressMap.findByMultiFactor", query = "SELECT a FROM AddressMap a WHERE a.multiFactor = :multiFactor")})
public class AddressMap implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "off_set")
    private long offSet;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "param_name")
    private String paramName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "word_length")
    private long wordLength;
    @Basic(optional = false)
    @NotNull
    @Column(name = "multi_factor")
    private double multiFactor;

    public AddressMap() {
    }

    public AddressMap(Long id) {
        this.id = id;
    }

    public AddressMap(Long id, long offSet, String paramName, long wordLength, double multiFactor) {
        this.id = id;
        this.offSet = offSet;
        this.paramName = paramName;
        this.wordLength = wordLength;
        this.multiFactor = multiFactor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getOffSet() {
        return offSet;
    }

    public void setOffSet(long offSet) {
        this.offSet = offSet;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public long getWordLength() {
        return wordLength;
    }

    public void setWordLength(long wordLength) {
        this.wordLength = wordLength;
    }

    public double getMultiFactor() {
        return multiFactor;
    }

    public void setMultiFactor(double multiFactor) {
        this.multiFactor = multiFactor;
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
        if (!(object instanceof AddressMap)) {
            return false;
        }
        AddressMap other = (AddressMap) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ilids.domain.AddressMap[ id=" + id + " ]";
    }
    
}
