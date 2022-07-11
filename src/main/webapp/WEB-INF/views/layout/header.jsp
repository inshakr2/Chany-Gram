<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal" var="principal" />
</sec:authorize>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Chanygram</title>

	<!-- 제이쿼리 -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	
	<!-- Style -->
	<link rel="stylesheet" href="/css/style.css">
	<link rel="stylesheet" href="/css/story.css">
	<link rel="stylesheet" href="/css/popular.css">
	<link rel="stylesheet" href="/css/profile.css">
	<link rel="stylesheet" href="/css/upload.css">
	<link rel="stylesheet" href="/css/update.css">
	<link rel="stylesheet" href="/css/edit.css">
	<link rel="shortcut icon" href="/image/insta.svg">
	
	<!-- Font awesome -->
	<!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" /> -->
	<script src="https://kit.fontawesome.com/dfcffd723c.js" crossorigin="anonymous"></script>
	<!-- Fonts -->
	<link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100;200;300;400;500;600;700&display=swap" rel="stylesheet">
</head>

<body>
	
	<input type="hidden" id="principalId" value="${principal.user.id}" />

	<header class="header">
		<div class="container">
			<a href="/" class="logo">
				<img src="/image/logo.jpg" alt="">
			</a>
			<div class="search_form">
    			<input class="search-bar_input" type="search" placeholder="검색" />
			</div>
			<nav class="navi">
				<ul class="navi-list">
					<li class="navi-item">
						<a href="/">
							<i class="fas fa-home"></i>
						</a>
					</li>
					<li class="navi-item">
						<a href="/images/popular">
							<i class="far fa-compass"></i>
						</a>
					</li>
					<li class="navi-item">
						<a href="/user/${principal.user.id}">
							<i class="far fa-user"></i>
						</a>
					</li>
				</ul>
			</nav>
		</div>
	</header>
