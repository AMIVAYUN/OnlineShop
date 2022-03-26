$(document).ready(function(){
    SessionCheck();
    SearchSetting();
    logoSetting();
    nameCheckMypage();
    mypageSetting();
})

async function SessionCheck(){
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
        console.log(baseurl .protocol +"//"+baseurl .host);
        window.location.assign(baseurl .protocol +"//"+baseurl .host);
    })

}

async function nameCheckMypage(){
    var mypageUrl="/api/v1/members/is-who";
    const usernameRes = await fetch(mypageUrl, {method:"GET"}).then((response) => response.text());
    var url="/api/v1/members/single/local/"+usernameRes;
    const userinfoRes = await fetch(url, {method:"get"}).then((response) => response.json());
    fetch(url).then((response) => console.log(response));
    $("#members_username").text(usernameRes);
    //$("#members_password").text(userinfoRes.password);
    $("#members_name").text(userinfoRes.name);
    $("#members_phone_num").text(userinfoRes.phone_num);
    $("#members_email").text(userinfoRes.email);
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