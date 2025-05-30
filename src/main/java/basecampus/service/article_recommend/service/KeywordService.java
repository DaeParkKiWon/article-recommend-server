package basecampus.service.article_recommend.service;

import basecampus.service.article_recommend.client.PerplexityApiClient;
import basecampus.service.article_recommend.dto.KeywordResponseDto;
import basecampus.service.article_recommend.entity.Keyword;
import basecampus.service.article_recommend.repository.KeywordRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
@Service
@RequiredArgsConstructor
@Slf4j
public class KeywordService {

    private final KeywordRepository keywordRepository;
    private final PromptBuilder promptBuilder;
    private final PerplexityApiClient perplexityApiClient;

    private static final int MAX_KEYWORDS_PER_DAY = 10;

    @Transactional
    public List<Keyword> generateKeywordsByToday() {
        LocalDate date = LocalDate.now();
        LocalDateTime start = LocalDate.now().atStartOfDay(); // LocalDateTime
        LocalDateTime end = start.plusDays(1);                // LocalDateTime

        long todayCount = keywordRepository.countByCreatedAtBetween(start, end);
        if (todayCount >= MAX_KEYWORDS_PER_DAY) {
            throw new IllegalStateException("이미 해당 날짜에 10개의 키워드가 등록되었습니다.");
        }

        String prompt = promptBuilder.buildDailyKeywordPrompt(date);
        String raw = perplexityApiClient.getRecentKeywordList(prompt);
        log.info("Perplexity 응답:\n{}", raw);

        if (raw == null || raw.isBlank()) {
            throw new RuntimeException("Perplexity 응답이 비어 있음");
        }

        List<String> parsed = Arrays.stream(raw.split("\n"))
                .map(line -> line.replaceAll("^[0-9]+\\.\\s*", "").trim())
                .filter(k -> !k.isBlank())
                .distinct()
                .limit(MAX_KEYWORDS_PER_DAY - todayCount)
                .toList();

        List<Keyword> saved = keywordRepository.saveAll(
                parsed.stream()
                        .filter(k -> !keywordRepository.existsByNameAndCreatedAtBetween(k, start, end))
                        .map(k -> Keyword.builder().name(k).build())
                        .toList()
        );

        return saved;
    }

    public KeywordResponseDto getTodayKeywords() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusDays(1).minusNanos(1);

        List<Keyword> keywords = keywordRepository.findAllByCreatedAtBetween(start, end);
        return KeywordResponseDto.from(keywords);
    }
}

