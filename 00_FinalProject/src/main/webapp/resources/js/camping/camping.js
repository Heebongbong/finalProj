/**
 * 
 */
 

$(document).ready(function(){

  campingAddList();  
	
	$('body').on("mousewheel",function(event){
		if(($(window).scrollTop()+$(window).innerHeight())>=$(document).height()-1){
			if(event.originalEvent.deltaY>0){
				campingAddList();
			}
		}
	});
	//터치 게시판 추가
	$('body').on("touchend",function(event){
		if(($(window).scrollTop()+$(window).innerHeight()) >= $(document).height()-1){
			campingAddList();
		}
	});

	// 선택 지역 배경색 고정
	let category = document.getElementById('hidden_category').value;

	if (category) {
    $('a[href="javascript:locSearch(\'' + category + '\')"]').css({
      'background': '#6FCF97',
      'color': '#FFFFFF'
    });
    $('a[href="javascript:locSearch(\'' + category + '\')"]').parent('li').css('background', '#6FCF97');
  }
});

//카테고리 클릭시 url 요청
function locSearch(cate){
	let keyword = $('#locs').val();
	let category = cate;
	console.log(cate);
	console.log(category);
	if(category==null){
		category = $('#hidden_category').val();
	}
	location.href=ctxPath+"/camping/camping?keyword="+keyword+"&category="+category;
}

function campingAddList(){
	let content_id = $('.content_id:last').val();
	
	if(content_id==null||content_id==''){
		content_id = 0;
	}
    
    $.ajax({
		type: "get",
		url: ctxPath+"/camping/addlist",
		data: {
			content_id: content_id,
			keyword: $('#locs').val(),
			category: $('#hidden_category').val()
		},
		dataType : "json",
		contentType : "application/json; charset=UTF-8;",
		async:false,
		success: function(data){
			console.log(data);
			let table = "";
			
			for (let i = 0; i < data.length; i++) {
				let content = data[i];

				let campTag = "";

				if (content.animalCmgCl === 1) {
					campTag += "#애견동반 ";
				}

				if (content.posblFcltyCl) {
					campTag += "#" + content.posblFcltyCl + " ";
				}

				if (content.exprnProgrm) {
					campTag += "#" + content.exprnProgrm + " ";
				}

				if (content.themaEnvrnCl) {
					campTag += "#" + content.themaEnvrnCl + " ";
				}

				table += 
					"<div class='camping_list_wrap'>" +
						"<div class='camping_list_img'>" +
							"<img onclick=\"location.href='" + ctxPath + "/camping/details?content_id=" + content.content_id + "'\" src='" + content.firstImageUrl + "' alt='캠핑장 대표 이미지' class='camping_image'>" +
						"</div>" +
						"<div class='camping_list_info'>" +
							"<input type='hidden' value='" + content.content_id + "' class='content_id'>" +
							"<div class='camp_head'>" +
								"<p class='camp_title'><a href='" + ctxPath + "/camping/details?content_id=" + content.content_id + "' class='camping_title'>" + content.facltNm + "</a></p>" +
								"<p class='camp_addr'>" + content.addr1 + "</p>" +
							"</div>" +
							"<div class='camp_tag'>" +
								"<p>" + campTag + "</p>" +
							"</div>" +
						"</div>" +
					"</div>";
			}
			
			$('#camping_search_wrap').append(table);	
		},
		error: function(){
			alert('게시물 로딩 중 오류');
		}
	});
}