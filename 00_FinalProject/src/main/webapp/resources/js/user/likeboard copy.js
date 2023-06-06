/**
 * 
 */
$(document).ready(function(){

	addList();

	$(".board_main_files").not('.slick-initialized').slick({
		dots: true,
		infinite: true,
		speed: 1000,
		slidesToShow: 1,
		slidesToScroll: 1,
		adaptiveHeight: true,
		arrows: false,
		draggable: true
	});
	
	//게시글 추가
	$('body').on("mousewheel", function(event) {
	  if (($(window).scrollTop() + $(window).innerHeight()) >= $(document).height() - 1) {
	    if (event.originalEvent.deltaY > 0) {
	      addList();
	    }
	  }
	});
	
	//게시판 상세메뉴 모달창 닫기
	$('body').on('click', function(event){
		if($(event.target).parents('.detail_modal_overlay').length < 1&&event.target.className!='board_detail_btn'){
			$('.detail_modal_overlay').hide();
		}
	});
	
	//유저프로필정보 닫기
	$('body').on('click', function(event){
		if($(event.target).parents('.user_modal_overlay').length < 1&&event.target.className!='board_user_prof_img'){
			$('.user_modal_overlay').hide();
		}
	});

});

// 게시판 상세메뉴 오픈
function open_board_detail(self){
	$(self).next().show();
}

//유저프로필정보 오픈
function open_user_modal(self){
	$(self).parent().next().show();
}

//신고 모달창 오픈
function cm_declaration(cm_no, nickname){ 
	if(loginUser_no==''){
		alert("로그인이 필요합니다.");
	}else{
		$(".decl_nickname").html("<b style='font-weight:bold'>"+nickname+"</b>의 게시글 신고");
		$(".decl_cm_no").val(cm_no);
		$('.declaration_modal_overlay').show();
	}
}

//신고 모달창 닫기
function close_declaration(){
	$('.declaration_modal_overlay').hide();
}

//신고하기 ajax
function declaration(){
	if(loginUser_no==''){
		alert("로그인이 필요합니다.");
	}else{
		$.ajax({
			type: "post",
			url: ctxPath + "/index/declaration",
			data: {
				cm_no: $('.decl_cm_no').val(),
				reason: $('.decl_modal_text').val()
			},
			dataType: "text",
			async: false,
			success: function(data) {
				if(data>0){
					alert('신고접수가 완료되었습니다.');
					close_declaration();
				}else if(data==-1){
					alert('이미 신고접수가 진행되었습니다.');
				}else{
					alert('신고처리 중 오류발생');
				}
			},
			error: function() {
				alert('신고처리 중 시스템 오류');
			}
		});
	}
}

//게시글 수정
function cm_modify(cm_no, user_no){
	if(loginUser_no==user_no){
		location.href=ctxPath+"/board/update?cm_no="+cm_no;
	}else{
		alert('본인의 게시글만 수정 가능합니다.');
	}
}

//게시글 삭제
function cm_delete(cm_no, user_no){ 
	if(user_no==loginUser_no){
		location.href=ctxPath+'/board/delete?cm_no='+cm_no;
	}else{
		alert('본인의 게시글만 삭제 가능합니다.');
	}
}

//게시글 좋아요 
function click_like_board(cm_no, self){
	if(loginUser_no!=''){
		let check;
		if($(self).find('.fa').hasClass('fa-heart')){
			check = 1; //like -> unlike
		}else{
			check = 0; // unlike -> like
		}
		$.ajax({
			type: 'get',
			url: ctxPath + '/board/like/manage',
			data: {
			  cm_no: cm_no,
			  check: check
			},
			dataType: 'json',
			contentType: 'application/json; charset=UTF-8;',
			success: function(data) {
				if(data==1){ //like -> unlike
					$(self).find('.fa').attr('class', 'fa fa-heart-o');
					let count = $(self).next().text();
					count = parseInt(count);
					$(self).next().text(count-1);
				}else if(data == 0){ // unlike -> like
					$(self).find('.fa').attr('class', 'fa fa-heart');
					let count = $(self).next().text();
					count = parseInt(count);
					$(self).next().text(count+1);
				}else{ //오류
					alert('좋아요 데이터 베이스 처리 오류');
				}
				
			}, error: function() {
				alert('좋아요 처리 중 오류');
			  }
		  });
	}else{
		alert('로그인이 필요합니다.');
	}
}

