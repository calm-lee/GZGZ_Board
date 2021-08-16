<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<div class="d-flex justify-content-center">

	<div class="login-box">

		<form id="loginForm" method="post" action="/user/sign_in">
		
			<div class="input-group-prepand">
				<%--input-group-prepand : input  --%>
				<span class="input-group-text">ID</span>
			</div>
			<input type="text" class="form-control" id="loginId" name="loginId">

			<div class="input-group-prepand mt-3">
				<%--input-group-prepand : input  --%>
				<span class="input-group-text">Password</span>
			</div>			
			<input type="password" class="form-control" id="password" name="password">
			 
			<input type="submit" class="btn btn-primary btn-block mt-3" value="로그인">
			<!-- btn-block: 부모 블록만큼 사이즈를 늘림 -->
			
			<a href="/user/sign_up_view" class="btn btn-dark btn-block">회원가입</a>
			
		</form>
	</div>

</div>

<script>
	$(document).ready(function(){
		
		$('#loginForm').submit(function(e){
			e.preventDefault();
			
			
			// validation check
			let login = $('#input[name=loginId]').val();
			if(login == ''){
				alert("아이디를 입력해주세요.");
				return;
			}
			
			let password = $('#input[name=password]').val();
			if(password == ''){
				alert("비밀번호를 입력해주세요.");
				return;
			}
		
			// AJAX로 submit
			let url = $(this).attr('action');
			let params = $(this).serialize();
			
			
			$.post(url,params).done(function(data){
				if(data.result == 'success'){
					location.href = "/post/post_list_view";
				} else {
					alert("로그인에 실패했습니다. 다시 시도해주세요.");
				}
			});
			
		});
	});
</script>
