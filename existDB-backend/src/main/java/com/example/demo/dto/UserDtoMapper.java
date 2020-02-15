package com.example.demo.dto;

import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.dto.model.UserDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RoleConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashSet;
import java.util.Set;

@Controller
public class UserDtoMapper {

    private UserRepository userRepository;

    private RoleConverter roleConverter;

    @Autowired
    public void setRoleConverter(RoleConverter roleConverter) {
        this.roleConverter = roleConverter;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto convertUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        Set<String> roleList = new HashSet<>();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        for (Role role : user.getRoles()) {
            switch (role.getName()) {
                case ROLE_USER:
                    roleList.add("user");
                    break;
                case ROLE_ADMIN:
                    roleList.add("admin");
                    break;
                case ROLE_PM:
                    roleList.add("pm");
                    break;
            }
        }
        userDto.setRoles(roleList);
        return userDto;
    }

    public User convertUserDtoToUserEntity(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new RuntimeException(String.format("Patient %d not found", userDto.getId())));
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        Set<String> strRoleSet = userDto.getRoles();
        user.setRoles(roleConverter.convertRoles(strRoleSet));

        return user;
    }
}
