/**
	2. 스토리 페이지
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 좋아요, 안좋아요
	(4) 댓글쓰기
	(5) 댓글삭제
 */

// (1) 스토리 로드하기
function storyLoad(lastImageId) {
	$.ajax({
		url:`/api/image?lastImageId=${lastImageId}`,
		type:"GET",
		dataType:"json"
	}).done(res=>{
		console.log(res);
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
		<div>${image.username}</div>
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

		<div id="storyCommentList-${image.imageId}">

			<div class="sl__item__contents__comment" id="storyCommentItem-1"">
				<p>
					<b>test :</b> 테스트 댓글.
				</p>

				<button>
					<i class="fas fa-times"></i>
				</button>

			</div>

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


// (3) 좋아요, 안좋아요
function toggleLike(imageId) {
	let likeIcon = $(`#storyLikeIcon-${imageId}`);
	if (likeIcon.hasClass("far")) {

		$.ajax({
			url:`/api/image/${imageId}/likes`,
			type:"POST",
			dataType:"JSON"
		}).done(res=>{	

			let likeCount = $(`#storyLikeCount-${imageId}`).text();
			likeCount = Number(likeCount) + 1;
			$(`#storyLikeCount-${imageId}`).text(likeCount);

			likeIcon.addClass("fas");
			likeIcon.addClass("active");
			likeIcon.removeClass("far");
		}).fail(error=>{
			console.log("ERROR", error);
		});
	} else {

		$.ajax({
			url:`/api/image/${imageId}/likes`,
			type:"DELETE",
			dataType:"JSON"
		}).done(res=>{

			let likeCount = $(`#storyLikeCount-${imageId}`).text();
			likeCount = Number(likeCount) - 1;
			$(`#storyLikeCount-${imageId}`).text(likeCount);

			likeIcon.removeClass("fas");
			likeIcon.removeClass("active");
			likeIcon.addClass("far");
		}).fail(error=>{
			console.log("ERROR", error);
		});
	}
}

// (4) 댓글쓰기
function addComment(imageId) {

	let commentInput = $(`#storyCommentInput-${imageId}`);
	let commentList = $(`#storyCommentList-${imageId}`);

	let data = {
		imageId: imageId,
		content: commentInput.val()
	}

	if (data.content === "") {
		alert("댓글을 작성해주세요!");
		return;
	}

	$.ajax({
		type:"POST",
		url:"/api/comment",
		data:JSON.stringify(data),
		contentType:"application/json; charset=utf-8",
		dataType:"json"
	}).done(res=>{
		console.log(res);
	}).fail(error=>{
		console.log(error);
	});

	let content = `
			  <div class="sl__item__contents__comment" id="storyCommentItem-2""> 
			    <p>
			      <b>GilDong :</b>
			      댓글 샘플입니다.
			    </p>
			    <button><i class="fas fa-times"></i></button>
			  </div>
	`;
	commentList.prepend(content);
	commentInput.val("");
}

// (5) 댓글 삭제
function deleteComment() {

}







