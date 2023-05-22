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
	
	let socket = null;
	
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

//chating js
function open_chat(){
	if(loginUser==''){
		alert('로그인이 필요합니다.');
	}else{
		$('.chat_wrap').css('display', 'flex');
	}
}

function close_chat(){
	$('.chat_wrap').css('display', 'none');
	$('.chat_send').attr('onclick','');
	$('.chat_cont').html("");
	$('.chat_msg').val("");
	socket.onclose();
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
			$(data).each(function(){
				let table = "";
				
				if(this.send_user == no){
					table = "<p style='width:100%;' class='chat_sendU'>"+this.nickname+" : "+this.chat_cont+"</p>";
					
				}else{
					table = "<p style='width:100%;' class='chat_loginU'>"+this.chat_cont+"</p>"
				}
				$('#chat_receipt').val(no);
				$('.chat_cont').append(table);
				$('.chat_send').attr('onclick', 'send_chat('+data[0].chat_room_no+')');
			});

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
		/* $("#writer").text("보내는 이 : " + sl[0]);
		$("#receiver").text("받는 이 : " + sl[1]);
		$("#content").text("내용: " + sl[2]); */
		
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
 
function send_chat(room_no){
	let receiveId = $('#chat_receipt').val();
	let msg = $('.chat_msg').val();
	console.log(msg);
	//evt.preventDefault();
	if (socket.readyState !== 1 ) return;
	socket.send(room_no+"," + receiveId + "," + msg);
	$('.chat_cont').append("<p style='width:100%;' class='chat_loginU'>"+msg+"</p>")
	$('.chat_msg').val("");
}

function chat_board(no){
	open_chat();
	$.ajax({
		type: "get",
		url: ctxPath+"/chat/board",
		data: {
			user_no: no,
		},
		dataType : "text",
		async:false,
		success: function(data){
			if(data>0){
				chat_start(no);
			}else{
				alert('채팅방 등록 중 오류');
			}
		},
		error: function(){
			alert('채팅상대 검색 중 오류');
		}
	});
}