<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<c:set value="${sessionScope.LoginUser }" var="loginUser"/>
<c:set value="${Receiver }" var="receiver" />
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.5/sockjs.min.js"></script>
<div id="chat_wrap">
	<div class="chat_title">
		<h2>${receiver.nickname }과의 대화</h2>
		<span class="popClose">x</span>
	</div>
	<div class="chat_cont">
	</div>
	<div class="well">
		<input type="text" id="receiveId" value="" class="form-control" placeholder="받는사람"> 
		<input type="text" id="msg" value="" class="form-control" placeholder="메세지">
		<button class="btn btn-primary" id="btnSend">Send Message</button>
	</div>
</div>