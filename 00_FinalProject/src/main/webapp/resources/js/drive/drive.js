/**
 * 
 */
 
 $(document).ready(function(){
	let mapOptions = {
	    center: new naver.maps.LatLng(37.567944413725904,126.9831230334937),
	    zoom: 18
	};
	
	let map = new naver.maps.Map('map', mapOptions);
	
	let marker = new naver.maps.Marker({
	    position: new naver.maps.LatLng(37.567944413725904,126.9831230334937),
	    map: map
	});
	
	naver.maps.Event.addListener(map, 'click', function(e) {
	    marker.setPosition(e.latlng);
	});
});


function openwindows() {
	let dri = $('#drive').val();
	
	naver.maps.Service.geocode({
        query: dri
    }, function(status, response) {
        if (status !== naver.maps.Service.Status.OK) {
            return alert('Something wrong!');
        }

		let result = response.v2, // 검색 결과의 컨테이너
			items = result.addresses; // 검색 결과의 배열
			
		let xDri = items[0].x;
		let yDri = items[0].y;
		$('#addr').html("검색하신 주소는 : "+items[0].roadAddress+" 입니다.")
       
		$.ajax({
			url: '${ctxPath }/drive/content', 
			type:'post',
			data:{
				xDri: xDri,
				yDri: yDri
			},
			success: function(data) {
			    
			    let jsonArray = JSON.parse(data);
			    
			    let polylinePath = [];
			    
			    for (let i = 0; i < jsonArray.length; i++) {
		    		let latitude = jsonArray[i][0];
		    		let longitude = jsonArray[i][1];
		    		
		    		polylinePath.push(new naver.maps.LatLng(longitude,latitude));
		    	}
			    
			    let map = new naver.maps.Map('map', {
			        zoom: 17,
			        center: new naver.maps.LatLng(37.567944413725904,126.9831230334937),
			    });
			    
			  	//위의 배열을 이용해 라인 그리기
			    let polyline = new naver.maps.Polyline({
			        path: polylinePath,      //선 위치 변수배열
			        strokeColor: '#FF0000', //선 색 빨강 #빨강,초록,파랑
			        strokeOpacity: 0.8, //선 투명도 0 ~ 1
			        strokeWeight: 6,   //선 두께
			        map: map           //오버레이할 지도
			    });

			    // 배열 마지막 위치를 마크로 표시함
			    let marker = new naver.maps.Marker({
			        position: polylinePath[polylinePath.length-1], //마크 표시할 위치 배열의 마지막 위치
			        map: map
			    });
			    
			    $('#view').text(data);
			},
			error: function(err) {
			}
		});
	});
}