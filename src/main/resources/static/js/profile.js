/**
  1. 유저 프로파일 페이지
  (0) 프로필 이미지 페이징
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


  // 프로필 이미지 페이징
var page = 0;
var userId = $("#pageUserId").val();

// (0) 프로필 페이지 이미지 로드
function resultLoad(page, userId) {
	$.ajax({
		url:`/api/user?page=${page}&userId=${userId}`,
		type:"GET",
		dataType:"json"
	}).done(res=>{
		res.data.forEach((image)=>{
			let item = getResultItem(image);
			$(".tab-1-content-inner").append(item);
		})
	}).fail(error=>{
		console.log(error);
	});
}

resultLoad(page, userId);

$(window).scroll(() => {

	let checkNum = $(window).scrollTop() - ($(document).height() - $(window).height());

	if (checkNum < 1 && checkNum > -1) {
		page += 1;
		resultLoad(page, userId);

	}
});


function getResultItem(image) {

	let item = `<div class="img-box" onclick="modalDetail(${image.imageId})">

	<a href=""> 
		<img src="/upload/${image.postImageUrl}" >
	</a>

<div class="comment">
	<a href="#" class=""> <i class="fas fa-heart">
		</i><span>${image.likesCount}</span>
	</a>
</div>

</div>`

	return item;
}

  

let principalId = $("#principalId").val();

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
