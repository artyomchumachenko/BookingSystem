var urlParams = new URLSearchParams(window.location.search);
var hotelId = urlParams.get('hotelId'); // здесь указываем название параметра
var userId = urlParams.get('userId'); // здесь указываем название параметра

var backButton = document.getElementById("back-button");
// Добавляем обработчик события клика
backButton.addEventListener("click", function () {
    // здесь указываем параметры запроса
    window.location.href = "/BookingSystem_war/details?hotelId=" + hotelId;
});

const calculatePriceButton = document.getElementById('calculate-price-button');
const bookButton = document.getElementById('book-button');

var adultsSelect;
var childrenSelect;
var roomTypeSelect;
var checkInDateInput;
var checkOutDateInput;
var totalPrice;
// добавляем обработчик события клика на кнопку "Рассчитать цену"
calculatePriceButton.addEventListener('click', () => {
    adultsSelect = document.getElementById("adults").value;
    childrenSelect = document.getElementById("children").value;
    roomTypeSelect = document.getElementById("room-type").value;
    checkInDateInput = document.getElementById("check-in-date").value;
    checkOutDateInput = document.getElementById("check-out-date").value;

    if (!clientValidationBeforeCalculationPrice()) return;

    // Составляем URL с параметрами
    var url = "/BookingSystem_war/calculation-price?adults=" + encodeURIComponent(adultsSelect)
        + "&children=" + encodeURIComponent(childrenSelect)
        + "&room-type=" + encodeURIComponent(roomTypeSelect)
        + "&check-in-date=" + encodeURIComponent(checkInDateInput)
        + "&check-out-date=" + encodeURIComponent(checkOutDateInput)
        + "&hotelId=" + encodeURIComponent(hotelId);

    // Отправляем GET-запрос на сервер
    var xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);

    // устанавливаем заголовок Content-Type для правильной обработки запроса на сервере
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    // Обрабатываем ответ от сервера
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            var totalPriceLabel = document.getElementById("total-price-label");
            var response = xhr.responseText;
            if (xhr.status === 200) {
                totalPriceLabel.innerHTML = "Итоговая цена: " + response + " руб.";
                totalPrice = response;
                bookButton.disabled = false;
                bookButton.style.backgroundColor = "#007bff";
            } else {
                totalPriceLabel.innerHTML = "Итоговая цена: " + response;
                console.error("Произошла ошибка при получении данных от сервера");
            }
        }
    };

    xhr.send();
});

// Если меняются данные формы для Бронирования блокируем кнопку для "Забронировать"
const form = document.querySelector('form');
form.addEventListener('change', () => {
    console.log('Что-то изменилось');
    bookButton.style.backgroundColor = "grey";
    bookButton.disabled = true;
});

// Обработчик событий для кнопки "Забронировать
bookButton.addEventListener("click", function () {
    var numGuests = parseInt(adultsSelect) + parseInt(childrenSelect);
    // Составляем URL с параметрами
    var url = "?numGuests=" + encodeURIComponent(numGuests)
        + "&roomId=" + encodeURIComponent(roomTypeSelect)
        + "&checkInDate=" + encodeURIComponent(checkInDateInput)
        + "&checkOutDate=" + encodeURIComponent(checkOutDateInput)
        + "&hotelId=" + encodeURIComponent(hotelId)
        + "&userId=" + encodeURIComponent(userId)
        + "&totalPrice=" + encodeURIComponent(totalPrice);
    window.location.href = "/BookingSystem_war/booking-confirm" + url;
});

function clientValidationBeforeCalculationPrice() {
    // Проверка на обязательное заполнение всех полей
    if (!adultsSelect || !childrenSelect || !roomTypeSelect || !checkInDateInput || !checkOutDateInput) {
        alert("Пожалуйста, заполните все поля.");
        return false;
    }

// Проверка на корректность дат
    var currentDate = new Date();
    var checkInDate = new Date(checkInDateInput);
    var checkOutDate = new Date(checkOutDateInput);

    if (checkInDate >= checkOutDate) {
        alert("Дата заезда должна быть раньше даты выезда.");
        return false;
    }

    if (new Date(checkInDate).setHours(0, 0, 0, 0) < new Date(currentDate).setHours(0, 0, 0, 0)) {
        alert("Дата заезда должна быть не раньше сегодняшней даты.");
        return false;
    }

    return true;
}

function loadRoomTypes() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/BookingSystem_war/room-types', true);
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var roomTypes = JSON.parse(xhr.responseText);
            var select = document.getElementById('room-type');
            roomTypes.forEach(function (room) {
                var option = document.createElement('option');
                option.value = room.roomId;
                option.text = room.name;
                select.appendChild(option);
            });
        } else {
            console.log('Ошибка ' + xhr.status + ': ' + xhr.statusText);
        }
    };
    xhr.send();
}

window.onload = function () {
    bookButton.style.backgroundColor = "grey";
    loadRoomTypes();
};