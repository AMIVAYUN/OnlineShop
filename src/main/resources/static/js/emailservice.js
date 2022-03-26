$(document).ready(function(){
    SessionCheck();
    SearchSetting();
    logoSetting();
    sendSetting();
    mypageSetting();
})

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
        $("#scart").click(function (){
            window.location.assign(baseurl .protocol +"//"+baseurl .host+"/shopping-list");
        })
    }else{
        $("#scart").click(function (){
            alert("로그인이 필요한 서비스 입니다.")
        })
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
function sendSetting(){
    $("#send").click(sendmail);
}
function logoSetting(){
    $("#logo").click(function(){
        var baseurl=window.location;
        window.location.assign(baseurl .protocol +"//"+baseurl .host);
    })

}

async function sendmail(){
    var baseurl=window.location;
    let obj= {

    }

    if(confirm("전송하시겠습니까?")){
        const res=await fetch("/api/v2/mails/post.do",{method:"post",headers:{'Content-Type': 'application/json'},
            body:JSON.stringify({
                "name":$("#name").val(),
                "title":$("#title").val(),
                "content":$("#content").val(),
                "phonenum":$("#phone1").val()+"-"+$("#phone2").val()+"-"+$("#phone3").val(),
                "writtendate":new Date()
            })
        }).then(response => response.text());
        alert(res);
        location.assign((baseurl .protocol +"//"+baseurl .host));
    }


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