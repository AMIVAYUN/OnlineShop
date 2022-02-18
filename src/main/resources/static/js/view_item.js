$(document).ready(function(){
    ListSetting()
})
function ListSetting(){
    getItems(0,100);
}
async function getItems(offset,limit){
    const res=await fetch("/api/v2/items/all?offset="+offset+"&limit="+limit).then(response => response.json())
    return ListPrint(res);
}

function ListPrint(res){
    var baseurl=window.location

    for(i=0; 0<res.length;i++){
        var html='<tr id="element"><td class="item_id">'+res[i].item_ID+'</td><td class="itemimg" style="background-image:url('+(baseurl .protocol +"//"+baseurl .host+"/"+res[i].imgSrc)+')"></td><td class="itemname">'+res[i].name+'</td><td class="item_desc">'+res[i].description+'</td><td class="item_price">'+res[i].price+'</td><td class="itemtype">'+res[i].dtype+'</td><td class="item_stock">'+res[i].stockQuantity+'</td><td class="madecomp">'+res[i].manufacturedCompany+'</td><td class="item_madein">'+res[i].madeIn+'</td><td class="item_weight">'+res[i].weight+'</td><td><button>수정</button></td><td><button>삭제</button></td></tr>'
        $("#itemlist").append(html);
        $(".itemimg").css('background-size','contain')
    }

}