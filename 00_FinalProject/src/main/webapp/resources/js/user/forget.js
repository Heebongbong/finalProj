
function sendSMS() {
  let phone = $("#input_phone").val();

  $.ajax({
    url : ctxPath + "/user/sms/siteuser",
    type : "post",
    data : { phone : phone },
    success : function(res) {
      if(res === "") {
        alert("등록되지 않은 번호입니다.");
      }else {
        alert("인증번호가 발송되었습니다.");
        $(".phone_user_no").val(res);
      }
    },
    error : function() {
      alert("전송 실패");
    }
    
  });
}

function checkCode() {
		
  let input_code = $("#input_code").val();
  console.log(input_code);
    $.ajax({
      url : ctxPath + "/user/sms/check",
      type : "post",
      data : { input_code : input_code },
      success : function(res) {
        console.log(res);
        if(res==0){
          alert("인증 코드가 다릅니다");
        }else{
          let list = JSON.parse(res);
          for(let i=0;i<list.length;i++){
            let cont = list[i];

            let table = "<div class='phone_result'>" +
                        "<input type='radio' id='"+cont.email+"' name='email_radio' onchange=\'email_radio_click(\""+cont.email+"\")\'>" +
                        "<label for='"+cont.email+"'>" + cont.email + "</label>" +
                        "</div>";
            $('.code_result').html(table);
          }
        }
      },
      error : function() {
        alert("인증 실패");
      }
    });
}

function email_radio_click(email){
  $('.email_hidden').val(email);
  $('.text_part_box-wrap').show();
}

