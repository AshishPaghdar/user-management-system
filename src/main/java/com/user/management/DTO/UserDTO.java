package com.user.management.DTO;

import com.user.management.entity.Role;
import com.user.management.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String mobile;
    private String gender;
    private Role role;

    public static UserDTO mapToUserDTO(User user) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setEmail(user.getEmail());
            userDTO.setGender(user.getGender());
            userDTO.setMobile(user.getMobile());
            userDTO.setRole(user.getRole());
            return userDTO;
    }

    // Method to map a list of User to a list of UserDTO
    public static List<UserDTO> mapToUserDTOList(List<User> users) {
        return users.stream()
                .map(UserDTO::mapToUserDTO)  // Apply mapping for each User in the list
                .collect(Collectors.toList());  // Collect into a List of UserDTO
    }
}
