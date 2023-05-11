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
    viewButton.addEventListener("click", function () {
        window.location.href = "/BookingSystem_war/details?hotelId=" + this.id;
    });
    viewButton.textContent = "Просмотр";

    var editButton = document.createElement("button");
    editButton.id = hotel.id;
    editButton.addEventListener("click", function () {
        window.location.href = "/BookingSystem_war/edit?hotelId=" + this.id;
    });
    editButton.textContent = "Редактирование";

    var deleteButton = document.createElement("button");
    deleteButton.id = hotel.id;
    deleteButton.addEventListener("click", function () {
        if (confirm("Вы действительно хотите выполнить это действие?")) {
            // Код, который будет выполнен при подтверждении
            window.location.href = "/BookingSystem_war/delete?hotelId=" + this.id;
        }
    });
    deleteButton.textContent = "Удалить";

    // Добавляем элементы в элемент списка
    hotelItem.appendChild(hotelName);
    hotelItem.appendChild(viewButton);
    hotelItem.appendChild(editButton);
    hotelItem.appendChild(deleteButton);

    // Добавляем элемент списка в список отелей
    hotelsList.appendChild(hotelItem);

    var homeButton = document.getElementById("home-button")
    homeButton.addEventListener("click", function () {
        window.location.href = "/BookingSystem_war/profile";
    });
}