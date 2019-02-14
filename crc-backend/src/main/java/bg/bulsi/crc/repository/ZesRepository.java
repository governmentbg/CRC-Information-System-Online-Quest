package bg.bulsi.crc.repository;

import bg.bulsi.crc.model.questionnaire.ZesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZesRepository extends JpaRepository<ZesEntity, Long> {
}
