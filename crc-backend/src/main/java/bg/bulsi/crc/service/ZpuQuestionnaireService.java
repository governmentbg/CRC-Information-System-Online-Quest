package bg.bulsi.crc.service;


import bg.bulsi.crc.api.ZpuQuestionnaireApiDelegate;
import bg.bulsi.crc.api.dto.Company;
import bg.bulsi.crc.api.dto.PostQuestionnaire;
import bg.bulsi.crc.api.dto.QTable;
import bg.bulsi.crc.api.dto.QTableRow;
import bg.bulsi.crc.config.properties.ApplicationProperties;
import bg.bulsi.crc.model.PostNomenclature;
import bg.bulsi.crc.model.profile.CompanyEntity;
import bg.bulsi.crc.model.questionnaire.QuestionnaireStatus;
import bg.bulsi.crc.model.questionnaire.ZpuEntity;
import bg.bulsi.crc.repository.CompanyRepository;
import bg.bulsi.crc.repository.ZpuRepository;
import bg.bulsi.crc.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@Slf4j
public class ZpuQuestionnaireService implements ZpuQuestionnaireApiDelegate {

    private final EntityManager em;
    private final CompanyRepository companyRepository;
    private final ModelMapper mapper;
    private final ApplicationProperties applicationProperties;
    private final ZpuRepository zpuRepository;
    private final SettingsService settingsService;


    @Autowired
    public ZpuQuestionnaireService(EntityManager em, CompanyRepository companyRepository, ModelMapper mapper, ApplicationProperties applicationProperties, ZpuRepository zpuRepository, SettingsService settingsService) {
        this.em = em;
        this.companyRepository = companyRepository;
        this.mapper = mapper;
        this.applicationProperties = applicationProperties;
        this.zpuRepository = zpuRepository;
        this.settingsService = settingsService;
    }

    private static void fillQuestionnaireTables(final PostQuestionnaire questionnaire, final EntityManager entityManager) {
        // Q1
        List<QTable> t = questionnaire.getQuestionnaires().getQ1().getData().getGroups()
                .stream().flatMap(groupOfTables -> groupOfTables.getTables().stream())
                .collect(Collectors.toList());
        fillTables(t, entityManager);
        // Q2
        t = questionnaire.getQuestionnaires().getQ2().getData().getGroups()
                .stream().flatMap(groupOfTables -> groupOfTables.getTables().stream())
                .collect(Collectors.toList());
        fillTables(t, entityManager);
        // Q3
        t = questionnaire.getQuestionnaires().getQ3().getData().getGroups()
                .stream().flatMap(groupOfTables -> groupOfTables.getTables().stream())
                .collect(Collectors.toList());
        fillTables(t, entityManager);
    }

    private static void fillTables(final List<QTable> t, final EntityManager entityManager) {
        t.stream()
                .filter(qTable -> StringUtils.isNoneBlank(qTable.getCodeBase()) && StringUtils.isNoneBlank(qTable.getKey()))
                .forEach(qTable -> {
                    Query query = entityManager.createNativeQuery(String.format("SELECT * FROM %s v WHERE v.key = '%s'", qTable.getCodeBase(), qTable.getKey()), PostNomenclature.class);
                    Query titleQuery = entityManager.createNativeQuery(String.format("SELECT * FROM %s v WHERE v.key = 'h_%s'", qTable.getCodeBase(), qTable.getKey()), PostNomenclature.class);

                    getTableRows(titleQuery, qTable.getColumns().getCount())
                            .stream()
                            .peek(qTableRow -> qTableRow.setRowType(QTableRow.RowTypeEnum.H))
                            .forEach(qTable::addRowsItem);

                    getTableRows(query, qTable.getColumns().getCount())
                            .forEach(qTable::addRowsItem);
                });
    }

    private static List<QTableRow> getTableRows(Query query, int columnCount) {

        //noinspection unchecked
        return ((Stream<PostNomenclature>) query
                .getResultList().stream())
                .map(o -> {
                    QTableRow tableRow = new QTableRow().id(o.getCode()).text(o.getDescription());
                    List<String> values = IntStream.range(0, columnCount).mapToObj(i -> "0").collect(Collectors.toCollection(ArrayList::new));
                    tableRow.setValues(values);

                    return tableRow;
                }).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<PostQuestionnaire> createNewQuestionnaire(Long id) {

        final PostQuestionnaire questionnaire;
        try {

            questionnaire = JsonUtil.loadJsonData(MessageFormat.format("questionnaire/{0}", applicationProperties.getQuestionnaire().getZpuTemplate()), PostQuestionnaire.class);
            questionnaire.year(settingsService.CurrentYear());

        } catch (IOException e) {

            log.error("createNewQuestionnaire", e);
            return ResponseEntity.unprocessableEntity().build();
        }

        if (questionnaire != null && id != null) {

            Optional<CompanyEntity> companyEntity = companyRepository.findById(id);

            if (companyEntity.isPresent()) {

                Company co = mapper.map(companyEntity.get(), Company.class);

                questionnaire.setCompany(co);

                fillQuestionnaireTables(questionnaire, em);

                if (log.isInfoEnabled())
                    log.info(JsonUtil.convertObjectToString(questionnaire));

                return ResponseEntity.ok(questionnaire);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @Override
    @Transactional
    public ResponseEntity<PostQuestionnaire> saveDraftDocument(Long companyId, PostQuestionnaire questionnaire) {

        Optional<ZpuEntity> entity = zpuRepository.findByCompanyIdAndQuestionnaireStatusAndYear(companyId, QuestionnaireStatus.DRAFT, 2019);

        if (entity.isPresent()) {
            entity.get().setQuestionnaire(questionnaire);
            zpuRepository.save(entity.get());

        } else {
            ZpuEntity newZpuEntity = new ZpuEntity();
            newZpuEntity.setQuestionnaire(questionnaire);

            Optional<CompanyEntity> companyEntity = companyRepository.findById(companyId);

            if (companyEntity.isPresent()) {

                newZpuEntity.setCompany(companyEntity.get());
                newZpuEntity.setQuestionnaireStatus(QuestionnaireStatus.DRAFT);

                zpuRepository.save(newZpuEntity);
            } else {
                return ResponseEntity.notFound().build();
            }

        }

        return ResponseEntity.ok(questionnaire);
    }
}
