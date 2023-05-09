function register() {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;
    var email = document.getElementById("email").value;

    var errorMessage = document.getElementById("error-message");

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "registration", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send("username=" + username + "&password=" + password + "&email=" + email + "&confirm=" + confirmPassword);

    xhr.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            alert("Регистрация прошла успешно!");
            window.location.href = "/BookingSystem_war/login";
            return;
        }
        if (this.status == 204) {
            errorMessage.textContent = "Ошибка регистрации";
        }
    };
}