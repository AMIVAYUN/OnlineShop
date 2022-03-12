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
        var html='<tr id="element"><td class="delivery_id">'+res[i].delivery_id+'</td><td class="order_id">'+res[i].order.order_ID+'</td><td class="customer_name">'+res[i].order.membername+'</td><td class="orderdate">'+res[i].order.orderdate+'</td><td class="delivery_status">'+res[i].status+'</td><td class="delivery_address">'+res[i].address+'</td></tr>'
        $("#delivery_list").append(html);
    }

}