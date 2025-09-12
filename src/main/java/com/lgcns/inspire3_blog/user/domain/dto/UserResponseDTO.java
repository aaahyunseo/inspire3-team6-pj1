package com.lgcns.inspire3_blog.user.domain.dto;


import com.lgcns.inspire3_blog.user.domain.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private Long userId;
    private String email;
    private String name;
    private String birthday;
    public static UserResponseDTO fromEntity(UserEntity user) { 
        return UserResponseDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .birthday(user.getBirthday())
                .build() ; 
    }
}