/**
 * Слушатель события, который отвечает за реакцию на нажатие кнопки "Подробнее"
 */
function hotelDetailsHandler(hotelId) {
    openHotelDetails(hotelId);
}

function openHotelDetails(hotelId) {
    console.log("Нажата кнопка с hotelId: ", hotelId);
    document.getElementById("hotelIdInput").value = hotelId;
    document.getElementById("detailsForm").submit();
}

/**
 * Слушатель события нажатия на кнопку "Удалить/Добавить в избранное" отель
 */
function addToFavoriteHotelHandler(hotelId) {
    // добавить/удалить отель из списка избранных
    toggleFavoriteButton(hotelId);
}

function toggleFavoriteButton(buttonId) {
    var userId = getCookie("user_uuid");
    if (userId == null) {
        alert("Пользователь не авторизован в системе");
        return;
    }

    var button = document.getElementById(buttonId);

    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/BookingSystem_war', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log('Запрос выполнен успешно');
        }
    };
    if (button.innerHTML === "Добавить в избранное") {
        xhr.send('hotelId=' + buttonId + '&action=' + "add");
        button.innerHTML = "Удалить из избранного";
    } else {
        xhr.send('hotelId=' + buttonId + '&action=' + "remove");
        button.innerHTML = "Добавить в избранное";
    }
}
