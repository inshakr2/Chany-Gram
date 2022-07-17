<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<!--프로필 섹션-->
<section class="profile">
	<!--유저정보 컨테이너-->
	<div class="profileContainer">
		
		<!--유저이미지-->
		<div class="profile-left">
			<c:choose>
				<c:when test="${dto.userId == principal.user.id}">
				
					<div class="profile-img-wrap story-border"
						onclick="popup('.modal-image')">

						<form id="userProfileImageForm">
							<input type="file" name="profileImageFile" style="display: none;"
								id="userProfileImageInput" />
						</form>

						<img class="profile-image" src="/upload/${dto.userProfileImageUrl}"
							onerror="this.src='/image/person.jpeg'" id="userProfileImage" />
					</div>
								
				</c:when>
				<c:otherwise>

					<div class="profile-img-wrap story-border">

						<img class="profile-image" src="/upload/${dto.userProfileImageUrl}"
							onerror="this.src='/image/person.jpeg'" id="userProfileImage" />
					</div>

				</c:otherwise>
			</c:choose>		
		</div>

		<!--유저이미지end-->

		<!--유저정보 및 사진등록 구독하기-->
		<div class="profile-right">
			<div class="name-group">
				<h2>${dto.username}</h2>

				<c:choose>
					<c:when test="${dto.pageOwner}">
						<button class="cta" onclick="location.href='/images/upload'">사진등록</button>
					</c:when>
					<c:otherwise>

						<!-- 로그인 유저 본인 아닌 프로필 페이지의 경우 구독하기 / 구독취소 버튼 분기 -->
						<c:choose>
							<c:when test="${dto.subscribeState}">
								<button class="cta blue" onclick="toggleSubscribe(${dto.userId}, this)">구독취소</button>
							</c:when>
							<c:otherwise>
								<button class="cta" onclick="toggleSubscribe(${dto.userId}, this)">구독하기</button>
							</c:otherwise>
						</c:choose>

					</c:otherwise>
				</c:choose>

				
				
				<button class="modi" onclick="popup('.modal-info')">
					<i class="fas fa-cog"></i>
				</button>
			</div>

			<div class="subscribe">
				<ul>
					<li>
						<a href=""> 게시물<span>${dto.imageCount}</span></a>
					</li>
					<li>
						<a href="javascript:followerInfoModalOpen(${dto.userId});"> 팔로워
							<span id="profileFollowerCount">${dto.follower}</span>
						</a>
					</li>
					<li>
						<a href="javascript:subscribeInfoModalOpen(${dto.userId});"> 팔로잉
							<span id="profileFollowingCount">${dto.following}</span>
						</a>
					</li>
				</ul>
			</div>
			<div class="state">
				<h4>${dto.userAboutMe}</h4>
				<h4>${dto.userWebsite}</h4>
			</div>
		</div>
		<!--유저정보 및 사진등록 구독하기-->

	</div>
</section>

<!--게시물컨섹션-->
<section id="tab-content">
	<!--게시물컨컨테이너-->
	<div class="profileContainer">
		<!--그냥 감싸는 div (지우면이미지커짐)-->
		<div id="tab-1-content" class="tab-content-item show">
			<!--게시물컨 그리드배열-->
			<div class="tab-1-content-inner">

				<!--아이템들-->

                <c:forEach var="image" items="${dto.images}">
                    <div class="img-box" onclick="modalDetail(${image.imageId})">

							<a href=""> 
								<img src="/upload/${image.postImageUrl}" >
							</a>
			
                        <div class="comment">
                            <a href="#" class=""> <i class="fas fa-heart">
								</i><span>${image.likesCount}</span>
                            </a>
                        </div>

                    </div>
                </c:forEach>

				<!--아이템들end-->
			</div>
		</div>
	</div>
</section>

<!--로그아웃, 회원정보변경 모달-->
<div class="modal-info" onclick="modalInfo()">
	<div class="modal">
		<c:choose>
			<c:when test="${dto.pageOwner}">
				<button onclick="location.href='/user/${dto.userId}/update'">회원정보 변경</button>
				<button class="withdrawalMember" onclick="withdrawalMember(${principal.user.id})">회원탈퇴</button>
			</c:when>
		</c:choose>
		<button onclick="location.href='/logout'">로그아웃</button>
		<button onclick="closePopup('.modal-info')">취소</button>
	</div>
</div>
<!--로그아웃, 회원정보변경 모달 end-->

<!--프로필사진 바꾸기 모달-->
<div class="modal-image" onclick="modalImage()">
	<div class="modal">
		<p>프로필 사진 바꾸기</p>
		<button onclick="profileImageUpload(${dto.userId}, ${principal.user.id})">사진 업로드</button>
		<button onclick="closePopup('.modal-image')">취소</button>
	</div>
</div>

<!--프로필사진 바꾸기 모달end-->

<div class="modal-subscribe">
	<div class="subscribe">
		<div class="subscribe-header">
			<span>구독정보</span>
			<button onclick="modalClose()">
				<i class="fas fa-times"></i>
			</button>
		</div>

		<div class="subscribe-list" id="subscribeModalList">
			<!-- 모달 리스트 ajax에서 처리 -->
		</div>
	</div>

</div>

<div class="modal-follower">
	<div class="subscribe">
		<div class="subscribe-header">
			<span>구독정보</span>
			<button onclick="followerModalClose()">
				<i class="fas fa-times"></i>
			</button>
		</div>

		<div class="subscribe-list" id="followerModalList">
			<!-- 모달 리스트 ajax에서 처리 -->
		</div>
	</div>

</div>


<!-- 이미지 상세 모달 -->

<div class="modal-detail">
	<div class="detail">
		<div class="detail-header">
			<span>
			<button onclick="modalDetailClose()">
				<i class="fas fa-times"></i>
			</button>

		</div>

		
		<div class="grid-container">
			
				<!-- image 영역 -->
				<div class="detail-img-box">

					

				</div>

				<!-- 부가정보 영역 -->
				<div class="detail-comment-box">





				</div>

		</div>
	</div>
</div>

<script src="/js/profile.js"></script>

<%@ include file="../layout/footer.jsp"%>