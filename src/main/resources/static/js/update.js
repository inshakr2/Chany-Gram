// (1) 회원정보 수정
function update(userId) {

    let data = $("#profileUpdate").serialize();

    $.ajax({
        type: "patch",
        url: `/api/user/${userId}`,
        data: data,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json"
    }).done(res=>{
        location.href=`/user/${userId}`
    }).fail(error=>{
    })

}