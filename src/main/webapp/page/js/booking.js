var backButton = document.getElementById("back-button");
// Добавляем обработчик события клика
backButton.addEventListener("click", function () {
    var urlParams = new URLSearchParams(window.location.search);
    var hotelId = urlParams.get('hotelId'); // здесь указываем название параметра

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
    ;

    // Отправляем GET-запрос на сервер
    var xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    // устанавливаем заголовок Content-Type для правильной обработки запроса на сервере
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send();
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

    if (checkInDate < currentDate) {
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
    loadRoomTypes();
};