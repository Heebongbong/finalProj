
function sendSMS() {
			
  let phoneError = $(".input_phoneError");
    const phone = $("#input_phone");
    console.log(phone.val());
    console.log(phoneError.val());

  if(phone.val() == "") {
    phoneError.text('휴대전화를 입력하세요.');
  }else if(phoneError.innerText === '' || phoneError.innerText === '휴대전화로 인증번호가 전송되었습니다.') {
    
    // 전송 버튼 클릭 후 재전송 버튼으로 바꾸기
    const button = document.getElementById('sendBtn');
    button.innerText = '재전송';

    
    $.ajax({
      url : ctxPath + "/user/sms/send",
      type : "post",
      data : { phone : phone },
      success : function(res) {
          phoneError.textContent = "휴대전화로 인증번호가 전송되었습니다.";
      },
      error : function() {
        alert("전송 실패");
      }
      
    });
    
  }
  //event.preventDefault();
}

// function checkCode() {
		
//   let input_code = document.getElementById("input_code").value;
  
//   let phoneError = document.getElementsByClassName("input_phoneError")[0];
//   let codeError = document.getElementsByClassName("codeError")[0];
  
//   let button = document.getElementById('sendBtn');
//   let buttonText = button.innerText;
  
//   if(buttonText === '재전송'){
    
    
//       if(input_code != null){
//         $.ajax({
//           url : ctxPath + "/user/sms/check",
//           type : "post",
//           data : { input_code : input_code },
//           success : function(res) {
//             if(res == "true"){
//               // 전송 버튼 클릭 후 재전송 버튼으로 바꾸기
//               let checkBtn = document.getElementById('checkBtn');
//               checkBtn.innerText = '인증완료';
//               codeError.textContent = " ";
//               let input_phone = $('#input_phone').val();
//               $('#phone').val(input_phone);
//             }else {
//               codeError.textContent = "인증 번호가 틀렸습니다.";
//             }
//           },
//           error : function() {
//             alert("인증 실패");
//           }
          
//         });
//       }	else {
//           codeError.textContent = "인증번호 발송 버튼을 눌러 인증번호를 받으세요";
//       }  
    
//   } else {
//     if(phoneError.innerText === '') {
//       codeError.textContent = "인증번호 발송 버튼을 눌러 인증번호를 받으세요";
//     } else {
//       codeError.textContent = "휴대전화번호를 입력하세요";
//     }
//   }
//   //event.preventDefault();
  
// }


// $(document).ready(function(){
		
//   // 비밀번호 입력 시 확인 필수
//   $("#pwd").keydown(function(){
    
//     let pwdCheckError = document.getElementsByClassName("pwd_checkError")[0];
    
//     if(pwdCheckError.textContent != "비밀번호 확인") {
//       pwdCheckError.textContent = "비밀번호 확인 후에 진행하세요";
//       $("#check_pwd").focus();
//     }
//   });
  
//   // 비밀번호 입력 시 확인 필수
//   $("#pwd_re").keydown(function(){
//     let pwdCheckError = document.getElementsByClassName("pwd_checkError")[0];
//     let pwdError = document.getElementsByClassName("pwdError")[0];
//     let pwdReError = document.getElementsByClassName("pwd_reError")[0];
    
//     if(pwdCheckError.textContent != "비밀번호 확인") {
//       pwdCheckError.textContent = "비밀번호 확인 후에 진행하세요";
//       $("#check_pwd").focus();
//     }else if(pwdError.textContent != "") {
//       pwdReError.textContent = "새 비밀번호를 먼저 확인하세요";
//       $("#pwd").focus();
//     }
//   });
  
//   // 공백제거
//   let elements = document.querySelectorAll('.noWhitespace');
  
//   for (var i = 0; i < elements.length; i++) {
//     elements[i].addEventListener('input', function(event) {
//       var currentValue = this.value;
//       var trimmedValue = currentValue.replace(/\s/g, '');
//       if (currentValue !== trimmedValue) {
//         this.value = trimmedValue;
//       }
//     });
//   }
  
//   // 숫자만 입력
//   let inputElement = document.getElementById("input_phone");

//   inputElement.addEventListener("input", function(event) {
//     let currentValue = inputElement.value;
//     let sanitizedValue = currentValue.replace(/\D/g, "");

//     if (currentValue !== sanitizedValue) {
//       inputElement.value = sanitizedValue;
//     }
//   });
  
  
//    $("#submitBtn").click(function() {
//      let input_phone = document.getElementById('input_phone').value;
//      const button = document.getElementById('sendBtn');
//      const buttonText = button.innerText; 
      
//      if(input_phone.length != 0 && input_phone != "${loginUser.phone}"){
//        if(buttonText === '인증번호발송') {
       
//         let codeError = document.getElementsByClassName("codeError")[0];

