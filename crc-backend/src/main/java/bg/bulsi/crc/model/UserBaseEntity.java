package bg.bulsi.crc.model;


import bg.bulsi.crc.model.audit.DateAudit;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "uk_username", columnNames = {
                "username"
        }),
        @UniqueConstraint(name = "uc_email", columnNames = {
                "email"
        })
}, indexes = {
        @Index(name = "IX_egn", columnList = "egn"),
        @Index(name = "IX_first_name", columnList = "first_name"),
        @Index(name = "IX_middle_name", columnList = "middle_name"),
        @Index(name = "IX_last_name", columnList = "last_name"),
        @Index(name = "IX_username", columnList = "username"),
        @Index(name = "IX_email", columnList = "email")
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        discriminatorType = DiscriminatorType.INTEGER,
        name = "user_type_id",
        columnDefinition = "smallint"
)
@EqualsAndHashCode(callSuper = true, exclude = {"id"})
@ToString
public abstract class UserBaseEntity extends DateAudit<Long> {
    private static final long serialVersionUID = -3804993928815975515L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(name = "prefix", length = 20)
    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private NamePrefixEnum namePrefix;

    @Getter
    @Setter
    @Size(max = 40)
    @Column(name = "first_name")
    private String firstName;

    @Getter
    @Setter
    @Size(max = 40)
    @Column(name = "middle_name")
    private String middleName;

    @Getter
    @Setter
    @Size(max = 40)
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "egn", length = 20)
    @Getter
    @Setter
    private String pid;

    @NotBlank
    @Size(max = 15)
    @Getter
    @Setter
    private String username;

    //@NaturalId
    @NotBlank

    @Size(max = 40)
    @Email
    @Getter
    @Setter
    private String email;

    @Size(max = 40)
    @Email
    @Getter
    @Setter
    private String signatureDeviceEmail;

    @NotBlank

    @Size(max = 100)
    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private Boolean active = false;

    @Getter
    @Setter
    private LocalDate activeFrom;

    @Getter
    @Setter
    private LocalDate activeTo;


    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    @Getter
    @Setter
    private UserAuthenticationType authenticationType = UserAuthenticationType.STANDART;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Getter
    @Setter
    private Set<Role> roles = new HashSet<>();

    public UserBaseEntity() {

    }

    public UserBaseEntity(String firstName,
                          String middleName,
                          String lastName,
                          String username, String email, String password) {

        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;

        this.username = username;
        this.email = email;
        this.password = password;
    }

}
