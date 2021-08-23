<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="d-flex justify-content-center my-4">
	<!-- 이번에는 form태그로 감싸지 않을 예정 -->

	<div class="post-box">

		<h1>글 상세/수정</h1>

		<input type="text" class="form-control" name="subject"
			placeholder="제목을 입력해주세요.">
		<textarea class="form-control" rows="15" cols="100" name="content"
			placeholder="내용을 입력해주세요.">
		</textarea>
		<div class="file-upload-btn clearfix">
		<input type="file" class="form-control float-right"
					accept=".jpg, .jpeg, .png, .gif" name="image">
		</div>
		
		<%-- 이미지가 있을 때에만 이미지 영역 추가 --%>
		<c:if test="${not empty post.imgPath}">
		<div class="image-area">
			<img src="'${post.imgPath}" alt="업로드 이미지" width="300">
		</div>
		</c:if>
		
		<div class="btn-area clearfix mt-5">
			<button id="postDeleteBtn" data-post-id="${post.id}" class="btn btn-secondary float-left">삭제</button>
			<button type="button" id="postList" class="btn btn-dark">목록</button>

				<%-- float 정렬을 하게 되면 block 태그도 옆에 계속 붙게 되기 때문에 해지를 해줘야 한다. --%>
				<div class="float-right">
					<button id="clearBtn" class="btn btn-secondary">모두 지우기</button>
				<%-- postId를 자바스크립트에서 사용하기 위해 data 속성을 추가한다. data-속성명 --%>
					<button id="saveBtn" class="btn btn-info" type="button"
						data-post-id="${post.id}">저장</button>
				</div>

		</div>


	</div>
</div>

<script>
	$(document).ready(function() {
		
		// 목록 버튼 클릭
		$('#postList').on('click', function(){
			location.href = "/post/post_list_view";
		});
		
		//삭제버튼 클릭
		$('#postDeleteBtn').on('click'), function(){}
		
		
		//글 내용 저장
		$('#saveBtn').on('click', function(e) {
			e.preventDefault();
			
			let subject = $('input[name=subject]').val().trim();

			if (subject == '') {
				alert("제목을 입력해주세요.");
				return;
			}

			let content = $('textarea[name=content]').val();
			if (content == '') {
				alert("내용을 입력해주세요.");
				return;
			}

			// 파일이 업로드 된 경우에만 확장자 체크
			let file = $('input[name=image]').val();
			//console.log(file);
			if (file != '') {
				let ext = file.split('.').pop().toLowerCase(); // .을 기준으로 나누고 확장자가 있는 마지막 배열 칸을 가져오고 소문자로 모두 변경
				if ($.inArray(ext, [ 'gif', 'jpg', 'jpeg', 'png' ]) == -1) {
					alert("gif, jpg, jpeg, png 파일만 업로드할 수 있습니다.");
					$('input[name=image]').val(''); // 내용을 비운다.
					return;
				}
			}

			// 서버에 보내기
			// form 태그를 JavaScript에서 보낸다.
			let formData = new FormData(); //form 태그를 위한 객체를 새로 만듦
			formData.append("postId", $(this).data('post-id'));
			formData.append("subject", subject);
			formData.append("content", content);

			// $('input[name=image]')[0] => 첫 번째 input file 태그를 의미 
			// .files[0] => 업로드된 파일 중 첫 번째를 의미
			formData.append("file", $('input[name=image]')[0].files[0]);
			
			$.ajax({
				url: '/post/update',
				type: 'post',
				data: formData,
				processData: false,
				contentType: false,
				encType: 'multipart/form-data',
				
				success: function(data){
					if(data.result == 'success'){
						alert("메모가 수정되었습니다.");
						location.reload(); //새로고침
					}
				}
				, error: function(e){
					alert("메모 수정에 실패했습니다." + e);
				}
			});
		});
	});
</script>