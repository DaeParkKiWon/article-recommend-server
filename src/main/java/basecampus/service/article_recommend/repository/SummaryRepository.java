package basecampus.service.article_recommend.repository;

import basecampus.service.article_recommend.entity.Summary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SummaryRepository extends JpaRepository<Summary, Integer> {
    List<Summary> findAllByCreatedAtBetween(LocalDate start, LocalDate end);
    Summary findByKeywordId(Long keywordId);
}
