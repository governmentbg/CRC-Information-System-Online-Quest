package bg.bulsi.crc.repository;


import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Disabled
public class NomenclatureTest {

  /*  @Autowired
    private PostQuestionnaireRepository postQuestionnaireRepository;

    @Test
    public void select_byKey_test() {
        List<PostNomenclature> aa = postQuestionnaireRepository.getByKeyOrderByCode("1.1.1.1.");
        aa.size();
    }*/
}
