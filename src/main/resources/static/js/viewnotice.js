$(document).ready(function (){
    chkIE();
    var Keyword=KeyWordCheck();
    getFaqlist(Keyword);
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
function KeyWordCheck(){
    var baseurl=window.location;
    var locate=baseurl .protocol +"//"+baseurl .host+"/contact/notice?keyword="
    var str=baseurl.toString();
    var Keyword=str.substr(locate.length,str.length);
    Keyword=decodeURI(Keyword);
    $("#faqkeyword").attr("value",Keyword);
    return Keyword;
}
async function getFaqlist(Keyword){
    if(Keyword==null){
        var url="/api/v2/faq/all";
    }else{
        var url="/api/v2/faq/all?keyword="+Keyword;
    }

    const res=await fetch(url,{method:"get"});
    if(res.status==200){
        res.json().then(
            (data) => {

                $.each(data, function (idx){

                    var innerhtml='<div className="partition">\n' +
                        '                    <details>\n' +
                        '                        <summary>'+data[idx].name+'</summary>\n' +
                        '                        <p>'+data[idx].description+'</p>\n' +
                        '                    </details>'
                    $("#content").append(innerhtml);

                })
            }
        )
    }else{
        res.text().then(
            msg=>{
                var innerhtml='<p>'+msg+'</p>';
                $("#content").append(innerhtml);
            }

        )

    }
        // .then((response) => response.json()).then(



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
async function SessionCheck(){
    //shoplist 세팅 포함
    var baseurl=window.location;

    const res1=await fetch("/api/v1/members/session",{method:"GET"}).then(response => response.json());
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
        $("#mailingservice").on("click",function(){
            location.assign(baseurl .protocol +"//"+baseurl .host+"/contact/em-faq");
        })
        $("#scart").click(function (){
            window.location.assign(baseurl .protocol +"//"+baseurl .host+"/shopping-list");
        })

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
        $("#mailingservice").on("click",function(){
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
function search(){
    var baseurl=window.location
    var keyword=$("#faqkeyword").val();
    window.location.assign(baseurl .protocol +"//"+baseurl .host+"/contact/notice?keyword="+keyword);

}
function mypageSetting(){
    var baseurl=window.location;



    $("#mypage").click(function (){
        if($("#login-navi").text()=="로그인"){
            alert("로그인이 필요한 서비스 입니다.")
        }else{
            var url=baseurl .protocol +"//"+baseurl .host+"/mypage"
            location.assign(url);
        }
    })
}
