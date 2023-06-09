function updateCampingList(){
	
    $.ajax({
		type: "get",
		url: ctxPath+"/admin/camping/update",
		async:false,
		success: function(data){
			console.log(data);
			alert('DB업데이트 완료');
		},
		error: function(){
			alert('DB업데이트 실패');
		}
	});
}

