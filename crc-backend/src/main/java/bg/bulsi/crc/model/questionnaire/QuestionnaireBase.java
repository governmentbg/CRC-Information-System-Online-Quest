package bg.bulsi.crc.model.questionnaire;

import bg.bulsi.crc.model.audit.DateAudit;
import bg.bulsi.crc.model.profile.CompanyEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
public abstract class QuestionnaireBase extends DateAudit<Long> {

    private static final long serialVersionUID = 788708763526755304L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER, cascade = {
            CascadeType.REFRESH
    }, optional = false)
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "fk_company"))
    private CompanyEntity company;

    @Getter
    @Setter
    private int year = LocalDate.now().getYear();

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private QuestionnaireStatus questionnaireStatus = QuestionnaireStatus.DRAFT;

    @Getter
    @Setter
    @Column(name = "q_version", nullable = false)
    private Integer questionnaireVersion = 1;


}
