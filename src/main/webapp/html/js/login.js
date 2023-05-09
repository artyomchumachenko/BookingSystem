function login() {
    // Получаем значения полей логина и пароля
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    // Создаем объект FormData для отправки данных формы
    const formData = new FormData();
    formData.append('username', username);
    formData.append('password', password);

    // Отправляем запрос на сервер с использованием метода POST
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "login", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send("username=" + username + "&password=" + password);

    xhr.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            console.log(this.responseText);
            window.location.href = "/BookingSystem_war/profile";
        }
        if (this.status == 204) {
            var errorMessage = document.getElementById("error-message");
            errorMessage.textContent = "Ошибка ввода логина или пароля";
        }
    };
}

function forgotPassword() {
    // Здесь можно добавить код для восстановления пароля
    alert("Функция восстановления пароля");
}

function register() {
    // Переходим на страницу регистрации
    window.location.href = "/BookingSystem_war/registration";
}

function goToHomePage() {
    window.location.href = "/BookingSystem_war";
}