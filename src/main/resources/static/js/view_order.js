
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
        var html='<li><span><a class="order_id">'+res[i].orderId+'</a><a class="username">'+res[i].user_name+'</a><a class="totalprice">'+res[i].totalprice+'</a><a class="orderdate">'+res[i].orderdate+'</a><button class="order_desc">주문 상세보기</button></span></li>'
        $("#orderlist").append(html);
    }

}