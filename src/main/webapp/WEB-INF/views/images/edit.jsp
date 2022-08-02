<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	
	<%@ include file="../layout/header.jsp" %>

    <!--메인 컨테이너-->
        <main class="editContainer">
           <section class="edit">
               
               <!--edit 로고-->
                <div class="edit-top">
                    <a href="home.html" class="">
                        <img src="/image/logo.jpg" alt="">
                    </a>
                    <p>게시물 수정</p>
                </div>
                <!--edit 로고 end-->
                
                <!--edit Form-->
                <div class="edit-bottom">
                    <div class="origin-img">
                        <img src="/upload/${image.postImageUrl}" alt="" id="imageUploadPreview" />
                    </div>
                    
                    <form class="edit-form" action="/images/${image.imageId}/edit" method="post">
                        <input type="hidden" id="imageId" value="${image.imageId}" name="imageId" />
                        <input type="hidden" id="orgTag" value="${image.tags}" name="orgTag" />
                        <div class="origin-img-detail">
                            <textarea type="text" placeholder="${image.caption}" name="caption" id="edited-caption-${image.imageId}"></textarea>
                            <br>
                            <input type="text" value="${image.tags}" name="newTag">
                            <button class="cta blue">수정</button>
                        </div>
                    </form>
                </div>

            </section>
        </main>
        <br/><br/>
	
	<script src="/js/upload.js" ></script>
    <%@ include file="../layout/footer.jsp" %>