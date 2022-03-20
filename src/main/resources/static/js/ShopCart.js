var sum=0;
$(document).ready(function (){
    SessionCheck();
    SearchSetting();
    logoSetting();
    mypageSetting();
    getTotalMoney();
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
        var suburl="/api/v1/shopcarts/items/all?username="+res1.iswho;
        const res=await fetch(suburl,{method:"get"}).then(response => response.json());

        await $.each(res, function(idx){
            var innerhtml='<tr class="component">\n' +
                '                    <td class="mer_img"><img src='+res[idx].imgSrc+'></td>\n' +
                '                    <td class="name">'+res[idx].item_name+'</td>\n' +
                '                    <td class="count">'+res[idx].count+'개</td>\n' +
                '                    <td class="price">'+res[idx].price+'</td>\n' +
                '                    <td class="total">'+res[idx].count*res[idx].price+'원</td>\n' +
                '                    <td class="del"><i class="fa-solid fa-trash-can"></i></td></tr>';
            $("#shoppinglist").append(innerhtml);
            sum+=res[idx].count*res[idx].price;
        })

    }else{
        $("#scart").click(function (){
            alert("로그인이 필요한 서비스 입니다.")
        })
    }
    $("#totalpricevalue").text(sum +"원");

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
