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

/**
 *
 * @author cirakas
 */
@Entity
@Table(name = "device_zone")
@NamedQueries({
    @NamedQuery(name = "DeviceZone.findAll", query = "SELECT d FROM DeviceZone d"),
    @NamedQuery(name = "DeviceZone.findById", query = "SELECT d FROM DeviceZone d WHERE d.id = :id"),
    @NamedQuery(name = "DeviceZone.findByName", query = "SELECT d FROM DeviceZone d WHERE d.name = :name"),
    @NamedQuery(name = "DeviceZone.findByDescription", query = "SELECT d FROM DeviceZone d WHERE d.description = :description")})
public class DeviceZone implements Serializable {

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
    @Column(name = "description")
    private String description;

    /**
     *
     */
    public DeviceZone() {
    }

    /**
     *
     * @param id
     */
    public DeviceZone(Long id) {
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
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DeviceZone)) {
            return false;
        }
        DeviceZone other = (DeviceZone) object;
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
