package bg.bulsi.crc.service;

import bg.bulsi.crc.model.SettingsEntity;
import bg.bulsi.crc.repository.SettingsRepository;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {

    private final static String YEAR_KEY = "CURRENT_YEAR";

    private final SettingsRepository settingsRepository;

    public SettingsService(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }


    int CurrentYear() {
        SettingsEntity value = settingsRepository.getOne(YEAR_KEY);
        return Integer.parseInt(value.getValue());
    }
}
