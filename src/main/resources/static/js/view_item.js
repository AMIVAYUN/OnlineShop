$(document).ready(function(){
    ListSetting()
})
function ListSetting(){
    getItems(0,100);
}
async function getItems(offset,limit){
    const res=await fetch("/api/v1/items/all?offset="+offset+"&limit="+limit).then(response => response.json())
    return ListPrint(res);
}

function ListPrint(res){
    var baseurl=window.location

    for(i=0; 0<res.length;i++){
        var html='<li id="element"><span><a class="item_id">'+res[i].item_id+'</a><img class="itemimg_src" src='+(baseurl .protocol +"//"+baseurl .host+"/"+res[i].imgSrc)+'><a class="itemname">'+res[i].name+'</a><a class="item_desc">'+res[i].description+'</a><a class="item_price">'+res[i].price+'</a><a class="itemtype">'+res[i].dtype+'</a><a class="item_stock">'+res[i].stockQuantity+'</a><a class="madecomp">'+res[i].manufacturedCompany+'</a><a class="item_madein">'+res[i].madeIn+'</a><a class="item_weight">'+res[i].weight+'</a></span></li>'
        $("#itemlist").append(html);
    }

}