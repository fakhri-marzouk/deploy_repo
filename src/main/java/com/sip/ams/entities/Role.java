package com.sip.ams.entities;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "role_id")
 	private int role_id;
	@Column(name = "role")
	private String role;  

	public Role() {
		super();
	}

	public Role(String role2) {
		super();
		this.role=role2;
	}

	public int getId() {
		return role_id;
	}

	public void setId(int id) {
		this.role_id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}