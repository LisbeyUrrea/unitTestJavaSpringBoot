package com.example.lisbey.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import dto.UserEntityDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lisbey.entity.UserEntity;
import com.example.lisbey.service.UsersEntityService;
import com.example.lisbey.exception.NotFoundException;
import com.example.lisbey.exception.ValidationException;

@RestController
@RequestMapping("/users")
public class UsersRestController {

	@Autowired
	private UsersEntityService usersService;

	public UsersRestController(UsersEntityService usersService) {
		this.usersService = usersService;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> findAll() {

		Map<String, Object> response = new HashMap<>();

		List<UserEntity> userList = usersService.findAll();

		response.put("data", userList);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserEntity> findUserById(@PathVariable Long id) throws NotFoundException {

		UserEntity userFind;
		userFind = findUserByIdAndReturnTheUser(id);

		return new ResponseEntity<>(userFind, HttpStatus.OK);

	}

	public UserEntity findUserByIdAndReturnTheUser(Long id) {

		try {

			return usersService.findById(id);

		} catch (NotFoundException e) {
			throw new NotFoundException("Usuario no encontrado");

		}
	}

	@Transactional
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserEntity> saveUser(@RequestBody UserEntityDto user) {

		ModelMapper modelMapper = new ModelMapper();
		UserEntity userToSaved = modelMapper.map(user,UserEntity.class );

		UserEntity userSaved = saveOrUpdateUserOnDatabase(userToSaved);

		return new ResponseEntity<>(userSaved, HttpStatus.OK);

	}

	public UserEntity saveOrUpdateUserOnDatabase(UserEntity user) throws ValidationException {
		return usersService.save(user);
	}

	@Transactional
	@PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @RequestBody UserEntityDto user) throws NotFoundException {
		UserEntity userFind = null;
		
		userFind = findUserByIdAndReturnTheUser(id);
		
		userFind.setName(user.getName());
		userFind.setLastName(user.getLastName());
		userFind.setAge(user.getAge());

		UserEntity userSaved = saveOrUpdateUserOnDatabase(userFind);

		return new ResponseEntity<>(userSaved, HttpStatus.OK);
	}

	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteUser(@PathVariable Long id) {

		try {
			usersService.delete(id);
			return new ResponseEntity<>("Usuario eliminado exitosamente.", HttpStatus.OK);
			
		} catch (NotFoundException e) {
			throw new NotFoundException("El usuario con el id "+ id +" No existe en la base de datos");
		}
		
		
	}

}
