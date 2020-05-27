$('#searchBtn').on('click', function (event) {
    event.preventDefault();
    search();
});

$('#search-result-modal').on('hidden.bs.modal', function () {
    location.reload();
})

function search(){
    var data = {
        keyword: $('#search').val()
    };

    $.ajax({
          type: 'POST',
          url: '/api/keywords?keyword='+data.keyword,
          dataType: 'json',
          async : false,
          contentType: 'application/json;charset=utf-8',
          data: JSON.stringify(data)
      }).done(function(response){
          $('#first_title').text(response[0].title);
          $('#first_viewCount').text(response[0].viewCount);
          $('#first_thumbnail').attr('src', response[0].thumbnailUrl);
          $('#first_url').attr('href', response[0].url);

          $('#second_title').text(response[1].title);
          $('#second_viewCount').text(response[1].viewCount);
          $('#second_thumbnail').attr('src', response[1].thumbnailUrl);
          $('#second_url').attr('href', response[1].url);

          $('#third_title').text(response[2].title);
          $('#third_viewCount').text(response[2].viewCount);
          $('#third_thumbnail').attr('src', response[2].thumbnailUrl);
          $('#third_url').attr('href', response[2].url);

          $('#fourth_title').text(response[3].title);
          $('#fourth_viewCount').text(response[3].viewCount);
          $('#fourth_thumbnail').attr('src', response[3].thumbnailUrl);
          $('#fourth_url').attr('href', response[3].url);

          $('#fifth_title').text(response[4].title);
          $('#fifth_viewCount').text(response[4].viewCount);
          $('#fifth_thumbnail').attr('src', response[4].thumbnailUrl);
          $('#fifth_url').attr('href', response[4].url);

          $('#keyword').text($('#search').val());
          $('#search-result-modal').modal('show');
      }).fail(function(error){
          alert('이미 입력된 키워드입니다.');
          location.reload();
     });
}

function searchKeyword(keyword){
    var data = {
        keyword: encodeURIComponent(keyword)
    };

    $.ajax({
          type: 'GET',
          url: '/api/youtube?keyword='+data.keyword,
          dataType: 'json',
          async : false,
      }).done(function(response){
          $('#my_first_title').text(response[0].title);
          $('#my_first_viewCount').text(response[0].viewCount);
          $('#my_first_thumbnail').attr('src', response[0].thumbnailUrl);
          $('#my_first_url').attr('href', response[0].url);

          $('#my_second_title').text(response[1].title);
          $('#my_second_viewCount').text(response[1].viewCount);
          $('#my_second_thumbnail').attr('src', response[1].thumbnailUrl);
          $('#my_second_url').attr('href', response[1].url);

          $('#my_third_title').text(response[2].title);
          $('#my_third_viewCount').text(response[2].viewCount);
          $('#my_third_thumbnail').attr('src', response[2].thumbnailUrl);
          $('#my_third_url').attr('href', response[2].url);

          $('#my_fourth_title').text(response[3].title);
          $('#my_fourth_viewCount').text(response[3].viewCount);
          $('#my_fourth_thumbnail').attr('src', response[3].thumbnailUrl);
          $('#my_fourth_url').attr('href', response[3].url);

          $('#my_fifth_title').text(response[4].title);
          $('#my_fifth_viewCount').text(response[4].viewCount);
          $('#my_fifth_thumbnail').attr('src', response[4].thumbnailUrl);
          $('#my_fifth_url').attr('href', response[4].url);

          $('#my_keyword').text(keyword);
          $('#hidden_my_keyword').text(keyword);
          $('#my-keyword-result-modal').modal('show');
      }).fail(function(error){
          location.reload();
     });
}