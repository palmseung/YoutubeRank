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
          alert("Sorry, you are not logged in.");
        }
    });
};