/**
  1. 유저 프로파일 페이지
  (1) 유저 프로파일 페이지 구독하기, 구독취소
  (2) 팔로워 정보 모달 보기
  (3) 팔로잉 리스트 모달 정보 (추가)
  (4) 유저 프로필 사진 변경
  (5) 사용자 정보 메뉴 열기 닫기
  (6) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
  (7) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달 
  (8) 구독자 정보 모달 닫기
  (9) 회원 탈퇴
 */

// (1) 유저 프로파일 페이지 구독하기, 구독취소
function toggleSubscribe(toUserId, obj) {
	if ($(obj).text() === "구독취소") {

	    $.ajax({
	        type: "DELETE",
	        url: "/api/subscribe/" + toUserId,
			dataType: "json"
	    }).done(res=> {
			$(obj).text("구독하기");
			$(obj).toggleClass("blue");
			
			location.reload();
	    }).fail(error=> {
			console.log("구독취소 실패", error)
	    });

		
	} else {

        $.ajax({
            type: "POST",
            url: "/api/subscribe/" + toUserId,
			dataType: "json"
        }).done(res=> {
			$(obj).text("구독취소");
			$(obj).toggleClass("blue");

			location.reload();

        }).fail(error=> {
			console.log("구독하기 실패", error)
        });

		
	}
}

// (2) 팔로워 정보  모달 보기
function subscribeInfoModalOpen(pageUserId) {
	$(".modal-subscribe").css("display", "flex");

	$.ajax({
		type: "GET",
		url: `/api/user/${pageUserId}/subscribe`,
		dataType: "json"
	}).done(res=> {
		res.data.forEach((u)=> {
			let item = getSubscribeModalItem(u);
			$("#subscribeModalList").append(item);
		});
		
	}).fail(error=> {
	});
}

// (3) 팔로잉 리스트 모달 정보 (추가)
function followerInfoModalOpen(pageUserId) {
	$(".modal-follower").css("display", "flex");

	$.ajax({
		type: "GET",
		url: `/api/user/${pageUserId}/follower`,
		dataType: "json"
	}).done(res=> {
		res.data.forEach((u)=> {
			let item = getSubscribeModalItem(u);
			$("#followerModalList").append(item);
		});
		
	}).fail(error=> {
	});
}

function followerModalClose() {
	$(".modal-follower").css("display", "none");
	location.reload();
}


function getSubscribeModalItem(u) {
	let item = `
	<div class="subscribe__item" id="subscribeModalItem-${u.userId}">
		<div class="subscribe__img">
			<img src="/upload/${u.profileImageUrl}" onerror="this.src='/image/person.jpeg'"/>
		</div>
		<div class="subscribe__text">
			<a href="/user/${u.userId}">
				<h2>${u.username}</h2>
			</a>
		</div>
		<div class="subscribe__btn">`;

		// 동일유저가 아닐때 구독하기 / 취소 버튼 생성
		if(! u.equalUserState){
			if(u.subscribeState){
				item += `<button class="cta blue" onclick="toggleSubscribe(${u.userId}, this)">구독취소</button>`;
			}else{
				item += `<button class="cta" onclick="toggleSubscribe(${u.userId}, this)">구독하기</button>`;
			}
		}
			
	item +=		
		`</div>
	</div>
	`;

	return item;
}



// (4) 유저 프로파일 사진 변경 (완)
function profileImageUpload(pageUserId, principalId) {

	if(pageUserId != principalId) {
		return;
	}

	$("#userProfileImageInput").click();

	$("#userProfileImageInput").on("change", (e) => {
		let f = e.target.files[0];

		if (!f.type.match("image.*")) {
			alert("이미지를 등록해야 합니다.");
			return;
		}

		// 서버로 이미지 전송
		let profileImageForm = $("#userProfileImageForm")[0];
		let formData = new FormData(profileImageForm);

		$.ajax({
			type:"PUT",
			url:`/api/user/${principalId}/profileImage`,
			data:formData,
			contentType:false,
			processData:false,
			encType:"multiPart/form-data",
			dataType:"json"
		}).done(res=>{

		// 사진 전송 성공시 이미지 변경
		let reader = new FileReader();
		reader.onload = (e) => {
			$("#userProfileImage").attr("src", e.target.result);
		}
		reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.
		location.reload();
		}).fail(error=>{

			console.log("error", error);
		});


	});
}


// (5) 사용자 정보 메뉴 열기 닫기
function popup(obj) {
	$(obj).css("display", "flex");
}

function closePopup(obj) {
	$(obj).css("display", "none");
}


// (6) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
function modalInfo() {
	$(".modal-info").css("display", "none");
}

// (7) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달
function modalImage() {
	$(".modal-image").css("display", "none");
}

// (8) 팔로워 정보 모달 닫기
function modalClose() {
	$(".modal-subscribe").css("display", "none");
	location.reload();
}

// (9) 회원 탈퇴
function withdrawalMember(principalId) {

	if(confirm("정말 탈퇴 하시겠습니까?\n(※ 작성한 이미지, 댓글 등은 삭제 됩니다.)") == true)
	{
		$.ajax({
			url:`/api/user/${principalId}`,
			type:"DELETE",
			dataType:"JSON"
		}).done(res=>{
			alert("회원 탈퇴가 완료되었습니다.");
			window.location.replace("/logout");
		}).fail(error=>{

		})
	}
}

// (10) 이미지 상페 모달 오픈
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

// (11) 이미지 상세 모달 닫기
function modalDetailClose() {
	$(".modal-detail").css("display", "none");
	location.reload();
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
	`

	return item;
}


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

// 이미지 삭제
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