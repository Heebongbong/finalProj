/**
 * 
 */
 
$(document).ready(function(){
    marketAddList();

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

    console.log('1-'+$('#market_keyword').val());

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
            console.log(data);
			let boardList = data.BoardList;
        	let mentionList = data.MentionList;
            $('#market_keyword').val(data.keyword);
            
        	let table = "";
        
			for(let i = 0; i < boardList.length; i++) {
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
							table += "<img src='/finproj/resources/images/board/"+folders+"/"+files[z]+"'>";
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
						"<div class='reply-list reply-list"+no+"'>";
				
				for(let j = 0; j < mention.length; j++) {
					table += "<div>"+mention[j].user_no+"</div>"+
						"<div>"+mention[j].ment+"</div>";
				}

				table += "</div>";
						
				if(loginUser_no==""){
					table += "<div class='row reply_write'>"+
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
						"</div>";
				}else{
					table += "<div class='row reply_write'> </div>";
				}
				table += "</div>"+
					"</div>"+
				"</div>";
			}

			$('.market_main').append(table);
		},
		error: function(){
			alert('게시물 로딩 중 오류');
		}
	});
}