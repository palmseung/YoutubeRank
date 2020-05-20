$('.btn').on('click', function () {
    $('.input').toggleClass('inclicked');
    $('.btn').toggleClass('close');
});

function onKeyDown(){
    if(event.keyCode == 13)
    {
      alert("enter");
    }
}