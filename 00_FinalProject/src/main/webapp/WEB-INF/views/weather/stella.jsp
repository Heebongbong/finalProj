<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%=request.getContextPath() %>"/>
<style type="text/css">
	#content{
		width: 100%;
	}
		
	#stella_wrap iframe{
		width: 100%;
		height: calc(100vh - 178px);
	}
	
	@media all and (max-width: 680px) {
		#stella_wrap iframe{
			height: calc(100vh - 140px);
		}
	}
</style>
<script type="text/javascript">
	$(document).ready(function(){
		$('#stella_wrap').css('height', $('#content').css('height'));
	});
</script>
<div id="stella_wrap">
	<iframe src="https://stellarium-web.org/"></iframe>
</div>