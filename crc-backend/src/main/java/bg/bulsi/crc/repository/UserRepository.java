package bg.bulsi.crc.repository;

import bg.bulsi.crc.model.UserAuthenticationType;
import bg.bulsi.crc.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsernameOrEmail(String username, String email);

    Optional<UserEntity> findByAuthenticationTypeAndUsernameOrEmail(UserAuthenticationType authenticationType, String username, String email);

    List<UserEntity> findByIdIn(List<Long> userIds);

    List<UserEntity> findByEmailContainsOrFirstNameContainsOrMiddleNameContainsOrLastNameContainsOrPidContains(String email, String firstName, String middleName, String lastName, String pid);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByPid(String pid);

    Boolean existsByUsername(String username);

    Boolean existsByAuthenticationTypeAndUsername(UserAuthenticationType authenticationType, String username);

    Boolean existsByEmail(String email);

    Boolean existsByAuthenticationTypeAndEmail(UserAuthenticationType authenticationType, String email);

    @Query(value = "SELECT u.* from users u, users_company_to_serve  cs where (u.id = cs.user_entity_id) AND (cs.company_to_serve_id IN (SELECT id from company where authorized_person_id = ?1))",
            nativeQuery = true)
    List<UserEntity> findManagedOperatorsByUser(Long id);

    @Query(value = "SELECT u.* FROM users u WHERE u.id = ?1", nativeQuery = true)
    Optional<UserEntity> getUserData(Long id);

}
