package com.lgcns.inspire3_blog.fortune.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true) // JSON에 정의되지 않은 필드는 무시
public class FortuneResponseDTO {
    private String title;     // 운세 제목
    private String date;      // 운세 날짜
    private String summary;   // 요약 설명
    private String keyPoint;  // 핵심 포인트
}