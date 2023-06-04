/**
 * 
 */

 $(document).ready(function(){

	//푸터메뉴 오픈
	$('body').on("mousewheel",function(event){
		if($('#footer').css('display')=='none'){
			open_footer(event);
		}
	});

	//유저 헤더메뉴 닫기
	$('body').on('click', function(event){
		if($(event.target).parents('.user_menu_wrap').length < 1 &&event.target.className!='user_menu_wrap'){
			close_user_menu();
		}
	});

	//유저 알람 모달창 닫기
	$('body').on('click', function(event){
		if($(event.target).parents('.alarm_modal_overlay').length < 1 &&event.target.className!='alarm_icon'){
			$('.alarm_modal_overlay').hide();
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
	$('.user_menu_wrap').animate({
		width: 'show'
 	}, 400);
}

//유저 헤더 닫기
function close_user_menu(){
	$('.user_menu_wrap').animate({
		width: 'hide'
	}, 400);
}

//푸터 열기
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
function open_chat(){
	if(loginUser==''){
		alert('로그인이 필요합니다.');
		return false;
	}else{
		$('.chat_wrap').css('display', 'flex');
		return true;
	}
}

function close_chat(){
	$('.chat_wrap').css('display', 'none');
	$('.chat_send').attr('onclick','');
	$('.chat_cont').html("");
	$('.chat_msg').val("");
	socket.onclose();
}

function chat_board(no){
	if(open_chat()){
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
				}else{
					let table = "<p><a href='javascript:chat_start("+no+")'>"+data+"</a></p>";
					$('.chat_list').append(table);
					chat_start(no);
				}
			},
			error: function(){
				alert('채팅상대 검색 중 오류');
			}
		});
	};
}

function chat_start(no){
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
			let table = "";

			$(data).each(function(){
				if(this.send_user == no){
					table += "<p style='width:100%;' class='chat_sendU'>"+this.nickname+" : "+this.chat_cont+"</p>";
					
				}else{
					table += "<p style='width:100%;' class='chat_loginU'>"+this.chat_cont+"</p>"
				}
				
			});
			console.log($('.chat_send'));
			$('#chat_receipt').val(no);
			$('.chat_cont').append(table);
			$('.chat_send').attr('onclick', 'send_chat('+data[0].chat_room_no+', '+loginUser_authen+', '+no+')');

			connect_chat();
		},
		error: function(){
			alert('채팅 시작 중 오류');
		}
	});
}


function connect_chat() {
	
	let ws = new WebSocket("ws://192.168.140.38:8787/finproj/chating");
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
		"받는 이 : " + sl[1]);
		"내용: " + sl[2]); */
		
		let table = "<p style='width:100%;' class='chat_sendU'>"+sl[0]+" : "+sl[2]+"</p>";
		$('.chat_cont').append(table);
		
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
			$('.chat_cont').append("<p style='width:100%;' class='chat_loginU'>"+msg+"</p>")
			$('.chat_msg').val("");
		}else{
			if(confirm('글 작성을 위해 유저 인증이 필요합니다. 이동하시겠습니까.')){
				location.href=ctxPath+'/user/mypage';
			}
		}
	}else{
		if (socket.readyState !== 1 ) return;
		socket.send(room_no+"," + receiveId + "," + msg);
		$('.chat_cont').append("<p style='width:100%;' class='chat_loginU'>"+msg+"</p>")
		$('.chat_msg').val("");
	}
}

function chat_admin(){
	$('.chat_cont').html("");
	$('.chat_msg').val("");

	let table = "<p style='width:100%;' class='chat_sendU'>Chat Bot : <span>"
				+"안녕하세요. Campion 챗봇입니다.<br>"
				+"궁금하신 사항을 물어보세요.<br>"
				+"<input type='button' value='관리자와 채팅하기' onclick='chat_start(1)' style='width: 150px;'><br>"
				+"<input type='button' value='F A Q' onclick='faq_start()' style='width: 150px;'>"
				+"</span></p>";
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
	let table = "챗봇 : <br>"+faqList[cate_no][0].name+"을 선택하셨습니다.<br>";
				
	for(let i = 0; i<faqList[cate_no].length;i++){
		let d = faqList[cate_no][i];
		table += "<p>" + d.content + "</p>";
	}
	table += "<p style='text-align:right'><input type='button' value='카테고리로' onclick='faq_cate()'></p>";
	$('.chat_cont').append(table);
	
}

function faq_cate(){
	let table = "챗봇 : <br><t>문의하실 카테고리를 선택하세요.<br>";
	for(let i=1;i<=5;i++){
		table += "<p style='text-align:center; width:130px;'><input type='button' value='"+faqList[i][0].name+"' onclick='faq_cont("+faqList[i][0].faq_cate_no+")'></p>";
	}
	$('.chat_cont').append(table);
}