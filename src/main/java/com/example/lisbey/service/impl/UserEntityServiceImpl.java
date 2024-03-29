package com.example.lisbey.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.lisbey.dao.UsersEntityDao;
import com.example.lisbey.entity.UserEntity;
import com.example.lisbey.exception.NotFoundException;
import com.example.lisbey.service.UsersEntityService;

@Service
public class UserEntityServiceImpl implements UsersEntityService {

	@Autowired
	private UsersEntityDao usersDao;

	@Override
	public List<UserEntity> findAll() {
		return usersDao.findAll();
	}

	@Override
	public UserEntity findById(Long id) throws NotFoundException {
		UserEntity user= null;
		user = usersDao.findById(id).orElse(null);
		if(user != null) {
			return user;
		}else {
			throw new NotFoundException("");
		}
			

	}

	@Override
	public UserEntity save(UserEntity user)  {
		return usersDao.save(user);

	}

	@Override
	public void delete(Long id){
		usersDao.deleteById(id);

	}

}
