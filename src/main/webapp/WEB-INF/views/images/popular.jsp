<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<!--인기 게시글-->
<main class="popular">
	<div class="exploreContainer">

		<!--인기게시글 갤러리(GRID배치)-->
		<div class="popular-gallery" id="popularResult-List">

		</div>

	</div>
</main>

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
<script src="/js/popular.js"></script>

<%@ include file="../layout/footer.jsp"%>

