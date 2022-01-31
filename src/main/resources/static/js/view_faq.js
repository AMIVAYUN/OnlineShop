$(document).ready(function(){
    getFaqlist();
    setting();
})
function getFaqlist(){
    fetch("/api/v1/view-faq.do",{method:"get"}).then((response) => response.json()).then(
        (data) => {
            $.each(data, function (idx){
                var innerhtml='<li id="faq">'+'<div id="date">'+data[idx].date+'</div>'+'<div id="id">'+data[idx].id+'</div>'+'<div id="name">'+"제목:"+data[idx].name+'</div>'+'<div id="desc">'
                    +"내용:" + data[idx].description+'</div><button type="submit">제거</button>' +'</br></li>'
                $("#faq_list").append(innerhtml);
            })
        })
}
function setting(){
    $("#faq_list").on("click","li",deletePost);
}

async function deletePost() {
    let obj = {
        "id": $(this).find("#id").text(),
        "name": $(this).find("#name").text(),
        "description": $(this).find("#desc").text(),
        "date": $(this).find("#date").text()
    };

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
