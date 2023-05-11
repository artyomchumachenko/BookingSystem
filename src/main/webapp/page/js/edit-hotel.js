// Обработчик события для кнопки "Редактировать"
document.getElementById("edit-button").addEventListener("click", function() {
    console.log("Разблокировать редактирование");
    // Разблокировать редактирование полей ввода и кнопку "Сохранить"
    document.getElementById("hotel-name").disabled = false;
    document.getElementById("hotel-address").disabled = false;
    document.getElementById("hotel-description").disabled = false;
    document.getElementById("hotel-icon-url").disabled = false;
    document.getElementById("hotel-phone").disabled = false;
    document.getElementById("hotel-city").disabled = false;

    document.getElementById("save-button").disabled = false;
});

// Обработчик события для кнопки "Сохранить"
document.getElementById("save-button").addEventListener("click", function() {
    console.log("Сохранить изменения, заблокировать редактирование и отправить запрос в БД");

    // Получаем данные из полей ввода
    var hotelName = document.getElementById("hotel-name").value;
    var hotelAddress = document.getElementById("hotel-address").value;
    var hotelDescription = document.getElementById("hotel-description").value;
    var hotelIconUrl = document.getElementById("hotel-icon-url").value;
    var hotelPhone = document.getElementById("hotel-phone").value;
    var hotelCity = document.getElementById("hotel-city").value;
    // Получаем значение параметра hotelId из URL
    const params = new URLSearchParams(window.location.search);
    const hotelId = params.get("hotelId");

    var xhr = new XMLHttpRequest();
    var url = "/BookingSystem_war/save-hotel";
    var paramsString = "hotelName=" + encodeURIComponent(hotelName) +
        "&hotelAddress=" + encodeURIComponent(hotelAddress) +
        "&hotelDescription=" + encodeURIComponent(hotelDescription) +
        "&hotelIconUrl=" + encodeURIComponent(hotelIconUrl) +
        "&hotelPhone=" + encodeURIComponent(hotelPhone) +
        "&hotelCity=" + encodeURIComponent(hotelCity) +
        "&hotelId=" + encodeURIComponent(hotelId);
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log(xhr.responseText);
        } else {
            console.log("Произошла ошибка при сохранении данных в БД.");
        }
    };
    xhr.send(paramsString);

    // Заблокировать редактирование полей ввода и кнопку "Сохранить"
    document.getElementById("hotel-name").disabled = true;
    document.getElementById("hotel-address").disabled = true;
    document.getElementById("hotel-description").disabled = true;
    document.getElementById("hotel-icon-url").disabled = true;
    document.getElementById("hotel-phone").disabled = true;
    document.getElementById("hotel-city").disabled = true;

    document.getElementById("save-button").disabled = true;
});

var backButton = document.getElementById("back-button");
backButton.addEventListener("click", function () {
    window.location.href = "/BookingSystem_war/my-hotels";
});
