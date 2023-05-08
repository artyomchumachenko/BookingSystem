function hotelDetailsHandler(hotelId) {
    console.log("Нажата кнопка с hotelId: ", hotelId);
    document.getElementById("hotelIdInput").value = hotelId;
    document.getElementById("detailsForm").submit();
}

function addToFavoriteHotelHandler(hotelId) {
    console.log("Нажата кнопка с hotelId: ", hotelId);
    document.getElementById("favoriteHotelId").value = hotelId;
    document.getElementById("addToFavoriteHotelForm").submit();
}