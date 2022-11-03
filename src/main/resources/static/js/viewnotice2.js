$(document).ready(function (){
    chkIE();
    var Keyword=KeyWordCheck();
    initFaqlist(Keyword,0,15);
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
async function initFaqlist(Keyword,offset,limit){
    var url="/api/v2/faq/all?keyword="+Keyword+"&offset="+offset+"&limit="+limit;


    const res=await fetch(url,{method:"get"});
    if(res.status==200){
        res.json().then(
            (data) => {
                localStorage.setItem("lenN",data.length);
                var idx1 = offset;
                $.each(data.reverse(), function (idx){

                    if( idx1 == offset+limit){
                        return;
                    }
                    var date = data[idx].date.toString();
                    date = date.replace(/\,/g,'-');
                    obj = Object();
                    obj.date = date;
                    obj.name=data[idx].name.toString();
                    obj.desc = data[idx].description.toString();

                    var innerhtml='<tr class="component">\n' +
                        '                <td class = "date"> '+date+' </td>\n' +
                        '                <td class = "title" onclick="mod_open('+'\''+ obj.name+ '\',' +'\''+obj.date+'\',\''+ obj.desc+'\'' +' )"> '+data[idx].name+' </td>\n' +
                        '                <td class = "desc">'+data[idx].description+'</td>\n' +
                        '            </tr>'
                    $("#content").append(innerhtml);
                    idx1++;


                })
            }
        )
    }else{
        res.text().then(
            msg=>{
                var innerhtml='<p>아직 작성된 게시물이 없습니다.</p>';
                $("#content").append(innerhtml);
            }

        )

    }
    // .then((response) => response.json()).then(



}
async function getFaqlist(Keyword,offset,limit){
    var url="/api/v2/faq/partition?keyword="+Keyword+"&offset="+offset+"&limit="+limit;


    const res=await fetch(url,{method:"get"});
    if(res.status==200){
        res.json().then(
            (data) => {
                var idx1 = offset;
                $.each(data, function (idx){

                    if( idx1 == offset+limit){
                        return;
                    }
                    var date = data[idx].date.toString();
                    date = date.replace(/\,/g,'-');
                    obj = Object();
                    obj.date = date;
                    obj.name=data[idx].name.toString();
                    obj.desc = data[idx].description.toString();
                    var innerhtml='<tr class="component">\n' +
                        '                <td class = "date"> '+data[idx].date+' </td>\n' +
                        '                <td class = "title" onclick="mod_open('+'\''+ obj.name+ '\',' +'\''+obj.date+'\',\''+ obj.desc+'\'' +' )"> '+data[idx].name+' </td>\n' +
                        '                <td class = "desc">'+data[idx].description+'</td>\n' +
                        '            </tr>'
                    $("#content").append(innerhtml);
                    idx1++;


                })
            }
        )
    }else{
        res.text().then(
            msg=>{
                var innerhtml='<p>관련 게시물이 없습니다.</p>';
                $("#content").append(innerhtml);
            }

        )

    }
    // .then((response) => response.json()).then(



}
function pageUp(){
    var value=parseInt($("#page").val());
    if($(".component").length == 15 & (value * 15) != localStorage.getItem("lenN")){
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

    var offset=0+((value-1)*15);
    var limit=offset+15;
    var Keyword=KeyWordCheck();
    $(".component").remove();
    getFaqlist(Keyword,offset,limit);
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
// 모달 관련
function mod_open( title,date,desc ){

    $("#modal").css({
        "display":"block"
    })

    $("#modal").find("#title").text( title );
    $("#modal").find("#date").text( date );
    $("#modal").find("#desc").text( desc );

}

function mod_close(){
    $("#modal").css({
        "display":"none"
    })
}