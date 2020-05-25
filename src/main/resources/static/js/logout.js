function logout() {
    $.ajax({
        type: 'GET',
        url: '/logout',
        async : false,
        success: function(response) {
          localStorage.clear();
          window.location.href = '/';
        },
        error: function() {
          window.location.href = '/';
        }
    });
};