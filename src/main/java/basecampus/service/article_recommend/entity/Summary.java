package basecampus.service.article_recommend.entity;

import basecampus.service.article_recommend.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "summary")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Summary extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword", nullable = false, unique = true)
    private Keyword keyword;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String summary;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String publicOpinion;

    @Column(name = "posts_url", columnDefinition = "TEXT", nullable = false)
    private String postsUrl;

    @Builder
    public Summary(String summary, String publicOpinion, String postsUrl) {
        this.summary = summary;
        this.publicOpinion = publicOpinion;
        this.postsUrl = postsUrl;
    }

}
