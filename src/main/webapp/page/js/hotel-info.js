// Получаем элементы DOM
var hotelNameInput = document.getElementById('hotel-name');
var hotelAddressInput = document.getElementById('hotel-address');
var hotelDescriptionTextarea = document.getElementById('hotel-description');
var hotelIconUrlInput = document.getElementById('hotel-icon-url');
var hotelPhoneInput = document.getElementById('hotel-phone');
var hotelCitySelect = document.getElementById('hotel-city');

// Загружаем данные об отеле
function loadHotel(hotelId) {
    console.log("Try to load hotel");
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/BookingSystem_war/hotel-info?hotelId=' + hotelId, true);
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var hotel = JSON.parse(xhr.responseText);
            hotelNameInput.value = hotel.hotelName;
            hotelAddressInput.value = hotel.address;
            hotelDescriptionTextarea.value = hotel.description;
            hotelIconUrlInput.value = hotel.profileIconUrl;
            hotelPhoneInput.value = hotel.phoneNumber;
        } else {
            console.log('Ошибка ' + xhr.status + ': ' + xhr.statusText);
        }
    };
    xhr.send();
}

// Вызываем функцию загрузки городов и информации об отеле при загрузке страницы
window.onload = function() {
    // Получаем значение параметра hotelId из URL
    const params = new URLSearchParams(window.location.search);
    const hotelId = params.get("hotelId");
    console.log(hotelId);
    loadHotel(hotelId);
    loadCities1();
};

function loadCities1() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/BookingSystem_war/cities', true);
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var cities = JSON.parse(xhr.responseText);
            var select = document.getElementById('hotel-city');
            cities.forEach(function(city) {
                var option = document.createElement('option');
                option.value = city.cityId;
                option.text = city.name;
                console.log(city.name);
                console.log(option);
                select.appendChild(option);
            });
        } else {
            console.log('Ошибка ' + xhr.status + ': ' + xhr.statusText);
        }
    };
    xhr.send();
}
