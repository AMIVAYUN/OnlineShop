$(document).ready(function(){
    getFaqlist();
})
function getFaqlist(){
    fetch("./view-faq.do",{method:"get"}).then((response) => response.json()).then(
        (data) => {
            $.each(data, function (idx){
                var innerhtml='<li id="faq">'+'<div id="name">'+"제목: "+data[idx].name+'</div>'+'<div id="desc">'
                    +"내용:" + data[idx].description+'</div><button type="submit">제거</button>' +'</br></li>'
                $("#faq_list").append(innerhtml);
            })
        })
}
