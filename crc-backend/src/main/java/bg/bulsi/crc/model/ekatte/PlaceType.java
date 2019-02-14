package bg.bulsi.crc.model.ekatte;

import lombok.Getter;
import lombok.Setter;

public enum PlaceType {

    VILLAGE("с."),
    MONASTERY("манастир"),
    CITY("гр.");

    @Getter
    @Setter
    private String placeName;

    PlaceType(String name) {
        this.placeName = name;
    }
}
