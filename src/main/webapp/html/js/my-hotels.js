// Получаем элемент списка отелей
var hotelsList = document.getElementById("hotels-list");

// Добавляем отели в список
for (var i = 0; i < hotels.length; i++) {
    var hotel = hotels[i];
    var hotelItem = document.createElement("li");

    // Создаем элементы для названия отеля и кнопок
    var hotelName = document.createElement("span");
    hotelName.textContent = hotel.name;

    var viewButton = document.createElement("button");
    viewButton.id = hotel.id;
    viewButton.addEventListener("click", function() {
        window.location.href = "/BookingSystem_war/details?hotelId=" + this.id;
    });
    viewButton.textContent = "Просмотр";

    var editButton = document.createElement("button");
    editButton.textContent = "Редактирование";

    var deleteButton = document.createElement("button");
    deleteButton.textContent = "Удалить";

    // Добавляем элементы в элемент списка
    hotelItem.appendChild(hotelName);
    hotelItem.appendChild(viewButton);
    hotelItem.appendChild(editButton);
    hotelItem.appendChild(deleteButton);

    // Добавляем элемент списка в список отелей
    hotelsList.appendChild(hotelItem);
}