$('#edit-password-btn').on('click', function () {
    alert('dfd');
    editPassword();
});

editPassword = function () {
    var data = {
        newPassword : $('#newPassword').val()
    };

    $.ajax({
        type: 'PUT',
        url: '/api/members/my-info/1',
        dataType: 'json',
        async : false,
        contentType: 'application/json',
        data: JSON.stringify(data),
        beforeSend : function(xhr) {
            xhr.setRequestHeader("Authorization", localStorage.getItem("accessToken"));
        }
    }).done(function(response){
        alert('비밀번호가 변경되었습니다.');
        window.location.href = '/';
    }).fail(function(error){
       alert(JSON.stringify(error));
   });
};