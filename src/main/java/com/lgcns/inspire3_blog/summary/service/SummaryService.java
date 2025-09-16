package com.lgcns.inspire3_blog.summary.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgcns.inspire3_blog.summary.domain.dto.SummaryResponseDTO;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SummaryService {

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    // yaml 설정에 따라 택 1
    @Value("${openai.model}")
    private String model;
    @Value("${openai.api-key}")
    private String key;
    @Value("${openai.url}")
    private String url;

    // @Value("${OPEN_AI_MODEL}")
    // private String model;
    // @Value("${OPEN_AI_KEY}")
    // private String key;
    // @Value("${OPEN_AI_URL}")
    // private String url;


    // 글 요약 서비스
    public SummaryResponseDTO summarizeText(String text) {
        // 1. 500자 단위로 잘라냄
        List<String> chunks = splitText(text, 500);

        List<String> partialSummaries = new ArrayList<>();

        for (String chunk : chunks) {
            String summary = callOpenAIForSummary(chunk);
            partialSummaries.add(summary);
        }

        // 2. 부분 요약들을 합쳐서 최종 요약 요청
        String combined = String.join(" ", partialSummaries);
        String finalSummary = callOpenAIForSummary(combined);

        try {
        // OpenAI가 준 JSON → DTO 변환
            return mapper.readValue(finalSummary, SummaryResponseDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            return SummaryResponseDTO.builder()
                    .title("요약 실패")
                    .overview(finalSummary) // 실패 시 원문 그대로 넣기
                    .build();
    }
    }

    // OpenAI 호출 로직
    private String callOpenAIForSummary(String input) {
        String systemPrompt = """
            당신은 한국어 기술 에디터이자 요약 전문가입니다.
            반드시 아래 JSON 스키마로만 응답하세요. 추가 설명 없이 JSON만 반환합니다.

            스키마:
            {
              "title": string,
              "overview": string,
              "bullet_summary": string[],
              "key_terms": [
                {"term": string, "definition": string, "why_it_matters": string, "example": string}
              ],
              "suggested_questions": string[],
              "action_items": string[]
            }
            """;
            /*
            스키마:
            {
            "title": string,
            "overview": string,                       // 2~3문장 핵심요약
            "bullet_summary": string[],               // 3~7개, 각 20자 내외
            "key_terms": [                            // 중요 용어 사전
                {
                "term": string,                       // 용어명
                "definition": string,                 // 쉬운 정의
                "why_it_matters": string,             // 왜 중요한지
                "example": string                     // 간단 예시(가능하면 입력 내용 기반)
                }
            ],
            "suggested_questions": string[],          // 독자가 더 알아볼 유익한 질문 3~5개
            "action_items": string[]                  // 실천 항목 2~5개 (해당 시)
            }

            */
        try {
            Map<String, Object> systemMsg = Map.of(
                    "role", "system",
                    "content", systemPrompt
            );
            Map<String, Object> userMsg = Map.of(
                    "role", "user",
                    "content", input
            );

            Map<String, Object> requestMsg = new HashMap<>();
            requestMsg.put("model", model);
            requestMsg.put("messages", List.of(systemMsg, userMsg));

            String json = mapper.writeValueAsString(requestMsg);

            RequestBody body = RequestBody.create(
                    json, MediaType.parse("application/json")
            );
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Bearer " + key)
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new RuntimeException("Unexpected code " + response);
                }
                String responseBody = response.body().string();
                JsonNode node = mapper.readTree(responseBody);
                return node.at("/choices/0/message/content").asText();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    // 500자 단위로 나누는 메서드
    private List<String> splitText(String text, int chunkSize) {
        List<String> chunks = new ArrayList<>();
        int length = text.length();
        for (int i = 0; i < length; i += chunkSize) {
            chunks.add(text.substring(i, Math.min(length, i + chunkSize)));
        }
        return chunks;
    }
}