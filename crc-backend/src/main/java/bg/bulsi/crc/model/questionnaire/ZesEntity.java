package bg.bulsi.crc.model.questionnaire;

import bg.bulsi.crc.api.dto.ZesQuestionnaire;
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
@Table(name = "zes_questionnaire", indexes = {
        @Index(name = "ix_unique_q", columnList = "company_id, year, status, q_version")
})
public class ZesEntity extends QuestionnaireBase {

    private static final long serialVersionUID = -3889444072641528387L;

    @Getter
    @Setter
    @Type(type = JsonType.TYPE)
    @JsonType.Conf(driver = "jacksonDriver", locator = SpringDriverLocator.class, type = JsonType.ColumnType.PostgresJsonb)
    @Column(name = "questionnaire")
    private ZesQuestionnaire questionnaire;

}
