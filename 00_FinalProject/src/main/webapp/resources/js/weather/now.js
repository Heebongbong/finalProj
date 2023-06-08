/**
 * 
 */

  $(document).ready(function(){
    let lat;
    let lng;
    
    if(serv_lat!=''){
        lat = serv_lat;
        lng = serv_lng;
    }else{
        lat = 37.567944413725904;
        lng = 126.9831230334937;
    }
    
	let mapOptions = {
	    center: new naver.maps.LatLng(lat, lng),
	    zoom: 18
	};
	
	let map = new naver.maps.Map('map', mapOptions);
	
	let marker = new naver.maps.Marker({
	    position: new naver.maps.LatLng(lat, lng),
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
			success: function(data) {
				console.log(data);
                $('.search-bar').val(data.addr);

			},
			error: function(err) {
				console.log('오류');
			}
		});

	});
});
 
 	 //<!--
    //
    // LCC DFS 좌표변환을 위한 기초 자료
    //
    const RE = 6371.00877; // 지구 반경(km)
    const GRID = 5.0; // 격자 간격(km)
    const SLAT1 = 30.0; // 투영 위도1(degree)
    const SLAT2 = 60.0; // 투영 위도2(degree)
    const OLON = 126.0; // 기준점 경도(degree)
    const OLAT = 38.0; // 기준점 위도(degree)
    const XO = 43; // 기준점 X좌표(GRID)
    const YO = 136; // 기1준점 Y좌표(GRID)
    //
    // LCC DFS 좌표변환 ( code : "toXY"(위경도->좌표, v1:위도, v2:경도), "toLL"(좌표->위경도,v1:x, v2:y) )
    //


    function dfs_xy_conv(code, v1, v2) {
        let DEGRAD = Math.PI / 180.0;
        let RADDEG = 180.0 / Math.PI;

        let re = RE / GRID;
        let slat1 = SLAT1 * DEGRAD;
        let slat2 = SLAT2 * DEGRAD;
        let olon = OLON * DEGRAD;
        let olat = OLAT * DEGRAD;

        let sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
        
        let sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
        
        let ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);
        
        let rs = {};
        
        if (code == "toXY") {
        
            rs['lat'] = v1;
            rs['lng'] = v2;
            
            let ra = Math.tan(Math.PI * 0.25 + (v1) * DEGRAD * 0.5);
            ra = re * sf / Math.pow(ra, sn);
            
            let theta = v2 * DEGRAD - olon;
            
            if (theta > Math.PI) theta -= 2.0 * Math.PI;
            
            if (theta < -Math.PI) theta += 2.0 * Math.PI;
            
            theta *= sn;
            
            rs['x'] = Math.floor(ra * Math.sin(theta) + XO + 0.5);
            rs['y'] = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);
        }
        else {
            rs['x'] = v1;
            rs['y'] = v2;
            let xn = v1 - XO;
            let yn = ro - v2 + YO;
            ra = Math.sqrt(xn * xn + yn * yn);
            if (sn < 0.0) - ra;
            let alat = Math.pow((re * sf / ra), (1.0 / sn));
            alat = 2.0 * Math.atan(alat) - Math.PI * 0.5;

            if (Math.abs(xn) <= 0.0) {
                theta = 0.0;
            }
            else {
                if (Math.abs(yn) <= 0.0) {
                    theta = Math.PI * 0.5;
                    if (xn < 0.0) - theta;
                }
                else theta = Math.atan2(xn, yn);
            }
            let alon = theta / sn + olon;
            rs['lat'] = alat * RADDEG;
            rs['lng'] = alon * RADDEG;
        }
        return rs;
    }
    
	function moveWeather() {
        let addr = $('.address').val();

        if(addr !== ''){
			
			naver.maps.Service.geocode({
		        query: addr
		    }, function(status, response) {
		        if (status !== naver.maps.Service.Status.OK) {
		            return alert('Something wrong!');
		        }
		
				let result = response.v2, // 검색 결과의 컨테이너
					items = result.addresses; // 검색 결과의 배열
					
                let lng = items[0].x;
                let lat = items[0].y;
                let ad = items[0].roadAddress;
                
                let rs = dfs_xy_conv("toXY", lat, lng);
                console.log(rs.x, "/", rs.y);
                    
                location.href=ctxPath+"/weather/now?locX="+rs.x+"&locY="+rs.y+"&address="+ad+"&lat="+lat+"&lng="+lng;
				
			});
		}else{
            alert('주소를 입력하세요.');
        } 
	}