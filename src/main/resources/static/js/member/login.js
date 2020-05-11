$('#loginBtn').on('click', function () {
    login();
});

login = function () {
    var data = {
        email: $('#email').val(),
        password: $('#password').val(),
    };

    $.ajax({
        type: 'POST',
        url: '/api/members/login',
        dataType: 'json',
        async : false,
        contentType: 'application/json',
        data: JSON.stringify(data)
    }).done(function(response){
        alert('로그인 되었습니다.');
        window.location.href = '/';
        localStorage.setItem("accessToken", response.accessToken);
        localStorage.setItem("tokenType", response.tokenType);
    }).fail(function(error){
       alert(JSON.stringify(error));
   });
};