document.addEventListener('DOMContentLoaded', function () {
    var sidebarToggle = document.getElementById('sidebarToggle');
    var sidebarToggleTop = document.getElementById('sidebarToggleTop');
    var sidebar = document.querySelector('.sidebar');

    if (sidebarToggle) {
        sidebarToggle.addEventListener('click', function () {
            sidebar.classList.toggle('show');
        });
    }

    if (sidebarToggleTop) {
        sidebarToggleTop.addEventListener('click', function () {
            sidebar.classList.toggle('show');
        });
    }

    var scrollToTop = document.querySelector('.scroll-to-top');
    if (scrollToTop) {
        window.addEventListener('scroll', function () {
            if (window.pageYOffset > 400) {
                scrollToTop.style.display = 'flex';
            } else {
                scrollToTop.style.display = 'none';
            }
        });
    }
});
