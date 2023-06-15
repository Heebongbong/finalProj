/**
 * 
 */
 
$(document).ready(function(){
	
	$(".index_camping_list").slick({
		dots: true,
		dotsClass : "slick-dots", 
		infinite: true,
		speed: 1000,
		slidesToShow: 1,
		adaptiveHeight: false,
		autoplay: true,
		autoplaySpeed: 4000,
		arrows: false
	});

	boardAddList();
	
	//게시판 추가
	$('body').on("mousewheel",function(event){
		if(($(window).scrollTop()+$(window).innerHeight()) >= $(document).height()-1){
			if(event.originalEvent.deltaY>0){
				boardAddList();
			}
		}
	});

	//터치 게시판 추가
	$('body').on("touchend",function(event){
		if(($(window).scrollTop()+$(window).innerHeight()) >= $(document).height()-1){
			boardAddList();
		}
	});
	

	
	//상세 모달창 닫기
	$('body').on('click', function(event){
		if($(event.target).parents('.detail_modal_overlay').length < 1 &&event.target.className!='board_detail_btn'){
			$('.detail_modal_overlay').hide();
		}
	});
});

let main_text = new Map();

//본문 더보기
function more_text_func(self, no){
	$(self).parent().text(main_text.get(no));
}

//댓글 삭제버튼 보이기
function open_reply_delete_btn(self){
	let btn = $(self).find('.board_reply_delete');
	btn.css('display','block');
	let set_reply_btn = setTimeout(function(){
		btn.hide();
	}, 3000);
}

//상세메뉴 오픈
function open_board_detail(self, event){
	event.stopPropagation();
	$(self).next().show();
}


//신고창 오픈
function cm_declaration(cm_no, nickname){ 
	if(loginUser_no==''){
		alert("로그인이 필요합니다.");
	}else{
		$(".decl_nickname").val(nickname);
		$(".decl_cm_no").val(cm_no);
		$('.declaration_modal_overlay').show();
	}
}

//신고창 닫기
function close_declaration(){
	$('.declaration_modal_overlay').hide();
}

//신고
function declaration(){
	if(loginUser_no==''){
		alert("로그인이 필요합니다.");
	}else{
		if($('.decl_modal_text').val()==''){
			alert('신고 내용을 입력하세요');
		}else{
			if(confirm($('.decl_nickname').val()+'님의 게시글을 신고합니다.')){
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
	}
}

function cm_modify(cm_no, user_no){ //게시글 수정
	if(loginUser_no==user_no){
		location.href=ctxPath+"/board/update?cm_no="+cm_no;
	}else{
		alert('본인의 게시글만 수정 가능합니다.');
	}
}

function cm_delete(cm_no, user_no){ //게시글 삭제
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
	if(loginUser_no==''){
		alert('로그인이 필요합니다.');
	}else {
		if($(self).prev().val()==''){
			alert('댓글 내용을 입력하세요.');
		}else{
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
		
				  let table = "";
				 //댓글 목록
				 table += "<div class='board_reply_cont_show'>";
					//<!-- 댓글이 목록이 들어가는 곳 -->
					for(let j = 0; j < mention.length; j++) {
					table +=  "<div class='board_reply_ment_cont' onmouseover='open_reply_delete_btn(this)' id='"+mention[j].mention_no+"'>"+
									"<div class='board_reply_user'><img src='"+mention[j].profile+"'><span>"+mention[j].nickname+"</sapn></div>"+
									"<div class='board_reply_cont_text'>" +
										"<div class='board_reply_ment'>"+mention[j].ment+"</div>" +
										"<div class='board_reply_date_delete_wrap'>" + 
											"<div>" + 
												mention[j].created.substring(5,10) +
											"</div>";
								if(mention[j].user_no==loginUser_no){
									table += "<a class='board_reply_delete' href='javascript:' onclick='delete_ment("+mention[j].mention_no+")'><i class='fa fa-trash-o' aria-hidden='true'></i></a>";
								}
							table += "</div>" +
								"</div>" +
								"<div class='mention_like_wrap'>" +
								//댓글 좋아요 버튼
									"<a href='javascript:' onclick='click_like_mention("+mention[j].mention_no+", this)'>";
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
								"</div>" +
							"</div>";
					}
					table += "</div>"; //reply_cont end
		
					//<!-- 댓글 작성 -->
					table += "<div class='board_reply_write'>";
						
						table += "<div class='reply_write_ment'>"+
									"<input class='reply_write_ment_inp' placeholder='댓글을 입력하세요..'>"+
									"<a class='reply_write_insert' href='javascript:' onclick='addMention("+no+", this)'><img src='"+ctxPath+"/resources/images/icon/m_send_icon.png'></a>"+
								"</div>";
					table += "</div>";	//reply_write end
				  $("."+no+"board_reply_wrap").append(table);
			  },
			  error: function() {
				alert('댓글 로딩 중 오류');
			  }
		  });
		}
	}
}

