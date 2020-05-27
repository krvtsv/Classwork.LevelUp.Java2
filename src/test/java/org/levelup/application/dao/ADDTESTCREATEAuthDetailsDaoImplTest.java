package org.levelup.application.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.levelup.application.domain.AuthDetailsEntity;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;


import javax.persistence.PersistenceException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ADDTESTCREATEAuthDetailsDaoImplTest {

    @Mock
    private SessionFactory factory;
    @Mock
    private Session session;
    @Mock
    private Transaction transaction;
    @Mock
    private Query query;
    @InjectMocks
    private AuthDetailsDaoImpl dao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(factory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.createQuery(anyString(), any())).thenReturn(query);
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
    }

    @Test
    void testFindByPasswordAndLogin_whenValidParams_thenExecuteQuery() {
        String login = "login";
        String password = "password";
        when(query.getSingleResult()).thenReturn(new AuthDetailsEntity(login, password));
        AuthDetailsEntity entity = dao.findByPasswordAndLogin(login, password);
        assertEquals(login, entity.getLogin());
        assertEquals(password, entity.getPassword());
        verify(session).close();
        verify(query).getSingleResult();
        verify(query).setParameter("login", login);
        verify(query, times(2)).setParameter(anyString(), any());

    }

    @Test
    void testUpdatePasswordByLogin_whenValidParams_thenExecuteQuery() {
        String login = "login";
        String password = "password";
        dao.updatePasswordByLogin(login, password);
        verify(session).close();
        verify(transaction).commit();
        verify(query, times(2)).setParameter(anyString(), any());
        verify(query).executeUpdate();
    }

    @Test
    void testUpdatePasswordByLogin_whenPasswordIsNull_thenThrowException() {
        String login = "login";
        String password = null;
        when(query.setParameter(anyString(), eq(password))).thenThrow(PersistenceException.class);
        assertThrows(PersistenceException.class, () -> dao.updatePasswordByLogin(login, password));
        verify(session).close();
        verify(query, times(2)).setParameter(anyString(), any());
    }

    @Test
    void testUpdatePasswordByLogin_whenLoginIsNull_thenThrowException() {
        String login = null;
        String password = "password";
        when(query.setParameter(anyString(), eq(login))).thenThrow(PersistenceException.class);
        assertThrows(PersistenceException.class, () -> dao.updatePasswordByLogin(login, password));
        verify(session).close();
        verify(query, times(1)).setParameter(anyString(), any());// method invokes only for login
    }

}