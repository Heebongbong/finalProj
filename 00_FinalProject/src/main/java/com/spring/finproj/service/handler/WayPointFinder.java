package com.spring.finproj.service.handler;

import java.util.List;

import com.spring.finproj.model.drive.DriveRoadDTO;
import com.spring.finproj.model.drive.LinearEquation;
import com.spring.finproj.model.drive.Point;

public class WayPointFinder {
	
	// 출발지와 교점 사이의 거리를 계산하는 메소드
    private double calculateDistance(double x1, double y1, double x2, double y2) {
        double deltaX = x2 - x1;
        double deltaY = y2 - y1;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    // 출발지와 목적지를 지나는 직선의 방정식을 구하는 메소드
    private LinearEquation calculateLineEquation(double startX, double startY, double endX, double endY) {
        // 생략: 두 점을 이용하여 직선의 방정식을 계산하여 반환
    	LinearEquation l = new LinearEquation();
    	
    	l.setSlope(((endY - startY) / (endX - startX)));
    	
    	l.setIntercept((startY - (((endY - startY) / (endX - startX) ) * startX )));
    	
    	return l;
    }
    
    // 수선의 발을 내리는 좌표를 계산하는 메소드
    private Point calculatePerpendicularPoint(LinearEquation lineEquation, double waypointX, double waypointY) {
        // 수선의 발을 내리는 직선의 방정식을 구합니다.
        double perpendicularSlope = -1 / lineEquation.getSlope();
        double perpendicularIntercept = waypointY - perpendicularSlope * waypointX;

        // 수선의 발과 주어진 직선의 교점을 계산합니다.
        double intersectionX = (lineEquation.getIntercept() - perpendicularIntercept) / (perpendicularSlope - lineEquation.getSlope());
        double intersectionY = lineEquation.getSlope() * intersectionX + lineEquation.getIntercept();

        return new Point(intersectionX, intersectionY);
    }

    // 출발지와 목적지를 지나는 직선과 수선의 발을 내리는 웨이포인트를 찾는 메소드
    public DriveRoadDTO findNearestWaypoint(double startX, double startY, double endX, double endY, List<DriveRoadDTO> waypoints) {
        LinearEquation lineEquation = calculateLineEquation(startX, startY, endX, endY);
        DriveRoadDTO nearestWaypoint = null;
        double minDistance = Double.MAX_VALUE;

        for (DriveRoadDTO waypoint : waypoints) {
            Point perpendicularPoint = calculatePerpendicularPoint(lineEquation, 
            		Double.parseDouble(waypoint.getWpX()), Double.parseDouble(waypoint.getWpY()));
            double distance = calculateDistance(startX, startY, perpendicularPoint.getX(), perpendicularPoint.getY());

            if (distance < minDistance) {
                minDistance = distance;
                nearestWaypoint = waypoint;
            }
        }

        return nearestWaypoint;
    }
}
