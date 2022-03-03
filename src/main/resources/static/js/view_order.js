
$(document).ready(function(){
    ListSetting()
})
function ListSetting(){
    getOrders(0,100);
}
async function getOrders(offset,limit){
    const res=await fetch("/api/v1/orders/all?offset="+offset+"&limit="+limit).then(response => response.json())
    return ListPrint(res);
}

function ListPrint(res){
    for(i=0; 0<res.length;i++){
        var html='<tr id="'+res[i].orderId+'"><td class="order_id">'+res[i].orderId+'</td><td class="username">'+res[i].user_name+'</td><td class="totalprice">'+res[i].totalprice+'</td><td class="orderdate">'+res[i].orderdate+'</td><td class="orderitem"><ul class="orderitem_list"></ul></td><td class="delivery_place">'+res[i].delivery.delivery_Address+'</td><td class="delivery_stat">'+res[i].delivery.status+'</td></tr>'
        $("#orderlist").append(html);
        for(j=0; j<res[i].orderItemDto.length;j++){
            subhtml='<li><span><a class="'+i+'_orderitem">'+"제품: "+ res[i].orderItemDto[j].itemname+" 가격: "+res[i].orderItemDto[j].totalprice+" 개수: "+res[i].orderItemDto[j].count+'</a></span></li>'
            $('#'+res[i].orderId+'').find(".orderitem").append(subhtml);
        }

    }

}