package bg.bulsi.crc.service;

import bg.bulsi.crc.api.EkatteApiDelegate;
import bg.bulsi.crc.api.dto.Ekatte;
import bg.bulsi.crc.api.dto.EkatteReq;
import bg.bulsi.crc.model.ekatte.EkatteEntity;
import bg.bulsi.crc.repository.EkatteRepository;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EkatteApiService implements EkatteApiDelegate {

    private final EkatteRepository ekatteRepository;
    private final ModelMapper mapper;

    @Autowired
    public EkatteApiService(ModelMapper mapper, EkatteRepository ekatteRepository) {
        this.mapper = mapper;
        this.ekatteRepository = ekatteRepository;
    }

    @PostConstruct
    void postConstruct() {


        mapper.createTypeMap(EkatteEntity.class, Ekatte.class)
                .addMapping(EkatteEntity::getEkatte, Ekatte::setId)
                .addMapping(EkatteEntity::getArea, Ekatte::setDistrict)
                .setPostConverter(context -> {
                    Ekatte ekatte = context.getDestination();
                    EkatteEntity source = context.getSource();

                    ekatte.setFullName(
                            source == null ? "" : source.getPlace() + "(" +
                                    (source.getPlaceType() == null ? "" : source.getPlaceType().getPlaceName()) + "), общ." +
                                    source.getMunicipality() + ", обл." + source.getArea()
                    );
                    return context.getDestination();
                });


    }

    @Override
    public ResponseEntity<List<Ekatte>> getByPlace(EkatteReq placeReq) {

        if (StringUtils.isEmpty(placeReq.getPlace())) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        List<EkatteEntity> list = ekatteRepository.findByPlaceContainingOrderByPlace(placeReq.getPlace() + "%");

        if (list.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        List<Ekatte> collect = list.stream().map(ekatteEntity -> mapper.map(ekatteEntity, Ekatte.class))
                .collect(Collectors.toList()).stream().peek(ekatte -> {
                    ekatte.setDistrict(null);
                    ekatte.setMunicipality(null);
                    ekatte.setPlace(null);
                    ekatte.setType(null);
                }).collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collect);
    }
}