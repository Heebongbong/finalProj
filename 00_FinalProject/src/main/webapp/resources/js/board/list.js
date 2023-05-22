/**
 * 
 */
$(document).ready(function(){
	$(".list_board_files").not('.slick-initialized').slick({
		slidesToShow: 1,
  		slidesToScroll: 1,
		draggable: true,    //리스트 드래그 가능여부 (boolean) -default:true
		
	});
	
	$('body').on("mousewheel",function(event){
		if(($(window).scrollTop()+$(window).innerHeight())>=$(document).height()-1){
			console.log(0);
			if(event.originalEvent.deltaY>0){
				console.log(1);
				$.ajax({
					type: "get",
					url: ctxPath+"/board/addlist",
					data: {
						cm_no: $('.board_no:last').val(),
					},
					dataType : "json",
					contentType : "application/json; charset=UTF-8;",
					async:false,
					success: function(data){
						console.log(data);
						let boardList = data.BoardList;
						let mentionList = data.MentionList;
						let table ="";
						console.log(boardList);
						console.log(mentionList);
						for(let i=0;i<boardList.length;i++){
							let board = boardList[i];
							let no = board.cm_no;
							let mention = mentionList[no];
							let files = board.photo_folder;
							
							for(let j=0;j<meList.length;j++){
								
								table = "<div class='list_board'>"
									+ "<input class='board_no' type='hidden' value='"+board.getCm_no()+"'>"
									+ "<c:set value='"+board.getPhoto_files()+"' var='"+files+"' />"
									+ "<div class='board_user'>"
									+ "<img src='"+board.profile+"'>"
									+ "<span>'"+board.nickname+"'</span>"
									+ "</div>"
									+ "<div class='"+list_board_files+"'>"
									+ "<c:if test='"+files+"'>"
									+ "<c:forEach items='"+ files +"' var='"+files[j]+"'>"
									+ "<div class='photo_file'>"
									+ "</div>"
									+ "</c:forEach>"
									+ "</c:if>"
									+ "<c:if test='"+files+"'>"
									+ "<p>파일이 없습니다.</p>"
									+ "</c:if>"
									+ "</div>"
									+ "<div class='"+board_content+"'>"
									+ "<span>'"+board.getContent()+"'</span>"
									+ "<p>'"+board.getHashtag()+"'</p>"
									+ "</div>"
									
								
								console.log("user_no"+meList[j].user_no);
								console.log("user_ment"+meList[j].ment);
							}
						}
						
					},
					error: function(){
						alert('게시물 로딩 중 오류');
					}
				});
			}
		}
	});
});