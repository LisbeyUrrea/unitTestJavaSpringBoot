package com.example.lisbey.Service;

import java.util.List;

import com.example.lisbey.Entity.UserEntity;
import com.example.lisbey.exception.NotFoundException;

public interface UsersEntityService {
	
	List<UserEntity> findAll();
	
	UserEntity findById(Long id) throws NotFoundException;
	
	UserEntity save (UserEntity user);
	
	void delete (Long id);
	

}
