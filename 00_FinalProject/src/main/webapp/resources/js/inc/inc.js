/**
 * 
 */
 $(document).ready(function(){
	$('body').on("mousewheel",function(event){
		if($('#footer').css('display')=='none'){
			open_footer(event);
		}
	});
	$('body').on('click', function(event){
		if($(event.target).parents('.user_menu_wrap').length < 1&&event.target.className!='user_menu_wrap'){
			close_user_menu();
		}
	});
	
	
	
	
	
 });
 
 
function open_user_menu(){
	$('.user_menu_wrap').animate({
		width: 'show'
 	}, 400);
}
 
function close_user_menu(){
	$('.user_menu_wrap').animate({
		width: 'hide'
	}, 400);
}

function open_footer(event){
	if(event.originalEvent.deltaY>0){
		$('#footer').animate({
			height: 'show'
		}, 400);
	}
	setTimeout(function(){
		$('#footer').animate({
			height: 'hide'
		}, 400);
	}, 5000);
}


// login function

function loginWithKakao() {
	Kakao.Auth.authorize({redirectUri: reUrl+"finproj/login/kakao"});
}


