
// (1) 유저 프로파일 페이지 구독하기, 구독취소
function searchBoxOpen() {
	
	$(".search_box").css("display", "flex");
}

function searchBoxClose() {
	
	$(".search_box").css("display", "none");
}

$(".search-bar_input").on("change input", function() {
    var currentVal = $(this).val();
    console.log("changed!");
})