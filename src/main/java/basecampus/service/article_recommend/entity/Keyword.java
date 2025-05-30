package basecampus.service.article_recommend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "keyword")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Keyword extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @OneToOne(mappedBy = "keyword", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private Summary summary;

  @Builder
  public Keyword(String name, Summary summary) {
    this.name = name;
    this.summary = summary;
  }
}
