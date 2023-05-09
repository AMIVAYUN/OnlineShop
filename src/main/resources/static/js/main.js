
$(document).ready(function(){
    chkIE();
    getItem(0,12);
    gotoItem();
    categorySetting();
    SessionCheck();
    SearchSetting();
    logoSetting();
    //mypageSetting();

})
function chkIE(){
    if (window.navigator.userAgent.match(/MSIE|Internet Explorer|Trident/i)) {
        window.open("microsoft-edge:" + window.location.href);
        window.location = 'https://support.microsoft.com/ko-kr/office/%ec%97%b0%ea%b2%b0%ed%95%98%eb%a0%a4%eb%8a%94-%ec%9b%b9-%ec%82%ac%ec%9d%b4%ed%8a%b8%ea%b0%80-internet-explorer%ec%97%90%ec%84%9c-%ec%9e%91%eb%8f%99%ed%95%98%ec%a7%80-%ec%95%8a%ec%8a%b5%eb%8b%88%eb%8b%a4-8f5fc675-cd47-414c-9535-12821ddfc554?ui=ko-kr&rs=ko-kr&ad=kr';
    }
}
function getItem(offset,limit){
    cacheName = "home";
    url = "./api/v2/items/all?offset="+offset+"&limit="+limit;
    Cleaning($("#shoplist"));
    fetch( url,{method:"GET"}).then((response) => {
        if( response.status==200 ){
            return response.json();

        }else if( response.status == 204 ){

            alert("더 이상 존재하지 않습니다.");
            window.location.href= window.location;
        }else{
            alert("내부 서버에 문제가 있습니다.");
        }





    }).then(
        (data) => {

            $.each(data, function (idx) {
                var innerhtml = '<li class="item" id='+data[idx].item_ID +'><div id="item_img"><img src=' + data[idx].imgSrc + '></div>' +
                    '<div id="item_text"><span><a id="merchansub">상품명:</a> <a id="item_name">'+data[idx].name+'</a></span></br>'+'<span><a id="merchansub">가 격:  </a><a id="item_price">'+data[idx].price.toLocaleString()+'</a></span></br></div></li>'
                $("#shoplist").append(innerhtml);
            })
        })
}
function gotoItem(){
    $('#shoplist').on("click","li",function (){
        var id=$(this).attr('id');
        location.assign("./products/"+id);
    })
}
function categorySetting(){
    $('#category_list').on("click","li",function(){

        if($(this).attr("id")=="default_category"&&$("#shoplist")!=null){
            getItem(0,12);
            $("#page").val(1);
        }
        else if($(this).attr("id")!="default_category"){
            getTypedItem($(this).attr("id"));
        }

        var rep=document.getElementById("category_list");
        var categoryitems=rep.getElementsByTagName('li');
        for(var i=0; i<categoryitems.length; i++){
            categoryitems[i].style.color="black";
            categoryitems[i].style.backgroundColor="white";
            categoryitems[i].style.borderRadius="10px 10px";
        }
        $(this).css("color","white");
        $(this).css("background-color","black");
        $(this).css("border-radius","10px 10px");



})
}
function Cleaning(bodytag){
    bodytag.empty();

}
function getTypedItem(dtype){
    Cleaning($("#shoplist"))
    var url="./api/v2/items/typed/"
    if(dtype=="ETC"){
        url+="ETC"
    }
    else if(dtype=="Tool"){
        url+="cast-tool"
    }
    else{
        url+=dtype[0];
    }
    url+="?offset=0&limit=11"
    fetch(url,{method:"GET"}).then((response) => response.json()).then(
        (data) => {
            $.each(data, function (idx) {
                var innerhtml = '<li class="item" id='+data[idx].item_ID +'><div id="item_img"><img src=' + data[idx].imgSrc + '></div>' +
                    '<div id="item_text"><span><a id="merchansub">상품명:</a> <a id="item_name">'+data[idx].name+'</a></span></br>'+'<span><a id="merchansub">가 격:  </a><a id="item_price">'+data[idx].price.toLocaleString()+'</a></span></br></div></li>'
                $("#shoplist").append(innerhtml);
            })
        }
    )

}

async function SessionCheck(){
    //shoplist 세팅 포함
    var baseurl=window.location;

    const res1= await fetch("/api/v1/members/session",{method:"GET"}).then(response => response.json());
    if(!res1.isauth){
        return false;
    }
    if(res1.iswhom !="[ROLE_ADMIN]"){
        $("#manager").remove();
    }
    if(res1.iswhom !="[ROLE_ANONYMOUS]"){
        $("#login-navi").text(res1.iswho + "님 안녕하세요");
        $("#login-navi").attr("href","#")
        $("#join-navi").text("로그아웃");
        $("#join-navi").attr("href","/logout");
        $("#scart").click(function (){
            window.location.assign(baseurl .protocol +"//"+baseurl .host+"/shopping-list");
        });
        $("#comp_mypage").click(function (){
            var url=baseurl .protocol +"//"+baseurl .host+"/mypage"
            location.assign(url);
        });
        $("#mypage").click(function (){
            var url=baseurl .protocol +"//"+baseurl .host+"/mypage"
            location.assign(url);
        });

    }else{
        $("#scart").click(function (){
            alert("로그인이 필요한 서비스 입니다.")
        });

        $("#comp_mypage").click(function (){
            alert("로그인이 필요한 서비스 입니다.")
        });
        $("#mypage").click(function (){
            alert("로그인이 필요한 서비스 입니다.")
        });
    }

}
function SearchSetting(){
    $("#searchicon").on("click",function(){
        if($("#searchbar").find("input").val()){
            var url="/search/"+$("#searchbar").find("input").val();
            window.location.assign(url);
        }else{
            location.reload();
        }

    })
}

function logoSetting(){
    $("#logo").click(function(){
        var baseurl=window.location;
        window.location.assign(baseurl .protocol +"//"+baseurl .host);
    })

}
function pageUp(){
    var value=parseInt($("#page").val());
    if($(".item").length == 12){
        $("#page").val(value+1)
        pageChange(value+1);
    }else{
        alert("다음 페이지가 존재하지 않습니다.")
    }
}
function pageDown(){
    var value=parseInt($("#page").val());
    if(value==1){
        alert("첫번째 페이지 입니다.")
    }else{
        $("#page").val(value-1);
        pageChange(value-1);
    }
}
function pageChange(value){

    var offset=0+ ( (value-1) *12);

    var limit=12;

    getItem(offset,limit);
}
/*
function mypageSetting(){
    var baseurl=window.location;

    $("#comp_mypage").click(function (){
    if($("#login-navi").text()=="로그인"){
            alert("로그인이 필요한 서비스 입니다.");
    }else{
        var url=baseurl .protocol +"//"+baseurl .host+"/mypage"
        location.assign(url);
        }
    })

    $("#mypage").click(function (){
        if($("#login-navi").text()=="로그인"){
            alert("로그인이 필요한 서비스 입니다.")
        }else{
            var url=baseurl .protocol +"//"+baseurl .host+"/mypage"
            location.assign(url);
        }
    })
}

 */



