package com.example.demo.services;

import com.example.demo.entities.RoutePoint;
import com.example.demo.util.Point;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RobotServiceConverter {
    private static final String COORD_FORMAT = "{%d,%d}";
    private static final String POSITION_FIELD = "position";
    private static final String ROUTE_FIELD = "route";
    private static final String CIRCULAR_FIELD = "circular";
    private static final String COORD_ARRAY_FORMAT = "{%s}";
    public static final String DELIMITER = ",";

    public static Map<String, String> getRobotPositionResponseFormat(Point routePoint) {
        return Map.of(POSITION_FIELD, String.format(COORD_FORMAT, routePoint.getX(), routePoint.getY()));
    }

    public static Map<String, String> getRobotRouteResponseFormat(List<RoutePoint> routePoints, boolean circular) {
        String routeString = routePoints.stream()
                .map(routePoint -> String.format(COORD_FORMAT,routePoint.getX(), routePoint.getY()))
                .collect(Collectors.joining(DELIMITER));

        return Map.of(ROUTE_FIELD,String.format(COORD_ARRAY_FORMAT,routeString),
                CIRCULAR_FIELD,Boolean.toString(circular));
    }

    private RobotServiceConverter() {
    }
}
