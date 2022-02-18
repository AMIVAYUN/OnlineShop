
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
    alert(res[0].username);
    for(i=0; 0<res.length;i++){
        var html='<tr><td class="order_id">'+res[i].orderId+'</td><td class="username">'+res[i].user_name+'</td><td class="totalprice">'+res[i].totalprice+'</td><td class="orderdate">'+res[i].orderdate+'</td><td><button class="order_desc">주문 상세보기</button></td></tr>'
        $("#orderlist").append(html);
    }

}