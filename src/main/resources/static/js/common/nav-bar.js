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
          alert('success');
          window.location.href = '/';
          window.location.reload();
        },
        error: function() {
          alert("Sorry, you are not logged in.");
        }
    });
};

