var firstGrid;
var selected;
const csrfToken = $('meta[name="_csrf"]').attr('content');

$(document).ready(function(){
    buttonSetting();

})
function buttonSetting(){
    $(".menu li a").on("click",function(){
        displayComponent($(".menu li a").index(this))
    })
}
function displayComponent(index){
    
    for(var i=0; i<$(".section").length;i++){
        if(i==index){
            $(".section").eq(i).css("display","block");
            listSetting(i);
        }else{
            $(".section").eq(i).css("display","none");
        }
    }
    

}
function listSetting(index){
    switch(index){
        case 0:
            defaultSetting();
        case 1:
            ItemlistSetting();
            break;
        case 2:
            FaqlistSetting();
            break;
        case 3:
            OrderlistSetting();
            break;
        case 4:
            MemberlistSetting();
    }
}
function defaultSetting(){

}
async function ItemlistSetting(){
    firstGrid = new ax5.ui.grid();
    var baseurl=window.location
    firstGrid.setConfig({
        target: $('[data-ax5grid="item-grid"]'),
        showLineNumber:true,
        showRowSelector:true,
        sortable:true,
        header:{align:"center",columnHeight:60},
        body:{align:"center",columnHeight:67.67},
        columns: [
            {key: "item_id", label: "제품 번호"},
            {key: "item_img", label: "제품 이미지",width:100,height:60,sortable:false,formatter:function(){
                    return "<img id='item_img' src='"+baseurl .protocol +"//"+baseurl .host+"/"+this.value+"' />";
                }},
            {key: "item_name", label: "제품 이름",editor:{type:"text"}},
            {key: "item_description", label: "제품 설명",sortable:false,editor: {type:"textarea"},width:250},
            {key: "item_price", label: "제품 가격",editor:{type:"money"},formatter:"money"},
            {key: "item_type", label: "제품 군",sortable:false,editor: {type:"select",config:{
                columnKeys:{
                    optionValue:"CD",optionText:"NM"
                },
                options: [
                    {CD:"L",NM:"누수 탐지기"},
                    {CD:"D",NM:"드릴"},
                    {CD:"E",NM:"내시경"},
                    {CD:"ETC",NM:"미 분류"},
                    {CD:"G",NM:"글라인더"},
                    {CD:"H",NM:"해머"},
                    {CD:"N",NM:"니퍼"},
                    {CD:"S",NM:"하수관 세척기"},
                    {CD:"W",NM:"렌치"},
                    {CD:"C",NM:"커터"}
                ]

                    }},formatter:function(){
                switch(this.value){
                    case 'L':
                        return '누수탐지기';
                    case 'D':
                        return '드릴';
                    case 'E':
                        return '내시경';
                    case 'ETC':
                        return '미 분류';
                    case 'G':
                        return '글라인더';
                    case 'H':
                        return '해머';
                    case 'N':
                        return '니퍼';
                    case 'S':
                        return '하수관 청소기';
                    case 'W':
                        return '렌치';
                    case 'C':
                        return '커터';

                }
                }},
            {key: "item_stock", label: "재고 수량",editor: {type:"money"}},
            {key: "item_company", label: "제조 회사",sortable:false,editor: {type:"text"}},
            {key: "item_country", label: "제조 국가",sortable:false,editor: {type:"text"}},
            {key: "item_weight", label: "제품 무게",editor: {type:"money"}}
            
        ]
    });

    await fetch("/api/v2/items/all?offset="+0+"&limit="+500).then(response => response.json()
        .then(
            (res) =>{
                var list=[];
                $.each(res,function(i){
                    list.push({item_id:res[i].item_ID,item_img:res[i].imgSrc,item_name:res[i].name,item_description:res[i].description,item_price:res[i].price,item_type:res[i].dtype,item_stock:res[i].stockQuantity,item_company:res[i].manufacturedCompany,item_country:res[i].madeIn,item_weight:res[i].weight})

                })
                firstGrid.setData(list);

            }
        ));


}
async function FaqlistSetting(){
    firstGrid = new ax5.ui.grid();

    firstGrid.setConfig({
        target: $('[data-ax5grid="faq-grid"]'),
        showRowSelector: true,
        columns: [
            {key: "faq_date", label: "등록 날짜"},
            {key: "faq_id", label: "공지 번호"},
            {key: "faq_title", label: "공지 제목", width:300,editor:{type:"textarea"}},
            {key: "faq_description", label: "공지 내용", width:675,editor:{type:"textarea"}},

            
        ]
    });
    await fetch("/api/v2/faq/all",{method:"get"}).then((response) => response.json()).then(
        (data) => {
            var list=[];
            var i=0;
            $.each(data, function (idx){
                list.push({faq_date:data[idx].date,faq_id:data[idx].notice_id,faq_title:data[idx].name,faq_description:data[idx].description})

            })
            firstGrid.setData(list);
        })
}

