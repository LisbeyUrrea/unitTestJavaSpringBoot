package com.example.lisbey.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.lisbey.Dao.UsersEntityDao;
import com.example.lisbey.Entity.UserEntity;
import com.example.lisbey.Service.UsersEntityService;

@Service
public class UserEntityServiceImpl implements UsersEntityService {
	
	@Autowired
	private UsersEntityDao usersDao;

	@Override
	public List<UserEntity> findAll() {
		return (List<UserEntity>) usersDao.findAll();
	}

	@Override
	public UserEntity findById(Long id) {
		return usersDao.findById(id).orElse(null);
	}

	@Override
	public UserEntity save(UserEntity user) {
		return usersDao.save(user);
		
	}

	@Override
	public void delete(Long id) {
		usersDao.deleteById(id);
		
	}

}
