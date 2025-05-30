package basecampus.service.article_recommend.repository;

import basecampus.service.article_recommend.entity.Keyword;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    boolean existsByNameAndCreatedAtBetween(String name, LocalDateTime start, LocalDateTime end);

    long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    List<Keyword> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
