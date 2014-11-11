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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author cirakas
 */
@Entity
@Table(name = "address_map")
public class AddressMaps implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @Column(name = "min_value")
    private Long minValue;
    @Column(name = "max_value")
    private Long maxValue;
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
    private Long wordLength;
    @Basic(optional = false)
    @NotNull
    @Column(name = "multi_factor")
    private double multiFactor;
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

    public Long getMinValue() {
        return minValue;
    }

    public void setMinValue(Long minValue) {
        this.minValue = minValue;
    }

    public Long getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Long maxValue) {
        this.maxValue = maxValue;
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

    public Long getWordLength() {
        return wordLength;
    }

    public void setWordLength(Long wordLength) {
        this.wordLength = wordLength;
    }

    public double getMultiFactor() {
        return multiFactor;
    }

    public void setMultiFactor(double multiFactor) {
        this.multiFactor = multiFactor;
    }
    
    
    
    
}
