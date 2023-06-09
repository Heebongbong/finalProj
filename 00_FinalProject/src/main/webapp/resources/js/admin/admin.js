//chat window manage
let faqList = null;
let socket = null;

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
							let table = "<p><a href='javascript:chat_start("+no+","+data.chat_room_no+")'><img src='"+data.profile+"'>"+data.nickname+"</a></p>";
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
			$('.chat_send').attr('onclick', 'send_chat('+room_no+', true, '+no+')');
			$('.chat_msg').attr('onkeydown', 'if( event.keyCode == 13 ){send_chat('+room_no+', true, '+no+');}');

			$('.chat_title_nick_send').text(send_user.nickname);
			$('.chat_title_img_send').attr('src', send_user.profile);

			console.log($('.chat_cont')[0].scrollHeight);
			$('.chat_cont').scrollTop($('.chat_cont')[0].scrollHeight);
			
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
		보내는 유저 넘버 : + sl[1]
		"받는 이 : " + sl[2]);
		"내용: " + sl[3]); 
		"작성시간": + sl[4]
		*/
		
		let table = "<div class='chat_sendU'><p>"+sl[3]+"</p><span class='chat_date_time'>"+sl[4].substring(11, 16)+"</span></div>";
		
		if(sl[1]==$('#chat_receipt').val()){
			$('.chat_cont').append(table);
			
			console.log($('.chat_cont')[0].scrollHeight);
			$('.chat_cont').scrollTop($('.chat_cont')[0].scrollHeight);
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
			let d_t = d.toString().slice(16,21);

			let table = "<div class='chat_loginU'><span class='chat_date_time'>"+d_t+"</span><p>"+msg+"</p></div>";
			$('.chat_cont').append(table);
			$('.chat_msg').val("");
			
			console.log($('.chat_cont')[0].scrollHeight);
			$('.chat_cont').scrollTop($('.chat_cont')[0].scrollHeight);
		}else{
			if(confirm('글 작성을 위해 유저 인증이 필요합니다. 이동하시겠습니까.')){
				location.href=ctxPath+'/user/mypage';
			}
		}
	}else{
		if (socket.readyState !== 1 ) return;
		socket.send(room_no + "," + receiv_no + "," + receiveId + "," + msg);
		let d = new Date();
		let table = "<div class='chat_loginU'><span class='chat_date_time'>"+d.getHours() + ":" + d.getMinutes()+"</span><p>"+msg+"</p></div>";
		$('.chat_cont').append(table);
		$('.chat_msg').val("");
		
		console.log($('.chat_cont')[0].scrollHeight);
		$('.chat_cont').scrollTop($('.chat_cont')[0].scrollHeight);
	}
}