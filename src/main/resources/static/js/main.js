document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('menu-btn').addEventListener('click', function() {
        var navbar = document.querySelector('.navbar');
        navbar.classList.toggle('show');
    });
});

function toggleDropdown() {
    document.getElementById("dropdown-menu").classList.toggle("show");
  }
  

  window.onclick = function(event) {
    if (!event.target.matches('.filter-button') && !event.target.matches('.filter-icon') && !event.target.matches('.filter-text')) {
      var dropdowns = document.getElementsByClassName("dropdown-content");
      for (var i = 0; i < dropdowns.length; i++) {
        var openDropdown = dropdowns[i];
        if (openDropdown.classList.contains('show')) {
          openDropdown.classList.remove('show');
        }
      }
    }
  }
  