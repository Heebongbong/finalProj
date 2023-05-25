/**
 * 
 */
 
 
$(document).ready(function(){
    $('body').on("mousewheel",function(event){
		if(($(window).scrollTop()+$(window).innerHeight())>=$(document).height()){
			if(event.originalEvent.deltaY>0){
				campingAddList();
			}
		}
	});
});


function campingAddList(){
    console.log($('#hidden_keyword').val());
    console.log($('#hidden_keyword2').val());
    
    $.ajax({
		type: "get",
		url: ctxPath+"/camping/addlist",
		data: {
			content_id: $('.content_id:last').val(),
            keyword: $('#hidden_keyword').val(),
            keyword2: $('#hidden_keyword2').val()
		},
		dataType : "json",
		contentType : "application/json; charset=UTF-8;",
		async:false,
		success: function(data){
			console.log(data);
		},
		error: function(){
			alert('게시물 로딩 중 오류');
		}
	});
}