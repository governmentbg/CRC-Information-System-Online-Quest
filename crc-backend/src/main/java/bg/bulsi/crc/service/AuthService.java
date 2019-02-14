package bg.bulsi.crc.service;

import bg.bulsi.crc.api.AuthApiDelegate;
import bg.bulsi.crc.api.dto.*;
import bg.bulsi.crc.exception.AppException;
import bg.bulsi.crc.model.Role;
import bg.bulsi.crc.model.RoleName;
import bg.bulsi.crc.model.UserAuthenticationType;
import bg.bulsi.crc.model.UserEntity;
import bg.bulsi.crc.repository.RoleRepository;
import bg.bulsi.crc.repository.UserRepository;
import bg.bulsi.crc.security.JwtTokenProvider;
import bg.bulsi.crc.service.email.AsyncEmailService;
import bg.bulsi.crc.service.email.MailContent;
import bg.bulsi.eauth.exception.EAuthProcessException;
import bg.bulsi.eauth.model.AuthData;
import bg.bulsi.eauth.model.UserData;
import bg.bulsi.eauth.saml.authnrequest.dom.AuthRqParams;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static bg.bulsi.eauth.saml.response.AuthenticationUseCase.AUTHENTICATED_SUCCESSFULLY;

@Slf4j
@Service
public class AuthService implements AuthApiDelegate {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final eAVTService avtService;
    private final eAVTService eAVTService;
    private final ModelMapper mapper;
    private final AsyncEmailService emailService;


    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider,
                       UserRepository userRepository, RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder, eAVTService avtService, eAVTService eAVTService,
                       ModelMapper mapper,
                       AsyncEmailService emailService) {

        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.avtService = avtService;
        this.eAVTService = eAVTService;
        this.mapper = mapper;
        this.emailService = emailService;
    }

    @Override
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        List<Roles> roles = authentication.getAuthorities().stream()
                .map(grantedAuthority -> Enum.valueOf(Roles.class,
                        grantedAuthority.getAuthority().replace("ROLE_", "")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtAuthenticationResponse()
                .accessToken(jwt).roles(roles)

        );
    }

    @Override
    @Transactional
    public ResponseEntity<CrcApiResponse> registerUser(User signUpRequest) {

        if (signUpRequest.getUsernamePassData() == null) {
            return new ResponseEntity<>(
                    new CrcApiResponse().success(false).message("Missing Username / Password data!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByAuthenticationTypeAndUsername(UserAuthenticationType.STANDART,
                signUpRequest.getUsernamePassData().getUsername())) {
            return new ResponseEntity<>(
                    new CrcApiResponse().success(false).message("Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByAuthenticationTypeAndEmail(UserAuthenticationType.STANDART, signUpRequest.getEmail())) {
            return new ResponseEntity<>(new CrcApiResponse().success(false).message("Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        UserEntity result = createUser(signUpRequest);

        createdUserEmail(result);
        notifyOperator(result);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity
                .created(location)
                .body(new CrcApiResponse().success(true).message("User registered successfully"));
    }


    private void createdUserEmail(UserEntity user) {

        final MailContent content = new MailContent()
                .to(user.getEmail())
                .from("michael.spasov@bul-si.bg")
                .subject("Account created");

        content.template()
                .templateName("email1-template")
                .property("name", user.getFirstName());

        emailService.sendMail(content);
    }

    private void notifyOperator(UserEntity user) {

        final MailContent content = new MailContent()
                .from("michael.spasov@bul-si.bg")
                .subject("Account created");
        final MailContent.TemplateModel model = content.template()
                .templateName("email1-template");

        if (user.getCompanyToServe() == null) return;

        user.getCompanyToServe().forEach(companyEntity -> {

            content.to(companyEntity.getAuthorizedPerson().getEmail());
            model.property("name", companyEntity.getAuthorizedPerson().getFirstName());

            log.info("Mail have sent for company  " + companyEntity.getName() + " authorized person " + companyEntity.getAuthorizedPerson().getFirstName());

            emailService.sendMail(content);
        });
    }

    @Transactional
    public UserEntity createUser(User user) {

        if (user.getRoles() != null && user.getRoles().size() == 0) {

            List<Roles> roles = new ArrayList<>();
            roles.add(Roles.USER);
            user.setRoles(roles);
        }

        UserEntity userEntity = mapper.map(user, UserEntity.class);

        return userRepository.save(userEntity);
    }

    @SuppressWarnings("UnusedReturnValue")
    @Transactional
    public UserEntity createUser(String firstName,
                                 String middleName,
                                 String lastName,
                                 String username, String email, String password) {

        // Creating user's account
        UserEntity user = new UserEntity(firstName, middleName, lastName, username, email, password);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        user.setActiveFrom(LocalDate.now());
        user.setActiveTo(null);

        return userRepository.save(user);
    }

    @Override
    public ResponseEntity<SamlRequestParams> spSamlRequest() {

        AuthRqParams params = null;

        try {
            params = avtService.generateSamlRequest();
        } catch (EAuthProcessException e) {

            // TODO process REST API exception
            e.printStackTrace();
        }

        assert params != null;

        return ResponseEntity.ok(new SamlRequestParams()
                .samlRequest(params.getSamlRequest())
                .relayState(params.getRelayState())
                .authenticatorUrl(params.geteAuthenticatorUrl())
        );
    }

    @Override
    public ResponseEntity<JwtAuthenticationResponse> samlSignin(String samlResponse, String relayState) throws AuthenticationException {

        AuthData authData = eAVTService.parseSamlResponse(samlResponse, relayState);

        if (!authData.getStatus().equals(AUTHENTICATED_SUCCESSFULLY)) {
            throw new BadCredentialsException(authData.getStatus().getDescriptionENG());
        }

        UserData userData = authData.getUserData();

        String principal = userData.getEmail();
        String pid = userData.getEgn();

        // TODO
        // Тук трябва да говоря с Иван за уникалността на потребителите
        //
        Optional<UserEntity> user = userRepository.findByAuthenticationTypeAndUsernameOrEmail(UserAuthenticationType.SAML, pid, principal);

        String[] userNames = userData.getName().split(" ");

        String firstName, lastName = "";
        if (userNames.length == 2) {
            firstName = userNames[0];
            lastName = userNames[1];
        } else {
            firstName = userData.getName();
        }

        // Register SAML user
        // TODO
        if (!user.isPresent()) {
            createUser(
                    firstName, "", lastName,
                    pid, userData.getEmail(), pid
            );
        }

        return authenticateSamlUser(
                new LoginRequest()
                        .usernameOrEmail(principal)
                        .password(pid)
        );
    }

    private ResponseEntity<JwtAuthenticationResponse> authenticateSamlUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateSamlToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse().accessToken(jwt));
    }
}
