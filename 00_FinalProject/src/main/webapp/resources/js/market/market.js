/**
 * 
 */
 
$(document).ready(function(){
    marketAddList();

    $('body').on("mousewheel",function(event){
		if(($(window).scrollTop()+$(window).innerHeight())>=$(document).height()){
			if(event.originalEvent.deltaY>0){
				marketAddList();
			}
		}
	});
});

function open_navi(i){
    $('.cate_cont').hide();
    $('.cate_cont').eq(i).show();
}
function open_navi_loc(i){
    $('.loc_cont').hide();
    $('.loc_cont').eq(i).show();
}

function move_search_cate(key){
    let keyword = $('#market_keyword').val();
    location.href=ctxPath+"/market/list?keyword=%23"+key+"%23"+keyword;
	
}

function marketAddList(){
	let cm_no = $('.board_no:last').val();
	if(cm_no==null){
		cm_no = 0;
	}

	$.ajax({
		type: "get",
		url: ctxPath+"/market/addlist",
		data: {
			cm_no: cm_no,
            keyword: $('#market_keyword').val()
		},
		dataType : "json",
		contentType : "application/json; charset=UTF-8;",
		async:false,
		success: function(data){
			let boardList = data.BoardList;
        	let mentionList = data.MentionList;
			$('#market_keyword').val(data.keyword);
          
		  
        	let table = "";
          
        	for (let i = 0; i < boardList.length; i++) {
				let board = boardList[i];
				let no = board.cm_no;
				let mention = mentionList[no];
				let files = board.photo_files;
				let folders = board.photo_folder;
				table += "<div class='board_content'>" +
					"<input class='board_no' type='hidden' value='"+no+"'>" +

				//게시글 헤더
				"<div class='board_user_wrap'>" +
					"<div class='board_user_prof'>" +
						"<img src='"+ board.profile +"'>" +
						"<span>'"+ board.nickname +"'</span>" +
					"</div>" +
					"<div class='board_detail_btn'>" +
						"<a href='javascript:'>***</a>" +
					"</div>" +
				"</div>" +

				//게시글 타이틀
				"<div class='board_title_wrap'>" +
					"<div class='board_title_text'>" +
						board.title +
					"</div>" +
					"<div class='board_title_price'>" +
						board.price +
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
						"<div class='board_main_hashtag'>" +	//hashtag
							"<p>'" + board.hashtag + "'</p>" +
						"</div>" + //hasgtag end
					"</div>" + //board_main_photo end


                "<div class='board_main_cont'>" +

               		"<div class='board_main_text'>'" + board.content + "'</div>" +
                
					"<div class='board_reply_wrap'>";

					//댓글 목록 - 보여지는 최대 3개
					table += "<div class='board_reply_cont_show'>";
					//<!-- 댓글이 목록이 들어가는 곳 -->
					for(let j = 0; j < ((mention.length>3) ? 3 : mention.length); j++) {
						table +=  "<div class='board_reply_ment_cont'><div class='board_reply_user'>"+mention[j].nickname+"</div>"+
									"<div class='board_reply_ment'>"+mention[j].ment+"</div>";
								if(mention[j].user_no==loginUser_no){
									table += "<input class='board_reply_delete' type='button' value='삭제' onclick='delete_ment("+mention[j].mention_no+")'>";
								}
						table += "</div>";
					}
					table += "</div>"; //reply_cont end

					
					//댓글 목록 - 숨어있는 전체목록
					table += "<div class='board_reply_cont_total'>";
					//<!-- 댓글이 목록이 들어가는 곳 -->
					for(let j = 0; j < mention.length; j++) {
						table +=  "<div class='board_reply_ment_cont'><div class='board_reply_user'>"+mention[j].nickname+"</div>"+
									"<div class='board_reply_ment'>"+mention[j].ment+"</div>";
								if(mention[j].user_no==loginUser_no){
									table += "<input class='board_reply_delete' type='button' value='삭제' onclick='delete_ment("+mention[j].mention_no+")'>";
								}
						table += "</div>";
					}
					table += "</div>"; //reply_cont end

					table += "<input type='button' value='전체댓글 보기' onclick='open_ment_modal(this)'>";

					//<!-- 댓글 작성 => 로그인한 상태여야만 댓글작성 칸이 나온다. -->
						table += "<div class='board_reply_write'>";
								if(loginUser_no!=""){
									table += "<div class='reply_write_user'>"+
												"<img class='board_reply_write_prof' src='"+loginUser_profile+"' />"+
											"</div>"+
											"<div class='reply_write_ment'>"+
												"<input class='reply_write_ment' id='"+no+"'>"+
												"<button type='button' class='reply_write_insert' onclick='addMention("+no+")'>댓글입력</button>"+
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

			$('.market_main').append(table);
		},
		error: function(){
			alert('게시물 로딩 중 오류');
		}
	});
}