//댓글 좋아요 관리
function click_like_mention(mention_no, self){
	if(loginUser_no!=''){
		let check;
		if($(self).find('.fa').hasClass('fa-heart')){
			check = 1; //like -> unlike
		}else{
			check = 0; // unlike -> like
		}
		$.ajax({
			type: 'get',
			url: ctxPath + '/board/like/mention/manage',
			data: {
				mention_no: mention_no,
			  check: check
			},
			dataType: 'json',
			contentType: 'application/json; charset=UTF-8;',
			success: function(data) {
				if(data==1){ //like -> unlike
					$(self).find('.fa').attr('class', 'fa fa-heart-o');
					let count = $(self).next().text();
					count = parseInt(count);
					$(self).next().text(count-1);
				}else if(data == 0){ // unlike -> like
					$(self).find('.fa').attr('class', 'fa fa-heart');
					let count = $(self).next().text();
					count = parseInt(count);
					$(self).next().text(count+1);
				}else{ //오류
					alert('좋아요 데이터 베이스 처리 오류');
				}
				
			}, error: function() {
				alert('좋아요 처리 중 오류');
			  }
		  });
	}else{
		alert('로그인이 필요합니다.');
	}
}

function addMention(no, self){
	
	  // 해당 버튼에 대한 AJAX 요청을 보냅니다.
	  $.ajax({
	    type: 'get',
	    url: ctxPath + '/board/addmention',
	    data: {
	      cm_no: no,
	      user_no: loginUser_no,
	      ment: $(self).prev().val()
	    },
	    dataType: 'json',
	    contentType: 'application/json; charset=UTF-8;',
	    success: function(data) {
			// 댓글 수신 영역 초기화
			$("."+no+"board_reply_wrap").html("");
	    	// 받아온 댓글 데이터를 처리하고 해당 댓글을 목록에 추가합니다.
			let mention = data.MentionList;
			let mentionLikeList = data.MentionLikeList;

			//댓글 목록
			let table = "";
			table += "<div class='board_reply_cont_show'>";
			//<!-- 댓글이 목록이 들어가는 곳 -->
			for(let j = 0; j < mention.length; j++) {
				table +=  "<div class='board_reply_ment_cont' id='"+mention[j].mention_no+"'><div class='board_reply_user'>"+mention[j].nickname+"</div>"+
							"<div class='board_reply_ment'>"+mention[j].ment+
							
								//댓글 좋아요 버튼
								"<a class='mention_like_wrap' href='javascript:' onclick='click_like_mention("+mention[j].mention_no+", this)'>";
								
								if(mentionLikeList.find(element => element == mention[j].mention_no)!=null){
									table += "<i class='fa fa-heart' aria-hidden='true'></i>";
								}else{
									table += "<i class='fa fa-heart-o' aria-hidden='true'></i>";
								}

								table += "</a>" + "<span>" + mention[j].likeCount + "</span>" + //좋아요 end
							
							"</div>";
						if(mention[j].user_no==loginUser_no){
							table += "<input class='board_reply_delete' type='button' value='삭제' onclick='delete_ment("+mention[j].mention_no+")'>";
						}
				table += "</div>";
			}
			table += "</div>"; //reply_cont end

			//<!-- 댓글 작성 => 로그인한 상태여야만 댓글작성 칸이 나온다. -->
			table += "<div class='board_reply_write'>";
				if(loginUser_no!=""){
					table += "<div class='reply_write_user'>"+
								"<img class='board_reply_write_prof' src='"+loginUser_profile+"' />"+
							"</div>"+
							"<div class='reply_write_ment'>"+
								"<input class='reply_write_ment'>"+
								"<button type='button' class='reply_write_insert' onclick='addMention("+no+", this)'>댓글입력</button>"+
							"</div>";
				}else{
					table += "<div><h2>로그인이 필요합니다.</h2></div>";
				}
			table += "</div>"+	//reply_write end



			$("."+no+"board_reply_wrap").append(table);
	    },
	    error: function() {
	      alert('댓글 로딩 중 오류');
	    }
	});
}

