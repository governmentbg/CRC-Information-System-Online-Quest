package bg.bulsi.crc.service;


import bg.bulsi.crc.api.dto.PostQuestionnaire;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.io.IOException;

import static bg.bulsi.crc.IntegrationTestUtil.loadJsonData;

@SpringBootTest
@Transactional
public class ZpuQuestionnaireServiceTest {


    @Autowired
    private ZpuQuestionnaireService zpuQuestionnaireService;

    @Test
    public void create_questionnaire_test() {
        zpuQuestionnaireService.createNewQuestionnaire(36L);
    }

    @Test
    @Rollback(value = false)
    public void save_draft_test() throws IOException {

        PostQuestionnaire payload = loadJsonData("json/ZpuQ.json", PostQuestionnaire.class);

        zpuQuestionnaireService.saveDraftDocument(36L, payload);
    }
}
