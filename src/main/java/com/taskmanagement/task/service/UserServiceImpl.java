package com.taskmanagement.task.service;

import com.taskmanagement.task.dto.UserDto;
import com.taskmanagement.task.models.Role;
import com.taskmanagement.task.models.User;
import com.taskmanagement.task.repository.RoleRepository;
import com.taskmanagement.task.repository.UserRepository;
import com.taskmanagement.task.util.TbConstants;
import com.taskmanagement.task.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveAdmin(UserDto userDto) {
        Role role = roleRepository.findByName(TbConstants.Roles.ADMIN);

        if (role == null) {
            role = roleRepository.save(new Role(TbConstants.Roles.ADMIN));
        }

        User user = new User(userDto.getName(), userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()),
                Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public void saveConsultant(UserDto userDto) {
        Role role = roleRepository.findByName(TbConstants.Roles.CONSULTANT);

        if (role == null) {
            role = roleRepository.save(new Role(TbConstants.Roles.CONSULTANT));
        }

        User user = new User(userDto.getName(), userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()),
                Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    


}