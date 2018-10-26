function identify() {
    var username = document.getElementById('username').value;
    if (!username || username === "") {
        alert("Please enter your username to access application.");
        return;
    }
    localStorage.setItem('username', username);
    window.location.href = "/";
}