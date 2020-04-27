package com.cemal.organization.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import com.cemal.organization.model.RegistrationRequest;
import com.cemal.organization.model.Role;
import com.cemal.organization.model.User;
import com.cemal.organization.repository.RoleRepository;
import com.cemal.organization.repository.UserRepo;
import com.cemal.organization.service.UserService;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

import javax.transaction.Transactional;


@Service(value = "userService")
@Slf4j
public class UserServiceImpl implements UserDetailsService, UserService {
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
	}

	private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
 
	/*	user.getRole().forEach(role -> {
			//authorities.add(new SimpleGrantedAuthority(role.getName()));
			System.out.println(role.getName());
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
		});*/
		for (Role role : user.getRole()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
             }
		return authorities;
		//return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}
	/*   @Override
	    public Collection<? extends GrantedAuthority> getAuthority(User user) {
	        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

	        list.add(new SimpleGrantedAuthority("ROLE_" +user.getRole()));

	        return list;
	    }*/

	
       @Override
	public User findById(Long id) {
		return userRepo.findById(id).get();
	}

	@Override
    public User save(User user) {
	    User newUser = new User();
	    newUser.setUsername(user.getUsername());
	    newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
	
        return userRepo.save(newUser);
    }
	@Transactional
	public Boolean register(User user2) {
		try {
			User user = new User();
			user.setEmail(user2.getEmail());
			user.setPassword(bcryptEncoder.encode(user2.getPassword()));
			user.setUsername(user2.getUsername());
			Role adminRole = roleRepo.findByName("USER");
			user.setRole(Arrays.asList(adminRole));
			System.out.println(""+user.getRole().size());
			userRepo.save(user);

			return Boolean.TRUE;
		} catch (Exception e) {
			log.error("REGISTRATION=>", e);
			return Boolean.FALSE;
		}
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User findOne(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	

}