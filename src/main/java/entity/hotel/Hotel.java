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
    private String country;
    private String city;
    private String address;
    private String description;
    private String profileIcon;
    private String phoneNumber;

    public Hotel(UUID hotelId, String name, String country, String city, String address, String description, String profileIcon, String phoneNumber) {
        this.hotelId = hotelId;
        this.name = name;
        this.country = country;
        this.city = city;
        this.address = address;
        this.description = description;
        this.profileIcon = profileIcon;
        this.phoneNumber = phoneNumber;
    }
}