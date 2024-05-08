package com.taskmanagement.task.service;

import com.taskmanagement.task.dto.UserDto;
import com.taskmanagement.task.models.User;


public interface UserService {

    User findUserByEmail(String email);
    
    void saveConsultant(UserDto userDto);
    
    void saveAdmin(UserDto userDto);
    
    
    
}