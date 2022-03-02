$(document).ready(function(){
    getFaqlist();
    setting();

})
function getFaqlist(){
    fetch("/api/v1/view-faq.do",{method:"get"}).then((response) => response.json()).then(
        (data) => {
            var i=0;
            $.each(data, function (idx){
                var innerhtml='<tr class="faq" id="'+(i++)+'"><td class="date">'+data[idx].date+'</td>'+'<td class="id">'+data[idx].id+'</td><td class="name">'+data[idx].name+'</td><td class="desc">'
                     + data[idx].description+'</td><td><button class="replace" type="수정">수정</button></td><td><button class="delete" type="submit">제거</button></td></tr>'
                $("#faq_list").append(innerhtml);
            })
        })
}
function setting(){
    $("#faq_list").on("click",".delete",deletePost);
    $("#faq_list").on("click",".replace",doUpdate);

}
function doUpdate(){
    var context=$(this).closest("tr");
    var id=context.attr("id");
    console.log(id);
    let obj={
        "id": $(this).closest("tr").find(".id").text(),
        "name": $(this).closest("tr").find(".name").text(),
        "description": $(this).closest("tr").find(".desc").text(),
        "date": $(this).closest("tr").find(".date").text()
    }
    context.find(".name").replaceWith('<td><input class="name" type="text" value="'+obj.name+'"/></td>');
    context.find(".desc").replaceWith('<td><input class="desc" type="text" value="'+obj.description+'"/></td>');
    context.find(".replace").replaceWith('<td><button class="done"onclick="putNotice('+id+')">완료</button></td>')


}
function putNotice(id){
    let obj ={
        "id": $('#'+id+'').find(".id").text(),
        "name": $('#'+id+'').find(".name").val(),
        "description": $('#'+id+'').find(".desc").val(),
        "date": $('#'+id+'').find(".date").text()
    }

    if(window.confirm("변경하시겠습니까?")){
        updatePost(obj);
    }
}
async function updatePost(obj){

    var url="/api/v1/faq/"+obj.id+"/update-faq.do"

    const res=await fetch(url,{
        method:"put",
        headers: {'Content-Type': 'application/json'},
        body:JSON.stringify(obj)
    }).then(response => response.text());
    const result=await res;
    alert(result);
    location.reload();



}
async function deletePost() {
    let obj = {
        "id": $(this).closest("tr").find(".id").text(),
        "name": $(this).closest("tr").find(".name").text(),
        "description": $(this).closest("tr").find(".desc").text(),
        "date": $(this).closest("tr").find(".date").text()
    };
    //var NoticeDto=makeJsonObject(name,description,date);
    //name:$(this).find("#name").text(),description:$(this).find("#desc").text(),date:$(this).find("#date")
    var url = "/api/v1/delete-faq.do"

    const res=await fetch(url, {
        method: "delete",
        headers: {'Content-Type': 'application/json', 'Accept': 'application/json;charset=utf-8'},
        body: JSON.stringify(obj)
    }).then(response => response.text());
    const result = await res
    alert(result);
    window.open('./view-faq',"_self");



}