function putNotice(){
    var selected=firstGrid.getList("selected");
    var list=[];


    if(window.confirm("작성하신 내용대로 변경하시겠습니까?")){

        $.each(selected,function(i){
            var obj={
                "notice_id": selected[i].faq_id,
                "name":selected[i].faq_title,
                "description":selected[i].faq_description,
            }
            list.push(obj);
        })
        updatePost(list);
    }
}
async function updatePost(obj){

    var url="/admin/manage/update-faq.do"

    await fetch(url,{
        method:"put",
        headers: {'Content-Type': 'application/json','X-CSRF-TOKEN': csrfToken},
        body:JSON.stringify(obj)
    }).then(response => {
        if(response.status==200){
            alert("변경에 성공하였습니다.");
            FaqlistSetting();
        }else{
            alert("내부 서버 에러 발생");
        }
    });



}
function registerNotice(){
    var popup=window.open("/admin/manage/register-faq" ,'new',toolbar=false,menubar=false,scrollbars=true,width=700,height=700)
    popup.focus();

}
async function deleteNotice() {
    list=[];
    var selected=firstGrid.getList("selected");
    $.each(selected,function(i){
        list.push(selected[i].faq_id)
    })
    let obj={
        values:list
    }
    //var NoticeDto=makeJsonObject(name,description,date);
    //name:$(this).find("#name").text(),description:$(this).find("#desc").text(),date:$(this).find("#date")
    var url = "/admin/manage/delete-faq.do"

    const res=await fetch(url, {
        method: "delete",
        headers: {'Content-Type': 'application/json', 'Accept': 'application/json;charset=utf-8','X-CSRF-TOKEN': csrfToken},
        body: JSON.stringify(obj)
    }).then(response => {
        if(response.status==200){
            alert("삭제에 성공하였습니다.")
            FaqlistSetting();
        }else{
            alert("내부 서버 에러 발생")
        }
    });




}

