document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('menu-btn').addEventListener('click', function() {
        var navbar = document.querySelector('.navbar');
        navbar.classList.toggle('show');
    });
});

function toggleDropdownSort() {
  document.getElementById("dropdown-menu").classList.toggle("show");
}

function toggleDropdownFilter() {
  document.getElementById("dropdown-menu-filter2").classList.toggle("show");
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
  if (!event.target.matches('.filter2-button') && !event.target.matches('.filter2-icon') && !event.target.matches('.filter2-text')) {
      var dropdowns2 = document.getElementsByClassName("dropdown-content-filter2");
      for (var i = 0; i < dropdowns2.length; i++) {
          var openDropdown2 = dropdowns2[i];
          if (openDropdown2.classList.contains('show')) {
              openDropdown2.classList.remove('show');
          }
      }
  }
}

