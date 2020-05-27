package org.levelup.application.dao;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.levelup.application.domain.CompanyEntity;
import org.levelup.application.domain.UserEntity;
import org.levelup.configuration.HibernateTestConfiguration;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplIntegrationTest {

    private static SessionFactory factory;
    private static UserDao userDao;

    @BeforeAll
    public static void setUp() {
        factory = HibernateTestConfiguration.getFactory();
        userDao = new UserDaoImpl(factory);
    }



    @Test
    void testCreateUser_whenUserDoesNotExistAndValidParams_thenPersistUser() {
        Integer id = 100;
        String name = "Oleg";
        String lastName = "Olegov";
        String passport = "1111 111111";
        Collection<String> addresses = new ArrayList<>(Arrays.asList("Sadovaya 20", "Lesnaya 34", "Novaya 7"));
        UserEntity user = userDao.createUser(id, name, lastName, passport, addresses);
        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(lastName, user.getLastName());
        assertEquals(passport, user.getPassport());

        //extract addresses from UserAddressEntities
        ArrayList<String> collectAddresses = user.getAddresses()
                .stream()
                .map(userAddressEntity -> userAddressEntity.getAddress())
                .collect(Collectors.toCollection(ArrayList::new));

        assertEquals(addresses,collectAddresses);
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }
}