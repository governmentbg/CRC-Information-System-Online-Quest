package bg.bulsi.crc.repository;

import bg.bulsi.crc.model.CrcUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CrcUserRepository extends JpaRepository<CrcUserEntity, Long> {

    Optional<CrcUserEntity> findByPid(String pid);

    List<CrcUserEntity> findByPidContainingOrderByPid(String pid);

}