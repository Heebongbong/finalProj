package com.spring.finproj.model.drive;

import java.util.List;
import java.util.Map;

public interface DriveDAO {
	int insertRoadInfo(DriveRoadDTO d);
	void insertRoadXY(Map<String, Object> map);
}