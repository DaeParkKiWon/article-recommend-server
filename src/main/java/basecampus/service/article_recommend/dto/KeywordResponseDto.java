package basecampus.service.article_recommend.dto;

import basecampus.service.article_recommend.entity.Keyword;
import java.time.LocalDate;
import java.util.List;

public record KeywordResponseDto(LocalDate date, List<String> keywords) {
    public static KeywordResponseDto from(List<Keyword> keywords) {
        List<String> names = keywords.stream()
                .map(Keyword::getName)
                .toList();

        return new KeywordResponseDto(LocalDate.now(), names);
    }
}