async function OrderlistSetting(){
    firstGrid = new ax5.ui.grid();

    firstGrid.setConfig({
        target: $('[data-ax5grid="order-grid"]'),

        columns: [
            {key: "order_Id", label: "주문 번호"},
            {key: "order_customer", label: "주문 고객"},
            {key: "order_totalprice", label: "주문 가격",formatter:"money"},
            {key: "order_date", label: "주문 날짜",width:160},
            {label: "주문 상품",width:180,columns:[
                    {key:"id", label: "식별 코드"}, //orderitem_id+//+item_id
                    {key:"itemname",label:"제품 이름"},
                    {key:"count",label:"제품 개수"},
                    {key:"price",label:"제품 가격"}


                ]},
            {
                key: "isChecked", label: "Checkbox", width: 50, sortable: false, align: "center", editor: {
                    type: "checkbox", config: {height: 17, trueValue: "Y", falseValue: "N"}
                }},
            {key: "order_address", label: "배달 장소",width:320,editor:{type:"textarea"}},
            {key: "order_status",label:"주문 상태"}

            
        ],
        body:{
            mergeCells:["order_Id","order_customer","order_totalprice","order_date","order_address","order_status"],
            onDBLClick: function(){
                if(this.item.order_status==='배송 대기중'){
                    if(confirm("배송 중인 상태로 수정하시겠습니까?")){
                        ChangeOrders(this.item.order_Id);
                        OrderlistSetting();
                    }
                }else{
                    alert("해당 주문은 배송 대기중인 건이 아닙니다.");
                }

            }
        }




    });

    await fetch("/api/v1/orders/all?offset="+0+"&limit="+50).then(response => response.json()).then(
        (res) => {
            var list=[];

            $.each(res,function(i){
                var listitems=[];
                console.log(res[i].orderId);
                var id=res[i].orderId;
                for(var j=0; j<res[i].orderItemDto.length;j++){
                    list.push({order_Id:res[i].orderId,order_customer:res[i].user_name,order_totalprice:res[i].totalprice,order_date:res[i].orderdate,id:res[i].orderItemDto[j].orderitem_id+"/"+res[i].orderItemDto[j].item_id,itemname:res[i].orderItemDto[j].itemname,count:res[i].orderItemDto[j].count,price:res[i].orderItemDto[j].price,order_address:res[i].address,order_status:res[i].deliverystatus})
                }



            })
            firstGrid.setData(list);
        }
    )


}
async function ChangeOrders(id){
    let obj={
        "order_id":id
    }
    await fetch("/admin/manage/orders/upto-delivery",{
        method:"put",headers:{'Content-Type':'application/json','X-CSRF-TOKEN': csrfToken},body:JSON.stringify(obj)
    }).then(res=> {
        if(res.status==200){
            alert("상태가 변경되었습니다.");
        }else{
            alert("서버 에러 발생");
        }
    })
}
async function MemberlistSetting(){
    firstGrid = new ax5.ui.grid();

    firstGrid.setConfig({
        target: $('[data-ax5grid="member-grid"]'),
        columns: [
            {key: "member_id", label: "회원 번호"},
            {key: "member_name", label: "회원 이름",width:200},
            {key: "member_role", label: "회원 타입"},
            {key: "member_username", label: "회원 아이디",width:200},
            {key: "member_email", label: "회원 이메일",width:200},
            {key: "member_Tel", label: "회원 연락처"},
            {key: "member_date", label: "가입 날짜",width:300}
            
        ]
    });
    var list=[];
    const res=await fetch("/api/v1/members/all?offset="+0+"&limit="+50).then(response => response.json())
    $.each(res,function(i){
        list.push({member_id:res[i].member_id,member_name:res[i].name,member_role:res[i].roleType,member_username:res[i].username,member_email:res[i].email,member_Tel:res[i].phone_num,member_date:res[i].enrollddate})
    })
    firstGrid.setData(list);
}
async function deleteItems(){
    var selected=getItemIDlists(firstGrid.getList("selected"));
    if(selected.length==0){
        alert("삭제하실 제품을 선택해주세요");
        return;
    }
    let obj={
        "itemIdList":selected
    }
    const res=await fetch("/admin/manage/items/delete-item.do",{method:"delete",headers:{'Content-Type':'application/json','X-CSRF-TOKEN': csrfToken},body:JSON.stringify(obj)}).then(response => response.text());
    alert(res);
    ItemlistSetting();

}
function getItemIDlists(list){
    var idlist=[];
    $.each(list,function(i){
        idlist.push(list[i]["item_id"]);
    })
    return idlist;
}
function replaceItems(){
    let formData=new FormData();
    selected=firstGrid.getList("selected");
    if(selected.length==1){
        let obj={
            "name":selected[0]['item_name'],
            "description": selected[0]['item_description'],
            "price": selected[0]['item_price'],
            "dtype":selected[0]['item_type'],
            "stockquantity":selected[0]['item_stock'],
            "manufacturedcompany":selected[0]['item_company'],
            "madein":selected[0]['item_country'],
            "weight":selected[0]['item_weight']
        }
        if(confirm("이미지를 변경하십니까?")){
            var popup=window.open("/admin/manage/image/putImage" ,'new',toolbar=false,menubar=false,scrollbars=true,width=700,height=700)
            popup.focus();
        }else{
            formData.append('file',null);
            formData.append('body',JSON.stringify(obj));
            updateItemSequence(selected[0].item_id,formData);
        }



        /*
        var baseurl=window.location;
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

         */
    }else{
        alert("제품을 한개만 선택해주세요.");
    }
}
function registerItems(){
    window.open('/admin/manage/register-item','new',toolbar=false,menubar=false,scrollbars=true,width=700,height=900);return false

}
function gotoHome(){
    location.assign('/');
}
function getReturnValue(returnValue) {
    let formData=new FormData();
    formData.append('file',returnValue.get('file'));
    let obj={
        "name":selected[0]['item_name'],
        "description": selected[0]['item_description'],
        "price": selected[0]['item_price'],
        "dtype":selected[0]['item_type'],
        "stockquantity":selected[0]['item_stock'],
        "manufacturedcompany":selected[0]['item_company'],
        "madein":selected[0]['item_country'],
        "weight":selected[0]['item_weight']
    }
    console.log(obj);
    formData.append('body',JSON.stringify(obj))
    updateItemSequence(selected[0].item_id,formData);

}
async function updateItemSequence(id,formData){
    var url="/admin/manage/items/"+id+"/update-item.do"

    await fetch(url,{method:"put",headers:{'X-CSRF-TOKEN': csrfToken},body:formData}).then(response => response.text()).then(
        (res) => {
            alert(res);

        }
    )



}