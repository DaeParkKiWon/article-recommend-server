package basecampus.service.article_recommend.service;

import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class PromptBuilder {

    public String buildDailyKeywordPrompt(LocalDate date) {
        return String.format(
            "%d년 %d월 %d일 기준, 한국에서 가장 이슈가 된 뉴스 키워드 10가지를 알려줘.\n" +
            "뉴스 제목과 기사 내용을 기반으로 대표적인 이슈를 짧은 단어 형태로 요약해서 번호를 붙여 제시해줘.\n" +
            "형식: 1. 키워드1  2. 키워드2 … 10. 키워드10\n" +
            "마크다운(`**`, `#`, `-` 등), 이모지(\uD83D\uDE0A), HTML 태그, 특수문자 없이 순수한 문장형 텍스트로만 응답해줘.",
            date.getYear(), date.getMonthValue(), date.getDayOfMonth()
        );
    }
}
