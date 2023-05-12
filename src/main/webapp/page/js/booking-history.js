function loadHistory() {
    fetch('/BookingSystem_war/history')
        .then(response => response.json())
        .then(data => {
            const bookings = data.bookings;
            const rooms = data.rooms;
            const hotels = data.hotels;
            const table = document.getElementById('bookingsTable');
            console.log(data);
            // Итерация по списку бронирований
            for (let i = 0; i < bookings.length; i++) {
                const row = document.createElement('tr');
                const booking = bookings[i];
                const hotel = hotels[i];
                const room = rooms[i];
                row.innerHTML = `
                        <td>${hotel.hotelName}</td>
                        <td>${room.name}</td>
                        <td>${booking.checkInDate}</td>
                        <td>${booking.checkOutDate}</td>
                        <td>${booking.totalPrice} руб.</td>
                        <td>${booking.numGuests}</td>
                        <td>${booking.bookingDate}</td>`;
                table.appendChild(row);
            }
        });
}

window.onload = function () {
    loadHistory();
};

var backButton = document.getElementById("back-button");
backButton.addEventListener("click", function () {
    window.location.href = "/BookingSystem_war/profile";
});