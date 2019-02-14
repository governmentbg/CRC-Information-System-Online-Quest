package bg.bulsi.crc.repository;

import bg.bulsi.crc.model.SettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsRepository extends JpaRepository<SettingsEntity, String> {
}
