package bg.bulsi.crc.model.questionnaire;

import bg.bulsi.crc.api.dto.PostQuestionnaire;
import lombok.Getter;
import lombok.Setter;
import net.optionfactory.hj.JsonType;
import net.optionfactory.hj.spring.SpringDriverLocator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "zpu_questionnaire", indexes = {
        @Index(name = "ix_unique_zpu_q", columnList = "company_id, year, status, q_version")
})
public class ZpuEntity extends QuestionnaireBase {

    private static final long serialVersionUID = -6956163917414492339L;

    @Getter
    @Setter
    @Type(type = JsonType.TYPE)
    @JsonType.Conf(driver = "jacksonDriver", locator = SpringDriverLocator.class, type = JsonType.ColumnType.PostgresJsonb)
    @Column(name = "questionnaire")
    private PostQuestionnaire questionnaire;
}
