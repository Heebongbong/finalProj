/**
 * 
 */

 $(document).ready(function(){

	//푸터메뉴 오픈
	$('body').on("mousewheel",function(event){
		if($('#footer').css('display')=='none'){
			if(event.originalEvent.deltaY>0){
				open_footer();
			}
		}
	});
	//터치 푸터메뉴 
	$('body').on("touchend",function(){
		if($('#footer').css('display')=='none'){
			open_footer();
		}
	});

	//유저 헤더메뉴 닫기
	$('body').on('click', function(event){
		if($(event.target).parents('.user_menu_wrap').length < 1 && event.target.className!='user_menu_wrap'){
			if($('.user_menu_wrap').css('display')=='block'){
				close_user_menu();
			}
		}
	});

	//채팅 닫기
	$('body').on('click', function(event){

		if($(event.target).parents('.chat_wrap').length < 1 && (event.target.className!='chat_wrap' && event.target.className!='user_profile_chat_plus')){
			
			if($('.chat_wrap').css('display')=='flex'){
				if($(event.target).val()=="관리자와 채팅하기"){
					return false;
				}
				close_chat();
			}
		}
	});

	//유저 알람 모달창 닫기
	$('body').on('click', function(event){
		if($(event.target).parents('.alarm_modal_overlay').length < 1 &&event.target.className!='alarm_modal_overlay'){
			if($('.alarm_modal_overlay').css('display')=='flex'){
				$('.alarm_modal_overlay').hide();
			}
		}
	});

	//푸터 플러스버튼 닫기
	$('body').on('click', function(event){
		if($(event.target).parents('.top_write_navi').length < 1 ){
			close_footer_write();
		}
	});

 });

 let faqList = null;
 let socket = null;

//top_move
function top_move_func(){
	$('html').stop().animate({
		scrollTop:'0'
	}, 300);
}
 
//푸터 플러스버튼 오픈
function open_footer_write(){
	$('.top_move_icon').hide();
	if($('.write_plus_navi').css('display')!='flex'){
		if(matchMedia("screen and (max-width:680px)").matches){
			$('.comm_write_icon').animate({
				bottom: "+=60",
				right: '+=2'
			},100);
			$('.market_write_icon').animate({
				bottom: "+=60",
				right: '+=50'
			},200);
			$('.review_write_icon').animate({
				right: '+=50',
				bottom: '+=15'
			},300);
		}else{
			$('.comm_write_icon').animate({
				bottom: "+=50"
			},100);
			$('.market_write_icon').animate({
				bottom: "+=50",
				right: '+=50'
			},200);
			$('.review_write_icon').animate({
				right: '+=50',
				bottom: '+=2'
			},300);
		}
	}
	$('.write_plus_navi').css('display', 'flex');
}

//푸터 플러스버튼 닫기
function close_footer_write(){
	$('.top_move_icon').css('display', 'flex');
	$('.write_plus_navi').hide();
	$('.review_write_icon').css('bottom','-90px');
	$('.review_write_icon').css('right','0');
	$('.market_write_icon').css('bottom','-90px');
	$('.market_write_icon').css('right','0');
	$('.comm_write_icon').css('bottom','-90px');
	$('.comm_write_icon').css('right','0');
}
 
 //유저 헤더 열기
function open_user_menu(){
	if($('.user_menu_wrap').css('display')=='none'){
		$('.user_menu_wrap').animate({
			height: 'show'
		 }, 400);
	}
}

//유저 헤더 닫기
function close_user_menu(){
	$('.user_menu_wrap').animate({
		height: 'hide'
	}, 400);
}

//푸터 열기
function open_footer(){
	$('#footer').animate({
		height: 'show'
	}, 400);
	setTimeout(function(){
		$('#footer').animate({
			height: 'hide'
		}, 400);
		$('.top_write_navi').animate({
			bottom: "10"
		},400);
	}, 5000);
	if($('#footer').css('display')=='block'){
		$('.top_write_navi').animate({
			bottom: "+=90"
		},400);
	}
}

//유저탈퇴버튼 임시
function delete_move(type){
	if(type=='S'){
		location.href=ctxPath + '/user/delete';
	}else{
		if(confirm('정말로 삭제하시겠습니까')){
			location.href=ctxPath + '/user/deleteOk';
		}
	}
}

//알람 모달창 오픈
function alarm_modal(){
	if($('.alarm_modal_overlay').css('display')=='none'){
		$('.alarm_modal_overlay').css('display', 'flex');
	
		//알람 DB에서 읽음 체크 update
		$.ajax({
			type: "get",
			url: ctxPath+"/alarm/check/delete",
			dataType : "text",
			async:false,
			success: function(data){
				console.log(data);
			},
			error: function(){
				alert('알람 읽음 처리 중 오류');
			}
		});
	}
}

function alarm_move_href(field){
	//알람 DB에서 데이터 삭제 및 게시글 리스트 이동
	$.ajax({
		type: "get",
		url: ctxPath+"/alarm/delete/list",
		data: {
			field: field
		},
		dataType : "text",
		async:false,
		success: function(data){
			if(field!=4){
				if(data>0){
					location.href=ctxPath+"/user/userboard?user_no="+loginUser_no;
				}
			}else if(field==4){
				if(data>0){
					open_chat(0);
				}
			}
		},
		error: function(){
			alert('알람 읽음 처리 중 오류');
		}
	});
}


