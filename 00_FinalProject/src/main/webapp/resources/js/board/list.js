/**
 * 
 */
$(document).ready(function(){
	$(".list_board_files").not('.slick-initialized').slick({
		slidesToShow: 1,
  		slidesToScroll: 1,
		draggable: true,    //리스트 드래그 가능여부 (boolean) -default:true
		
	});
	
	$('body').on("mousewheel", function(event) {
	  if (($(window).scrollTop() + $(window).innerHeight()) >= $(document).height() - 1) {
	    console.log(0);
	    if (event.originalEvent.deltaY > 0) {
	      console.log(1);
	      $.ajax({
	        type: "get",
	        url: ctxPath + "/board/addlist",
	        data: {
	          cm_no: $('.board_no:last').val(),
	        },
	        dataType: "json",
	        contentType: "application/json; charset=UTF-8;",
	        async: false,
	        success: function(data) {
	          console.log(data);
	          let boardList = data.BoardList;
	          let mentionList = data.MentionList;
	          let loginUser = JSON.stringify(data.LoginUser);
	          console.log("login >>> "+loginUser);
	          let table = "";
	          console.log(boardList);
	          console.log(mentionList);
	          for (let i = 0; i < boardList.length; i++) {
	            let board = boardList[i];
	            let no = board.cm_no;
	            let mention = mentionList[no];
	            let files = board.photo_folder;
	            let loginUserObject = JSON.parse(loginUser);
	            console.log("OOOUserNO >>>" + loginUserObject.no)

	            for (let j = 0; j < mention.length; j++) {
	              table = "<div class='list_board'>" +
	                "<input class='board_no' type='hidden' value='" + no + "'>" +
	                "<c:set value='" + board.photo_files + "' var='" + files + "' />" +
	                "<div class='board_user'>" +
	                "<img src='" + board.profile + "'>" +
	                "<span>'" + board.nickname + "'</span>" +
	                "</div>" +
	                "<div class='list_board_files'>" +
	                (files ? "<c:forEach items='" + files + "' var='" + files[j] + "'>" +
	                  "<div class='photo_file'></div>" +
	                  "</c:forEach>" :
	                  "<p>파일이 없습니다.</p>") +
	                "</div>" +
	                "<div class='" + board.content + "'>" +
	                "<span>'" + board.content + "'</span>" +
	                "<p>'" + board.hashtag + "'</p>" +
	                "</div>" +
	                "<p>댓글창</p>"+
					"<div id='reply_card"+no+"'>"+
						"<div class='card card-body'>"+
							<!-- 댓글 목록 -->
							"<div class='reply-list reply-list"+no+"'>"+
								<!-- 댓글이 목록이 들어가는 곳 -->
								"<div>"+mention[j].user_no+"</div>"+
								"<div>"+mention[j].ment+"</div>"+
							"</div>"+
							<!-- 댓글 작성 => 로그인한 상태여야만 댓글작성 칸이 나온다. -->
								(loginUser ? "<div class='row reply_write'>"+
									"<input class='loginUserNo' type='hidden' value='" + loginUserObject.no + "'>"+
									"<div>"+
											"<img id='profileImage' src='"+loginUserObject.profile+"' />"+
									"</div>"+
									"<div>"+
										"<textarea id='"+mention[j].cm_no+"' name='mention' rows='4' cols='50'></textarea>"+
									"</div>"+
									"<div>"+
										"<button type='button' id='"+mention[j].cm_no+"' class='btn write_reply'>댓글입력</button>"+
									"</div>"+
								"</div>" : "<div class='row reply_write'> </div>") +
							"</div>"+
						"</div>"+
	                "</div>";
	
	              console.log("user_no" + mention[j].user_no);
	              console.log("user_ment" + mention[j].ment);
	            }
	          }
	
	          // Add the generated table HTML to the list_main element
	          $('.list_main').append(table);
	          
				$('.write_reply').on('click', function() {
					  // 클릭한 버튼의 id를 가져옵니다.
					  let cm_no = $(this).attr('id');
					  console.log("cm_no >>> "+cm_no);
					  
					  let user_no = $('.loginUserNo').val();
  					  console.log("user_no >>> " + user_no);
					
					  // 해당 버튼에 대한 AJAX 요청을 보냅니다.
					  $.ajax({
					    type: 'get',
					    url: ctxPath + '/board/addmention',
					    data: {
					      cm_no: $('.board_no').val(),
					      user_no: loginUserObject.user_no,
					      ment: $('#' + cm_no + 'mention').val()
					    },
					    dataType: 'json',
					    contentType: 'application/json; charset=UTF-8;',
					    success: function(data) {
					      // 받아온 댓글 데이터를 처리하고 해당 댓글을 목록에 추가합니다.
					      let mentions = data.Mention;
					      for (let i = 0; i < mentions.length; i++) {
					        let mention = mentions[i];
					        let replyListId = '.reply-list' + mention.cm_no; // 적절한 reply-list 선택자로 변경하세요.
					        let comment = '<div>' + mention.user_no + '</div>' +
					          '<div>' + mention.ment + '</div>';
					        $(replyListId).append(comment);
					      }
					    },
					    error: function() {
					      alert('댓글 로딩 중 오류');
					    }
					 });
				});
	        },
	        error: function() {
	          alert('게시물 로딩 중 오류');
	        }
	      });
	    }
	  }
	});
	
});