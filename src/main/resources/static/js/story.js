/**
	2. 스토리 페이지
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 좋아요, 안좋아요
	(4) 댓글쓰기
	(5) 댓글삭제
 */

let principalId = $("#principalId").val();

// (1) 스토리 로드하기
function storyLoad(lastImageId) {
	$.ajax({
		url:`/api/image?lastImageId=${lastImageId}`,
		type:"GET",
		dataType:"json"
	}).done(res=>{
		res.data.forEach((image)=>{
			let storyItem = getStoryItem(image);
			$("#storyList").append(storyItem);
		})
	}).fail(error=>{

	});
}
// Number.MAX_SAFE_INTEGER (9007199254740991.. 약 9천조)
// 최초 로딩시 가장 작은 id값이 없기 때문에 해당 값으로 로딩 차후 변경 필요한 시점이 있을 수 있음.
storyLoad(Number.MAX_SAFE_INTEGER);
function getStoryItem(image) {
	let item = `<div class="story-list__item" id="${image.imageId}">
	<div class="sl__item__header">
		<div>
			<img class="profile-image" src="/upload/${image.userProfileImageUrl}"
				onerror="this.src='/image/person.jpeg'" />
		</div>
		<div>
			<a href="/user/${image.userId}">
				<b>${image.username}</b>
			</a>
		</div>
	</div>

	<div class="sl__item__img">
		<img src="/upload/${image.postImageUrl}" />
	</div>

	<div class="sl__item__contents">
		<div class="sl__item__contents__icon">

			<button>`;

			if (image.likeStatus) {
				item += `<i class="fas fa-heart active" id="storyLikeIcon-${image.imageId}" onclick="toggleLike(${image.imageId})"></i>`
			} else {
				item += `<i class="far fa-heart" id="storyLikeIcon-${image.imageId}" onclick="toggleLike(${image.imageId})"></i>`
			}
				

		item += `
			</button>
		</div>

		<span class="like"><b id="storyLikeCount-${image.imageId}">${image.likeCount} </b>likes</span>

		<div class="sl__item__contents__content">
			<p>${image.caption}</p>
		</div>
		<div class="tag">`;
		
		image.tags.forEach((tag) => {
			item += `<span class="tag-span" onclick="location.href='/images/search?tag=${tag}'">#${tag} </span>`;
		})


		item += `</div>
		<div id="storyCommentList-${image.imageId}">`;

		
		image.comments.forEach((comment) => {

			item += `
				<div class="sl__item__contents__comment" id="storyCommentItem-${comment.commentId}"">
					<p>
						<b>${comment.username} :</b> ${comment.content}
					</p>`

					if(principalId == comment.userId){
						item += 
						`<button onClick="deleteComment(${comment.commentId})">
							<i class="fas fa-times"></i>
						</button>`
					}
					
			item += `	
				</div>
			`
		})
			
		item += `
		</div>

		<div class="sl__item__input">
			<input type="text" placeholder="댓글 달기..." id="storyCommentInput-${image.imageId}" />
			<button type="button" onClick="addComment(${image.imageId})">게시</button>
		</div>

	</div>
</div>`;

	return item;

}

// (2) 스토리 스크롤 페이징하기
function getLastImageId() {

	var id = [];
	var story = document.getElementsByClassName('story-list__item');

	for(let i = 0; i < story.length; i++)  {
		id.push(story[i].id);
	  }
	id = Math.min.apply(null, id);

	return id;
}

$(window).scroll(() => {

	let checkNum = $(window).scrollTop() - ($(document).height() - $(window).height());

	if (checkNum < 1 && checkNum > -1) {

		var lastImageId = getLastImageId();
		storyLoad(lastImageId);

	}
});
