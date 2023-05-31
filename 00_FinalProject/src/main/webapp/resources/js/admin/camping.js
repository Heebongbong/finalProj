function insertCampingList(){
	
    $.ajax({
		type: "get",
		url: ctxPath+"/camping/admin/insert",
		async:false,
		success: function(data){
			console.log(data);
			alert('DB추가 완료');
		},
		error: function(){
			alert('DB추가 실패');
		}
	});
}

function deleteCampingList(){
	
    $.ajax({
		type: "get",
		url: ctxPath+"/camping/admin/delete",
		async:false,
		success: function(data){
			console.log(data);
			alert('DB삭제 완료');
		},
		error: function(){
			alert('DB삭제 실패');
		}
	});
}