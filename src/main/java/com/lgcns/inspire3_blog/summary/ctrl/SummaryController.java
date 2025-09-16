package com.lgcns.inspire3_blog.summary.ctrl;

import com.lgcns.inspire3_blog.summary.domain.dto.SummaryResponseDTO;
import com.lgcns.inspire3_blog.summary.service.SummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/summary")
@RequiredArgsConstructor
public class SummaryController {

    private final SummaryService summaryService;

    @PostMapping
    public SummaryResponseDTO summarize(@RequestBody String text) {
        return summaryService.summarizeText(text);
    }
}