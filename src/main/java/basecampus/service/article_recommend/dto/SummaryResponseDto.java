package basecampus.service.article_recommend.dto;

import basecampus.service.article_recommend.entity.Summary;

public record SummaryResponseDto(
    String keyword,
    String summary,
    String publicOpinion,
    String postUrl
) {
    public static SummaryResponseDto from(Summary summary) {
        return new SummaryResponseDto(
            summary.getKeyword().getName(),
            summary.getSummary(),
            summary.getPublicOpinion(),
            summary.getPostsUrl()
        );
    }
}
