const csrfToken = $('meta[name="_csrf"]').attr('content');
$( document ).ready( function (){
  buttonSetting();

});
$(function(){
  $('.tab-content > div').hide();
  $('.tab-nav a').click(function () {
    $('.tab-content > div').hide().filter(this.hash).fadeIn();
    $('.tab-nav a').removeClass('active');
    $(this).addClass('active');
    return false;
  }).filter(':eq(0)').click();
  });


function buttonSetting(){
  $( "#btn_create" ).click( createDtype );

}


function createDtype(){
  let name = $("#categories_textbox").val();
  if( name.length == 0 ){
    alert(" 카테고리 이름을 지정해주세요. ");
    return;
  }
  let obj = {
    "name":name,
  }
  console.log( obj );
  fetch( "/api/v2/dtypes/register-dType.do",{
    method: "post",
    headers:{'Content-Type':'application/json','X-CSRF-TOKEN': csrfToken},
    body:JSON.stringify( obj )
  }).then( response => {
    if( response.status == 200 ){
      alert( "카테고리 생성이 완료되었습니다.");
    }else if( response.status == 400 ){
      alert( "이름을 다시 입력해주세요. ");
    }else if( response.status == 417 ){
      alert( "중복된 이름 입니다." );
    }else{
      alert( " 서버 에러 잠시 후 다시 시도해주세요. " );
    }

  });

}