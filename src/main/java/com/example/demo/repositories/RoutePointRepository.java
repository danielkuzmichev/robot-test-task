package com.example.demo.repositories;

import com.example.demo.entities.RoutePoint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutePointRepository extends CrudRepository<RoutePoint,Integer> {
    RoutePoint findFirstByOrderByIdDesc();
}
