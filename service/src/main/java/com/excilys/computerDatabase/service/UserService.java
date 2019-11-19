package com.excilys.computerDatabase.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.computerDatabase.dao.UserDao;

@Service
public class UserService implements UserDetailsService {

	 private final UserDao userDao;
	    @Autowired
	    public UserService(UserDao userDao) {
	        this.userDao = userDao;
	    }
	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        Objects.requireNonNull(username);
	        com.excilys.computerDatabase.entity.User user = userDao.findOneByLogin(username);
	        
	        if( user == null) {
	        	throw new UsernameNotFoundException("User not found");
	        }
	        UserDetails userDetai = User.builder().username(user.getLogin()).password(user.getPassword()).roles("user").build();
	        return userDetai;
	    }


}
