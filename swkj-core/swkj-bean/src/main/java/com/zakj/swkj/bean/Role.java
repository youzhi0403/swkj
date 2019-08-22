package com.zakj.swkj.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * 角色实体
 */

public class Role implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String code;
	private String description;
	private Set<Function> functions = new HashSet<>(0);//当前角色对应的多个权限
	private Set<User> users = new HashSet<>(0);//当前角色对应的多个用户

	// Constructors

	/** default constructor */
	public Role() {
	}

	/** minimal constructor */
	public Role(String id) {
		this.id = id;
	}

	/** full constructor */
	public Role(String id, String name, String code, String description,
			Set functions, Set users) {
		this.id = id;
		this.name = name;
		this.code = code;
		this.description = description;
		this.functions = functions;
		this.users = users;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Function> getFunctions() {
		return functions;
	}

	public void setFunctions(Set<Function> functions) {
		this.functions = functions;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
}