package org.levelup.application.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.levelup.application.domain.AuthDetailsEntity;
import org.levelup.application.domain.PositionEntity;
import org.levelup.configuration.HibernateTestConfiguration;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import static org.junit.jupiter.api.Assertions.*;

class AuthDetailsDaoImplIntegrationTest {

    private static SessionFactory factory;
    private static AuthDetailsDao authDetailsDao;

    @BeforeAll
    public static void setUp() {
        factory = HibernateTestConfiguration.getFactory();
        authDetailsDao = new AuthDetailsDaoImpl(factory);
    }


    @Test
    void testCreateAccount_whenAccountExists_thenThrowException() {
        String login = "login";
        String password = "password";
        authDetailsDao.createAccount(login, password);
        assertThrows(PersistenceException.class, () -> authDetailsDao.createAccount(login, password));
    }

    @Test
    void testCreateAccount_whenAccountDoesNotExistValidParams_thenCreateAccount() {
        String login = "existinglogin";
        String password = "existingpassword";
        AuthDetailsEntity entity = authDetailsDao.createAccount(login, password);

        assertEquals(login, entity.getLogin());
        assertEquals(password, entity.getPassword());
        assertNotNull(entity.getId());
    }

    @Test
    void testCreateAccount_whenLoginIsNull_thenThrowException() {
        String login = null;
        String password = "password";
        assertThrows(PersistenceException.class, () -> authDetailsDao.createAccount(login, password));
    }

    @Test
    void testCreateAccount_whenLoginIsTooLong_thenThrowException() {
        String login = "toolonglogintoolonglogintoolonglogin";
        String password = "password";
        assertThrows(PersistenceException.class, () -> authDetailsDao.createAccount(login, password));
    }

    @Test
    void testCreateAccount_whenPasswordIsNull_thenThrowException() {
        String login = "login";
        String password = null;
        assertThrows(PersistenceException.class, () -> authDetailsDao.createAccount(login, password));
    }

    @Test
    void testCreateAccount_whenPasswordIsTooLong_thenThrowException() {
        String login = "login";
        String password = "toolongpasswordtoolongpasswordtoolongpasswordtoolongpassword";
        assertThrows(PersistenceException.class, () -> authDetailsDao.createAccount(login, password));
    }

    @Test
    void testFindByPasswordAndLogin_whenAccountExists_thenFindAccount() {
        String login = "logintofind";
        String password = "passwordtofind";
        authDetailsDao.createAccount(login, password);

        AuthDetailsEntity entity = authDetailsDao.findByPasswordAndLogin(login, password);
        assertEquals(login, entity.getLogin());
        assertEquals(password, entity.getPassword());
        assertNotNull(entity.getId());
    }

    @Test
    void testFindByPasswordAndLogin_whenAccountDoesNotExist_thenThrowException() {
        String login = "notexistinglogin";
        String password = "notexistingpassword";
        assertThrows(NoResultException.class, () -> authDetailsDao.findByPasswordAndLogin(login, password));
    }

    @Test
    void testUpdatePasswordByLogin_whenAccountDoesNotExist_thenNoUpdateAndNoException() {
        String login = "notexistinglogin";
        String password = "notexistingpassword";
        authDetailsDao.updatePasswordByLogin(login, password);

        Session session = factory.openSession();
        assertThrows(NoResultException.class, () -> session.createQuery("from AuthDetailsEntity where login=:login and password=:password", AuthDetailsEntity.class)
                .setParameter("login", login).setParameter("password", password)
                .getSingleResult());
        session.close();

    }

    @Test
    void testUpdatePasswordByLogin_whenAccountExistsValidParams_thenChangePassword() {
        String login = "validlogin";
        String password = "validpassword";
        String newPassword = "newpassword";

        AuthDetailsEntity originalEntity = authDetailsDao.createAccount(login, password);
        authDetailsDao.updatePasswordByLogin(login, newPassword);
        AuthDetailsEntity changedEntity = authDetailsDao.findByPasswordAndLogin(login, newPassword);
        assertEquals(login, originalEntity.getLogin());
        assertEquals(password, originalEntity.getPassword());
        assertEquals(login, changedEntity.getLogin());
        assertEquals(newPassword, changedEntity.getPassword());
        assertEquals(originalEntity.getId(), changedEntity.getId());
    }


    @Test
    void testUpdatePasswordByLogin_whenNewPasswordIsTooLong_thenThrowException() {
        String login = "login1";
        String password = "password1";
        authDetailsDao.createAccount(login, password);
        String newPassword = "toolongpasswordtoolongpasswordtoolongpasswordtoolongpassword";
        assertThrows(PersistenceException.class, () -> authDetailsDao.updatePasswordByLogin(login, newPassword));

    }


    @Test
    void testUpdatePasswordByLogin_whenNewPasswordIsNull_thenThrowException() {
        String login = "login2";
        String password = "password2";
        authDetailsDao.createAccount(login, password);
        String newPassword = null;
        assertThrows(PersistenceException.class, () -> authDetailsDao.updatePasswordByLogin(login, newPassword));

    }
}