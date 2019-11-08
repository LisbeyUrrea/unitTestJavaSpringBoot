package com.example.lisbey.Service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.lisbey.Dao.UsersEntityDao;
import com.example.lisbey.Entity.UserEntity;
import com.example.lisbey.Service.UsersEntityService;
import com.example.lisbey.exception.NotFoundException;



@Service
public class UserEntityServiceImpl implements UsersEntityService {

	@Autowired
	private UsersEntityDao usersDao;

	@Override
	public List<UserEntity> findAll() {
		return (List<UserEntity>) usersDao.findAll();
	}

	@Override
	public UserEntity findById(Long id) throws NotFoundException {
		Optional<UserEntity> userFound = null;

		userFound = usersDao.findById(id);

		return userFound.get();
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
