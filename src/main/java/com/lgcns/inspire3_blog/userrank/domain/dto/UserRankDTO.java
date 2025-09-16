package com.lgcns.inspire3_blog.userrank.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRankDTO {
    private Long userId;
    private String userName;
    private int postCount;
    private int commentCount;
    private long score;
}
