package com.example.lisbey.Service;

import java.util.ArrayList;
import java.util.List;


import com.example.lisbey.Entity.UserEntity;

public class UsersEntityServiceTest implements UsersEntityService{
	
	List<UserEntity> usersFake = new ArrayList<>();

	@Override
	public List<UserEntity> findAll() {
		return usersFake;
	}

	@Override
	public UserEntity findById(Long id) {
		UserEntity user = usersFake.stream()
				.filter(us -> us.getId().equals(id))
				.findAny()
				.orElse(null);
				
		return user;
	}

	@Override
	public UserEntity save(UserEntity user) {
		usersFake.add(user);
		return  user;
		
	}

	@Override
	public void delete(Long id) {
		usersFake.remove(Integer.parseInt(id.toString()));
	}


}
