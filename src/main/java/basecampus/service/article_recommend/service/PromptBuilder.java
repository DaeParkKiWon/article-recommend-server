package basecampus.service.article_recommend.service;

import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class PromptBuilder {

    public String buildDailyKeywordPrompt(LocalDate date) {
        return String.format(
            "%d년 %d월 %d일 기준, 한국에서 가장 이슈가 된 뉴스 키워드 10가지를 알려줘. 해당날짜를 기준으로 최신 뉴스여야해.\n" +
            "뉴스 제목과 기사 내용을 기반으로 대표적인 이슈를 제목 형태로 요약해서 번호를 붙여 제시해줘.\n" +
            "형식: 1. 키워드1  2. 키워드2 … 10. 키워드10\n" +
            "신뢰도 있는 정보를 전달해줘. 키워드만 보고서 잘못된 정보로 오해할 소지를 만들지 마\n" +
            "마크다운(`**`, `#`, `-` 등), 이모지(\uD83D\uDE0A), HTML 태그, 특수문자 없이 순수한 문장형 텍스트로만 응답해줘.",
            date.getYear(), date.getMonthValue(), date.getDayOfMonth()
        );
    }

    public String buildKeywordDetailPrompt(String keyword, LocalDate date) {
        return String.format(
                """
                [요청 목적]
                아래의 키워드와 날짜를 기반으로 해당 주제와 관련된 뉴스를 수집하고, 다음 정보를 추출해줘.
        
                [요청 항목]
                1. 뉴스 요약: 주요 기사 내용 10줄 이내로 요약. 요약안에 사람들의 생각(민심)이 어떻게 다른지를 마지막에 포함시켜줘.
                2. 민심 분석: 댓글 기반으로 민심을 분석하여 아래와 같이 퍼센트로 출력
                   - 긍정: xx%%
                   - 중립: xx%%
                   - 부정: xx%%
                3. 기사 링크: 다양한 시각(보수/진보/중도 포함)의 기사 5개 정도 추천 
        
                [조건]
                - 응답은 순수한 JSON 형식으로 제공해줘.
                - 불필요한 문장이나 마크다운 포맷 없이 아래 형식으로 응답해줘.
        
                [응답 형식 예시]
                {
                  "keyword": "%s",
                  "date": "%s",
                  "summary": "2025년 5월 31일 한덕수 총리에 대한 소환 요구가 제기되며 정치권의 긴장이 고조되고 있습니다.",
                  "sentiment": {
                    "positive": 20,
                    "neutral": 50,
                    "negative": 30
                  },
                  "articles": [
                    {
                      "title": "한덕수 총리, 소환 조사 예정",
                      "url": "https://example.com/article1"
                    },
                    {
                      "title": "여야, 한덕수 총리 책임 공방",
                      "url": "https://example.com/article2"
                    }
                  ]
                }
        
                [입력 값]
                - 키워드: "%s"
                - 날짜: "%s"
                """,
                keyword,
                date.toString(),
                keyword,
                date.toString()
        );
    }

}
