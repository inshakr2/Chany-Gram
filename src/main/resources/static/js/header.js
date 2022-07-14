
// (1) 유저 프로파일 페이지 구독하기, 구독취소
function searchBoxOpen() {
	
	$(".search_box").css("display", "flex");
}

function searchBoxClose() {
	
	$(".search_box").css("display", "none");
}

function getSearchModalList(user) {

    let item = `
	<div class="search__item" id="searchModalItem-${user.userId}">
		<div class="search__img">
			<img src="/upload/${user.userProfileImageUrl}" onerror="this.src='/image/person.jpeg'"/>
		</div>
		<div class="search__text">
            <div class="text_username">
                <a href="/user/${user.userId}">
                    <h2>${user.username}</h2>
                </a>
            </div>`;

            if(user.subscribeStatus) {
                item += `<div class="text_name">${user.name} ㆍ 팔로잉</div>`;
            }
                
        item += `</div>
    </div>`;
    
    return item;
}

$(".search-bar_input").on("change input", function() {
    var keyword = $(this).val();
    if(keyword != "") {
        $.ajax({
            type:'POST',
            url:"/api/user/search",
            data:{username:keyword}
        }).done(res=>{
            $(".search-list").empty();

            res.data.forEach((u)=> {
                let item = getSearchModalList(u);
                $(".search-list").append(item);
            });
        }).fail(error=>{
            console.log(error);
        });
    }
})


// 프로필 이미지 , 아이디 , 닉네임 , pk , 팔로우 유무
// <div class="search-list" id="searchResultList">