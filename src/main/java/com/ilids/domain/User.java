package com.ilids.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import org.hibernate.validator.constraints.Email;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 *
 * @author cirakas
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private boolean enabled = true;
    @Column(nullable = false, unique = true)
    @Email
    private String email;
   
    @Transient
    private String roleId;
    
    @JoinColumn(name = "role", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Role role;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles = new HashSet<Role>();

    /**
     *
     * @param role
     */
    public void addRole(Role role) {
        roles.add(role);
    }

    /**
     *
     * @param role
     */
    public void removeRole(Role role) {
        roles.remove(role);
    }

    /**
     *
     */
    public void encryptPassword() {
        password = Md5Hash.encrypt(password);
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
    public void setId(long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     *
     * @param enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     *
     * @return
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     *
     * @param roles
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
    public Role getRole() {
	return role;
    }

    /**
     *
     * @param role
     */
    public void setRole(Role role) {
	this.role = role;
    }
    
    /**
     *
     * @return
     */
    public String getRoleId() {
	return roleId;
    }

    /**
     *
     * @param roleId
     */
    public void setRoleId(String roleId) {
	this.roleId = roleId;
    }
    
    
    }
