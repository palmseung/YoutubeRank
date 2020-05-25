$('#loginBtn').on('click', function () {
    login();
});

login = function () {
    var data = {
        email: $('#loginEmail').val(),
        password: $('#loginPassword').val(),
    };

    $.ajax({
        type: 'POST',
        url: '/api/members/login',
        dataType: 'json',
        async : false,
        contentType: 'application/json',
        data: JSON.stringify(data)
    }).done(function(response){
        localStorage.setItem("accessToken", JSON.stringify(response.accessToken));
        localStorage.setItem("tokenType", JSON.stringify(response.tokenType));

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
                  alert('login success');
                  window.location.href = '/';
                },
                error: function() {
                  alert("Sorry, you are not logged in.");
                }
            });
    }).fail(function(error){
       alert(JSON.stringify(error));
   });
};