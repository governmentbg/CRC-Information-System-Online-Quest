package bg.bulsi.crc.repository;

import bg.bulsi.crc.model.questionnaire.QuestionnaireStatus;
import bg.bulsi.crc.model.questionnaire.ZpuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ZpuRepository extends JpaRepository<ZpuEntity, Long> {

    Optional<ZpuEntity> findByCompanyIdAndQuestionnaireStatusAndYear(Long companyId, QuestionnaireStatus status, int year);
}
