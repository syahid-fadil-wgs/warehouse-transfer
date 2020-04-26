package com.wrhstrnsfr.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;

import com.wrhstrnsfr.api.model.UserModel;
import com.wrhstrnsfr.api.model.UserRoleModel;
import com.wrhstrnsfr.api.repository.UserRepository;
import com.wrhstrnsfr.api.repository.UserRoleRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	UserRoleRepository userRoleRepo;

	@Override
    public UserDetails loadUserByUsername(String username) {
        UserModel user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        
        UserRoleModel userRole = userRoleRepo.findByUser(user);
        UserBuilder userBuilder = User.withUsername(username);
        userBuilder.password(user.getPassword());
        userBuilder.roles(userRole.getEid().getRole().getRoleName().toUpperCase());
        return userBuilder.build();
    }
}
