$(document).ready(function(){
    getItem();
    gotoItem();
    categorySetting();
})
function getItem(){
    Cleaning($("#shoplist"));
    fetch("./api/v1/m-item",{method:"GET"}).then((response) => response.json()).then(
        (data) => {
            $.each(data, function (idx, row) {
                var innerhtml = '<li class="item" id='+data[idx].dtype +'><div id="item_img"><img src=' + data[idx].imgSrc + '></div>' +
                    '<div id="item_text"><span><a id="merchansub">상품명:</a> <a id="item_name">'+data[idx].name+'</a></span></br>'+'<span><a id="merchansub">가 격:  </a><a id="item_price">'+data[idx].price+'</a></span></br></div></li>'
                $("#shoplist").append(innerhtml);
            })
        })
}
function gotoItem(){
    $('#shoplist').on("click","li",function (){
        var name=$(this).find("#item_name").text();
        window.open("./item/"+name);
    })
}
function categorySetting(){
    $('#category_list').on("click","li",function(){

        if($(this).attr("id")=="default_category"&&$("#shoplist")!=null){
            getItem();
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
    var url="./api/v1/m-item/"
    if(dtype=="ETC"){
        url+="ETC"
    }
    else if(dtype=="Tool"){
        url+="cast-tool"
    }
    else{
        url+=dtype[0];
    }
    fetch(url,{method:"GET"}).then((response) => response.json()).then(
        (data) => {
            $.each(data, function (idx, row) {
                var innerhtml = '<li class="item" id='+data[idx].dtype +'><div id="item_img"><img src=' + data[idx].imgSrc + '></div>' +
                    '<div id="item_text"><span><a id="merchansub">상품명:</a> <a id="item_name">'+data[idx].name+'</a></span></br>'+'<span><a id="merchansub">가 격:  </a><a id="item_price">'+data[idx].price+'</a></span></br></div></li>'
                $("#shoplist").append(innerhtml);
            })
        }
    )

}
