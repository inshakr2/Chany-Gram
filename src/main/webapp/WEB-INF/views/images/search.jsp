<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<input type="hidden" id="tag" value="${tag}" />
<input type="hidden" id="tagId" value="${dto.tagId}" />

<section class="search-section">
	<div class="search-profile">
		<div class="search-left">
			<div class="search-img-wrap">
				<img class="profile-image" src="/upload/${dto.topImageUrl}"
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
<main class="searchMain">
	<div class="exploreContainer">

		<!--검색 결과 갤러리(GRID배치)-->
		<div class="search-gallery" id="searchList">

		</div>

	</div>
</main>
<script src="/js/search.js"></script>
<%@ include file="../layout/footer.jsp"%>

