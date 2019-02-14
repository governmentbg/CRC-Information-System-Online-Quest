package bg.bulsi.crc.service;

import bg.bulsi.crc.api.ProfileApiDelegate;
import bg.bulsi.crc.api.dto.Company;
import bg.bulsi.crc.api.dto.CrcApiResponse;
import bg.bulsi.crc.api.dto.User;
import bg.bulsi.crc.api.dto.UserSearchCriteria;
import bg.bulsi.crc.model.CrcUserEntity;
import bg.bulsi.crc.model.UserEntity;
import bg.bulsi.crc.model.profile.CompanyEntity;
import bg.bulsi.crc.repository.CompanyRepository;
import bg.bulsi.crc.repository.CrcUserRepository;
import bg.bulsi.crc.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Profile Service
 */
@Service
@Slf4j
public class ProfileService implements ProfileApiDelegate {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final CrcUserRepository crcUserRepository;
    private final ModelMapper mapper;

    @Autowired
    public ProfileService(ModelMapper mapper,
                          UserRepository userRepository,
                          CrcUserRepository crcUserRepository,
                          CompanyRepository companyRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.crcUserRepository = crcUserRepository;
        this.companyRepository = companyRepository;
    }

    /*----------------------------------------------------------------------------------------------------------------
        COMPANY Operations
      ----------------------------------------------------------------------------------------------------------------*/
    @SuppressWarnings("unchecked")
    @Override
    public ResponseEntity createCompany(Company newCompany) {
        // Първо проверяваме дали съществува в базата
        boolean exists = companyRepository.existsByPidOrUid(newCompany.getPid(), newCompany.getUid());

        if (exists) {
            return ResponseEntity.status(409)
                    .body(new CrcApiResponse()
                            .message("The company with PID: " + newCompany.getPid() + " and UID: " + newCompany.getUid() + " exists!")
                            .success(false)
                    );
        }

        CompanyEntity co = mapper.map(newCompany, CompanyEntity.class);
        co = saveCompany(co);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.map(co, Company.class));
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResponseEntity updateCompany(Company companyToUpdate) {
        if (companyToUpdate.getId() == null) {
            return ResponseEntity.status(417)
                    .body(new CrcApiResponse()
                            .message("The company with PID: " + companyToUpdate.getPid() + " and UID: " + companyToUpdate.getUid() + " is not company to update.")
                            .success(false)
                    );
        }

        Optional<CompanyEntity> company = companyRepository.findById(companyToUpdate.getId());

        if (company.isPresent()) {

            CompanyEntity updatedCompany = company.get();
            mapper.map(companyToUpdate, updatedCompany);

            updatedCompany = saveCompany(updatedCompany);

            companyToUpdate = mapper.map(updatedCompany, Company.class);

            return ResponseEntity
                    .ok()
                    .body(companyToUpdate);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new CrcApiResponse()
                        .message("The company with PID: " + companyToUpdate.getPid() + " and UID: " + companyToUpdate.getUid() + " can't be find!")
                        .success(false)
                );
    }

    @Override
    public ResponseEntity<Object> deleteCompany(Long id) {
        Optional<CompanyEntity> co = companyRepository.findById(id);

        if (co.isPresent()) {

            this.deleteCompanyData(co.get());
            return ResponseEntity
                    .ok()
                    .build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new CrcApiResponse()
                        .message("The company with ID: " + id + " can't be find!")
                        .success(false)
                );
    }