function boardAddList(){
	let cm_no = $('.board_no:last').val();
	if(cm_no==null){
		cm_no = 0;
	}

	$('.board_main_files').slick('unslick');

	$.ajax({
        type: "get",
        url: ctxPath + "/index/addlist",
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

          if(boardList.length==0){
			alert('게시물이 없습니다');
		  }

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
					"<div class='board_user_prof' onclick='if(confirm(\"유저의 게시글 페이지로 이동합니다\")){location.href=\""+ctxPath+"/user/userboard?user_no="+board.user_no+"\"}'>" +
						"<img src='"+ board.profile +"' class='board_user_prof_img' onclick='open_user_modal(this)'>" +
						"<span>"+ board.nickname +"</span>" +
					"</div>" +

					//게시글 상세메뉴 버튼
					"<div class='board_detail_btn' onclick='open_board_detail(this, event)'>" +
						"<a class='board_detail_btn' href='javascript:'><i class='fa fa-ellipsis-h' aria-hidden='true'></i></a>" +
					"</div>" +

					//게시글 상세 메뉴 모달창
					"<div class='detail_modal_overlay'>" +
						"<div class='detail_modal_window'>"+
							"<a href='javascript:cm_declaration("+no+",\""+board.nickname+"\")'>게시글 신고</a>"+
							"<a href='javascript:cm_modify("+no+","+board.user_no+")'>게시글 수정</a>"+
							"<a href='javascript:cm_delete("+no+","+board.user_no+")'>게시글 삭제</a>"+
						"</div>"+
					"</div>" +
				"</div>" +

				//게시글 본문
				"<div class='board_main_wrap'>" +
					"<div class='board_main_photo'>" +
						"<div class='board_main_files'>";
								if(files.length==0){
									table += "<div class='board_file_slick'><img src='/finproj/resources/images/board/default/default.jpg'></div>";
								}else{
									for(let z=0;z<files.length;z++){
										table += "<div class='board_file_slick'><img src='/finproj/resources/images/board/"+folders+"/"+files[z]+"'></div>";
									}
								}
					table += "</div>" + //files end
								"<div class='board_like_date_wrap'>" +
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
									"<div class='board_write_date'>";
									if(board.update==null){
										table += board.date.substring(0,10);
									}else{
										table += board.update.substring(0,10);
									}
							table += "</div>" +
								"</div>" +
						"<div class='board_main_hashtag'>" +	//hashtag
							"<p>" + board.hashtag + "</p>" +
						"</div>" + //hasgtag end
					"</div>" + //board_main_photo end


                "<div class='board_main_cont'>" +

               		"<div class='board_main_text'>"; 
						
					main_text.set(no, content);
					if(content.length>40){
						table += content.substring(0,40)+"......<span class='more_text_btn' onclick='more_text_func(this, "+no+")'>더보기</span>";
					}else{
						table += content;
					}
					table += "</div>"; //main_cont end

					//댓글 목록
					table += "<div class='"+no+"board_reply_wrap reply_wrap'>" +
								"<div class='board_reply_cont_show'>";
					//<!-- 댓글이 목록이 들어가는 곳 -->
					for(let j = 0; j < mention.length; j++) {
						table +=  "<div class='board_reply_ment_cont' onmouseover='open_reply_delete_btn(this)' id='"+mention[j].mention_no+"'>"+
										"<div class='board_reply_user' onclick='if(confirm(\"유저의 게시글 페이지로 이동합니다\")){location.href=\""+ctxPath+"/user/userboard?user_no="+mention[j].user_no+"\"}'>" +
											"<img src='"+mention[j].profile+"'><span>"+mention[j].nickname+"</sapn></div>"+
										"<div class='board_reply_cont_text'>" +
											"<div class='board_reply_ment'>"+mention[j].ment+"</div>" +
											"<div class='board_reply_date_delete_wrap'>" + 
												"<div>" + 
													mention[j].created.substring(5,10) +
												"</div>";
									if(mention[j].user_no==loginUser_no){
										table += "<a class='board_reply_delete' href='javascript:' onclick='delete_ment("+mention[j].mention_no+")'><i class='fa fa-trash-o' aria-hidden='true'></i></a>";
									}
								table += "</div>" +
									"</div>" +
									"<div class='mention_like_wrap'>" +
									//댓글 좋아요 버튼
										"<a href='javascript:' onclick='click_like_mention("+mention[j].mention_no+", this)'>";
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
									"</div>" +
								"</div>";
					}
					table += "</div>"; //reply_cont end

					//<!-- 댓글 작성  -->
						table += "<div class='board_reply_write'>" +
										 "<div class='reply_write_ment'>"+
											"<input class='reply_write_ment_inp' placeholder='댓글을 입력하세요..'>"+
											"<a class='reply_write_insert' href='javascript:' onclick='addMention("+no+", this)'><img src='"+ctxPath+"/resources/images/icon/m_send_icon.png'></a>"+
										"</div>" +
								 "</div>"+	//reply_write end

						"</div>"+	// reply wrap end

					"</div>" + // main_cont end

				"</div>" + //게시글 본문 end
			"</div>"; //board_content end
          }
          // Add the generated table HTML to the list_main element
          $('.index_board_wrap').append(table);

		  $(".board_main_files").not('.slick-initialized').slick({
				dots: false,
				infinite: true,
				speed: 1000,
				slidesToShow: 1,
				slidesToScroll: 1,
				adaptiveHeight: true,
				arrows: true,
				draggable: true,
				nextArrow: "<img class='slick_arrows' src='"+ctxPath+"/resources/images/icon/slick_arrow.png'>"
			});
        },
        error: function() {
          alert('게시물 로딩 중 오류');
        }
    });
}

//댓글 삭제
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
				$("#"+no).remove();
			}

		},
		error: function() {
			alert('댓글 로딩 중 오류');
		}
	});
}
