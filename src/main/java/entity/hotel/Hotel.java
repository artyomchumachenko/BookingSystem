package entity.hotel;

import lombok.Getter;

import java.util.UUID;

/**
 * Класс сущности Hotel
 */
@Getter
public class Hotel {
    private UUID hotelId;
    private String name;
    private String address;
    private String description;
    private String profileIcon;
    private String phoneNumber;
    private UUID cityId;

    public Hotel() {}

    public Hotel(UUID hotelId, String name, String address, String description, String profileIcon, String phoneNumber, UUID cityId) {
        this.hotelId = hotelId;
        this.name = name;
        this.address = address;
        this.description = description;
        this.profileIcon = profileIcon;
        this.phoneNumber = phoneNumber;
        this.cityId = cityId;
    }
}