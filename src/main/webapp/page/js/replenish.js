// TODO доделать

// Получаем кнопку "Пополнить"
var replenishButton = document.getElementById("replenish-button");

// Добавляем обработчик события клика
replenishButton.addEventListener("click", function () {
    // Получаем данные, введенные пользователем
    var cardNumber = document.getElementById("card-number").value;
    var cvcCode = document.getElementById("cvc-code").value;
    var amount = document.getElementById("amount").value;

    // Проверяем данные на корректность
    if (!cardNumber || !cvcCode || !amount) {
        alert("Пожалуйста, заполните все поля.");
        return;
    }

    if (cardNumber.length !== 16) {
        alert("Пожалуйста, введите правильный номер карты.");
        return;
    }

    if (cvcCode.length !== 3) {
        alert("Пожалуйста, введите правильный cvc-код.");
        return;
    }

    if (isNaN(amount) || amount <= 0) {
        alert("Пожалуйста, введите правильную сумму.");
        return;
    }

    // Отправляем запрос на пополнение баланса
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/BookingSystem_war/replenish", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Если запрос успешно выполнен, переходим на страницу профиля
            window.location.href = "/BookingSystem_war/profile";
        } else if (xhr.readyState === 4 && xhr.status !== 200) {
            // Если произошла ошибка, выводим сообщение об ошибке
            alert("Произошла ошибка при пополнении баланса. Пожалуйста, попробуйте еще раз.");
        }
    };
    xhr.send("cardNumber=" + cardNumber + "&cvcCode=" + cvcCode + "&amount=" + amount);
});