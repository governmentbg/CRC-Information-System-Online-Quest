package bg.bulsi.crc.mapping;


import bg.bulsi.crc.api.dto.Roles;
import bg.bulsi.crc.api.dto.User;
import bg.bulsi.crc.api.dto.UserUsernamePassData;
import bg.bulsi.crc.exception.AppException;
import bg.bulsi.crc.model.CrcUserEntity;
import bg.bulsi.crc.model.Role;
import bg.bulsi.crc.model.RoleName;
import bg.bulsi.crc.model.UserAuthenticationType;
import bg.bulsi.crc.repository.CrcUserRepository;
import bg.bulsi.crc.repository.RoleRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
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
public class CrcUserMapping extends AbstractMapping {

    private final CrcUserRepository crcUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CrcUserMapping(PasswordEncoder passwordEncoder, CrcUserRepository crcUserRepository, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.crcUserRepository = crcUserRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    void fromDtoToEntity(ModelMapper modelMapper) {

        modelMapper.createTypeMap(User.class, CrcUserEntity.class)
                .setProvider(request -> {
                    User user = (User) request.getSource();

                    if (user.getId() == null) {
                        return new CrcUserEntity();
                    } else {
                        Optional<CrcUserEntity> dbUser = crcUserRepository.findById(user.getId());

                        if (!dbUser.isPresent()) {
                            throw new AppException("Missing user with ID: " + user.getId());
                        }

                        return dbUser.get();
                    }
                })

                .addMappings(mapping -> {
                    mapping.using((Converter<List<Roles>, Set<Role>>) context -> {
                        List<Roles> userRoles = context.getSource();

                        if (userRoles == null) {
                            userRoles = new ArrayList<>();
                        }

                        if (userRoles.size() == 0) {
                            userRoles.add(Roles.USER);
                            userRoles.add(Roles.CRC_USER);
                        }

                        return userRoles.stream().map(roles -> {
                            Optional<Role> role = roleRepository.findByName(RoleName.valueOf("ROLE_" + roles.toString()));

                            if (!role.isPresent()) {
                                throw new AppException("Missing Role - " + "ROLE_" + roles.toString());
                            }

                            return role.get();

                        }).collect(Collectors.toSet());

                    })
                            .map(User::getRoles, CrcUserEntity::setRoles);
                    mapping.map(User::getEgn, CrcUserEntity::setPid);
                    mapping.map(User::getActiveFrom, CrcUserEntity::setActiveFrom);
                    mapping.map(User::getActiveTo, CrcUserEntity::setActiveTo);
                    mapping.map(User::isActivationStatus, CrcUserEntity::setActive);
                })

                .setPostConverter(context -> {
                    if (context.getDestination().getId() == null) {
                        if (context.getSource().getSignatureDeviceData() != null) {
                            throw new AppException("This user don't support SAML authentication");

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
        modelMapper.createTypeMap(CrcUserEntity.class, User.class)
                .addMappings(mapping -> {
                    mapping.map(CrcUserEntity::getPid, User::setEgn);
                    mapping.using((Converter<Set<Role>, List<Roles>>) context -> {
                        List<Roles> r = context.getSource().stream()
                                .map(role -> Roles.fromValue(role.getName().toString().replace("ROLE_", "")))
                                .collect(Collectors.toList());
                        return r;
                    })
                            .map(CrcUserEntity::getRoles, User::setRoles);
                    mapping.map(CrcUserEntity::getActiveFrom, User::setActiveFrom);
                    mapping.map(CrcUserEntity::getActiveTo, User::setActiveTo);
                    mapping.using((Converter<Boolean, Boolean>) context -> context.getSource() == null ? false : context.getSource())
                            .map(CrcUserEntity::getActive, User::setActivationStatus);
                })

                .setPostConverter(context -> {
                    CrcUserEntity dbUser = context.getSource();
                    if (dbUser.getAuthenticationType() == UserAuthenticationType.SAML) {
                        throw new AppException("CRC User authentication can't be SAML");
                    } else {
                        UserUsernamePassData usernamePassData = new UserUsernamePassData()
                                .username(dbUser.getUsername())
                                .password("******");
                        context.getDestination().setUsernamePassData(usernamePassData);
                    }
                    return context.getDestination();
                });
    }
}
