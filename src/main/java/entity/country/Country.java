package entity.country;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Country {

    private UUID countryId;
    private String name;

    public Country() {}

    public Country(String name) {
        this.countryId = UUID.randomUUID();
        this.name = name;
    }
}