//글쓰기 오픈 & 사기번호 조회 오픈
function fraud_check_window(){
	window.open(ctxPath+"/market/security", "사기번호 조회",
	"titlebar=0,height=700,width=1000,top=50,left=200,status=0,scrollbars=0,location=0,resizable=0,menubar=0,toolbar=0"
	, "");
}

function move_write_page(bool){
	if(bool){
		location.href=ctxPath+'/board/write';
	}else{
		if(confirm('글 작성을 위해 유저 인증이 필요합니다. 이동하시겠습니까.')){
			location.href=ctxPath+'/user/mypage';
		}
	}
}

function move_market_write_page(bool){
	if(bool){
		location.href=ctxPath+'/market/write';
	}else{
		if(confirm('글 작성을 위해 유저 인증이 필요합니다. 이동하시겠습니까.')){
			location.href=ctxPath+'/user/mypage';
		}
	}
}


// login function
function check_login_inp(){
	if($('#user_menu_email_inp').val()==''){
		alert('Email을 입력하세요');
		$('#user_menu_email_inp').focus();
		return false;
	}else if($('#user_menu_pwd_inp').val()==''){
		alert('비밀번호를 입력하세요');
		$('#user_menu_pwd_inp').focus();
		return false;
	}
}

function loginWithKakao() {
	Kakao.Auth.authorize({redirectUri: reUrl+"finproj/login/kakao"});
}

function loginWithGoogle() {
	//구글 인증 서버로 인증코드 발급 요청
	location.href="https://accounts.google.com/o/oauth2/v2/auth?"
	+"client_id=763924312013-ppith6f1s7furfp1jvagis96rboh584f.apps.googleusercontent.com"
	+"&redirect_uri="+reUrl+"finproj/login/google&response_type=code&scope=email%20profile%20openid &access_type=offline"
}

//chat window manage
function open_room_out(self){
	$(self).children("button").show();
	setTimeout(function(){
		$(self).children("button").hide();
	}, 3000);
}

function chat_room_out(room_no, self){
	if(confirm('채팅방을 나가시겠습니까.')){
		$.ajax({
			type: "get",
			url: ctxPath+"/chat/room/out",
			data: {
				chat_room_no: room_no,
			},
			dataType : "text",
			async:false,
			success: function(data){
				if(data>0){
					$(self).parent().remove();
				}else{
					alert('채팅방 퇴장 중 오류');
				}
			},
			error: function(){
				alert('채팅방 퇴장 중 오류');
			}
		});
	}
}

//chating js
function open_chat(i){
	if(i==1){
		alarm_move_href(4);
	}
	if($('.chat_wrap').css('display')=='none'){
		console.log(123);
		$('.chat_wrap').css('display', 'flex');
	}
	return true;
}

function close_chat(){
	$('.chat_wrap').css('display', 'none');
	$('.chat_send').attr('onclick','');
	$('.chat_cont').html("");
	$('.chat_msg').val("");
	$('.chat_title_img_send').attr('src',ctxPath+'/resources/images/profile/default/default_profile.png');
	$('.chat_title_nick_send').html('');
	if(socket!=null){
		socket.onclose();
	}
}

function chat_board(no){
	if(loginUser_no!=no){
		let re = open_chat(2)
		if(re){
			$.ajax({
				type: "get",
				url: ctxPath+"/chat/board",
				data: {
					user_no: no,
				},
				dataType : "text",
				async:false,
				success: function(data){
					if(data==0){
						alert('채팅방 등록 중 오류');
					}else { // 기존 채팅 존재
						if(data.nickname == ''){
							chat_start(no, data.chat_room_no);
						}else{
							let table = "<p><a href='javascript:chat_start("+no+","+data.chat_room_no+")'><img src='"+data.profile+"'>"+data.nickname+"</a></p>" +
										"<button onclick='chat_room_out("+data.chat_room_no+")' class='chat_room_out'><i class='fa fa-ellipsis-v' aria-hidden='true'></i></button>";
							$('.chat_list').append(table);
							chat_start(no, data.chat_room_no);
						}
					}
				},
				error: function(){
					alert('채팅상대 검색 중 오류');
				}
			});
		}
	}
}

