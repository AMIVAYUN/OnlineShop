$(document).ready(function(){
    getFaqlist();
    setting();
})
function getFaqlist(){
    fetch("/api/v1/view-faq.do",{method:"get"}).then((response) => response.json()).then(
        (data) => {
            $.each(data, function (idx){
                var innerhtml='<tr class="faq"><td class="date">'+data[idx].date+'</td>'+'<td class="id">'+data[idx].id+'</td><td class="name">'+"제목:"+data[idx].name+'</td><td class="desc">'
                    +"내용:" + data[idx].description+'</td><td><button type="submit">제거</button></td></tr>'
                $("#faq_list").append(innerhtml);
            })
        })
}
function setting(){
    $("#faq_list").on("click","button",deletePost);
}

async function deletePost() {
    let obj = {
        "id": $(this).closest("tr").find(".id").text(),
        "name": $(this).closest("tr").find(".name").text(),
        "description": $(this).closest("tr").find(".desc").text(),
        "date": $(this).closest("tr").find(".date").text()
    };
    console.log(obj.id);
    //var NoticeDto=makeJsonObject(name,description,date);
    //name:$(this).find("#name").text(),description:$(this).find("#desc").text(),date:$(this).find("#date")
    var url = "/api/v1/delete-faq.do"

    const res=await fetch(url, {
        method: "post",
        headers: {'Content-Type': 'application/json', 'Accept': 'application/json;charset=utf-8'},
        body: JSON.stringify(obj)
    }).then(response => response.text());
    const result = await res
    alert(result);
    window.open('./view-faq',"_self");



}
