/**
 * 
 */
$(document).ready(function(){
	$(".index_camping_list").not('.slick-initialized').slick({
		dots: true,
		dotsClass : "slick-dots", 
		infinite: true,
		speed: 1000,
		slidesToShow: 1,
		adaptiveHeight: true,
		autoplay: true,
		autoplaySpeed: 4000,
		arrows: false
	});
	
	$('body').on("mousewheel",function(event){
		if(($(window).scrollTop()+$(window).innerHeight())>=$(document).height()){
			if(event.originalEvent.deltaY>0){
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
						$.each(data.BoardList, function(){
							let table = "<div class='index_board'>" 
							+ "<input class='board_no' type='hidden' value='"+this.cm_no+"'>"
							+ "<p>"
							+ this.content
							+ "</p>"
							+ "<p>"
							+ this.date
							+ "</p>"
							+ "<p>"
							+ this.hashtag
							+ "</p>"
							+ "<p>"
							+ this.photo_files
							+ "</div>";
							console.log(table);
							console.log(this);
							$('.index_board_wrap').append(table);
						});
					},
					error: function(){
						alert('게시물 로딩 중 오류');
					}
				});
			}
		}
	});
});

