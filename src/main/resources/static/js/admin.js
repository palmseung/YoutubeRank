function adminMember() {
    $.ajax({
        type: 'GET',
        url: '/admin/members',
        async : false,
        dataType : 'html',
        beforeSend : function (xhr) {
        if (localStorage.getItem('accessToken') != null) {
            alert(localStorage.getItem('accessToken'));
            xhr.setRequestHeader('Authorization',
            localStorage.getItem('accessToken').replace(/^"(.*)"$/, '$1'));
        }},
        success: function(response) {
          alert('admin success');
          $('#insert').replaceWith(response);
          $('#insert-admin-keyword-list').replaceWith(response);
        },
        error: function() {
          alert('failure');
        }
    });
};

function adminKeyword() {
    $.ajax({
        type: 'GET',
        url: '/admin/keywords',
        async : false,
        dataType : 'html',
        beforeSend : function (xhr) {
        if (localStorage.getItem('accessToken') != null) {
            alert(localStorage.getItem('accessToken'));
            xhr.setRequestHeader('Authorization',
            localStorage.getItem('accessToken').replace(/^"(.*)"$/, '$1'));
        }},
        success: function(response) {
          alert('admin keyword success');
          $('#insert-admin-member-list').replaceWith(response);
        },
        error: function() {
          alert('failure');
        }
    });
};