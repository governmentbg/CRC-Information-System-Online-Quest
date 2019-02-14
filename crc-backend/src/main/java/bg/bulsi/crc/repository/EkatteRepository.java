package bg.bulsi.crc.repository;

import bg.bulsi.crc.model.ekatte.EkatteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EkatteRepository extends JpaRepository<EkatteEntity, Long> {

    List<EkatteEntity> findByPlaceContainingOrderByPlace(String place);

}