    @Override
    public ResponseEntity<List<Company>> getAllCompanies() {

        List<CompanyEntity> list = companyRepository.findAll();

        List<Company> response = list.stream()
                .map(companyEntity -> mapper.map(companyEntity, Company.class))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @Override
    public ResponseEntity<Company> getCompanyById(Long id) {

        Optional<CompanyEntity> company = companyRepository.findById(id);

        if (company.isPresent()) {
            return ResponseEntity.ok(mapper.map(company.get(), Company.class));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<List<Company>> getCompanyByEik(String id) {

        List<CompanyEntity> company = companyRepository.getAllByPidContainingOrderByName(id + "%");

        return ResponseEntity.status(HttpStatus.OK)
                .body(company.stream().map(companyEntity -> mapper.map(companyEntity, Company.class))
                        .collect(Collectors.toList())
                );

    }

    @Override
    public ResponseEntity<List<Company>> getCompanyByUri(String id) {

        List<CompanyEntity> company = companyRepository.getAllByUidContainingOrderByName(id + "%");

        return ResponseEntity.status(HttpStatus.OK)
                .body(company.stream().map(companyEntity -> mapper.map(companyEntity, Company.class))
                        .collect(Collectors.toList())
                );
    }

    /*----------------------------------------------------------------------------------------------------------------
        USER Operations
      ----------------------------------------------------------------------------------------------------------------*/
    @SuppressWarnings("unchecked")
    @Override
    public ResponseEntity createUser(User user) {

        if (!checkForUserId(user)) return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new CrcApiResponse()
                            .message("The company with PID: " + user.getEgn() + " is not new user")
                            .success(false)
                    );

        UserEntity entity = mapper.map(user, UserEntity.class);

        entity = saveUser(entity);
        user = mapper.map(entity, User.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                user
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResponseEntity deleteUser(Long id) {

        Optional<UserEntity> user = this.userRepository.findById(id);
        if (user.isPresent()) {

            deleteUserData(user.get());

            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<User> getUserByEgn(String id) {

        Optional<UserEntity> entity = userRepository.findByPid(id);

        return getUserResponseEntity(entity);
    }

    @Override
    public ResponseEntity<User> getUserById(Long id) {
        Optional<UserEntity> entity = userRepository.findById(id);

        return getUserResponseEntity(entity);
    }

    @Override
    public ResponseEntity<List<User>> getUserByCriteria(UserSearchCriteria criteria) {
        List<UserEntity> users = userRepository.findByEmailContainsOrFirstNameContainsOrMiddleNameContainsOrLastNameContainsOrPidContains(
                criteria.getCriteria(), // email
                criteria.getCriteria(), // firstName
                criteria.getCriteria(), // middleName
                criteria.getCriteria(), // lastName
                criteria.getCriteria()  // egn
        );
        if (users.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<User> response = users.stream()
                .map(userEntity -> mapper.map(userEntity, User.class, "userSearchCriteria"))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<List<User>> getManagedOperatorsByUser(Long id) {

        List<UserEntity> managedUsers = userRepository.findManagedOperatorsByUser(id);

        return ResponseEntity.ok().body(
                managedUsers.stream().map(userEntity -> mapper.map(userEntity, User.class)).collect(Collectors.toList())
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResponseEntity updateUser(User user) {
        if (checkForUserId(user)) return ResponseEntity.status(417)
                .body(new CrcApiResponse()
                        .message("The user with PID: " + user.getEgn() + " is undefined.")
                        .success(false)
                );

        Optional<UserEntity> userEntity = userRepository.findById(user.getId());

        if (userEntity.isPresent()) {
            mapper.map(user, userEntity.get());
            UserEntity updateUser = saveUser(userEntity.get());

            user = mapper.map(updateUser, User.class);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(user);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /*----------------------------------------------------------------------------------------------------------------
     *   CRC User operations
     *----------------------------------------------------------------------------------------------------------------*/
    @SuppressWarnings("unchecked")
    @Override
    public ResponseEntity createCrcUser(User user) {

        if (user.getId() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new CrcApiResponse()
                            .message("The company with PID: " + user.getEgn() + " is not new user")
                            .success(false)
                    );
        }

        CrcUserEntity entity = mapper.map(user, CrcUserEntity.class);

        entity = saveUser(entity);
        user = mapper.map(entity, User.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                user
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResponseEntity updateCrcUser(User user) {

        if (checkForUserId(user)) return ResponseEntity.status(417)
                .body(new CrcApiResponse()
                        .message("The user with PID: " + user.getEgn() + " is undefined.")
                        .success(false)
                );

        Optional<CrcUserEntity> userEntity = crcUserRepository.findById(user.getId());

        if (userEntity.isPresent()) {
            mapper.map(user, userEntity.get());
            CrcUserEntity updateUser = saveUser(userEntity.get());

            user = mapper.map(updateUser, User.class);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(user);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResponseEntity deleteCrcUser(Long id) {

        Optional<CrcUserEntity> user = this.crcUserRepository.findById(id);
        if (user.isPresent()) {

            deleteUserData(user.get());

            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<List<User>> getAllCrcUsers() {

        List<User> users = crcUserRepository.findAll().stream()
                .map(crcUserEntity -> mapper.map(crcUserEntity, User.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(users);
    }

    @Override
    public ResponseEntity<User> getCrcUserById(Long id) {
        Optional<CrcUserEntity> entity = crcUserRepository.findById(id);

        return getCrcUserResponseEntity(entity);
    }

    private static boolean checkForUserId(User user) {
        return user.getId() == null;
    }

    @Override
    public ResponseEntity<User> getCrcUserByPid(String id) {

        Optional<CrcUserEntity> entity = crcUserRepository.findByPid(id);

        return getCrcUserResponseEntity(entity);
    }

    @Override
    public ResponseEntity<List<User>> searchCrcUserByPid(String pid) {
        // TODO check pid
        List<CrcUserEntity> userEntities = crcUserRepository.findByPidContainingOrderByPid(pid + "%");
        return ResponseEntity.status(HttpStatus.OK)
                .body(userEntities.stream()
                        .map(crcUserEntity -> mapper.map(crcUserEntity, User.class))
                        .collect(Collectors.toList()));
    }

    /*----------------------------------------------------------------------------------------------------------------*/
    @Transactional
    protected CompanyEntity saveCompany(CompanyEntity company) {
        return companyRepository.save(company);
    }

    @Transactional
    protected void deleteCompanyData(CompanyEntity company) {
        companyRepository.delete(company);
    }

    @Transactional
    protected UserEntity saveUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Transactional
    protected CrcUserEntity saveUser(CrcUserEntity userEntity) {
        return crcUserRepository.save(userEntity);
    }

    @Transactional
    protected void deleteUserData(UserEntity user) {
        userRepository.delete(user);
    }

    @Transactional
    protected void deleteUserData(CrcUserEntity user) {
        crcUserRepository.delete(user);
    }

    private ResponseEntity<Company> getCompanyResponseEntity(@SuppressWarnings("OptionalUsedAsFieldOrParameterType") Optional<CompanyEntity> company) {
        if (company.isPresent()) {
            Company co = mapper.map(company.get(), Company.class);
            ResponseEntity
                    .status(HttpStatus.OK)
                    .body(co);
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    private ResponseEntity<User> getUserResponseEntity(@SuppressWarnings("OptionalUsedAsFieldOrParameterType") Optional<UserEntity> entity) {
        if (entity.isPresent()) {
            User user = mapper.map(entity.get(), User.class);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(user);
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }



    private ResponseEntity<User> getCrcUserResponseEntity(@SuppressWarnings("OptionalUsedAsFieldOrParameterType") Optional<CrcUserEntity> entity) {
        if (entity.isPresent()) {
            User user = mapper.map(entity.get(), User.class);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(user);
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }
}
