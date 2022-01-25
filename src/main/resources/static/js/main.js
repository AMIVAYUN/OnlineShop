$(document).ready(function(){
    getItem();
})
function getItem(){
    var rep;
    fetch("./api/v1/m-item",{method:"GET"}).then((response) => response.json()).then(
        (data) => {
            $.each(data, function (idx, row) {
                var innerhtml = '<li class="item"><div id="item_img"><img src=' + data[idx].imgSrc + '></div>' +
                    '<div id="item_text"><a id="item_name">'+data[idx].name+'</a></br>'+'<a id="item_price">'+data[idx].price+'</a></br><button>바로가기</button></div></li>'
                $("#shoplist").append(innerhtml);
            })
        })


            /*
            var html_itemcode=`<li class="item">
                <div id="item_img">
                    <img src="${rep}.imgSrc">
                </div>
                <div id="item_text">
                    <a id="item_name">${rep}.name</a></br>
                <a id="item_price">${rep}.price</a></br>
            <button>바로가기</button>
        </div>
        </li>`
            $("#shoplist").append(html_itemcode);


             */



}
