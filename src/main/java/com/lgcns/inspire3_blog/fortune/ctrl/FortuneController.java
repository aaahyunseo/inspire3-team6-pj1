package com.lgcns.inspire3_blog.fortune.ctrl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lgcns.inspire3_blog.fortune.service.FortuneService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/fortune")
@RequiredArgsConstructor
public class FortuneController {

    private final FortuneService fortuneService;

    @GetMapping("/{birthday}")
    public ResponseEntity<?> getFortune(@PathVariable String birthday) {
        return ResponseEntity.ok(fortuneService.getTodayFortune(birthday));
    }
}