/**
 * 
 */
$(document).ready(function(){
	$(".list_board_files").not('.slick-initialized').slick({
		slidesToShow: 1,
  		slidesToScroll: 1,
		draggable: true,    //리스트 드래그 가능여부 (boolean) -default:true
		
	});
	
	addList();
	
	$('body').on("mousewheel", function(event) {
	  if (($(window).scrollTop() + $(window).innerHeight()) >= $(document).height() - 1) {
	    if (event.originalEvent.deltaY > 0) {
	      addList();
	    }
	  }
	});
});

function addMention(self){
	// 클릭한 버튼의 id를 가져옵니다.
	  let cm_no = $(self).attr('id');
	  console.log("cm_no >>> "+cm_no);
	
	  // 해당 버튼에 대한 AJAX 요청을 보냅니다.
	  $.ajax({
	    type: 'get',
	    url: ctxPath + '/board/addmention',
	    data: {
	      cm_no: cm_no,
	      user_no: loginUser_no,
	      ment: $('#' + cm_no).val()
	    },
	    dataType: 'json',
	    contentType: 'application/json; charset=UTF-8;',
	    success: function(data) {
	      // 받아온 댓글 데이터를 처리하고 해당 댓글을 목록에 추가합니다.
	      let mentions = data.cm_no;
	      console.log(mentions);
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
}

function addList(){
	let cm_no = $('.board_no:last').val();
	console.log(cm_no);
	if(cm_no==null){
		cm_no = 0;
	}

	$.ajax({
        type: "get",
        url: ctxPath + "/board/addlist",
        data: {
          cm_no: cm_no,
        },
        dataType: "json",
        contentType: "application/json; charset=UTF-8;",
        async: false,
        success: function(data) {
          console.log(data);
          let boardList = data.BoardList;
          let mentionList = data.MentionList;
          
          let table = "";
          
          for (let i = 0; i < boardList.length; i++) {
			console.log(11);
            let board = boardList[i];
            let no = board.cm_no;
            let mention = mentionList[no];
            let files = board.photo_files;
            let folders = board.photo_folder;

			table += "<div class='list_board'>" +
			"<input class='board_no' type='hidden' value='" + no + "'>" +
			"<div class='board_user'>" +
			"<img src='" + board.profile + "'>" +
			"<span>'" + board.nickname + "'</span>" +
			"</div>" +
			"<div class='list_board_files'>";
			
			if(files==null){
				table += "<p>파일이 없습니다.</p>";
                }else{
                	table += "<div class='photo_file'>";
                		for(let z=0;z<files.length;z++){
                			table += "<img src='"+ctxPath+"/resources/images"+folders+"/"+files[z]+"'>";
                		}
                	table += "</div>";
                }
                table += "</div>" +
                "<div class='" + board.content + "'>" +
                "<span>'" + board.content + "'</span>" +
                "<p>'" + board.hashtag + "'</p>" +
                "</div>" +
                "<p>댓글창</p>"+
				"<div id='reply_card"+no+"'>"+
				"<div class='card card-body'>"+
				//댓글 목록
				"<div class='reply-list reply-list"+no+"'>";
			
				//<!-- 댓글이 목록이 들어가는 곳 -->
				for(let j = 0; j < mention.length; j++) {
					table += "<div>"+mention[j].user_no+"</div>"+
							"<div>"+mention[j].ment+"</div>";
					}
					table += "</div>"+
						//<!-- 댓글 작성 => 로그인한 상태여야만 댓글작성 칸이 나온다. -->
					(loginUser ? "<div class='row reply_write'>"+
								"<input class='loginUserNo' type='hidden' value='" + loginUser_no + "'>"+
								"<div>"+
										"<img id='profileImage' src='"+loginUser_profile+"' />"+
								"</div>"+
								"<div>"+
									"<textarea id='"+no+"' name='mention' rows='4' cols='50'></textarea>"+
								"</div>"+
								"<div>"+
									"<button type='button' id='"+no+"' class='btn write_reply' onclick='addMention(this)'>댓글입력</button>"+
								"</div>"+
								"</div>" : "<div class='row reply_write'> </div>") +
								"</div>"+
							"</div>"+
						"</div>";
          }
          // Add the generated table HTML to the list_main element
          $('.list_main').append(table);
        },
        error: function() {
          alert('게시물 로딩 중 오류');
        }
      });
}