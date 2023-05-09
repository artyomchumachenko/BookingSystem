// Получаем кнопку "Выход"
var logoutButton = document.getElementById("logout-button");

// Добавляем обработчик события клика
logoutButton.addEventListener("click", function () {
    // Удаляем куки пользователя
    eraseCookie("user_uuid");
    eraseCookie("username");
    eraseCookie("role");
    // Переходим на страницу логина
    window.location.href = "/BookingSystem_war/login";
});

// Получаем кнопку "На главную"
var homeButton = document.getElementById("home-button");

// Добавляем обработчик события клика
homeButton.addEventListener("click", function () {
    // Переходим на главную страницу
    window.location.href = "/BookingSystem_war";
});

// Получаем кнопку "Пополнить баланс"
var replenishButton = document.getElementById("replenish-button");

// Добавляем обработчик события клика
replenishButton.addEventListener("click", function () {
    // Получаем роль пользователя из cookie
    var userRole = getCookie("role");
    console.log(userRole);
    // Проверяем роль пользователя и изменяем атрибут "disabled" у кнопки
    if (userRole === "CLIENT") {
        // Переходим на страницу пополнения баланса
        window.location.href = "/BookingSystem_war/replenish";
    }
});

var hotelList = document.getElementById("my-hotels");
if (hotelList != null) {
    // Добавляем обработчик события клика
    hotelList.addEventListener("click", function () {
        // Переходим на главную страницу
        window.location.href = "/BookingSystem_war/my-hotels";
    });
}

// Обработчик событий для кнопки "Проверить" пользователя в слое "Изменение роли"
const checkUserButton = document.getElementById("check-user");
checkUserButton.addEventListener("click", () => {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/BookingSystem_war/profile', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                alert("Операция выполнена успешно, пользователь найден! Роль: " + xhr.responseText);
            } else if (xhr.status === 201) {
                alert("Пользователь не найден!");
            } else {
                console.log('Ошибка ' + xhr.status); // Выводим код ошибки в консоль
            }
        }
    }
    xhr.send('username=' + encodeURIComponent(document.getElementById('username').value));
});

// Обработчик событий для кнопки "Изменить роль" пользователя

// Получаем элементы, с которыми будем работать
var usernameInput = document.getElementById('username');
var roleSelect = document.getElementById('role');
var changeRoleButton = document.getElementById('change-role-button');

// Назначаем обработчик события для кнопки "Изменить роль"
changeRoleButton.addEventListener('click', function () {
    // Создаем объект XMLHttpRequest для отправки запроса на сервер
    var xhr = new XMLHttpRequest();

    // Настраиваем запрос
    xhr.open('POST', '/BookingSystem_war/change-role', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    // Назначаем обработчик события для получения ответа от сервера
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Обработка случая успешной смены роли
            alert('Роль пользователя успешно изменена!');
            window.location.href = "/BookingSystem_war/profile";
        } else if (xhr.status === 201) {
            // Обработка случая ошибки на сервере
            alert('Произошла ошибка при изменении роли пользователя!');
        }
};

// Получаем значения элементов ввода
var username = usernameInput.value;
var role = roleSelect.value;

// Формируем строку с параметрами запроса
var params = 'username=' + encodeURIComponent(username) + '&role=' + encodeURIComponent(role);

// Отправляем запрос на сервер
xhr.send(params);
})
;

