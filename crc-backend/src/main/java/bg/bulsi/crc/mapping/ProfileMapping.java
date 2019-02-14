package bg.bulsi.crc.mapping;

import bg.bulsi.crc.api.dto.*;
import bg.bulsi.crc.exception.AppException;
import bg.bulsi.crc.model.Role;
import bg.bulsi.crc.model.RoleName;
import bg.bulsi.crc.model.UserAuthenticationType;
import bg.bulsi.crc.model.UserEntity;
import bg.bulsi.crc.model.ekatte.EkatteEntity;
import bg.bulsi.crc.model.profile.AddressEntity;
import bg.bulsi.crc.model.profile.CompanyEntity;
import bg.bulsi.crc.repository.CompanyRepository;
import bg.bulsi.crc.repository.RoleRepository;
import bg.bulsi.crc.repository.UserRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
@Component
public class ProfileMapping extends AbstractMapping {


    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;


    @Autowired
    public ProfileMapping(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, CompanyRepository companyRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    void fromDtoToEntity(ModelMapper modelMapper) {

        // Company
        modelMapper.createTypeMap(Company.class, CompanyEntity.class)
                .addMappings(new PropertyMap<Company, CompanyEntity>() {
                    @Override
                    protected void configure() {
                        map().setName(source.getName());
                        map().setEmployeesCount(source.getEmployeesCount());
                        map().setLegalForm(source.getLegalForm());
                        map().setPid(source.getPid());


                    }
                })
                .addMappings(mapper -> mapper.using(context ->
                {
                    User user = (User) context.getSource();
                    if (user.getId() != null) {
                        Optional<UserEntity> u = userRepository.findById(user.getId());
                        if (u.isPresent()) {
                            // MERGE @@@
                            return context.getMappingEngine().map(context.create(context.getSource(), u.get()));
                        }
                        throw new AppException("Missing user with PID: " + user.getEgn());
                    } else {
                        return context.getMappingEngine().map(context.create(context.getSource(), context.getDestinationType()));
                    }
                }).map(Company::getAuthorizedPerson, CompanyEntity::setAuthorizedPerson))
                .addMapping(Company::getAddress, CompanyEntity::setAddress)
        ;

        // EKATTE
        modelMapper.createTypeMap(Ekatte.class, EkatteEntity.class)
                .addMappings(new PropertyMap<Ekatte, EkatteEntity>() {
                    @Override
                    protected void configure() {
                        map().setArea(source.getDistrict());
                        map().setEkatte(source.getId());
                    }
                });

        // Address
        modelMapper.createTypeMap(Address.class, AddressEntity.class)
                .addMappings(mapper -> {
                    mapper.using(context -> context.getMappingEngine().map(context.create(context.getSource(), context.getDestinationType())))
                            .map(Address::getEkatte, AddressEntity::setEkatte);
                    mapper.map(Address::getAddress, AddressEntity::setAddressDescription);
                });

        // User
        modelMapper.createTypeMap(User.class, UserEntity.class)
                .setProvider(request -> {
                    User user = (User) request.getSource();
                    if (user.getId() == null) {
                        return new UserEntity();
                    } else {
                        Optional<UserEntity> dbUser = userRepository.findById(user.getId());

                        if (!dbUser.isPresent()) {
                            throw new AppException("Missing user with ID: " + user.getId());
                        }

                        return dbUser.get();
                    }
                })
                .addMapping(User::getEgn, UserEntity::setPid)
                .addMapping(User::getActiveFrom, UserEntity::setActiveFrom)
                .addMapping(User::getActiveTo, UserEntity::setActiveTo)
                .addMappings(mapping -> {

                    mapping.using((Converter<List<Roles>, Set<Role>>) context -> {
                        List<Roles> userRoles = context.getSource();

                        if (userRoles == null) {
                            userRoles = new ArrayList<>();
                        }

                        if (userRoles.size() == 0) {
                            userRoles.add(Roles.USER);
                        }

                        return userRoles.stream().map(roles -> {
                            Optional<Role> role = roleRepository.findByName(RoleName.valueOf("ROLE_" + roles.toString()));

                            if (!role.isPresent()) {
                                throw new AppException("Missing Role - ROLE_" + roles.toString());
                            }

                            return role.get();

                        }).collect(Collectors.toSet());

                    }).map(User::getRoles, UserEntity::setRoles);
                    mapping.map(User::isActivationStatus, UserEntity::setActive);
                    mapping.using((Converter<List<Long>, Set<CompanyEntity>>) context -> {
                        if (context.getSource() == null) return null;

                        return context.getSource()
                            .stream()
                            .map(aLong -> companyRepository.findById(aLong).orElseThrow(() -> new AppException("Cant find company with ID - " + aLong.toString())))
                                .collect(Collectors.toSet());
                    }).map(User::getCompanyToServe, UserEntity::setCompanyToServe);
                })
                // post convert
                .setPostConverter(context -> {
                    if (context.getDestination().getId() == null) {
                        if (context.getSource().getSignatureDeviceData() != null) {
                            // EAVT - EGN
                            context.getDestination().setUsername(context.getSource().getSignatureDeviceData().getEgnFromDevice());
                            context.getDestination().setPassword(passwordEncoder.encode(context.getSource().getSignatureDeviceData().getEgnFromDevice()));
                            context.getDestination().setSignatureDeviceEmail(context.getSource().getSignatureDeviceData().getMailFromDevice());
                            context.getDestination().setAuthenticationType(UserAuthenticationType.SAML);

                        } else if (context.getSource().getUsernamePassData() != null) {

                            // User-Pass
                            context.getDestination().setUsername(context.getSource().getUsernamePassData().getUsername());
                            context.getDestination().setPassword(passwordEncoder.encode(context.getSource().getUsernamePassData().getPassword()));
                        }
                        context.getDestination().setActiveFrom(LocalDate.now());
                    }
                    return context.getDestination();
                });
    }

    @Override
    void fromEntityToDto(ModelMapper modelMapper) {

        modelMapper.createTypeMap(CompanyEntity.class, Company.class)
                .addMappings(mapping -> mapping.map(CompanyEntity::getAuthorizedPerson, Company::authorizedPerson));

        modelMapper.createTypeMap(UserEntity.class, User.class)
                .addMappings(mapping -> {
                    mapping.map(UserEntity::getPid, User::setEgn);
                    mapping.using((Converter<Set<Role>, List<Roles>>) context -> {
                        List<Roles> r = context.getSource().stream()
                                .map(role -> Roles.fromValue(role.getName().toString().replace("ROLE_", "")))
                                .collect(Collectors.toList());
                        return r;
                    }).map(UserEntity::getRoles, User::setRoles);

                    mapping.map(UserEntity::getActiveFrom, User::setActiveFrom);
                    mapping.map(UserEntity::getActiveTo, User::setActiveTo);
                    mapping.map(UserEntity::getActive, User::setActivationStatus);

                    mapping.using((Converter<Set<CompanyEntity>, List<Long>>) context -> {
                        if (context.getSource() == null) return new ArrayList<>();
                                return context.getSource()
                                        .stream().map(CompanyEntity::getId).collect(Collectors.toList());
                            }
                    )
                            .map(UserEntity::getCompanyToServe, User::setCompanyToServe);
                })

                .setPostConverter(context -> {
                    UserEntity dbUser = context.getSource();
                    if (dbUser.getAuthenticationType() == UserAuthenticationType.SAML) {
                        UserSignatureDeviceData signatureDeviceData = new UserSignatureDeviceData()
                                .egnFromDevice(dbUser.getUsername())
                                .mailFromDevice(dbUser.getSignatureDeviceEmail());
                        context.getDestination().setSignatureDeviceData(signatureDeviceData);
                    } else {
                        UserUsernamePassData usernamePassData = new UserUsernamePassData()
                                .username(dbUser.getUsername())
                                .password("******");
                        context.getDestination().setUsernamePassData(usernamePassData);
                    }
                    return context.getDestination();
                });

        modelMapper.createTypeMap(UserEntity.class, User.class, "userSearchCriteria")
                .addMappings(mapping -> {
                    mapping.map(UserEntity::getId, User::setId);
                    mapping.map(UserEntity::getPid, User::setEgn);
                    mapping.map(UserEntity::getEmail, User::setEmail);
                    mapping.map(UserEntity::getFirstName, User::setFirstName);
                    mapping.map(UserEntity::getMiddleName, User::setMiddleName);
                    mapping.map(UserEntity::getLastName, User::setLastName);
                    mapping.using((Converter<Set<Role>, List<Roles>>) context -> {
                        List<Roles> r = context.getSource().stream()
                                .map(role -> Roles.fromValue(role.getName().toString().replace("ROLE_", "")))
                                .collect(Collectors.toList());
                        return r;
                    })
                            .map(UserEntity::getRoles, User::setRoles);
                    mapping.map(UserEntity::getActiveFrom, User::setActiveFrom);
                    mapping.map(UserEntity::getActiveTo, User::setActiveTo);

                });
    }
}
