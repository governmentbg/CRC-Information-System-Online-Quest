package bg.bulsi.crc.repository;

import bg.bulsi.crc.model.profile.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    boolean existsByPidOrUid(String pid, String uid);

    Optional<CompanyEntity> findByPid(String pid);

    Optional<CompanyEntity> findByUid(String uid);

    List<CompanyEntity> getAllByUidContainingOrderByName(String searchString);

    List<CompanyEntity> getAllByPidContainingOrderByName(String searchString);
}


