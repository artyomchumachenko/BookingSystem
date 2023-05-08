// Получаем кнопку "Выход"
var logoutButton = document.getElementById("logout-button");

// Добавляем обработчик события клика
logoutButton.addEventListener("click", function () {
    // Удаляем куки пользователя
    document.cookie = "username=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/BookingSystem_war;";
    document.cookie = "password=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/BookingSystem_war;";
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