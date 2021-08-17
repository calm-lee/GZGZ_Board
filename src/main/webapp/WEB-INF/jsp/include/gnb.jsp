<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="d-flex">

		<div class="logo">
			<h1 class="font-weight-bold text-white p-4">메모 게시판</h1>
		</div>
		
		<div class="login-info d-flex justify-content-end">

		<%--로그인이 된 경우--%>
		<c:if test="${not empty userName}">
			<div class="mt-5 mr-5">
				<span class="text-white"><b>${userName}</b>님 안녕하세요.</span> 
				<a href="/user/sign_out" class="ml-3 text-white font-weight-bold">로그아웃</a>
			</div>
		</c:if>			
		
		<%--로그인이 안 된 경우--%>
		<c:if test="${empty userName}">
			<div class="mt-5 mr-5">
				<span class="text-white">로그인을 해주세요.</span> 
				<a href="/user/sign_up_view" class="ml-3 text-white font-weight-bold">회원가입</a>
			</div>
		</c:if>
		</div>
		
	</div>
</body>
</html>