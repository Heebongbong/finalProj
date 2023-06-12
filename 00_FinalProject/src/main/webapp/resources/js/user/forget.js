
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
        const button = $('#sendBtn');
			  button.text('재전송');
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
          let checkBtn = document.getElementById('checkBtn');
          checkBtn.innerText = '인증완료';
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


// 비밀번호 확인
function checkPwd() {
  let check_pwd = $("#check_pwd").val();
  let pwdCheckError = $(".pwd_checkError");
  
  if(check_pwd != null){
    
    $.ajax({
        url : ctxPath + "/user/check/pwd",
        type : "post",
        data : {check_pwd : check_pwd},
        dataType : "text",
        success : function(res) {
          if(res === "true") {
            pwdCheckError.text("비밀번호 확인");
          } else {
            pwdCheckError.text("비밀번호가 틀렸습니다.");
          }
        },
        error : function() {
          alert("비밀번호 확인 실패");
        }
      });
  } else {
    alert("error");
  }
  //event.preventDefault();
}

$(document).ready(function(){

  $("#pwd").keydown(function(){
    let pwdCheckError = $(".pwd_checkError");
    if(pwdCheckError.text() != "비밀번호 확인") {
      pwdCheckError.text("비밀번호 확인 후에 진행하세요");
      $("#check_pwd").focus();
    }
  });
  
  $("#pwd_re").keydown(function(){
    let pwdCheckError = $(".pwd_checkError");
    let pwdError = $(".pwdError");
    let pwdReError = $(".pwd_reError");
    if(pwdCheckError.text() != "비밀번호 확인") {
      pwdCheckError.text("비밀번호 확인 후에 진행하세요");
      $("#check_pwd").focus();
    }else if(pwdError.text() != "") {
      pwdReError.text("새 비밀번호를 먼저 확인하세요");
      $("#pwd").focus();
    }
  });
  
  // 공백제거
  let elements = document.querySelectorAll('.noWhitespace');
  for (var i = 0; i<elements.length; i++) {
    elements[i].addEventListener('input', function(event) {
      var currentValue = this.value;
      var trimmedValue = currentValue.replace(/\s/g, '');
      if (currentValue !== trimmedValue) {
        this.value = trimmedValue;
      }
    });
  }
  
  // 숫자만 입력
  let inputElement = document.getElementById("input_phone");
  inputElement.addEventListener("input", function(event) {
    let currentValue = inputElement.value;
    let sanitizedValue = currentValue.replace(/\D/g, "");
    if (currentValue !== sanitizedValue) {
      inputElement.value = sanitizedValue;
    }
  });
  
  $("#submitBtn").click(function() {
    let input_phone = document.getElementById('input_phone').value;
    const button = document.getElementById('sendBtn');
    const buttonText = button.innerText; 
    
    if(input_phone.length != 0 && input_phone != loginUser_phone){
      if(buttonText === '인증번호발송') {
      let codeError = document.getElementsByClassName("codeError")[0];
      codeError.textContent = "인증번호 발송 버튼을 눌러 인증번호를 받으세요";
      event.preventDefault();
      }
    }
    
    let input_code_length = document.getElementById('input_code').value.length;
    const checkBtn = document.getElementById('checkBtn');
    const checkBtnText = checkBtn.innerText;
      
    if(input_code_length != 0 && checkBtnText === '인증하기') {
      let codeError = document.getElementsByClassName("codeError")[0];
      codeError.textContent = "인증하기를 눌러 인증을 완료하세요";
      event.preventDefault();
    }
  });
   
  $.validator.addMethod("engAndNum", function(value, element) {
    var pattern = /^[A-Za-z0-9]*$/;
    return pattern.test(value);
  });
  
  $.validator.addMethod("specialChars", function(value, element) {
    var pattern = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/;
    return pattern.test(value);
  });
  
  $.validator.addMethod("capitalLetters", function(value, element) {
    var pattern = /[A-Z]/;
    return pattern.test(value);
  });
  
  $.validator.addMethod("emailCheck", function(value, elements) {
      var pattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
      return pattern.test(value);
  });
  
  $.validator.addMethod("phoneCheck", function(value, elements) {
    var length = value.trim().length;
      if(length == 0){
        return true;
      }else {
        var pattern = /^010\d{7,8}$/;
        return pattern.test(value);
      }
  });
    
  $.validator.addMethod("phoneLength", function(value, element) {
    var length = value.trim().length;
    if(length == 0){
      return true;
    }else {
      return length >= 10 && length <= 11;
    }
  });
   
  $.validator.addMethod("codeLength", function(value, element) {
    const button = document.getElementById('sendBtn');
    const buttonText = button.innerText;
    var length = value.trim().length;
    if(buttonText === '재전송'){
          return length = 6;
        }else {
          return true;
        }
  });
   
  $.validator.addMethod("codeCheck", function(value, element) {
    const button = document.getElementById('sendBtn');
    const buttonText = button.innerText;
    var length = value.trim().length;
    if(buttonText === '재전송'){
          return length != 0;
        }else {
          return true;
        }
  });
   
  $.validator.addMethod("nicknameLength", function(value, element) {
        var length = value.trim().length;
        return length >= 3 && length <= 20;
  });
   
  $.validator.addMethod("noSpecialChars", function(value, element) {
      var pattern = /^[^!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]*$/;
      return pattern.test(value);
  });

  $.validator.addMethod("minlength1", function(value, element) {
    var length = value.trim().length;

    if($(".pwd_checkError").text().trim() === "비밀번호 확인" || length != 0 ){
        var length = value.trim().length;
        return length >= 6;
    }else {return true}
  });

  $.validator.addMethod("maxlength1", function(value, element) {
    var length = value.trim().length;
  
    if($(".pwd_checkError").text().trim() === "비밀번호 확인" || length != 0 ){
      var length = value.trim().length;
      return length <= 20;
    }else {return true}
  });

  $.validator.addMethod("specialChars1", function(value, element) {
    var length = value.trim().length;
  
    if($(".pwd_checkError").text().trim() === "비밀번호 확인" || length != 0 ){
      // Define the pattern to match special characters
      var pattern = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/;

      // Test the value against the pattern and return true or false
      return pattern.test(value);
    }else {return true}
  });

  $.validator.addMethod("capitalLetters1", function(value, element) {
    var length = value.trim().length;
    if($(".pwd_checkError").text().trim() === "비밀번호 확인" || length != 0 ){
    var pattern = /[A-Z]/;
    return pattern.test(value);
    } else {return true}
  });

  $.validator.addMethod("equalTo1", function(value, element) {
    var length = value.trim().length;
    if($(".pwd_checkError").text().trim() === "비밀번호 확인" || length != 0 ){
      let pwd = $("#pwd").val();
      let pwd_re = $("#pwd_re").val();
      return pwd == pwd_re;
    }else {return true}
  });
   
  $.validator.addMethod("phoneUnique", function(value, element) {
    let isUnique = false;
    let length = value.trim().length;
      if(length == 0 || (loginUser_phone == value)){
        return true;
      }else {
        $.ajax({
          url: ctxPath + "/user/check/phone",
          type: "get",
          data: { 
                  phone : value
                },
          async : false,
          datatype : "text",
          success : function(result) {
            isUnique = result;
            console.log(result);
            },
          error : function () {
              console.log("/user/check/phone 못받아유~");
            }
        });
        return isUnique;
      }
  });
  

  $("#forgetForm").validate({
    rules : {
      pwd: {
        minlength1: true,
        maxlength1: true,
        specialChars1: true,
        capitalLetters1: true,
      },
      pwd_re: {
        minlength1: true,
        maxlength1: true,
        specialChars1: true,
        capitalLetters1: true,
        equalTo1: true
      },
      input_phone: {
        phoneLength : true,
        phoneCheck : true,
        phoneUnique : true
      }, 
      code: {
        codeCheck : true,
        codeLength : true
      },
      },	
    messages : {
      pwd: {
        minlength1 : "최소 6글자 이상 입력해주세요.",
        maxlength1 : "12글자를 넘지 말아주세요.",
        specialChars1 : "특수문자 입력해주세요.",
        capitalLetters1: "대문자 하나 입력해주세요"
      },
      pwd_re: {
        minlength1 : "최소 6글자 이상 입력해주세요.",
        maxlength1 : "12글자를 넘지 말아주세요.",
        specialChars1 : "특수문자 입력해주세요.",
        capitalLetters1: "대문자 하나 입력해주세요",
        equalTo1: "일치하지 않아요...."

      },
      input_phone: {
        phoneLength : "유효한 휴대전화 번호를 입력하세요",
        phoneCheck : "공백, 문자 없이 입력하세요",
        phoneUnique : "이미 등록된 번호입니다."
      },
      code: {
        codeCheck : "인증번호를 입력하세요",
        codeLength : "인증번호는 6자리입니다."
      },
  },
  errorPlacement: function(error, element) {
      var targetElementClass = element.attr("name") + "Error";
      var targetElement = $("." + targetElementClass);
      targetElement.html(error);
      targetElement.css("color", "red");
  },
  success: function(label, element) {
      var targetElementClass = $(element).attr("name") + "Error";
      var targetElement = $("." + targetElementClass);
      targetElement.css("height", "20px");
  }
  
  });
});
