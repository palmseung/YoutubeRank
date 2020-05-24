$('#searchBtn').on('click', function () {
    alert('search');
    search();
});

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
          alert(JSON.stringify(error));
     });
}