package com.ilids.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author cirakas
 */
@Entity
@Table(name = "devices")
@NamedQueries({
    @NamedQuery(name = "Devices.findAll", query = "SELECT d FROM Devices d"),
    @NamedQuery(name = "Devices.findById", query = "SELECT d FROM Devices d WHERE d.id = :id"),
    @NamedQuery(name = "Devices.findByName", query = "SELECT d FROM Devices d WHERE d.name = :name"),
    @NamedQuery(name = "Devices.findBySlaveId", query = "SELECT d FROM Devices d WHERE d.slaveId = :slaveId"),
    @NamedQuery(name = "Devices.findByCreatedDate", query = "SELECT d FROM Devices d WHERE d.createdDate = :createdDate")})
public class Devices implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "slave_id")
    private long slaveId;
    @Column(name = "created_date")
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    @Column(name = "used", columnDefinition="SMALLINT(1)  NOT NULL DEFAULT 0", length = 1, nullable = false)
    private Short used; 
    
    @Transient
    private String deviceZoneId;

//join columns
    @JoinColumn(name = "device_zone", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private DeviceZone deviceZone;

    /**
     *
     */
    public Devices() {
    }

    /**
     *
     * @param id
     */
    public Devices(Long id) {
        this.id = id;
    }
    

    /**
     *
     * @param id
     * @param slaveId
     */
    public Devices(Long id, long slaveId) {
        this.id = id;
        this.slaveId = slaveId;
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
    public long getSlaveId() {
        return slaveId;
    }

    /**
     *
     * @param slaveId
     */
    public void setSlaveId(long slaveId) {
        this.slaveId = slaveId;
    }

    /**
     *
     * @return
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     *
     * @param createdDate
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     *
     * @return
     */
    public String getDeviceZoneId() {
        return deviceZoneId;
    }

    /**
     *
     * @param deviceZoneId
     */
    public void setDeviceZoneId(String deviceZoneId) {
        this.deviceZoneId = deviceZoneId;
    }

    /**
     *
     * @return
     */
    public DeviceZone getDeviceZone() {
        return deviceZone;
    }

    /**
     *
     * @param deviceZone
     */
    public void setDeviceZone(DeviceZone deviceZone) {
        this.deviceZone = deviceZone;
    }
    
    
    public Short getUsed() {
        return used;
    }

    public void setUsed(Short used) {
        this.used = used;
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
        if (!(object instanceof Devices)) {
            return false;
        }
        Devices other = (Devices) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ilids.domain.Devices[ id=" + id + " ]";
    }

}
