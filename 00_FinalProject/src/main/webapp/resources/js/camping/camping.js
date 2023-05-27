/**
 * 
 */
 
 
$(document).ready(function(){
    $('body').on("mousewheel",function(event){
		if(($(window).scrollTop()+$(window).innerHeight())>=$(document).height()-1){
			if(event.originalEvent.deltaY>0){
				console.log(22);
				campingAddList();
			}
		}
	});

});

function campingAddList(){
	console.log($('.content_id:last').val());
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
			console.log("마지막 id:"+$('.camping_list_wrap').find('.content_id:last').val());
			console.log(data);
			let table = "";
			
			for (let i = 0; i < data.length; i++) {
				let content = data[i];

				table += "<div class='camping_list_wrap'>"+
							"<input type='hidden' value='"+content.content_id+"' class='content_id'>" +
								"<div class='camping_list_img'>"+
									"<img src='"+content.firstImageUrl+"' alt='캠핑장 대표 이미지' class='camping_image'>"+
								"</div>"+
								"<div class='camping_list_info'>"+
									"<p>이름:<a href='"+ctxPath +"/camping/details?content_id="+content.content_id+"'>"+content.facltNm+"</a> </p>"+
									"<p>소개:"+content.LineIntro+"</p>"+
									"<p>홈페이지: <a href='"+content.homepage+"' target='_blank'>"+content.homepage+"</a></p>"+
									"<p>시설 : "+content.induty+"</p>"+
									"<p>주소 : "+content.addr1+"</p>"+
								"</div>"+
							"</div>";
			}
			$('#camping_search_wrap').append(table);	
		},
		error: function(){
			alert('게시물 로딩 중 오류');
		}
	});
}