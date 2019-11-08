package com.example.lisbey.Controller;

import com.example.lisbey.Entity.UserEntity;
import com.example.lisbey.Service.UsersEntityService;
import com.example.lisbey.exception.NotFoundException;
import com.example.lisbey.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
class UsersRestControllerTest {

    static UsersRestController controller;

    //@Mock
    //static UsersEntityService UsersEntityServiceTestMock;
    static UsersEntityService UsersEntityServiceTestMock = mock(UsersEntityService.class);

    @BeforeEach
    void setUp() {
        controller = new UsersRestController(UsersEntityServiceTestMock);
    }

    @Nested
    @DisplayName("Save User")
    class SaveUser {

        @Test
        void shouldSaveTheUserAndReturnStatusCode200() {

            UserEntity userToSave = UserEntity.builder().id((long) 1).name("Lisbey").lastName("Urrea").age("35")
                    .build();

            HttpStatus status = controller.saveUser(userToSave).getStatusCode();

            assertEquals(HttpStatus.OK, status);

            verify(UsersEntityServiceTestMock).save(userToSave);

        }

        @Test
        void shouldSaveUserAndReturnUserEntityObject() {

            UserEntity userToSave = UserEntity.builder().name("Lisbey").lastName("Urrea").age("35").build();

            when(controller.saveOrUpdateUserOnDatabase(userToSave)).thenReturn(userToSave);

            UserEntity userSaved = controller.saveOrUpdateUserOnDatabase(userToSave);

            assertAll(() -> assertNotNull(userSaved), () -> assertEquals(userToSave, userSaved));

            verify(UsersEntityServiceTestMock).save(userToSave);

        }

        @Test
        void shouldReturnThrowsValidationExceptionOnSaveUserWithoutAllFills() {

            UserEntity userToSave = UserEntity.builder().name("Lisbey").age("35").build();

            when(controller.saveOrUpdateUserOnDatabase(userToSave))
                    .thenThrow(new ValidationException("INVALID_USER_FIELDS"));

            assertAll(() -> assertThrows(ValidationException.class,
                    () -> controller.saveOrUpdateUserOnDatabase(userToSave)));

        }
    }

    @Nested
    @DisplayName("Update User")
    class UpdateUser {

        @Test
        void shouldUpdateUserAndReturnStatusCode200() throws NotFoundException, javassist.NotFoundException {

            UserEntity userToUpdate = UserEntity.builder().id((long) 2).name("Lisbey").lastName("Urrea").age("35")
                    .build();

            when(UsersEntityServiceTestMock.findById((long) 2)).thenReturn(userToUpdate);

            HttpStatus status = controller.updateUser((long) 2, userToUpdate).getStatusCode();

            assertAll(() -> assertEquals(HttpStatus.OK, status));

            verify(UsersEntityServiceTestMock).findById((long) 2);
            verify(UsersEntityServiceTestMock).save(userToUpdate);


        }

        @Test
        void shouldReturnNotFoundExceptionOnUpdateUser() {

            UserEntity userToUpdate = UserEntity.builder().name("Lisbey").lastName("Urrea").age("35").build();


            when(controller.updateUser((long) 2, userToUpdate)).thenThrow(new NotFoundException("Usuario no encontrado"));

            assertThrows(NotFoundException.class, () -> controller.updateUser((long) 2, userToUpdate));

        }

    }

    @Nested
    @DisplayName("Find User ")
    class FindUser {

        @Test
        void shouldFindAllUsersReturnStatusCode200() {

            HttpStatus status = controller.findAll().getStatusCode();
            assertAll(() -> assertEquals(HttpStatus.OK, status));

            verify(UsersEntityServiceTestMock).findAll();

        }

        @Test
        void shouldFindUsersByIdAndReturnStatusCode200() throws NotFoundException {

            UserEntity userToUpdate = UserEntity.builder().id((long) 3).name("Lisbey").lastName("Urrea").age("35").build();
            when(controller.saveOrUpdateUserOnDatabase(userToUpdate)).thenReturn(userToUpdate);


            HttpStatus status = controller.findUserById((long) 3).getStatusCode();
            assertAll(() -> assertEquals(HttpStatus.OK, status));

            verify(UsersEntityServiceTestMock).findById((long) 3);

        }

        @Test
        void shouldFindUserByIdThenReturnTheUser() throws NotFoundException {

            UserEntity userToSave = UserEntity.builder().id((long) 5).name("Lisbey").lastName("Urrea").age("35")
                    .build();

            when(controller.findUserByIdAndReturnTheUser((long) 5)).thenReturn(userToSave);

            UserEntity userSaved = controller.findUserByIdAndReturnTheUser((long) 5);

            assertAll(() -> assertNotNull(userSaved), () -> assertSame(userToSave, userSaved),
                    () -> assertEquals(userToSave, userSaved));

            verify(UsersEntityServiceTestMock).findById((long) 5);

        }

        @Test
        void shouldReturnNotFoundExceptionOnFindById() {

            when(controller.findUserByIdAndReturnTheUser((long) 1))
                    .thenThrow(new NotFoundException("Usuario no encontrado"));

            assertThrows(NotFoundException.class, () -> controller.findUserByIdAndReturnTheUser((long) 1));

        }
    }

    @Nested
    @DisplayName("Delete User")
    class DeleteUser {


        @Test
        void shouldReturnNotFoundExceptionOnFindById() {

            when(controller.deleteUser((long) 4))
                    .thenThrow(new NotFoundException("Usuario no encontrado"));

            assertThrows(NotFoundException.class, () -> controller.deleteUser((long) 4));

        }

        @Test
        void shouldDeleteUsersByIdAndReturnStatusCode200() {

            HttpStatus status = controller.deleteUser((long) 5).getStatusCode();

            assertEquals(HttpStatus.OK, status);

            verify(UsersEntityServiceTestMock).delete((long) 5);

        }


    }

}
