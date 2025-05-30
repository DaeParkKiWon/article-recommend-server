package basecampus.service.article_recommend.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerplexityReqDto {

    private String model;
    private List<Message> messages;
    private double temperature;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message {
        private String role;
        private String content;
    }

    public static PerplexityReqDto create(String prompt) {
        return new PerplexityReqDto(
                "sonar",
                List.of(
                        new Message("system", "Be precise and concise."),
                        new Message("user", prompt)
                ),
                0.2
        );
    }
}
