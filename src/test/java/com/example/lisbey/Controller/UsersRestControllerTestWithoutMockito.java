package com.example.lisbey.Controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.example.lisbey.Entity.UserEntity;
import com.example.lisbey.Service.UsersEntityServiceTest;

class UsersRestControllerTestWithoutMockito {

	static UsersRestController controller;

	static UsersEntityServiceTest usersEntityServiceTest;

	@BeforeAll
	static void init() {
		usersEntityServiceTest = new UsersEntityServiceTest();
		controller = new UsersRestController(usersEntityServiceTest);
	}

	@Nested
	@DisplayName("Without Mockito")
	class WithoutMockito {

		@Test
		void saveUserAndReturnStatusCode200() {

			UserEntity userToSave = UserEntity.builder().id((long) 1).name("Lisbey").lastName("Urrea").age("35")
					.build();

			HttpStatus status = controller.saveUser(userToSave).getStatusCode();

			assertEquals(HttpStatus.OK, status);

		}

		@Test
		void findAllUsersReturnStatusCode200() {

			HttpStatus status = controller.findAll().getStatusCode();
			assertAll(() -> assertEquals(HttpStatus.OK, status));

		}

		@Test
		void findUsersByIdReturnStatusCode200() {
			HttpStatus status = controller.findUserById((long) 1).getStatusCode();
			assertAll(() -> assertEquals(HttpStatus.OK, status));

		}
		
	

	}

}
