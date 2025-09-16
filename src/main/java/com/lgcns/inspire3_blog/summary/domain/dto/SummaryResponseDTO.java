package com.lgcns.inspire3_blog.summary.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true) // 혹시 JSON에 추가 필드가 와도 무시
public class SummaryResponseDTO {

    private String title;          // 글의 핵심 제목
    private String overview;       // 전체 요약 설명

    @JsonProperty("bullet_summary")   // JSON 키: bullet_summary
    private List<String> bulletSummary; // 핵심 요약 포인트

    @JsonProperty("key_terms")        // JSON 키: key_terms
    private List<KeyTerm> keyTerms; // 주요 용어 리스트

    @JsonProperty("suggested_questions") // JSON 키: suggested_questions
    private List<String> suggestedQuestions; // 추가 학습/이해를 위한 질문

    @JsonProperty("action_items")     // JSON 키: action_items
    private List<String> actionItems; // 실행/학습 액션 아이템

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class KeyTerm {
        private String term;           // 용어
        private String definition;     // 정의
        @JsonProperty("why_it_matters") // JSON 키: why_it_matters
        private String whyItMatters;   // 왜 중요한지
        private String example;        // 예시
    }
}