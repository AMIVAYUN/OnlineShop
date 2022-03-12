$(document).ready(function (){
    ListSetting();
})

async function getMembers(offset,limit){
    const res=await fetch("/api/v1/members/all?offset="+offset+"&limit="+limit).then(response => response.json())
    return ListPrint(res);
}

function ListSetting(){
    getMembers(0,100);
}

function ListPrint(res){


    for(i=0; i<res.length;i++){
        var html=getLiTag(res[i].member_id,res[i].name,res[i].roleType,res[i].username,res[i].email,res[i].phone_num,res[i].enrollddate)
        $("#memberlist").append(html);
    }
}
function getLiTag(memberid,name,roletype,id,email,phone_num,enrolldate){
    return '<tr><td class="member_id">'+memberid+'</td><td class="name">'+name+'</td> <td class="roleType">'+roletype+'</td> <td class="아이디">'+id+'</td><td class="email">'+email+'</td><td class="phone_num">'+phone_num+'</td><td class="enrolldate">'+enrolldate+'</td></a></span></tr>'
}