package com.lgcns.inspire3_blog.summary.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SummaryRequestDTO {
    private String content;  // 사용자가 요약 요청할 글
}