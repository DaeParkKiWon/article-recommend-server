package basecampus.service.article_recommend.client;
import basecampus.service.article_recommend.dto.PerplexityReqDto;
import basecampus.service.article_recommend.dto.PerplexityResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class PerplexityApiClient {

    private final WebClient perplexityWebClient;

    @Value("${perplexity.api.key}")
    private String apiKey;

    @Value("${perplexity.api.url}")
    private String apiUrl;

    public String getResponse(String prompt) {
        PerplexityReqDto request = PerplexityReqDto.create(prompt);

        try {
            PerplexityResDto response = perplexityWebClient.post()
                    .uri("/chat/completions")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(PerplexityResDto.class)
                    .block();

            return response != null ? response.extractText() : null;

        } catch (Exception e) {
            log.error("[Perplexity API ERROR] {}", e.getMessage(), e);
            return null;
        }
    }
}
