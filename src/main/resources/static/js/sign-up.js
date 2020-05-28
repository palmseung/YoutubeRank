$('#signUpBtn').on('click', function (event) {
    event.preventDefault();
    create();
});

$('#adminSignUpBtn').on('click', function (event) {
    event.preventDefault();
    createAdmin();
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
        alert('입력정보를 다시 확인해 주세요');
   });
};

createAdmin = function () {
    var data = {
        email: $('#inputEmail').val(),
        name: $('#inputName').val(),
        password: $('#inputPassword').val()
    };

    $.ajax({
        type: 'POST',
        url: '/api/admin',
        dataType: 'json',
        async : false,
        contentType: 'application/json',
        data: JSON.stringify(data)
    }).done(function(){
        alert(data.name + '님의 어드민 계정이 생성 되었습니다.');
        window.location.href = '/';
    }).fail(function(error){
        alert('admin 계정 가입이 거부되었습니다.');
   });
};