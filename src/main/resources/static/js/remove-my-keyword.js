$('#remove-my-keyword').on('click', function (event, keyword) {
    event.preventDefault();
    confirm('정말로 삭제 하시겠습니까?');
    removeMyKeyword(keyword);
});

$('#search-result-modal').on('hidden.bs.modal', function () {
    location.reload();
})

function removeMyKeyword(keyword){
    var data = {
        keyword: encodeURIComponent(keyword)
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