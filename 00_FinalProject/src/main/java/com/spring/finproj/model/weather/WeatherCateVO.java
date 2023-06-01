package com.spring.finproj.model.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherCateVO {
	private String lgt; // 낙뢰 kA
	private String pty; // 강수형태 (코드값)
	private String rn1; // 강수량 mm
	private String sky; // 하늘 상태
	private String t1h; //기온 `C
	private String reh; // 습도 %
	private String uuu; // 동서 바람 성분 m/s
	private String vvv; // 남북 바람 성분 m/s
	private String vec; // 풍향 deg
	private String wsd; // 풍속 m/s
}
