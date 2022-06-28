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
                    <p>사진 캡션 수정</p>
                </div>
                <!--edit 로고 end-->
                
                <!--edit Form-->
                <div class="edit-bottom">
                    <div class="origin-img">
                        <img src="/upload/${image.postImageUrl}" alt="" id="imageUploadPreview" />
                    </div>
                    
                    <div class="origin-img-detail">
                        <input type="text" placeholder="${image.caption}" name="caption" id="edited-caption-${image.imageId}"/>
                        <button class="cta blue">수정</button>
                    </div>
                </div>

            </section>
        </main>
        <br/><br/>
	
	<script src="/js/upload.js" ></script>
    <%@ include file="../layout/footer.jsp" %>