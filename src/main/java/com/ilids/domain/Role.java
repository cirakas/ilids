package com.ilids.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author cirakas
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @Column(name = "description", length = 1000)
    private String description;
    
    @Transient
    private String[] menuvalues;
    
    @Transient
    private List<Object> menuObjectList;

    /**
     *
     * @return
     */
    public List<Object> getMenuObjectList() {
	return menuObjectList;
    }

    /**
     *
     * @param menuObjectList
     */
    public void setMenuObjectList(List<Object> menuObjectList) {
	this.menuObjectList = menuObjectList;
    }

    /**
     *
     * @return
     */
    public String[] getMenuvalues() {
        return menuvalues;
    }

    /**
     *
     * @param menuvalues
     */
    public void setMenuvalues(String[] menuvalues) {
        this.menuvalues = menuvalues;
    }
   
    /**
     *
     * @return
     */
    public long getId() {
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Role)) {
            return false;
        }
        Role other = (Role) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ilids.entity.Role[ id=" + id + " ]";
    }
}
