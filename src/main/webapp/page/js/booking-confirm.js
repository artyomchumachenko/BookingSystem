var payButton = document.getElementById("pay-button");
payButton.addEventListener("click", function () {
    // Получить все элементы внутри тега <fieldset id="payer-details">
    var payerDetailsInputs = document.querySelectorAll('#payer-details input');

    // Проверить, что все поля заполнены
    var isPayerDetailsValid = true;
    for (var i = 0; i < payerDetailsInputs.length; i++) {
        if (payerDetailsInputs[i].value.trim() === '') {
            isPayerDetailsValid = false;
            break;
        }
    }

    if (!isPayerDetailsValid) {
        // Не все поля заполнены
        alert("Внимание! Проверьте данные плательщика!");
        return;
    } else {
        // Все поля заполнены
        var confirmResult = confirm("Подтвердить оплату");
        if (confirmResult) {
            // Пользователь подтвердил оплату
            // Можно выполнить необходимые действия
            var urlParams = new URLSearchParams(window.location.search);
            var numGuests = urlParams.get('numGuests');
            var roomId = urlParams.get('roomId');
            var checkInDate = urlParams.get('checkInDate');
            var checkOutDate = urlParams.get('checkOutDate');
            var hotelId = urlParams.get('hotelId');
            var userId = urlParams.get('userId');
            var totalPrice = urlParams.get('totalPrice');
            // Составляем URL с параметрами
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function() {
                if (this.readyState === 4 && this.status === 200) {
                    // Обработка ответа сервера
                    var serverResponse = this.responseText;
                    console.log(serverResponse);

                    // Отключение второй формы для заполнения
                    payButton.disabled = true;
                    var payerDetails = document.querySelector('#payer-details');
                    payerDetails.disabled = true;

                    // после этого можно взять создать pdf файл текущей страницы и отправить его
                    // в БД отдельным запросом и потом скачивать Ticket.pdf?
                    window.location.href = "/BookingSystem_war/booking-history";
                }
            };
            xhr.open("POST", "/BookingSystem_war/payment", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.send(
                "userId=" + encodeURI(userId)
                + "&hotelId=" + encodeURI(hotelId)
                + "&roomId=" + encodeURI(roomId)
                + "&checkInDate=" + encodeURI(checkInDate)
                + "&checkOutDate=" + encodeURI(checkOutDate)
                + "&totalPrice=" + encodeURI(totalPrice)
                + "&numGuests=" + encodeURI(numGuests)
            );
        } else {
            return;
            // Пользователь отменил оплату
            // Можно не выполнять дальнейшие действия
        }
    }

    console.log("Всё прошло успешно");
});