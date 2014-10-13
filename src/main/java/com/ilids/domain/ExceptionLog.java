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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author user
 */

@Entity
@Table(name="exception_log")
public class ExceptionLog implements Serializable{
    
     private static final long serialVersionUID = 1L;
     private Long id;
     private String userName;
     private String issueCausedModule;
     private String exceptionRemark;
     private Date occuringTime;
     private String exceptionType;

    /**
     *
     * @return
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
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
    @Column(name="user_name")
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @return
     */
    @Column(name="issue_caused_module")
    public String getIssueCausedModule() {
        return issueCausedModule;
    }

    /**
     *
     * @param issueCausedModule
     */
    public void setIssueCausedModule(String issueCausedModule) {
        this.issueCausedModule = issueCausedModule;
    }

    /**
     *
     * @return
     */
    @Column(name="exception_remark")
    public String getExceptionRemark() {
        return exceptionRemark;
    }

    /**
     *
     * @param exceptionRemark
     */
    public void setExceptionRemark(String exceptionRemark) {
        this.exceptionRemark = exceptionRemark;
    }

    /**
     *
     * @return
     */
    @Column(name="issue_occured_time")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getOccuringTime() {
        return occuringTime;
    }

    /**
     *
     * @param occuringTime
     */
    public void setOccuringTime(Date occuringTime) {
        this.occuringTime = occuringTime;
    }
    
    /**
     *
     * @return
     */
    @Column(name="exception_type")
    public String getExceptionType() {
        return exceptionType;
    }

    /**
     *
     * @param exceptionType
     */
    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }
    
     
}
