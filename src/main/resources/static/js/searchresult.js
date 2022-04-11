$(document).ready(function(){
    var keyword=KeyWordCheck();
    SessionCheck();
    SearchByKeyWord(keyword);
    SearchSetting();
    logoSetting();
    gotoItem();
    mypageSetting();
})
async function SessionCheck(){
    //shoplist 세팅 포함
    var baseurl=window.location;

    const res1=await fetch("/api/v1/members/session",{method:"GET"}).then(response => response.json());
    if(!res1.isauth){
        return false;
    }
    function gotoItem(){
        $('#shoplist').on("click","li",function (){
            var id=$(this).attr('id');
            location.assign("./products/"+id);
        })
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
        })
    }else{
        $("#scart").click(function (){
            alert("로그인이 필요한 서비스 입니다.")
        })
    }

}

function KeyWordCheck(){
    var baseurl=window.location;
    var locate=baseurl .protocol +"//"+baseurl .host+"/search/"
    var str=baseurl.toString();
    var Keyword=str.substr(locate.length,str.length);
    Keyword=decodeURI(Keyword);
    $("#searchbar").find("input").attr("value",Keyword)
    return Keyword;
}
function SearchByKeyWord(keyword){
    getItembykeyword(keyword);
}
async function getItembykeyword(keyword){
    var url="/api/v2/items/"+keyword+"?offset=0&limit=36";
    await fetch(url,{method:"get"}).then(response => response.json()).then(
        data => {
            var baseurl=window.location
            $.each(data, function (idx) {
                var innerhtml = '<li class="item" id='+data[idx].dtype +'><div id="item_img"><img src=' +baseurl .protocol +"//"+baseurl .host+"/"+ data[idx].imgSrc + '></div>' +
                    '<div id="item_text"><span><a id="merchansub">상품명:</a> <a id="item_name">'+data[idx].name+'</a></span></br>'+'<span><a id="merchansub">가 격:  </a><a id="item_price">'+data[idx].price.toLocaleString()+'</a></span></br></div></li>'
                $("#shoplist").append(innerhtml);
            })
            $("#explain_part").append('<span><mark>'+keyword+'</mark>'+"(으)로 검색한 결과, 총 "+'<mark>'+data.length+'</mark>'+ "건 입니다."+'</span>');
        }
    )

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
function gotoItem(){
    $('#shoplist').on("click","li",function (){
        var id=$(this).attr('id');
        location.assign("./products/"+id);
    })
}
function mypageSetting(){
    var baseurl=window.location;

    $("#comp_mypage").click(function (){
    if($("#login-navi").text()=="로그인"){
            alert("로그인이 필요한 서비스 입니다.")
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