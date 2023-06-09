
$(document).ready(function(){
    chkIE();
    getItem(0,12);
    gotoItem();
    categorySetting();
    SessionCheck();
    SearchSetting();
    logoSetting();
    pagerSetting();
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
    url = "./api/v2/items?offset="+offset+"&limit="+limit;
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
                var innerhtml = '<li class="l_col l_col_3_12 l_col_tablet_4_12 l_col_tablet_6_12 l_col_mobile_12_12 item" id='+ data[idx].item_ID +'><div class="itemdesc"><div class="item-cover-section"><img id="item_img" class="item-cover" src=' + data[idx].imgSrc + '></div><div class="item-contents"><p id="item_name">'+data[idx].name+'</p><h1 class="item-price">'+data[idx].price.toLocaleString()+'</h1><div class="item-footer"><ul class="footer-btn"><li class="btn btn-view"><a class="">자세히 보기</a></li></ul></div></div></div></li>'                
                    $("#shoplist").append(innerhtml);
            })
        })
}


function categorySetting(){
    $('#category_parts').on("click","li",function(){

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



})};

function gotoItem(){
    $('#shoplist').on("click","li",function (){
        var id=$(this).attr('id');
        location.assign("./products/"+id);
    })
}


function Cleaning(bodytag){
    bodytag.empty();

}


function getTypedItem(dtype){
    Cleaning($("#shoplist"))
    var url="./api/v2/items/typed/"
    if(dtype=="ETC"){
        url+="기타"
    }
    else if(dtype=="Tool"){
        url+="공구"
    }
    else{
        let result;

        switch( dtype ){
            case "LeakDetector":
                result = "누수탐지기"
                break;
            case "SewerCleaner":
                result = "하수관 청소기"
                break;
            case "Endoscope":
                result = "내시경"
                break;
            default:
                result = "누수탐지기"
                break;
        }

        url+= result
    }
    url+="?offset=0&limit=11"
    fetch(url,{method:"GET"}).then((response) => response.json()).then(
        (data) => {
            $.each(data, function (idx) {
                var innerhtml = '<li class="l_col l_col_3_12 l_col_tablet_4_12 l_col_tablet_6_12 l_col_mobile_12_12 item" id='+ data[idx].item_ID +'><div class="itemdesc"><div class="item-cover-section"><img id="item_img" class="item-cover" src=' + data[idx].imgSrc + '></div><div class="item-contents"><p id="item_name">'+data[idx].name+'</p><h1 class="item-price">'+data[idx].price.toLocaleString()+'</h1><div class="item-footer"><ul class="footer-btn"><li class="btn btn-view"><a class="">자세히 보기</a></li></ul></div></div></div></li>'
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
    if(res1.iswhom =="[ROLE_ADMIN]"){
        let innerhtml = '<li class="nav_l li_section_className"><a id=" manager" href="/admin/manage">관리자</a></li>';
        $("#navi-bar").append( innerhtml );
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
    $("#db_logo").click(function(){
        var baseurl=window.location;
        window.location.assign(baseurl .protocol +"//"+baseurl .host);
    })

}

function pagerSetting(){
    fetch( "/api/v2/items/len" , { method: "GET" } ).then( res => res.json() ).then( data => {
        let total = data.count;

        let max = total / 12;

        for( i = 1; i < max + 1; i ++ ){
            let innerhtml;
            if( i > 4 ){
                innerhtml = '<li className="page-num"><a>' + i + '</a></li>'
            }else{
                innerhtml = '<li className=""><a>' + i + '</a></li>'
            }
            $(".pagination").append( innerhtml );

        }

        $(".pagination > li > a ").on("click", function(){
            var offset=0+ ( ( $(this).text() - 1 ) *12);

            var limit=12;

            getItem(offset,limit);
        })

    });





}