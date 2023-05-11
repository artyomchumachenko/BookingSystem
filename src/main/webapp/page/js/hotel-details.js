var bookingButton = document.getElementById("book-button");

// Добавляем обработчик события клика
bookingButton.addEventListener("click", function () {
    var urlParams = new URLSearchParams(window.location.search);
    var hotelId = urlParams.get('hotelId'); // здесь указываем название параметра
    var userId = getCookie("user_uuid"); // берём userId из cookie

     // здесь указываем параметры запроса
    window.location.href = "/BookingSystem_war/booking?hotelId=" + hotelId + "&userId=" + userId;
});

function exampleRequestToServer() {
    // var xhr = new XMLHttpRequest();
    // xhr.open("GET", url);
    //
    // xhr.onload = function() {
    //     if (xhr.status === 200) {
    //         console.log(xhr.responseText);
    //         window.location.href = url; // перенаправляем пользователя на страницу, которую вернул сервер
    //     }
    //     else {
    //         console.log('Request failed.  Returned status of ' + xhr.status);
    //     }
    // };
    //
    // xhr.send();
}