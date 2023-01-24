package com.example.demo.repositories;

import com.example.demo.entities.Command;
import org.springframework.data.repository.CrudRepository;

public interface CommandRepository extends CrudRepository <Command,Integer>{
}
