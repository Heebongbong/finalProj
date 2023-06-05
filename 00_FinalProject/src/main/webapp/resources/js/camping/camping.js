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

	// let hiddenKeyword = document.getElementById('hidden_keyword').value;
	// let campingSideUl = document.querySelector('.camping_side_ul');
	// let campingSideLinks = campingSideUl.getElementsByTagName('a');
	
	// for (let i = 0; i < campingSideLinks.length; i++) {
	// 	let link = campingSideLinks[i];
	
	// 	if (link.getAttribute('href').includes(`keyword=${hiddenKeyword}`)) {
	// 		link.style.background = '#6FCF97';
	// 		link.style.color = '#FFFFFF';
	// 		link.parentNode.style.background = '#6FCF97';
	// 	}
	// }

});

// function locSearch() {
// 	let locs = $('#locs').val();
// 	location.href=ctxPath+'/camping/camping?keyword='+locs;
// }

//키워드 url인코딩
function replace_keyword(key){
	key= key.replace(/\#/g,"%23");
	return key;
}

//카테고리 클릭시 url 요청
function locSearch(cate){
	let keyword = $('#locs').val();
	let category = cate;
	keyword = replace_keyword(keyword);
	category= replace_keyword(category);
	location.href=ctxPath+"/camping/camping?keyword="+keyword+"&category="+category;
}





function campingAddList(){
	console.log($('.content_id:last').val());
	console.log($('#hidden_keyword').val());
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