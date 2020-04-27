package org.levelup.application.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.levelup.application.domain.CompanyLegalDetailsEntity;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CompanyLegalDetailsDaoImplTest {

    @Mock
    private SessionFactory factory;
    @Mock
    private Session session;
    @Mock
    private Transaction transaction;
    @Mock
    private Query query;
    @InjectMocks
    private CompanyLegalDetailsDaoImpl dao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(factory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.createQuery(anyString(), any())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
    }

    @AfterEach
    void tearDown() {
        dao = null;
    }

    @Test
    void testUpdateLegalDetailInCompany() {
        Integer companyId = 3;
        String bankName = "Test bank name";
        String bic = "2525252";
        dao.updateLegalDetailInCompany(companyId,bankName,bic);
        verify(session).persist(new CompanyLegalDetailsEntity(companyId, bankName, bic));
        verify(transaction).commit();
        verify(session).close();

    }

    @Test
    void testFindAllByBankName() {
        String bankName = "Test bank name";
        dao.findAllByBankName(bankName);
        verify(query).getResultList();
        verify(query).setParameter(anyString(), eq("Test bank name"));
        verify(session).close();
    }
}