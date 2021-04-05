package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "t_permission")
public class Permission extends BaseEntity {

    private String permissionName;

    private String permissionKey;
    
    @ManyToMany(mappedBy = "permissions")
	Set<Role> roles = new HashSet<>();

	public Permission(String permissionName, String permissionKey) {
		super();
		this.permissionName = permissionName;
		this.permissionKey = permissionKey;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionKey() {
		return permissionKey;
	}

	public void setPermissionKey(String permissionKey) {
		this.permissionKey = permissionKey;
	}

	public Permission() {
		super();
	}

    
}
