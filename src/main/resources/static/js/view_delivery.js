$(document).ready(function(){
    ListSetting()
})
function ListSetting(){
    getDeliverys(0,100);
}
async function getDeliverys(offset,limit){
    const res=await fetch("/api/v1/delivery/all?offset="+offset+"&limit="+limit).then(response => response.json())
    return ListPrint(res);
}

function ListPrint(res){

    for(i=0; 0<res.length;i++){
        var html='<li id="element"><span><a class="delivery_id">'+res[i].delivery_id+'</a><a class="order_id">'+res[i].order.order_ID+'</a><a class="customer_name">'+res[i].order.membername+'</a><a class="orderdate">'+res[i].order.orderdate+'</a><a class="delivery_status">'+res[i].status+'</a><a class="delivery_address">'+res[i].address+'</a></span></li>'
        $("#delivery_list").append(html);
    }

}