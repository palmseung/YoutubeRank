function home() {
    $.ajax({
        type: 'GET',
        url: '/',
        async : false,
        beforeSend : function (xhr) {
        if (localStorage.getItem('accessToken') != null) {
            xhr.setRequestHeader('Authorization',
            localStorage.getItem('accessToken').replace(/^"(.*)"$/, '$1'));
        }},
        success: function(response) {
          window.location.href = '/';
        },
        error: function() {
          window.location.href = '/';
        }
    });
};

function logout() {
    $.ajax({
        type: 'GET',
        url: '/logout',
        async : false,
        success: function(response) {
          localStorage.clear();
          window.location.href = '/login';
        },
        error: function() {
          window.location.href = '/';
        }
    });
};

