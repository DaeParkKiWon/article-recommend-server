package basecampus.service.article_recommend.config;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // 여기서 실제 사용자 이름 반환
        return Optional.of("System"); // 예: SecurityContextHolder.getContext().getAuthentication().getName()
    }
}
