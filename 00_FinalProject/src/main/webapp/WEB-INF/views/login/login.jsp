<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<script src="https://accounts.google.com/gsi/client" async defer></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
<div id="login_wrap">
	<div>
		<input name="id" placeholder="id"><br>
		<input name="pwd" placeholder="pwd">
	</div>
	<div>
		<div><input type="button" value="로그인"></div>
		<div id="naver_id_login"></div>
		<div>
			<a id="kakao-login-btn" href="javascript:loginWithKakao()">
				<img src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg" width="222" alt="카카오 로그인 버튼" />
			</a>
		</div>
		<div id="g_id_onload" data-client_id="763924312013-ppith6f1s7furfp1jvagis96rboh584f.apps.googleusercontent.com"
			data-login_uri="http://localhost:8787/finproj/login/google" data-auto_prompt="false">
		</div>
		<div class="g_id_signin" data-type="standard" data-size="large"
			data-theme="outline" data-text="sign_in_with" data-shape="rectangular" data-logo_alignment="left">
		</div>
	</div>
</div>
<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
<script>
	let naver_login = new naver_id_login("2fzdhIRlmXgPi9uo_5Xi", "http://localhost:8787/finproj/login/naver");
	let state = naver_login.getUniqState();
	naver_login.setButton("white", 2,40);
	naver_login.setDomain("http://localhost:8787");
	naver_login.setState(state);
	naver_login.response_type = "code";
	naver_login.init_naver_id_login();
</script>
<script src="https://t1.kakaocdn.net/kakao_js_sdk/2.1.0/kakao.min.js" integrity="sha384-dpu02ieKC6NUeKFoGMOKz6102CLEWi9+5RQjWSV0ikYSFFd8M3Wp2reIcquJOemx" crossorigin="anonymous"></script>
<script>Kakao.init('78f087e4814e7f60f4ee016cae934876');</script>
<script>
	function loginWithKakao() {
		Kakao.Auth.authorize({redirectUri: 'http://localhost:8787/finproj/login/kakao'});
	}
</script>