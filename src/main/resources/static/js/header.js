
// (1) 유저 프로파일 페이지 구독하기, 구독취소
function searchBoxOpen() {
	
	$(".search_box").css("display", "flex");
}

function searchBoxClose() {
	
	$(".search_box").css("display", "none");
}

function getSearchUserList(data) {

    let item = `
	<div class="search__item" id="searchModalItem-${data.userId}">
		<div class="search__img">
			<img src="/upload/${data.userProfileImageUrl}" onerror="this.src='/image/person.jpeg'"/>
		</div>
		<div class="search__text">
            <div class="text_result">
                <a href="/user/${data.userId}">
                    <h2>${data.username}</h2>
                </a>
            </div>`;

            if(data.subscribeStatus) {
                item += `<div class="text_name">${data.name} ㆍ 팔로잉</div>`;
            }
                
        item += `</div>
    </div>`;
    
    return item;
}

function getSearchTagList(data) {

    let item = `
	<div class="search__item">
		<div class="search__img">
			<img src="/image/hashtag.png"/>
		</div>
		<div class="search__text">
            <div class="text_result">
                <a href="images/search?tag=${data.tag}">
                    <h2>#${data.tag}</h2>
                </a>
            </div>
            <div class="text_count">게시물 ${data.count}</div>
        </div>
    </div>`;
    
    return item;
}

$(".search-bar_input").on("change input", function() {
    var keyword = $(this).val();
    if(keyword != "") {
        $.ajax({
            type:'POST',
            url:"/api/search",
            data:{searchKeywords:keyword}
        }).done(res=>{
            $(".search-list").empty();

            res.data.forEach((d)=> {

                if(d.tagResult) {
                    let item = getSearchTagList(d);
                    $(".search-list").append(item);
                } else {
                    let item = getSearchUserList(d);
                    $(".search-list").append(item);
                }
                
            });

        }).fail(error=>{
            console.log(error);
        });
    }
})