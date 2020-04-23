package org.levelup.application.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;
import org.levelup.application.domain.PositionEntity;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;

class PositionDaoImplTest {



    private SessionFactory factory;
    private Session session;
    private Transaction transaction;
    private PositionDao dao;

    
    @BeforeAll
    public static void beforeAll(){

        System.out.println("Before all");
    }
    
    @BeforeEach
    public void initialize() {
        factory = mock(SessionFactory.class);
        session = mock(Session.class);
        transaction = mock(Transaction.class);
        Mockito.when(factory.openSession()).thenReturn(session);
        Mockito.when(session.beginTransaction()).thenReturn(transaction);
        dao = new PositionDaoImpl(factory);
    }

    @Test
    public void testCreatePosition_whenValidParams_persistNewPosition() {
        String name = "position name";
        PositionEntity positionEntity = dao.createPosition(name);
        Assertions.assertEquals(name, positionEntity.getName());

        Mockito.verify(transaction, Mockito.times(1)).commit();
        Mockito.verify(session).close();

    }

    @AfterEach
    public void removeDao() {
        dao = null;
    }

    @AfterAll
    public static void afterAll(){

        System.out.println("After all");
    }
    
    }

