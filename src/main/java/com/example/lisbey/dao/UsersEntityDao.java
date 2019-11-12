package com.example.lisbey.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lisbey.entity.UserEntity;

public interface UsersEntityDao  extends JpaRepository<UserEntity, Long>{

}
