package com.example.lisbey.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

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

import com.example.lisbey.dto.UserEntityDto;
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
		response.put("message", "Todos los usuarios del sistema.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> findUserById(@PathVariable Long id)  {
		Map<String, Object> response = new HashMap<>();

			UserEntity userFind = findUserByIdAndReturnTheUser(id);
			
			response.put("data", userFind);
		response.put("message", "Usuario con el id "+id);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

	public UserEntity findUserByIdAndReturnTheUser(Long id) {

		try {

			return usersService.findById(id);

		} catch (NotFoundException e) {
			throw new NotFoundException("El usuario con el id "+id+" no existe en la base de datos");

		}
	}

	@Transactional
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> saveUser(@RequestBody UserEntityDto user) {
		Map<String, Object> response = new HashMap<>();

		ModelMapper modelMapper = new ModelMapper();
		UserEntity userToSaved = modelMapper.map(user,UserEntity.class );

		UserEntity userSaved = saveOrUpdateUserOnDatabase(userToSaved);

		response.put("data", userSaved);
		response.put("message", "Usuario almacenado exitosamente.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

	public UserEntity saveOrUpdateUserOnDatabase(UserEntity user) throws ValidationException {
		return usersService.save(user);
	}

	@Transactional
	@PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody UserEntityDto user) throws NotFoundException {
		Map<String, Object> response = new HashMap<>();

		UserEntity userFind = null;
		
		userFind = findUserByIdAndReturnTheUser(id);
		
		userFind.setName(user.getName());
		userFind.setLastName(user.getLastName());
		userFind.setAge(user.getAge());

		UserEntity userSaved = saveOrUpdateUserOnDatabase(userFind);

		response.put("data", userSaved);
		response.put("message", "Usuario actualizado exitosamente.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			usersService.delete(id);
			response.put("data", null);
			response.put("message", "Usuario eliminado exitosamente.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			
		} catch (NotFoundException e) {
			throw new NotFoundException("El usuario con el id "+ id +" No existe en la base de datos");
		}
		
		
	}

}

