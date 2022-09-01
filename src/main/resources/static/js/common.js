/**
  ### 유저 서비스 기능 공통 JS 모음
  (1) 이미지 상세 모달 오픈
  (2) 이미지 상세 모달 닫기
  (3) 좋아요
  (4) 댓글 작성
  (5) 댓글 삭제
  (6) 이미지 삭제
 */




let principalUserId = $("#principalId").val();

// (1) 이미지 상페 모달 오픈
function modalDetail(imageId) {
	$(".modal-detail").css("display", "flex");
	
	$.ajax({
		url:`/api/image/${imageId}`,
		type:"GET",
		dataType:"json"
	}).done(res=>{
		$(".detail-img-box").append(getImageBox(res.data));
		$(".detail-comment-box").append(getCommentBox(res.data));
	}).fail(error=>{
		console.log(error);
	});
}

// (2) 이미지 상세 모달 닫기
function modalDetailClose() {
	$(".modal-detail").css("display", "none");
	$(".detail-img-box").empty();
	$(".detail-comment-box").empty();
}

function getImageBox(image) {

	let item = `
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
	`

	return item;
}

function getCommentBox(image) {
	
	let item = `
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
			<span class="like"><b id="storyLikeCount-${image.imageId}">${image.likeCount} </b>likes</span>`;
			
			if (image.pageOwner) {
				item += `<button>
					<i class="fa-solid fa-trash fa-xs" onclick="deleteImage(${image.imageId})"></i>
				</button>
				<button>
					<i class="fa-solid fa-pen-to-square fa-xs" onclick="location.href='/images/${image.imageId}/edit'"></i>
				</button>
				`

			}

		item += `	
		</div>

		<div class="sl__item__contents__content">
			<p>${image.caption}</p>
		</div>
		<div class="tag">`

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

					if(principalUserId == comment.userId){
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
	`

	return item;
}

// (3) 좋아요
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

// (4) 댓글 작성
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

		let comment = res.data;

		let content = `
		<div class="sl__item__contents__comment" id="storyCommentItem-${comment.commentId}"> 
		  <p>
			<b>${comment.username} :</b> ${comment.content}
		  </p>
			<button onClick="deleteComment(${comment.commentId})">
		  		<i class="fas fa-times"></i>
			</button>
		</div>
		`;
		commentList.prepend(content);

	}).fail(error=>{
		console.log(error);
		alert(error.responseJSON.data.content);
	});


	commentInput.val("");
}

// (5) 댓글 삭제
function deleteComment(commentId) {

	$.ajax({
		type:"delete",
		url:`/api/comment/${commentId}`,
		dataType:"json"
	}).done(res=>{
		$(`#storyCommentItem-${commentId}`).remove();
	}).fail(error=>{

	});

}

// (6) 이미지 삭제
function deleteImage(imageId) {
	if(confirm("정말 삭제 하시겠습니까?") == true)
		{$.ajax({
			type:"delete",
			url:`/api/image/${imageId}`,
			dataType:"json"
		}).done(res=>{ 
			location.reload();
		}).fail(error=>{
			
		})
	}
}