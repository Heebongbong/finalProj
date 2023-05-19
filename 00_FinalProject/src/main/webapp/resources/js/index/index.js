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
					contentType : "application/json",
					type: "post",
					url: ctxPath+"/board/addlist",
					data: {
						cm_no: $('.board_no:last').val(),
					},
					success: function(data){
						for(let board in data){
							let table = "<div>"
							+"<p>"+board.id.cm_no+"</p>"
							+"<p>"+board.id.cm_no+"</p>"
							+"</div>";

							$('.index_board_wrap').after(table);
						}
						
						
						$(data).each(function(){
							
						});
					},
					error: function(){
						alert('카드 가져오는 중 시스템 오류');
					}
				});
			}
		}
	});
});