$(document).ready(function (){
    getFaqlist();
    SessionCheck();
    SearchSetting();
    logoSetting();
})

function getFaqlist(){
    fetch("/api/v1/view-faq.do",{method:"get"}).then((response) => response.json()).then(
        (data) => {
            var i=0;
            $.each(data, function (idx){

                var innerhtml='<div className="partition">\n' +
                    '                    <details>\n' +
                    '                        <summary>'+data[idx].name+'</summary>\n' +
                    '                        <p>'+data[idx].description+'</p>\n' +
                    '                    </details>'
                $("#content").append(innerhtml);

            })
        })
}
function SearchSetting(){
    $("#searchicon").on("click",function(){
        if($("#searchbar").find("input").val()){
            var url="/search/"+$("#searchbar").find("input").val();
            window.open(url);
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
    const res1=await fetch("/api/v1/members/session",{method:"GET"}).then(response => response.json());

    if(res1.iswhom !="[ROLE_ADMIN]"){
        $("#manager").remove();
    }
    if(res1.iswhom !="[ROLE_ANONYMOUS]"){
        $("#login-navi").text(res1.iswho + "님 안녕하세요");
        $("#login-navi").css("left",1135);
        $("#join-navi").text("로그아웃");
        $("#join-navi").attr("href","/logout");

    }
}