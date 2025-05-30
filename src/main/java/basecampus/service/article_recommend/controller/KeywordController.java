package basecampus.service.article_recommend.controller;

import basecampus.service.article_recommend.entity.Keyword;
import basecampus.service.article_recommend.service.KeywordService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/keywords")
class KeywordController {

    private final KeywordService keywordService;

    /**
     * 오늘 날짜 기준으로 Perplexity를 통해 키워드 10개 생성 및 저장
     */
    @PostMapping
    public ResponseEntity<List<Keyword>> generateTodayKeywords() {
        List<Keyword> keywords = keywordService.generateKeywordsByToday();
        return ResponseEntity.ok(keywords);
    }
}
