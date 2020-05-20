window.onload = function(){
    $('.btn').on('click', function () {
        $('.input').toggleClass('inclicked');
        $('.btn').toggleClass('close');
    });
};


function onKeyDown(){
    if(event.keyCode == 13)
    {
        var data = {
            keyword: $('#keyword').val()
        };

        $.ajax({
              type: 'POST',
              url: '/api/keywords',
              dataType: 'html',
              async : false,
              contentType: 'application/json',
              data: JSON.stringify(data)
          }).done(function(response){
              $('html').html(response);
          }).fail(function(error){
              alert(JSON.stringify(error));
         });
    }
}