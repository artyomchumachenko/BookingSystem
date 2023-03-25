package entity;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Класс сущности Hotel
 */
@Getter
public class Hotel {

    // Поля для хранения атрибутов отеля
    private int hotel_id; // уникальный идентификатор отеля в таблице
    private String hotel_name; // название отеля
    private String address; // адрес отеля
    private String city; // город, где расположен отель
    private String state; // штат или провинция, в которой расположен отель
    private String country; // страна, в которой расположен отель
    private String phone; // номер телефона отеля
    private String email; // адрес электронной почты отеля
    private String website; // веб-сайт отеля
    private String description; // описание отеля
    private double rating; // рейтинг отеля
    private int num_rooms; // количество номеров в отеле
    private String checkin_time; // время заезда в отель
    private String checkout_time; // время выезда из отеля
    private LocalDateTime created_at; // дата и время создания записи об отеле
    private String photo_link; // ссылка на фотографию отеля

    // Конструктор с параметрами для инициализации полей
    public Hotel(int hotel_id, String hotel_name, String address, String city, String state, String country, String phone, String email, String website, String description, double rating, int num_rooms, String checkin_time, String checkout_time, String photo_link) {
        this.hotel_id = hotel_id;
        this.hotel_name = hotel_name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.phone = phone;
        this.email = email;
        this.website = website;
        this.description = description;
        this.rating = rating;
        this.num_rooms = num_rooms;
        this.checkin_time = checkin_time;
        this.checkout_time = checkout_time;
        this.created_at = LocalDateTime.now(); // устанавливаем текущую дату и время создания записи
        this.photo_link = photo_link;
    }
}