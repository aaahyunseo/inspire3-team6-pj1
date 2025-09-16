package com.lgcns.inspire3_blog.mypage.dto;

import com.lgcns.inspire3_blog.user.domain.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserInfoResponseDTO {
    private Long userId;
    private String name;
    private String email;
    private String birthday;

    public static UserInfoResponseDTO from(UserEntity user) {
        return UserInfoResponseDTO.builder()
                                .userId(user.getUserId())
                                .name(user.getName())
                                .email(user.getEmail())
                                .birthday(user.getBirthday())
                                .build();
    }
}
