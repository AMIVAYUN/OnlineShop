var globalid=0;
var data;
$(document).ready(function(){
    ListSetting()

})
function ListSetting(){
    getItems(0,100);
    setting()
}

async function getItems(offset,limit){
    await fetch("/api/v2/items/all?offset="+offset+"&limit="+limit).then(response => response.json()
        .then(
            (res) =>{
                $.each(res,function(i){
                    var baseurl=window.location
                    var html='<tr class="element" id='+res[i].item_ID+'><td class="item_id">'+res[i].item_ID+'</td><td><img class="itemimg" src="'+(baseurl .protocol +"//"+baseurl .host+"/"+res[i].imgSrc)+'"></td><td class="itemname">'+res[i].name+'</td><td class="item_desc">'+res[i].description+'</td><td class="item_price">'+res[i].price+'</td><td class="itemtype">'+res[i].dtype+'</td><td class="item_stock">'+res[i].stockQuantity+'</td><td class="madecomp">'+res[i].manufacturedCompany+'</td><td class="item_madein">'+res[i].madeIn+'</td><td class="item_weight">'+res[i].weight+'</td><td><button class="replace">수정</button></td><td><button class="delete">삭제</button></td></tr>'
                    $("#itemlist").append(html);
                    $(".itemimg").css('background-size','contain')
                })

            }
        ));


}
function setting(){
    $("#itemlist").on("click",".replace",updateItem);
    $("#itemlist").on("click",".delete",deleteItem);
}
/*
function ListPrint(res){
    var baseurl=window.location

    for(i=0; 0<res.length;i++){


    }
    $(".replace").attr("onclick",updateItem());
    $(".element").on("click",".delete",deleteItem());

}

 */
function updateItem(){
    var context=$(this).closest("tr");
    var id=context.find(".item_id").text();
    context.find(".itemname").replaceWith('<td><input class="itemname" type="text" value="'+context.find(".itemname").text()+'"/></td>');
    context.find(".item_desc").replaceWith('<td><input class="item_desc" type="text" value="'+context.find(".item_desc").text()+'"/></td>');
    context.find(".item_price").replaceWith('<td><input class="item_price" type="text" value="'+context.find(".item_price").text()+'"/></td>');
    //context.find(".itemtype").replaceWith('<td><input class="itemtype" type="text" value="'+context.find(".itemtype").text()+'"/></td>');
    context.find(".item_stock").replaceWith('<td><input class="item_stock" type="text" value="'+context.find(".item_stock").text()+'"/></td>');
    context.find(".madecomp").replaceWith('<td><input class="madecomp" type="text" value="'+context.find(".madecomp").text()+'"/></td>');
    context.find(".item_madein").replaceWith('<td><input class="item_madein" type="text" value="'+context.find(".item_madein").text()+'"/></td>');
    context.find(".item_weight").replaceWith('<td><input class="item_weight" type="text" value="'+context.find(".item_weight").text()+'"/></td>');
    context.find(".replace").replaceWith('<td><button class="done"onclick="putItem('+id+')">완료</button></td>')
    context.find(".itemimg").click(changeimg);
    globalid=id;
}
function changeimg(){
    if(confirm("이미지를 변경하시겠습니까?")){
        $(this).replaceWith(('<td><input type="file" name="img" class="img" onchange="uploadImage()" /></td>'))

    }
}
function uploadImage(){
    var alph=$('#'+globalid+'').find(".img");
    data=alph[0].files[0];
    console.log(alph[0].files[0].name)
    console.log(data)
}
function putItem(id){
    var baseurl=window.location;
    let formData=new FormData();
    let obj ={
        "name": $('#'+id+'').find(".itemname").val(),
        "description": $('#'+id+'').find(".item_desc").val(),
        "price": $('#'+id+'').find(".item_price").val(),
        "dtype":$('#'+id+'').find(".itemtype").val(),
        "stockquantity":$('#'+id+'').find(".item_stock").val(),
        "manufacturedcompany":$('#'+id+'').find(".madecomp").val(),
        "madein":$('#'+id+'').find(".item_madein").val(),
        "weight":$('#'+id+'').find(".item_weight").val(),
    }
    if($('#'+id+'').find(".img").length){

        formData.append('file',data)
    }else{
        formData.append('file',null)
    }
    if(confirm("변경하시겠습니까?")){
        formData.append('body',JSON.stringify(obj));
        updateItemSequence(id,formData);
    }
}
async function updateItemSequence(id,formData){
    alert(id);
    var url="/api/v2/items/"+id+"/update-item.do"

    await fetch(url,{method:"put",body:formData}).then(response => response.text()).then(
        (res) => {
            alert(res);
            location.reload();
        }
    )



}
async function deleteItem(){
    if(confirm("정말로 삭제하시겠습니까?")){
        var id=$(this).closest("tr").attr('id');
        var url="/api/v2/items/"+id+"/delete-item.do"
        await fetch(url,{method:"delete"}).then(response => response.text()).then(
            (res) => {
                alert(res);
                location.reload();
            }
        )
    }
}