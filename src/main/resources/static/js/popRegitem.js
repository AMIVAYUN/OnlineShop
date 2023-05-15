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
  $("#tab01").click( categorySetting( event ) );
  $( "#item_chart" ).on('click', 'option', function() {
    $("#item_chart").val( $(this).val() );
  });
  $("#btn_cancel").click( function(){
    window.close();
  })

  $("#btn_save").click( postItem );

}

function categorySetting( event ){
  event.stopPropagation(); // 이벤트 버블링 처리 안하면 로직 안돌아감.
  $(".categories_dropdown").empty(); // 일단 지우기
  fetch( "/api/v2/dtype", { method:"get"}).then( r => r.json() ).then( data => {
    console.log( data );
    $.each( data, function( idx ){
      let html = '<option id="'+ data[ idx ].dtype_id + '">' + data[idx].name + '</option>'
      console.log( html );
      $(".categories_dropdown").append( html );
    });
  })



  return;


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

  fetch( "/admin/manage/items/dtypes",{
    method: "post",
    headers:{'Content-Type':'application/json','X-CSRF-TOKEN': csrfToken},
    body:JSON.stringify( obj )
  }).then( response => {
    if( response.status == 200 ){
      alert( "카테고리 생성이 완료되었습니다.");
      location.reload();

    }else if( response.status == 400 ){
      alert( "이름을 다시 입력해주세요. ");
    }else if( response.status == 417 ){
      alert( "중복된 이름 입니다." );
    }else{
      alert( " 서버 에러 잠시 후 다시 시도해주세요. " );
    }

  });

}

//ref https://ko.javascript.info/formdata
function postItem(){
  let formData = new FormData();

  let img =$("#file_upload");
  let data = img[ 0 ].files[ 0 ];

  if( data != undefined ){
    formData.append('file',data);
  }else{
    alert("이미지를 추가해주세요.");
    return;
  }

  formData.append("name",$("#name").val())
  formData.append( "Description",$("#desc").val())
  formData.append("price", $("#price").val() )
  formData.append( "dtype_id",$("#item_chart option:selected").attr("id"))
  formData.append("StockQuantity",$("#StockQuantity").val())
  formData.append( "ManufacturedCompany",$("#ManufacturedCompany").val() )
  formData.append("MadeIn",$("#MadeIn").val())
  formData.append( "weight",$("#Weight").val() )


  //console.log( formData );

  fetch( "/admin/manage/items",{
    method: "post",
    headers:{'X-CSRF-TOKEN': csrfToken},
    body: formData,
  }).then( response => {
    if( response.status == 200 ){
      alert( "제품 생성이 완료되었습니다.");
      window.opener.reload();
      window.close();
    }else if( response.status == 400 ){
      alert( "내용을 다시 입력해주세요. ");
    }else if( response.status == 417 ){
      alert( "중복된 이름 입니다." );
    }else{
      alert( " 서버 에러 잠시 후 다시 시도해주세요. " );
    }

  });


}