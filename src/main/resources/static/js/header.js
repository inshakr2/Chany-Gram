
// (1) 유저 프로파일 페이지 구독하기, 구독취소
function searchBoxOpen() {
	
	$(".search_box").css("display", "flex");
}

function searchBoxClose() {
	
	$(".search_box").css("display", "none");
}

$(".search-bar_input").on("change input", function() {
    var keyword = $(this).val();

    $.ajax({
        type:'POST',
        url:"/api/user/search",
        data:{username:keyword}
    }).done(res=>{
        console.log(res);
    }).fail(error=>{
        console.log(error);
    });
})

// 프로필 이미지 , 아이디 , 닉네임 , pk , 팔로우 유무