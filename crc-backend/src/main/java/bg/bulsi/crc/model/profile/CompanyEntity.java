package bg.bulsi.crc.model.profile;


import bg.bulsi.crc.model.UserEntity;
import bg.bulsi.crc.model.audit.UserDateAudit;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "company", indexes = {
        @Index(name = "IX_pid", columnList = "pid"),
        @Index(name = "IX_uid", columnList = "uid"),
        @Index(name = "IX_authorized_person_id", columnList = "authorized_person_id"),
        @Index(name = "IX_address_id", columnList = "address_id")
})
@EqualsAndHashCode(callSuper = true, exclude = {"id"})
@ToString
public class CompanyEntity extends UserDateAudit<Long> {

    private static final long serialVersionUID = 4902589295468407252L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Setter
    private String pid;

    @Getter
    @Setter
    private String uid;

    @NotBlank
    @Getter
    @Setter
    private String name;

    @NotBlank
    @Column(name = "legal_form")
    @Getter
    @Setter
    private String legalForm;

    @Column(name = "employees_count")
    @Getter
    @Setter
    private Integer employeesCount;


    @Column(name = "trademark", length = 128)
    @Getter
    @Setter
    private String companyTrademark;

    @Column(name = "company_email", length = 128)
    @Getter
    @Setter
    private String companyEmailWeb;

    @Column(name = "phone", length = 128)
    @Getter
    @Setter
    private String phone;

    @Column(name = "fax", length = 128)
    @Getter
    @Setter
    private String fax;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, cascade = {
            CascadeType.REFRESH,
            CascadeType.MERGE,
            CascadeType.PERSIST
    })
    @JoinColumn(name = "authorized_person_id", foreignKey = @ForeignKey(name = "fk_authorized_person"))
    @Getter
    @Setter
    private UserEntity authorizedPerson;

    @NotNull
    @Embedded
    @Getter
    @Setter
    private CompanyRepresentative representative;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "address_id", foreignKey = @ForeignKey(name = "fk_address"))
    @Getter
    @Setter
    private AddressEntity address;

}
