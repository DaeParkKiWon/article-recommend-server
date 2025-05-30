package basecampus.service.article_recommend.service;

import basecampus.service.article_recommend.client.PerplexityApiClient;
import basecampus.service.article_recommend.dto.SummaryResponseDto;
import basecampus.service.article_recommend.entity.Keyword;
import basecampus.service.article_recommend.entity.Summary;
import basecampus.service.article_recommend.repository.KeywordRepository;
import basecampus.service.article_recommend.repository.SummaryRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class SummaryService {

    private final PerplexityApiClient perplexityApiClient;
    private final PromptBuilder promptBuilder;
    private final KeywordRepository keywordRepository;
    private final SummaryRepository summaryRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public SummaryResponseDto generateSummary(String keywordName) {

        LocalDate date = LocalDate.now();
        // 1. 키워드 조회
        Keyword keyword = keywordRepository.findByName(keywordName)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 키워드입니다: " + keywordName));

        // 2. 프롬프트 생성 및 요청
        String prompt = promptBuilder.buildKeywordDetailPrompt(keywordName, date);
        String raw = perplexityApiClient.getResponse(prompt);
        log.info("Perplexity 응답 (raw):\n{}", raw);

        if (raw == null || raw.isBlank()) {
            throw new RuntimeException("Perplexity 응답이 비어 있음");
        }
        raw = sanitizeJson(raw);

        try {
            // 3. JSON 파싱
            JsonNode root = objectMapper.readTree(raw);

            String summaryText = root.path("summary").asText().trim();
            JsonNode sentiment = root.path("sentiment");

            int positive = sentiment.path("positive").asInt();
            int neutral = sentiment.path("neutral").asInt();
            int negative = sentiment.path("negative").asInt();

            String publicOpinion = String.format("긍정: %d%%, 중립: %d%%, 부정: %d%%", positive, neutral, negative);

            StringBuilder postsUrlBuilder = new StringBuilder();
            root.path("articles").forEach(article -> {
                String url = article.path("url").asText();
                if (!url.isBlank()) {
                    postsUrlBuilder.append(url).append("\n");
                }
            });

            String postsUrl = postsUrlBuilder.toString().trim();

            // 4. Summary 생성 및 저장
            Summary summary = Summary.builder()
                    .keyword(keyword)
                    .summary(summaryText)
                    .publicOpinion(publicOpinion)
                    .postsUrl(postsUrl)
                    .build();

            Summary saved = summaryRepository.save(summary);
            keyword.setSummary(saved); // OneToOne 양방향 연결

            return SummaryResponseDto.from(saved);

        } catch (Exception e) {
            log.error("Perplexity 응답 파싱 실패", e);
            throw new RuntimeException("응답 파싱 실패", e);
        }
    }

    private String sanitizeJson(String raw) {
        return raw.replaceAll("^```json", "")
                .replaceAll("^```", "")
                .replaceAll("```$", "")
                .trim();
    }

    @Transactional
    public SummaryResponseDto getSummaryByKeyword(String keywordName) {
        Keyword keyword = keywordRepository.findByName(keywordName)
                .orElseThrow(() -> new IllegalArgumentException("해당 키워드가 존재하지 않습니다: " + keywordName));

        Summary summary = keyword.getSummary();
        if (summary == null) {
            throw new IllegalStateException("해당 키워드에 대한 요약 정보가 존재하지 않습니다.");
        }

        return SummaryResponseDto.from(summary);
    }

}
