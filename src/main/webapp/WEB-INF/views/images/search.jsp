<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<input type="hidden" id="tag" value="${tag}" />
<input type="hidden" id="tagId" value="${dto.tagId}" />

<section class="search-section">
	<div class="search-profile">
		<div class="search-left">
			<div class="search-img-wrap">
				<img class="profile-image" src="/upload/${dto.popularImages[0].postImageUrl}"
									onerror="this.src='/image/person.jpeg'" id="topImageUrl" />
			</div>
		</div>
		<div class="search-right">
			<div class="search-tag">
				<h1># ${tag}</h1>
			</div>
			<div class="search-count">
				게시물 <span>${dto.imageCount}</span>
			</div>
		</div>
	</div>
</section>

<!--검색 결과 -->
<main class="searchPopular">
	<div class="exploreContainer">
		<div>
			<span><h3>인기 게시물</h3></span>
		</div>
		<!--검색 결과 갤러리(GRID배치)-->
		<div class="search-gallery" id="searchPopularList">
			
            <c:forEach var="image" items="${dto.popularImages}">

                <div class="p-img-box" onclick="modalDetail(${image.imageId})">
                    <a href="#"> <img src="/upload/${image.postImageUrl}" name="${image.imageId}"/>
                    </a>
                </div>

			</c:forEach>
		</div>

	</div>
</main>


<c:choose>
	<c:when test="${dto.enoughImage}">
		<main class="searchResult">
			<div class="exploreContainer">
				<div>
					<span><h3>최근 사진</h3></span>
				</div>

				<div class="search-gallery" id="searchResult-List">


				</div>
			</div>
		</main>
	</c:when>
</c:choose>


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


<script src="/js/search.js"></script>
<%@ include file="../layout/footer.jsp"%>

