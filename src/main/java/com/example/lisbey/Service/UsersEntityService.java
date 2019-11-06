package com.example.lisbey.Service;

import java.util.List;

import com.example.lisbey.Entity.UserEntity;

public interface UsersEntityService {
	
	List<UserEntity> findAll();
	
	UserEntity findById(Long id);
	
	UserEntity save (UserEntity user);
	
	void delete (Long id);
	

}
