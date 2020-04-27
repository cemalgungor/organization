package com.cemal.organization.model;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.JoinColumn;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	@Column
	String username;
	@Column
	String password;
	@Column
	String email;
	@ManyToMany
	@JoinTable(name = "users_roles", 
	joinColumns =
	@JoinColumn(name = "user_id", referencedColumnName = "id"), 
	inverseJoinColumns = 
	@JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Collection<Role> role;

	@Column
	private boolean isEnabled;




}
