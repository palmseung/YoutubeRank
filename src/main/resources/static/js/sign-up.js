$('#signUpBtn').on('click', function (event) {
    event.preventDefault();
    create();
});

create = function () {
    var data = {
        email: $('#inputEmail').val(),
        name: $('#inputName').val(),
        password: $('#inputPassword').val()
    };

    $.ajax({
        type: 'POST',
        url: '/api/members',
        dataType: 'json',
        async : false,
        contentType: 'application/json',
        data: JSON.stringify(data)
    }).done(function(){
        alert(data.name + '님 회원 가입을 환영합니다.');
        window.location.href = '/';
    }).fail(function(error){
        alert(JSON.stringify(error));
   });
};