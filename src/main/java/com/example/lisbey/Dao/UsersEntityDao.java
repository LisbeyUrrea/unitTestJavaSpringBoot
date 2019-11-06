package com.example.lisbey.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.lisbey.Entity.UserEntity;

public interface UsersEntityDao  extends JpaRepository<UserEntity, Long>{

}
