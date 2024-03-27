package com.blog.security;

import com.blog.entities.User;
import com.blog.exceptions.ResourseNotFoundException;
import com.blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomeUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException
    {
        // loading user from database by username
        User user = userRepository.findByEmail(username).orElseThrow(() -> new ResourseNotFoundException("Username Not Found !!!", HttpStatus.NOT_FOUND));
        return user;
    }
}