function addList(){
	let cm_no = $('.board_no:last').val();
	if(cm_no==null){
		cm_no = 0;
	}

	$.ajax({
        type: "get",
        url: ctxPath + "/board/userboard/likelist",
		data: {
			cm_no: cm_no
		  },
        dataType: "json",
        contentType: "application/json; charset=UTF-8;",
        async: false,
        success: function(data) {
          let boardList = data.BoardList;
          let mentionList = data.MentionList;
		  let likeList = data.LikeList;
		  let mentionLikeList = data.MentionLikeList;
		  
          let table = "";
          
          for (let i = 0; i < boardList.length; i++) {
            let board = boardList[i];
            let no = board.cm_no;
            let mention = mentionList[no];
            let files = board.photo_files;
            let folders = board.photo_folder;
			let content = board.content.replace(/(?:\r\n|\r|\n)/g, '<br />');

			table += "<div class='board_content'>" +
				"<input class='board_no' type='hidden' value='"+no+"'>" +

				//게시글 헤더
				"<div class='board_user_wrap'>" +
					"<div class='board_user_prof'>" +
						"<img src='"+ board.profile +"' class='board_user_prof_img' onclick='open_user_modal(this)'>" +
						"<span>'"+ board.nickname +"'</span>" +
					"</div>" +

					//유저 프로필 모달창
					"<div class='user_modal_overlay'>" +
						"<div class='user_modal_window'>" +
							"<div class='user_modal_title'>" +
								"<img src='"+ board.profile +"'>" +
								"<div>"+board.nickname+"</div>" ;
								if(board.type=='S'){
									table += "<img class='user_modal_logo' src='"+ctxPath+"/resources/images/logo/logo.png'>";
								}else if(board.type=='G'){
									table += "<img class='user_modal_logo' src='"+ctxPath+"/resources/images/logo/google_logo.png'>";
								}else if(board.type=='N'){
									table += "<img class='user_modal_logo' src='"+ctxPath+"/resources/images/logo/naver_logo.jpg'>";
								}else if(board.type=='K'){
									table += "<img class='user_modal_logo' src='"+ctxPath+"/resources/images/logo/kakao_logo.png'>";
								}
						table += "</div>" +
							"<div class='user_modal_body'>" +
								"<a href='javascript:chat_board("+board.user_no+")'>유저와 채팅하기</a>" +
								"<a href='"+ctxPath+"/user/userboard?user_no="+board.user_no+"'>유저 게시글 보기</a>" +
							"</div>" +
						"</div>" +
					"</div>" +

					//게시글 상세메뉴 버튼
					"<div class='board_detail_btn' onclick='open_board_detail(this)'>" +
						"<a class='board_detail_btn' href='javascript:'>***</a>" +
					"</div>" +

					//게시글 상세 메뉴 모달창
					"<div class='detail_modal_overlay'>" +
						"<div class='detail_modal_window'>"+
							"<a href='javascript:cm_declaration("+no+",\""+board.nickname+"\")'>게시글 신고</a>"+
							"<a href='javascript:cm_modify("+no+","+board.user_no+")'>게시글 수정</a>"+
							"<a href='javascript:cm_delete("+no+","+board.user_no+")'>게시글 삭제</a>"+
						"</div>"+
					"</div>" +
				"</div>" ;

				// 마켓일 경우 게시글 제목+가격
				if(board.title!=null){
			table += "<div class='board_market_title'>" +
						"<p>"+board.title+"</p>" +
						"<p>"+board.price+"</p>" +
					"</div>" ;
				}

				//게시글 본문
		table += "<div class='board_main_wrap'>" +
					"<div class='board_main_photo'>" +
						//슬릭 적용
						"<div class='board_main_files'>";
								if(files.length==0){
									table += "<div class='board_file_slick'><img src='/finproj/resources/images/board/default/default.jpg'></div>";
								}else{
									for(let z=0;z<files.length;z++){
										table += "<div class='board_file_slick'><img src='/finproj/resources/images/board/"+folders+"/"+files[z]+"'></div>";
									}
								}
					table += "</div>" + //files end
						"<div class='board_main_hashtag'>" +	//hashtag
							"<p>" + board.hashtag + "</p>" +
						"</div>" + //hasgtag end
					"</div>" + //board_main_photo end


                "<div class='board_main_cont'>" +

               		"<div class='board_main_text'>" + content +
						"<div class='board_like_wrap'>" +
							"<a href='javascript:' onclick='click_like_board("+no+", this)'>";

							
							// 유저에 따른 좋아요 등록 여부
							if(loginUser_no != ''){
								if(likeList.find(element => element == no)!=null){
									table += "<i class='fa fa-heart' aria-hidden='true'></i>";
								}else{
									table += "<i class='fa fa-heart-o' aria-hidden='true'></i>";
								}
							}else{
								table += "<i class='fa fa-heart-o' aria-hidden='true'></i>";
							}
							
					table += "</a>" + 
							"<span>" + board.likeCount + "</span>" +
						"</div>" +
					"</div>"; //main_cont end


					//댓글 목록
					table += "<div class='"+no+"board_reply_wrap reply_wrap'>" +
								"<div class='board_reply_cont_show'>";
					//<!-- 댓글이 목록이 들어가는 곳 -->
					for(let j = 0; j < mention.length; j++) {
						table +=  "<div class='board_reply_ment_cont' id='"+mention[j].mention_no+"'>"+
										"<div class='board_reply_user'>"+mention[j].nickname+"</div>"+
										"<div class='board_reply_ment'>"+mention[j].ment+
											
											//댓글 좋아요 버튼
											"<a class='mention_like_wrap' href='javascript:' onclick='click_like_mention("+mention[j].mention_no+", this)'>";
											if(loginUser_no != ''){
												if(mentionLikeList.find(element => element == mention[j].mention_no)!=null){
													table += "<i class='fa fa-heart' aria-hidden='true'></i>";
												}else{
													table += "<i class='fa fa-heart-o' aria-hidden='true'></i>";
												}
											}else{
												table += "<i class='fa fa-heart-o' aria-hidden='true'></i>";
											}
										table += "</a>" + "<span>" + mention[j].likeCount + "</span>" + //좋아요 end
										"</div>";
								if(mention[j].user_no==loginUser_no){
									table += "<input class='board_reply_delete' type='button' value='삭제' onclick='delete_ment("+mention[j].mention_no+")'>";
								}
						table += "</div>";
					}
					table += "</div>"; //reply_cont end

					//<!-- 댓글 작성 => 로그인한 상태여야만 댓글작성 칸이 나온다. -->
						table += "<div class='board_reply_write'>";
								if(loginUser!=''){
									table += "<div class='reply_write_user'>"+
												"<img class='board_reply_write_prof' src='"+loginUser_profile+"' />"+
											"</div>"+
											"<div class='reply_write_ment'>"+
												"<input class='reply_write_ment'>"+
												"<button type='button' class='reply_write_insert' onclick='addMention("+no+", this)'>댓글입력</button>"+
											"</div>";
								}else{
									table += "<div><h2>로그인이 필요합니다.</h2></div>";
								}
							table += "</div>"+	//reply_write end

						"</div>"+	// reply wrap end

					"</div>" + // main_cont end

				"</div>" + //게시글 본문 end
			"</div>"; //board_content end
          }
          // Add the generated table HTML to the list_main element
          $('.list_main').append(table);
        },
        error: function() {
          alert('게시물 로딩 중 오류');
        }
      });
}

function delete_ment(no){
	// 해당 버튼에 대한 AJAX 요청을 보냅니다.
	$.ajax({
	    type: 'get',
	    url: ctxPath + '/board/deletemention',
	    data: {
	      mention_no: no
	    },
	    dataType: 'text',
	    success: function(data) {
			let check = data;
			if(check == "1"){
				// no 다 넣어주기 $("."+no+"board_reply_wrap").html("");
				$("#"+no).html("");
			}
		},
	    error: function() {
	      alert('댓글 로딩 중 오류');
	    }
	});
}
