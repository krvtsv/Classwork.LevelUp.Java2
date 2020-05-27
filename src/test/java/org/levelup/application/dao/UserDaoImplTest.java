package org.levelup.application.dao;

import org.h2.engine.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.levelup.application.domain.PositionEntity;
import org.levelup.application.domain.UserEntity;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class UserDaoImplTest {

    @Mock
    private SessionFactory factory;
    @Mock
    private Session session;
    @Mock
    private Transaction transaction;
    @Mock
    private Query query;
    @InjectMocks
    private UserDaoImpl dao;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(factory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.createQuery(anyString(), any())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
    }

    @Test
    void testCreateUser_whenValidParams_thenPersistNewUser() {
        String name = "position name";

//       UserEntity userEntity = dao.createUser (name);
//        Assertions.assertEquals (name, positionEntity.getName ());
//        verify (transaction, times (1)).commit ();
//        verify (session).close ();
//        verify(session).persist(positionEntity);
//    }
    }
    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }
}