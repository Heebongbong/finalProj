
function sendSMS() {
  let phone = $("#input_phone").val();

  // String check = "";
  // String res = userDao.checkTypeAndPhone(phone);
  
  // if(!res.equals("")) {
    
  //   /*
  //    * SendSMSAPI send = new SendSMSAPI();
  //    * 
  //    * String code = send.sendSMS(phone);
  //    */
    
  //   String code = "123";
    
    
  //   if (code != null) {
  //     session.setAttribute("code", code);
  //     System.out.println("코드생성 및 발신 성공~");
  //     check = "전송";
  //   }
  // }
  // System.out.println("type S 일치 전화번호 없음");

  // return check;
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
  //event.preventDefault();
}

function checkCode() {
		
  let input_code = $("#input_code").val();
        $.ajax({
          url : ctxPath + "/user/sms/check",
          type : "post",
          data : { input_code : input_code },
          success : function(res) {
            alert("인증 완료");
          },
          error : function() {
            alert("인증 실패");
          }
        });
  //event.preventDefault();
}

  