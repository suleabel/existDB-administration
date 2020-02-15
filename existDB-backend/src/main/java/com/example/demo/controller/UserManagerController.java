package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.dto.UserDtoMapper;
import com.example.demo.dto.model.UserDto;
import com.example.demo.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/userManager")
public class UserManagerController {

    private UserService userService;

    private UserDtoMapper userDtoMapper;

    @Autowired
    public void setUserDtoMapper(UserDtoMapper userDtoMapper) {
        this.userDtoMapper = userDtoMapper;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/getAllUser", method = RequestMethod.GET)
    public ArrayList<UserDto> getAllUser() {
        Iterable<User> users = userService.getAllUser();
        ArrayList<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(userDtoMapper.convertUserToUserDto(user));
        }
        return userDtos;
    }

    @GetMapping(value = "/getUserById")
    public UserDto getUserById(@RequestParam Long id) {
        return userDtoMapper.convertUserToUserDto(userService.getUserById(id));
    }

    @PostMapping(value = "/saveUser")
    public String saveUser(@RequestBody UserDto userDto) {
        String response = userService.saveUser(userDtoMapper.convertUserDtoToUserEntity(userDto));
        System.out.println(response);
        return response;
    }

    @DeleteMapping(value = "/deleteUser")
    public String deleteUser(@RequestParam Long id) {
        return userService.deleteUser(id);

    }

}
