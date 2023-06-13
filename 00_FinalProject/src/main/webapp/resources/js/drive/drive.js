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

		$.ajax({
			url: ctxPath+'/drive/rev/geocode', 
			type: 'GET',
			data: {
				coords_x: e.latlng.x,
				coords_y: e.latlng.y,
			},
			async: false,
			dataType: "json",
			contentType: "application/json; charset=UTF-8;",
			success: function(data) {
				console.log(data);

				let contentString = "<div class='iw_inner'>" +
									"<p class='iw_build'>"+data.building+"</p>" +
									"<p class='iw_addr'>" +
									data.addr +
									"</p><p>" +
									"<input class='iw_start_btn' type='button' value='출발' onclick='add_addr_inp(0)'>" +
									"<input class='iw_goal_btn' type='button' value='도착' onclick='add_addr_inp(1)'>" +
									"</p></div>" ;

				let infowindow = new naver.maps.InfoWindow({
					content: contentString
				});
				
				if (infowindow.getMap()) {
					infowindow.close();
				} else {
					infowindow.open(map, marker);
				}

			},
			error: function(err) {
				console.log('오류');
			}
		});
	});
});

function add_addr_inp(i){
	if(i==0){
		$('#start_drive').val($('.iw_addr').text());
	}else{
		$('#drive').val($('.iw_addr').text());
	}
}

function openwindows() {
	let startDri = $('#start_drive').val();
	let dri = $('#drive').val();
	
	let xStart = 0;
	let yStart = 0;
	
	console.log(startDri);
	
	if(dri !== ''){
	
		if(startDri !== ''){
			
			naver.maps.Service.geocode({
		        query: startDri
		    }, function(status, response) {
		        if (status !== naver.maps.Service.Status.OK) {
		            return alert('Something wrong!');
		        }
		
				let result = response.v2, // 검색 결과의 컨테이너
					items = result.addresses; // 검색 결과의 배열
					
				xStart = items[0].x;
				yStart = items[0].y;
				
			});
		}
	
		
		
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
				url: ctxPath+'/drive/content', 
				type:'post',
				data:{
					xDri: xDri,
					yDri: yDri,
					xStart: xStart,
					yStart: yStart
				},
				success: function(data) {
					
					let pathList = data.PathList;
					let wp = data.WP;
					
				    let jsonArray = JSON.parse(pathList);
				    
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
	
					let markers = new Array();
					let infos = new Array();
					
				    // 배열 마지막 위치를 마크로 표시함
				    let marker = new naver.maps.Marker({
				    	title: items[0].roadAddress,
				        position: polylinePath[polylinePath.length-1], //마크 표시할 위치 배열의 마지막 위치
				        map: map
				    });
				    
				    let marker2 = new naver.maps.Marker({
				        position: new naver.maps.LatLng(wp.wpY, wp.wpX), //마크 표시할 위치 배열의 마지막 위치
				        map: map
				    });
				
					markers.push(marker);
					markers.push(marker2);
					
					let infoWindow = new naver.maps.InfoWindow({
						content: "<div style='width: 200px;test-align:center;padding:10px;'>"+
						"<b>"+items[0].roadAddress+"</b></div>"
					});
					
					let infoWindow2 = new naver.maps.InfoWindow({
						content: "<div style='width: 200px;test-align:center;padding:10px;'>"+
						"<b>"+wp.name+"</b>"+
						"<br><font>"+wp.address+"</font></div>"
					});
					
					infos.push(infoWindow);
					infos.push(infoWindow2);
				    
				    function getClickHandler(seq) {
					return function(e) {
							let marker = markers[seq],
							infoWindow = infos[seq];
							
							if(infoWindow.getMap()){
								infoWindow.close();
							}else{
								infoWindow.open(map, marker);
							}
						}
					}
					
					for(let i=0;i<markers.length;i++){
						naver.maps.Event.addListener(markers[i], 'click', getClickHandler(i));
					}
				    
				    let table = "<div class='text'>" + wp.name + "</div>" +
								"<div class='drive_background'>" +
									"<div class='text_wrap'>" +
										"<div class='drive_road_img'>" +
											"<img src='"+wp.img+"'/>" +
										"</div>" +
										"<div class='drive_road_address'>" +
											"주소 : "+ wp.address +
										"</div>" +
										"<div class='drive_road_info'>" +
											"상세정보"+ "<hr>"+ wp.info +
										"</div>" +
									"</div>" +
								"</div>";
					$('#drive_road_view').css('height', 'auto');
				    $('#drive_road_view').html(table);

				},
				error: function(err) {
				}
			});
		});
	}else{
		alert('도착지를 입력하세요.');
	}
}