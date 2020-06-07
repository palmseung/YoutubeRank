$('#remove-my-keyword').on('click', function (event) {
    event.preventDefault();
    removeMyKeyword();
});

$('#search-result-modal').on('hidden.bs.modal', function () {
    location.reload();
})

function removeMyKeyword(){
    var data = {
        keyword: encodeURIComponent($('#hidden_my_keyword').text())
    };

    $.ajax({
          type: 'DELETE',
          url: '/api/keywords/'+data.keyword,
          dataType: 'json',
          async : false,
      }).done(function(response){
          alert('삭제 되었습니다.')
      }).fail(function(error){
          location.reload();
     });
}