<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<div class="d-flex justify-content-center my-4">
	<!-- 이번에는 form태그로 감싸지 않을 예정 -->
	
	<div class="post-box">
		
		<h1>글쓰기</h1>
			
			<input type="text" class="form-control" name="subject" placeholder="제목을 입력해주세요.">
			<textarea class="form-control" rows="15" cols="100" name="content" placeholder="내용을 입력해주세요.">
			</textarea>
			
			<div>
				<%-- 여러 파일을 업로드할 경우에는 multiple property를 추가한다. --%>
				<input type="file" class="form-control" accept=".jpg, .jpeg, .png, .gif" name="image">
			</div>
			
			<div class="mt-3 clearfix">
				<button type="button" id="postList" class="btn btn-dark mt-2">목록</button>
				
				<%-- float 정렬을 하게 되면 block 태그도 옆에 계속 붙게 되기 때문에 해지를 해줘야 한다. --%>
				<div class="float-right">
					<button id="clearBtn" class="btn btn-secondary">모두 지우기</button>
					<button id="saveBtn" class="btn btn-info">저장</button>
				</div>
			
			</div>
	</div>
</div>

<script>
	$(document).ready(function(){
		
		// 목록 버튼 클릭
		$('#postList').on('click',function(e){
			location.href = "/post/post_list_view";
		});
		
		// 모두 지우기 버튼 클릭
		$('#clearBtn').on('click',function(e){
			// 제목과 textarea, image 파일 영역을 빈 칸으로 채운다.
			$('input[name=subject]').val(''); // 빈칸 만들기
			$('textarea[name=content]').val('');
			$('input[name=image]').val('');
		});
		
		// 저장 버튼 클릭 -> DB 저장
		$('#saveBtn').on('click', function(){
			
			let subject = $('input[name=subject]').val().trim();
			
			if(subject == ''){
				alert("제목을 입력해주세요.");
				return;
			}
			
			let content = $('textarea[name=content]').val();
			if(content == ''){
				alert("내용을 입력해주세요.");
				return;
			}
			
			// 파일이 업로드 된 경우에만 확장자 체크
			let file = $('input[name=image]').val();
			//console.log(file);
			if(file != ''){
				let ext = file.split('.').pop().toLowerCase(); // .을 기준으로 나누고 확장자가 있는 마지막 배열 칸을 가져오고 소문자로 모두 변경
				if($.inArray(ext, ['gif', 'jpg', 'jpeg', 'png']) == -1){
					alert("gif, jpg, jpeg, png 파일만 업로드할 수 있습니다.");
					$('input[name=image]').val(''); // 내용을 비운다.
					return;
				}
			}
			
			//확장자 validation(2)
/* 			let ext = file.split('.'); // image.png -> img:[0], png:[1]
			if(ext.length < 2 || 
					(ext[ext.length - 1] != 'jpg' //확장자 확인
					&& ext[ext.length - 1] != 'jpeg'
					&& ext[ext.length - 1] != 'png'
					&& ext[ext.length - 1] != 'gif'
							)){
				alert("이미지 파일만 업로드 가능합니다.");
				$('#file').val('');
				return;
			} */
			
			// -- validation check 끝
			
			// 서버에 보내기
			// form 태그를 JavaScript에서 보낸다.
			let formData = new FormData(); //form 태그를 위한 객체를 새로 만듦
			formData.append("subject", subject);
			formData.append("content", content);
			
			// $('input[name=image]')[0] => 첫 번째 input file 태그를 의미 
			// .files[0] => 업로드된 파일 중 첫 번째를 의미
			formData.append("file", $('input[name=image]')[0].files[0]);
		
			// AJAX form 태그에 있는 데이터를 서버에 전송 
			$.ajax({
				url: '/post/create'
				, method: 'POST'
				, data: formData
				
				// 파일 업로드 시 필수 파라미터
				, processData: false // 기본은 true(json, String 형태)로 넘어갔으나 이번에는 formData 객체를 통으로 넘겨야 하므로 false로 바꿀 것임
				, contentType: false
				, enctype: 'multipart/form-data'
				
				, success: function(data){
					if(data.result == 'success'){
						alert("메모가 저장되었습니다.");
						location.href = "/post/post_list_view";
					}
				}
				, error: function(e){
					alert("메모 저장에 실패했습니다. 관리자에게 문의해주십시오." + e);
				}
			});
		});
	});
</script>