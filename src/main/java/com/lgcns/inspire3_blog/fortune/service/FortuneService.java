package com.lgcns.inspire3_blog.fortune.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgcns.inspire3_blog.fortune.domain.dto.FortuneResponseDTO;

import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FortuneService {

    @Value("${OPEN_AI_MODEL}")
    private String model;

    @Value("${OPEN_AI_KEY}")
    private String key;

    @Value("${OPEN_AI_URL}")
    private String url;

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public FortuneResponseDTO getTodayFortune(String birthday) {
        try {
            String prompt = """
                오늘은 사용자의 생년월일을 바탕으로 운세를 알려줘.
                JSON 포맷으로만 응답해야 해.

                출력 스키마:
                {
                  "title": "총운 제목",
                  "date": "2025-09-15",
                  "summary": "운세 설명",
                  "keyPoint": "핵심 키포인트"
                }

                사용자의 생년월일: """ + birthday;

            Map<String, Object> systemMsg = new HashMap<>();
            systemMsg.put("role", "system");
            systemMsg.put("content", "넌 반드시 JSON 포맷으로 응답해야 하는 AI");

            Map<String, Object> userMsg = new HashMap<>();
            userMsg.put("role", "user");
            userMsg.put("content", prompt);

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

                // AI 응답에서 content 부분만 추출
                String content = node.at("/choices/0/message/content").asText();

                // content(JSON 문자열)를 FortuneResponseDTO로 매핑
                return mapper.readValue(content, FortuneResponseDTO.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}