//         codeError.textContent = "인증번호 발송 버튼을 눌러 인증번호를 받으세요";
        
//         event.preventDefault();
//       }
//    }
     
//      let input_code_length = document.getElementById('input_code').value.length;
//      const checkBtn = document.getElementById('checkBtn');
//       const checkBtnText = checkBtn.innerText;
        
//      if(input_code_length != 0 && checkBtnText === '인증하기') {
       
//         let codeError = document.getElementsByClassName("codeError")[0];

//         codeError.textContent = "인증하기를 눌러 인증을 완료하세요";
        
//         event.preventDefault();
//      }
     
//     /*  let check_pwd_length = document.getElementById('check_pwd').value.length;
//      let pwd_length = document.getElementById('pwd').value.length;
//      let pwd_re_length = document.getElementById('pwd_re').value.length;

//      if(check_pwd_length == 0 && (pwd_length != 0 || pwd_re_length != 0)) {
       
//         let pwdCheckError = document.getElementsByClassName("pwd_checkError")[0];

//         pwdCheckError.textContent = "비밀번호 확인 후에 진행하세요";
        
//         event.preventDefault();
//      } */
//    });
   
//   $.validator.addMethod("engAndNum", function(value, element) {
//        var pattern = /^[A-Za-z0-9]*$/;
  
//        return pattern.test(value);
//     });
  
//     $.validator.addMethod("specialChars", function(value, element) {
//        // Define the pattern to match special characters
//        var pattern = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/;
  
//        // Test the value against the pattern and return true or false
//        return pattern.test(value);
//     });
  
//     $.validator.addMethod("capitalLetters", function(value, element) {
//        // Define the pattern to match capital letters
//        var pattern = /[A-Z]/;
  
//        // Test the value against the pattern and return true or false
//        return pattern.test(value);
//     });
  
//     $.validator.addMethod("emailCheck", function(value, elements) {
//        var pattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
  
//        return pattern.test(value);
  
//     });
  
//     $.validator.addMethod("phoneCheck", function(value, elements) {
//         var length = value.trim().length;
        
//           if(length == 0){
//             return true;
//           }else {
//             var pattern = /^010\d{7,8}$/;
//             return pattern.test(value);
//           }
        
//     });
    
//    $.validator.addMethod("phoneLength", function(value, element) {
//           var length = value.trim().length;
          
//           if(length == 0){
//             return true;
//           }else {
//             return length >= 10 && length <= 11;
//           }
//    });
   
//    $.validator.addMethod("codeLength", function(value, element) {
//       const button = document.getElementById('sendBtn');
//       const buttonText = button.innerText;
//       var length = value.trim().length;
//       if(buttonText === '재전송'){
//             return length = 6;
//           }else {
//             return true;
//           }
//    });
   
//    $.validator.addMethod("codeCheck", function(value, element) {
//       const button = document.getElementById('sendBtn');
//       const buttonText = button.innerText;
//       var length = value.trim().length;
//       if(buttonText === '재전송'){
//             return length != 0;
//           }else {
//             return true;
//           }
//    });
   
//    $.validator.addMethod("nicknameLength", function(value, element) {
//           var length = value.trim().length;
//           return length >= 3 && length <= 20;
//    });
   
//    $.validator.addMethod("noSpecialChars", function(value, element) {
//        var pattern = /^[^!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]*$/;
//        return pattern.test(value);
//    });

   
   
   
   
//    $.validator.addMethod("minlength1", function(value, element) {
//      var length = value.trim().length;

//      if($(".pwd_checkError").text().trim() === "비밀번호 확인" || length != 0 ){
//          var length = value.trim().length;
//          return length >= 6;
//      }else {return true}
//    });

//    $.validator.addMethod("maxlength1", function(value, element) {
//      var length = value.trim().length;
    
//      if($(".pwd_checkError").text().trim() === "비밀번호 확인" || length != 0 ){
//        var length = value.trim().length;
//        return length <= 20;
//      }else {return true}
//    });

//    $.validator.addMethod("specialChars1", function(value, element) {
//      var length = value.trim().length;
    
//      if($(".pwd_checkError").text().trim() === "비밀번호 확인" || length != 0 ){
//         // Define the pattern to match special characters
//        var pattern = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/;
  
//        // Test the value against the pattern and return true or false
//        return pattern.test(value);
//      }else {return true}
//    });

//    $.validator.addMethod("capitalLetters1", function(value, element) {
//      var length = value.trim().length;
    
//      if($(".pwd_checkError").text().trim() === "비밀번호 확인" || length != 0 ){
//     // Define the pattern to match capital letters
//      var pattern = /[A-Z]/;

//      // Test the value against the pattern and return true or false
//      return pattern.test(value);
//      }else {return true}
//    });

//    $.validator.addMethod("equalTo1", function(value, element) {
//      var length = value.trim().length;
    
