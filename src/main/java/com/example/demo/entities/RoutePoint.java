package com.example.demo.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "route_points")
public class RoutePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_route")
    private Long routeId;

    @Column(name = "x")
    private Integer x;

    @Column(name = "y")
    private Integer y;

    @Column(name = "dir")
    private String direction;

    @ManyToOne
    @JoinColumn(name = "id_cmd")
    private Command cmd;

    public RoutePoint() {
    }

    public RoutePoint(Long routeId, Integer x, Integer y, String direction) {
        this.routeId = routeId;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public RoutePoint(Integer x, Integer y, String direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public RoutePoint(Long routeId, Integer x, Integer y, String direction, Command cmd) {
        this.routeId = routeId;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.cmd = cmd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Command getCmd() {
        return cmd;
    }

    public void setCmd(Command cmd) {
        this.cmd = cmd;
    }

}
