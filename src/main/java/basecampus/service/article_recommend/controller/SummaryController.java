package basecampus.service.article_recommend.controller;

import basecampus.service.article_recommend.dto.SummaryResponseDto;
import basecampus.service.article_recommend.service.SummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/summaries")
@RequiredArgsConstructor
public class SummaryController {

    private final SummaryService summaryService;

    /**
     * 특정 키워드에 대한 요약 정보를 생성 및 저장
     * @param keyword 키워드 이름 (예: "한덕수 소환")
     */
    @PostMapping("/generate")
    public ResponseEntity<SummaryResponseDto> generateSummary(
            @RequestParam String keyword) {

        SummaryResponseDto summary = summaryService.generateSummary(keyword);
        return ResponseEntity.ok(summary);
    }

    @GetMapping
    public ResponseEntity<SummaryResponseDto> getSummary(@RequestParam String keyword) {
        SummaryResponseDto response = summaryService.getSummaryByKeyword(keyword);
        return ResponseEntity.ok(response);
    }

}