//      if($(".pwd_checkError").text().trim() === "비밀번호 확인" || length != 0 ){
//        let pwd = $("#pwd").val();
//        let pwd_re = $("#pwd_re").val();
       
//        return pwd == pwd_re;
//      }else {return true}
//    });
   
//    $.validator.addMethod("nicknameUnique", function(value, element) {
          
//      let isUnique = false;
//      // AJAX 요청
//           $.ajax({
//               url: ctxPath + "/user/check/nickname",
//               type: "get",
//               data: 
//                 { nickname: value,
//           },
//               async : false,
//               datatype : "text",
//               success : function(result) {
//                 isUnique = result;
//                 console.log(result);
//                },
//               error : function (){
//                  console.log(1);
//                }
//           });
//           return isUnique;
//    });
   
//    $.validator.addMethod("phoneUnique", function(value, element) {
//      let isUnique = false;
//      let length = value.trim().length;
//           if(length == 0 || (loginUser_phone == value)){
//             return true;
//           }else {
//           $.ajax({
//               url: ctxPath + "/user/check/phone",
//               type: "get",
//               data: { phone : value,
//             type : loginType
//             },
//               async : false,
//               datatype : "text",
//               success : function(result) {
//                 isUnique = result;
//                 console.log(result);
//                },
//               error : function (){
//                  console.log("/user/check/phone 못받아유~");
//                }
//           });
//           return isUnique;
//           }
//    });
   
//   $.validator.addMethod("emailUnique", function(value, element) {
//     let isUnique = false;
//     let length = value.trim().length;
//        if(length == 0 || (loginEmail == value)){
//          return true;
//        }else {
//        $.ajax({
//          url: ctxPath + "/user/check/email",
//          type: "get",
//          data: { email : value
//            },
//          async : false,
//          datatype : "text",
//          success : function(result) {
//            isUnique = result;
//            console.log(result);
//         },
//          error : function (){
//           console.log("/user/check/email 못받아유~");
//         }
//        });
//        return isUnique;
//        }
//   });
  
//   $("#mypageForm").validate({
//     rules : {
//       pwd: {
//         minlength1: true,
//         maxlength1: true,
//         specialChars1: true,
//         capitalLetters1: true,
//       },
//       pwd_re: {
//         minlength1: true,
//         maxlength1: true,
//         specialChars1: true,
//         capitalLetters1: true,
//         equalTo1: true
//       },
//       email: {
//         required : true,
//         emailCheck : true,
//         email : true,
//         emailUnique : true
//       },
//       input_phone: {
//         phoneLength : true,
//         phoneCheck : true,
//         phoneUnique : true
//       }, 
//       code: {
//         codeCheck : true,
//         codeLength : true
//       },
//       nickname: {
//         nicknameLength : true,
//         nicknameUnique : true,
//         noSpecialChars : true
//       }
//       },	
//     messages : {
//       pwd: {
//         minlength1 : "최소 6글자 이상 입력해주세요.",
//         maxlength1 : "12글자를 넘지 말아주세요.",
//         specialChars1 : "특수문자 입력해주세요.",
//         capitalLetters1: "대문자 하나 입력해주세요"
//       },
//       pwd_re: {
//         minlength1 : "최소 6글자 이상 입력해주세요.",
//         maxlength1 : "12글자를 넘지 말아주세요.",
//         specialChars1 : "특수문자 입력해주세요.",
//         capitalLetters1: "대문자 하나 입력해주세요",
//         equalTo1: "일치하지 않아요...."

//       },
//       email: {
//         required: '이메일을 입력해주세요',
//         emailCheck : '유효한 이메일 주소를 입력하세요',
//         email : '유효한 이메일 주소를 입력하세요',
//         emailUnique : '이미 등록된 이메일 주소입니다.'

//       },
//       input_phone: {
//         phoneLength : "유효한 휴대전화 번호를 입력하세욘",
//         phoneCheck : "공백, 문자 없이 입력하세요",
//         phoneUnique : "이미 등록된 번호입니다."
//       },
//       code: {
//         codeCheck : "인증번호를 입력하세요",
//         codeLength : "인증번호는 6자리입니다."
//       },
//       nickname: {
//         nicknameLength : "닉네임은 3글자에서 20글자 사이어야 합니다.",
//         nicknameUnique : "이미 사용 중인 닉네임입니다.",
//         noSpecialChars : "특수문자는 입력할 수 없습니다."
//       }
//   },
    
//     errorPlacement: function(error, element) {
//         var targetElementClass = element.attr("name") + "Error";
//         var targetElement = $("." + targetElementClass);
//         targetElement.html(error);
//         targetElement.css("color", "red");
//     },
    
//     success: function(label, element) {
//         var targetElementClass = $(element).attr("name") + "Error";
//         var targetElement = $("." + targetElementClass);
//         targetElement.css("height", "20px");
//     }

//   });
  
// });