function chat_start(no, room_no){
	$('.chat_cont').html("");
	$('.chat_msg').val("");
	$.ajax({
		type: "get",
		url: ctxPath+"/chat/enter",
		data: {
			user_no: no,
		},
		dataType : "json",
		contentType : "application/json; charset=UTF-8;",
		async:false,
		success: function(data){
			console.log(data);
			let chat_list = data.ChatList;
			let send_user = data.Send_user;
			let table = "";

			$(chat_list).each(function(){
				if(this.send_user == no){
					table += "<div class='chat_sendU'><p>"+this.chat_cont+"</p><span class='chat_date_time'>"+this.created.substring(11, 16)+"</span></div>";
					
				}else{
					table += "<div class='chat_loginU'><span class='chat_date_time'>"+this.created.substring(11, 16)+"</span><p>"+this.chat_cont+"</p></div>"
				}
				
			});

			$('#chat_receipt').val(no);
			$('.chat_cont').append(table);
			$('.chat_send').attr('onclick', 'send_chat('+room_no+', '+loginUser_authen+', '+no+')');
			$('.chat_msg').attr('onkeydown', 'if( event.keyCode == 13 ){send_chat('+room_no+', '+loginUser_authen+', '+no+');}');

			$('.chat_title_nick_send').text(send_user.nickname);
			$('.chat_title_img_send').attr('src', send_user.profile);
			
			connect_chat();
		},
		error: function(){
			alert('채팅 시작 중 오류');
		}
	});
}


function connect_chat() {
	
	let ws = new WebSocket("ws://localhost:8787/finproj/chating");
	socket = ws;
	//이벤트 헨들러
	ws.onopen = function() {
		console.log('Info: connection opened.');
	};
	
	//소켓에 메시지를 보냈을 때(sess.sendMessage) 여기서 받아짐 
	ws.onmessage = function (event) {
		let sm = event.data;
		let sl = sm.split(',');
		/* "보내는 이 : " + sl[0]);
		보내는 유저 넘버 : + sl[1]
		"받는 이 : " + sl[2]);
		"내용: " + sl[3]); 
		"작성시간": + sl[4]
		*/
		
		let table = "<div class='chat_sendU'><p>"+sl[3]+"</p><span class='chat_date_time'>"+sl[4].substring(11, 16)+"</span></div>";
		
		if(sl[1]==$('#chat_receipt').val()){
			$('.chat_cont').append(table);
		}
		
		console.log("ReceiveMessage:" + event.data+'\n');
	};
	
	ws.onclose = function (event) { 
		console.log('Info: connection closed'); 
		//setTimeout( function() {connect(); }, 1000); // retry connection!!
	};
	
	ws.onerror = function (err) { console.log('Error:', err); };
}

function send_chat(room_no, authen, receiv_no){
	let receiveId = $('#chat_receipt').val();
	let msg = $('.chat_msg').val();
	//evt.preventDefault();
	if(receiv_no!=1){
		if(authen){
			if (socket.readyState !== 1 ) return;
			socket.send(room_no + "," + receiv_no + "," + receiveId + "," + msg);
			
			let d = new Date();

			let table = "<div class='chat_loginU'><span class='chat_date_time'>"+d.getHours() + ":" + d.getMinutes()+"</span><p>"+msg+"</p></div>";
			$('.chat_cont').append(table);
			$('.chat_msg').val("");
		}else{
			if(confirm('글 작성을 위해 유저 인증이 필요합니다. 이동하시겠습니까.')){
				location.href=ctxPath+'/user/mypage';
			}
		}
	}else{
		if (socket.readyState !== 1 ) return;
		socket.send(room_no+"," + receiveId + "," + msg);
		let d = new Date();
		let table = "<div class='chat_loginU'><span class='chat_date_time'>"+d.getHours() + ":" + d.getMinutes()+"</span><p>"+msg+"</p></div>";
		$('.chat_cont').append(table);
		$('.chat_msg').val("");
	}
}

function chat_admin(room_no){
	$('.chat_cont').html("");
	$('.chat_msg').val("");

	let table = "<p class='chat_admin_mess'>"
				+"안녕하세요.<br>Campion 챗봇입니다.<br>"
				+"궁금하신 사항을<br>물어보세요.<br>"
				+"<span>"
				+"<input type='button' value='관리자와 채팅하기' onclick='chat_start(1,"+room_no+")'><br>"
				+"<input type='button' value='F A Q' onclick='faq_start()'>"
				+"</span>"
				+"</p>";
	$('.chat_cont').append(table);
}



function faq_start(){
	$.ajax({
		type: "get",
		url: ctxPath+"/chat/faq",
		dataType : "json",
		contentType : "application/json; charset=UTF-8;",
		async:false,
		success: function(data){
			faqList = data;
			faq_cate();
		},
		error: function(){
			alert('FAQ 로딩 중 오류');
		}
	});
}

function faq_cont(cate_no){
	let table = "<p class='chat_admin_mess_u'>"+faqList[cate_no][0].name+"<p>";
				
	for(let i = 0; i<faqList[cate_no].length;i++){
		let d = faqList[cate_no][i];
		table += "<p class='chat_admin_mess'>" + d.content + "</p>";
	}
	table += "<p class='chat_admin_mess'><span style='margin:0;'><input type='button' value='카테고리로' onclick='faq_cate()'></span></p>";
	$('.chat_cont').append(table);
	
}

function faq_cate(){
	let table = "<p class='chat_admin_mess'>문의하실 카테고리를 선택하세요.";
	for(let i=1;i<=5;i++){
		table += "<span><input type='button' value='"+faqList[i][0].name+"' onclick='faq_cont("+faqList[i][0].faq_cate_no+")'></span>";
	}
	table += "</p>";
	$('.chat_cont').append(table);
}