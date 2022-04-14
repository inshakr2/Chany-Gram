<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<main class="main">
	<section class="container">
		<!--전체 리스트 시작-->
		<article class="story-list" id="storyList">

			<!--전체 리스트 아이템-->
			<div class="story-list__item">
				<div class="sl__item__header">
					<div>
						<img class="profile-image" src="#"
							onerror="this.src='/image/person.jpeg'" />
					</div>
					<div>c.yeol2</div>
				</div>

				<div class="sl__item__img">
					<img src="/image/home.jpg" />
				</div>

				<div class="sl__item__contents">
					<div class="sl__item__contents__icon">

						<button>
							<i class="fas fa-heart active" id="storyLikeIcon-1" onclick="toggleLike()"></i>
						</button>
					</div>

					<span class="like"><b id="storyLikeCount-1">3 </b>likes</span>

					<div class="sl__item__contents__content">
						<p>테스트 이미지</p>
					</div>

					<div id="storyCommentList-1">

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
						<input type="text" placeholder="댓글 달기..." id="storyCommentInput-1" />
						<button type="button" onClick="addComment()">게시</button>
					</div>

				</div>
			</div>

		</article>
	</section>
</main>
<script src="/js/story.js"></script>
</body>
</html>
