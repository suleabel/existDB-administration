package com.example.demo.security.service;

import com.example.demo.model.Role;
import com.example.demo.model.RoleName;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));
        return UserPrinciple.build(user);
    }

    @Transactional
    public UserDetails loadUserByUsername(String username, String password) {
        Role role = new Role((long) 1, RoleName.ROLE_ADMIN);
        Set<Role> roles = null;
        roles.add(role);
        User user = new User((long) 1,username, "firstName","lastName","admin@mail.com",password,roles);
        return UserPrinciple.build(user);
    }
}
