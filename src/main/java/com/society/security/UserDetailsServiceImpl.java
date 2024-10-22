package com.society.security;
import java.sql.SQLException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.society.Model.User;
import com.society.dao.implementation.UserDAO;
 
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserDAO userdao;
 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userRes = null;
		try {
			userRes = userdao.getUserByUserName(username);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (userRes==null)
			throw new UsernameNotFoundException("Could not findUser with username = " + username);
	
		return new org.springframework.security.core.userdetails.User(username, userRes.getPassword(),
				Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
	}
}