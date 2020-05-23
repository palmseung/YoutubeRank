$('#editPasswordBtn').on('click', function () {
    editPassword();
});

editPassword = function () {
    var data = {
        newPassword : $('#editPassword').val()
    };

    $.ajax({
        type: 'PUT',
        url: '/api/members/my-info/' + $('#editId').val(),
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