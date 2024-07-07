document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('menu-btn').addEventListener('click', function() {
        var navbar = document.querySelector('.navbar');
        navbar.classList.toggle('show');
    });
});
