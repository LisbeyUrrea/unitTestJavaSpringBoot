package com.example.lisbey.service.impl;

import com.example.lisbey.entity.UserEntity;
import com.example.lisbey.service.UsersEntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
class UserEntityServiceImplTest {

    //@Spy
    //static UsersEntityService UserEntityServiceImpl;

    static UsersEntityService UserEntityServiceImpl =  mock(UserEntityServiceImpl.class);
    static UsersEntityService UserEntityServiceImpl2 =  mock(UsersEntityService.class);

    private UserEntity userToSave;

    @BeforeEach
    void setUp() {
        userToSave = UserEntity.builder().id((long) 1).name("Lisbey").lastName("Urrea").age("35")
                .build();
    }


    @Test
    void save() {
        when(UserEntityServiceImpl2.save(userToSave)).thenReturn(userToSave);

        UserEntity userSaved = UserEntityServiceImpl2.save(userToSave);
        assertAll(
                () -> assertNotNull(userSaved),
                () -> assertEquals(userToSave, userSaved)
        );
        verify(UserEntityServiceImpl2).save(userToSave);
    }

    @Test
    void findAll() {

        List<UserEntity> usersListSended = new ArrayList<>();

        when(UserEntityServiceImpl.findAll()).thenReturn(usersListSended);

        List<UserEntity> listUserReurned = UserEntityServiceImpl.findAll();
        assertAll(
                () -> assertNotNull(listUserReurned),
                () -> assertEquals(usersListSended, listUserReurned)
        );
        verify(UserEntityServiceImpl).findAll();
    }

    @Test
    void findById() {

        when(UserEntityServiceImpl.findById((long) 1)).thenReturn(userToSave);

        UserEntity userReurned = UserEntityServiceImpl.findById((long) 1);
        assertAll(
                () -> assertNotNull(userReurned),
                () -> assertEquals(userToSave, userReurned)
        );
        verify(UserEntityServiceImpl).findById((long) 1);
    }

}