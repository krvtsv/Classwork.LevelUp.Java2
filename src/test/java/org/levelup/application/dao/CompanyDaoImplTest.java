package org.levelup.application.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.levelup.application.domain.CompanyEntity;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CompanyDaoImplTest {

    @Mock
    private SessionFactory factory;
    @Mock
    private Session session;
    @Mock
    private Transaction transaction;
    @Mock
    private Query query;
    @InjectMocks
    private CompanyDaoImpl dao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(factory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.createQuery(anyString(), any())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
    }

    @Test
    void testCreateCompany_whenValidParams_thenCreateCompany() {
        String name = "TestCompany";
        String ein = "000000000";
        String address = "Test address";
        CompanyEntity entity = dao.createCompany(name, ein, address);
        assertEquals(name, entity.getName());
        assertEquals(ein, entity.getEin());
        assertEquals(address, entity.getAddress());
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    void testFindById_whenValidParams_thenFindCompany() {
        Integer id = 2;
        dao.findById(id);
        verify(session).get(eq(CompanyEntity.class), any(Serializable.class));
        verify(session).close();
    }

    @Test
    void testFindByEin_whenValidParams_thenFindCompany() {
        String ein = "000000000";
        dao.findByEin(ein);
        verify(query).getSingleResult();
        verify(query).setParameter(anyString(), any());
        verify(session).close();
    }

    @Test
    void testFindByName_whenValidParams_thenFindCompany() {
        String name = "TestName";
        dao.findByName(name);
        verify(query).getResultList();
        verify(query).setParameter(anyString(), any());
        verify(session).close();
    }

    @Test
    void testFindAll_whenValidParams_thenFindCompanies() {
        dao.findAll();
        verify(query).getResultList();
        verify(session).close();
    }
}