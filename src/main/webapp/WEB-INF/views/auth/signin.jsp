<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chanygram</title>
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
        integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous" />

    <link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Pacifico&display=swap" rel="stylesheet">
</head>

<body>
    <div class="container">
        <main class="loginMain">
        <!--로그인섹션-->
            <section class="login">
               <!--로그인박스-->
                <article class="login__form__container">
                   <!--로그인 폼-->
                   <div class="login__form">
                        <p style="font-family: 'Pacifico', cursive; font-size: 36px">ChanyGram</p>
                        
                        <!--로그인 인풋-->
                        <form class="login__input" action="/auth/signin" method="post">
                            <input type="text" name="username" placeholder="유저네임" required="required" />
                            <input type="password" name="password" placeholder="비밀번호" required="required" />
                            <button>로그인</button>
                        </form>
                        <!--로그인 인풋end-->
                        
                        <!-- 또는 -->
                        <div class="login__horizon">
                            <div class="br"></div>
                            <div class="or">또는</div>
                            <div class="br"></div>
                        </div>
                        <!-- 또는end -->
                        
                        <!-- Oauth 소셜로그인 -->
                        <div class="login__facebook">
                            <button class="fb_button" onclick="javascript:location.href='/oauth2/authorization/facebook'">
                                <i id="fb_fab" class="fab fa-facebook-square"></i>
                                <span>Facebook으로 로그인</span>
                            </button>
                        </div>
                        <div class="login__google">
                            <button class="google_button" onclick="javascript:location.href='/oauth2/authorization/google'">
                                <i id="google_fab" class="fab fa-google"></i>
                                <span>Google로 로그인</span>
                            </button>
                        </div>
                        <div class="login__kakao">
                            <button class="kakao_button" onclick="javascript:location.href='/oauth2/authorization/kakao'">
                                <i id="kakao_fab" class="fa fa-comment"></i>
                                <span>Kakao로 로그인</span>
                            </button>
                        </div>
                        <div class="login__naver">
                            <button class="naver_button" onclick="javascript:location.href='/oauth2/authorization/naver'">
                                <i id="naver_fab" class="fab fa-neos"></i>
                                <span>Naver로 로그인</span>
                            </button>
                        </div>
                        <!-- Oauth 소셜로그인end -->
                        <c:choose>
                            <c:when test="${error}">
                                <p style="font-size: 20px; color: red; padding-top: 5%;">${exception}</p>
                            </c:when>
                        </c:choose>
                    </div>

                    


                    
                    <!--계정이 없으신가요?-->
                    <div class="login__register">
                        <span>계정이 없으신가요?</span>
                        <a href="/auth/signup">가입하기</a>
                    </div>
                    <!--계정이 없으신가요?end-->
                </article>
            </section>
        </main>
        
    </div>
</body>

</html>