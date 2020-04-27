package org.levelup.application.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;
import org.levelup.application.domain.CompanyEntity;
import org.levelup.application.domain.PositionEntity;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.Serializable;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

class PositionDaoImplTest {


    @Mock
    private SessionFactory factory;
    @Mock
    private Session session;
    @Mock
    private Transaction transaction;
    @Mock
    private Query query;
    @InjectMocks
    private PositionDaoImpl dao;


    @BeforeAll
    public static void beforeAll () {

        System.out.println ("Before all");
    }

    @BeforeEach
    public void initialize () {

        MockitoAnnotations.initMocks (this);
//        factory = mock (SessionFactory.class); -----> instead of this we used @Mock annotation
//        session = mock (Session.class);
//        transaction = mock (Transaction.class);
        when (factory.openSession ()).thenReturn (session);
        when (session.beginTransaction ()).thenReturn (transaction);
//        dao = new PositionDaoImpl (factory);  -----> instead of this we used @InjectMocks annotation
        when(session.createQuery(anyString())).thenReturn(query);
        when(session.createQuery(anyString(), any())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
    }

    @AfterEach
    public void removeDao () {
        dao = null;
    }

    @AfterAll
    public static void afterAll () {

        System.out.println ("After all");
    }

    @Test
    public void testCreatePosition_whenValidParams_thenPersistNewPosition () {
        String name = "position name";
        PositionEntity positionEntity = dao.createPosition (name);
        Assertions.assertEquals (name, positionEntity.getName ());
        verify (transaction, times (1)).commit ();
        verify (session).close ();
        verify(session).persist(positionEntity);
    }

    @Test
    void testFindAll_whenValidParams_thenFindPositions() {
        dao.findAll();
        verify(query).getResultList();
        verify(session).close();
    }

    @Test
    void testDeletePositionById_whenValidParams_thenDeletePosition() {
        Integer id = 2;
        dao.deletePositionById(id);
        verify(query).setParameter(anyString(), eq(2));
        verify(session).createQuery(anyString());
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    void testDeletePositionByName_whenValidParams_thenDeletePosition() {
        String name = "TestName";
        dao.deletePositionByName(name);
        verify(query).setParameter(anyString(), eq("TestName"));
        verify(session).createQuery(anyString());
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    void testFindAllPositionWhichNameLike_whenValidParams_thenFindPositions() {
        String name = "TestName";
        dao.findAllPositionWhichNameLike(name);
        verify(query).getResultList();
        verify(query).setParameter(anyString(), eq("TestName"));
        verify(session).createQuery(anyString(), any());
        verify(session).close();
    }

    @Test
    void testFindPositionById_whenValidParams_thenFindPosition() {
        Integer id = 2;
        dao.findPositionById(id);
        verify(session).get(eq(PositionEntity.class), any(Serializable.class));
        verify(session).close();
    }

    @Test
    void testFindPositionByName_whenValidParams_thenFindPosition() {
        String name = "TestName";
        dao.findPositionByName(name);
        verify(query).getSingleResult();
        verify(query).setParameter(anyString(), any());
        verify(session).createQuery(anyString(), any());
        verify(session).close();
    }
}

