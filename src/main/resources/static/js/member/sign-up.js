$('#signupBtn').on('click', function () {
    create();
});

create = function () {
    var data = {
        email: $('#email').val(),
        name: $('#name').val(),
        password: $('#password').val()
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
        window.location.href = '/login';
    }).fail(function(error){
        alert(JSON.stringify(error));
   });
};

//$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
//    alert('prefilter working');
//    if (options.headers === undefined) {
//      options.headers = {};
//    }
//
//    if (!options.headers.Authorization) {
//      var jwt = localStorage.getItem('accessToken');
//      options.beforeSend = function(xhr) {
//        xhr.setRequestHeader('Authorization', 'Bearer ' + jwt);
//      }
//    }
//});