function loadCities() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/BookingSystem_war/cities', true);
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var cities = JSON.parse(xhr.responseText);
            var select = document.getElementById('city');
            cities.forEach(function(city) {
                var option = document.createElement('option');
                option.value = city.cityId;
                option.text = city.name;
                console.log(city.name);
                console.log(option);
                select.appendChild(option);
            });
        } else {
            console.log('Ошибка ' + xhr.status + ': ' + xhr.statusText);
        }
    };
    xhr.send();
}