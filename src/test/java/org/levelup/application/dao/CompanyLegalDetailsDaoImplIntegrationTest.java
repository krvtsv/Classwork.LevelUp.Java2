package org.levelup.application.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.levelup.application.domain.AuthDetailsEntity;
import org.levelup.application.domain.CompanyEntity;
import org.levelup.application.domain.CompanyLegalDetailsEntity;
import org.levelup.application.domain.UserEntity;
import org.levelup.configuration.HibernateTestConfiguration;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CompanyLegalDetailsDaoImplIntegrationTest {

    private static SessionFactory factory;
    private static CompanyLegalDetailsDao companyLegalDetailsDao;

    @BeforeAll
    public static void setUp() {
        factory = HibernateTestConfiguration.getFactory();
        companyLegalDetailsDao = new CompanyLegalDetailsDaoImpl(factory);
    }


    @Test
    void testUpdateLegalDetailInCompany_whenCompanyDoesNotExist_thenCreateRecordWithNullCompany() {

        Integer companyId = 110;
        String bankName = "Bank name";
        String BIC = "111111111";
        companyLegalDetailsDao.updateLegalDetailInCompany(companyId, bankName, BIC);

        Session session = factory.openSession();
        CompanyLegalDetailsEntity companyLegalDetailsEntity = session.get(CompanyLegalDetailsEntity.class, companyId);
        session.close();

        assertEquals(companyLegalDetailsEntity.getCompanyId(), companyId);
        assertEquals(companyLegalDetailsEntity.getBankName(), bankName);
        assertEquals(companyLegalDetailsEntity.getBIC(), BIC);
        assertNull(companyLegalDetailsEntity.getCompany());
    }

    @Test
    void testUpdateLegalDetailInCompany_whenCompanyExists_thenCreateRecord() {
        //creating a company
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        CompanyEntity companyEntity = new CompanyEntity("Name", "EIN", "Address");
        session.persist(companyEntity);
        transaction.commit();

        //updating legal details
        Integer companyId = companyEntity.getId();
        String bankName = "Bank name 2";
        String BIC = "222222222";
        companyLegalDetailsDao.updateLegalDetailInCompany(companyId, bankName, BIC);
        CompanyLegalDetailsEntity companyLegalDetailsEntity = session.get(CompanyLegalDetailsEntity.class, companyId);
        session.close();

        assertEquals(companyEntity.getId(), companyLegalDetailsEntity.getCompanyId());
        assertEquals(companyLegalDetailsEntity.getBankName(), bankName);
        assertEquals(companyLegalDetailsEntity.getBIC(), BIC);

        //checking that company and legal details are correlated correctly
        assertEquals(companyEntity, companyLegalDetailsEntity.getCompany());
    }

    @Test
    void testUpdateLegalDetailInCompany_whenCompanyAndDetailsAlreadyExist_thenThrowException() {
        //creating a company
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        CompanyEntity companyEntity = new CompanyEntity("Name2", "EIN2", "Address2");
        session.persist(companyEntity);
        transaction.commit();
        session.close();

        //first updating legal details
        Integer companyId = companyEntity.getId();
        String bankName = "Bank name 3";
        String BIC = "333333333";
        companyLegalDetailsDao.updateLegalDetailInCompany(companyId, bankName, BIC);

        //second updating legal details
        String updatedBankName = "Bank name updated";
        String updatedBIC = "000000000";
        assertThrows(PersistenceException.class, () -> companyLegalDetailsDao.updateLegalDetailInCompany(companyId, updatedBankName, updatedBIC));

    }

    @Test
    void testUpdateLegalDetailInCompany_whenBankNameIsNull_thenThrowException() {

        Integer companyId = 111;
        String bankName = null;
        String BIC = "444444444";
        assertThrows(PersistenceException.class, () -> companyLegalDetailsDao.updateLegalDetailInCompany(companyId, bankName, BIC));

    }

    @Test
    void testUpdateLegalDetailInCompany_whenBICIsNull_thenThrowException() {

        Integer companyId = 112;
        String bankName = "Bank name 4";
        String BIC = null;
        assertThrows(PersistenceException.class, () -> companyLegalDetailsDao.updateLegalDetailInCompany(companyId, bankName, BIC));

    }

    @Test
    void testUpdateLegalDetailInCompany_whenCompanyIdIsNull_thenThrowException() {

        Integer companyId = null;
        String bankName = "Bank name 5";
        String BIC = "555555555";
        assertThrows(PersistenceException.class, () -> companyLegalDetailsDao.updateLegalDetailInCompany(companyId, bankName, BIC));

    }

    @Test
    void testUpdateLegalDetailInCompany_whenBICExists_thenCreateRecordWithTheSameBIC() {

        Integer firstCompanyId = 113;
        String firstBankName = "Bank name 6";
        Integer secondCompanyId = 114;
        String secondBankName = "Bank name 7";
        String BIC = "666666666";
        companyLegalDetailsDao.updateLegalDetailInCompany(firstCompanyId, firstBankName, BIC);
        companyLegalDetailsDao.updateLegalDetailInCompany(secondCompanyId, secondBankName, BIC);
        Session session = factory.openSession();
        CompanyLegalDetailsEntity firstCompanyLegalDetails = session.get(CompanyLegalDetailsEntity.class, firstCompanyId);
        CompanyLegalDetailsEntity secondCompanyLegalDetails = session.get(CompanyLegalDetailsEntity.class, secondCompanyId);
        session.close();

        assertEquals(firstCompanyLegalDetails.getBIC(), secondCompanyLegalDetails.getBIC());
        assertEquals(BIC, firstCompanyLegalDetails.getBIC());
        assertEquals(firstCompanyId, firstCompanyLegalDetails.getCompanyId());
        assertEquals(secondCompanyId, secondCompanyLegalDetails.getCompanyId());
        assertEquals(firstBankName, firstCompanyLegalDetails.getBankName());
        assertEquals(secondBankName, secondCompanyLegalDetails.getBankName());
    }

    @Test
    void testUpdateLegalDetailInCompany_whenBankNameExists_thenCreateRecordWithTheSameBank() {

        Integer firstCompanyId = 115;
        String bankName = "Bank name 8";
        Integer secondCompanyId = 116;
        String secondBIC = "888888888";
        String firstBIC = "777777777";
        companyLegalDetailsDao.updateLegalDetailInCompany(firstCompanyId, bankName, firstBIC);
        companyLegalDetailsDao.updateLegalDetailInCompany(secondCompanyId, bankName, secondBIC);
        Session session = factory.openSession();
        CompanyLegalDetailsEntity firstCompanyLegalDetails = session.get(CompanyLegalDetailsEntity.class, firstCompanyId);
        CompanyLegalDetailsEntity secondCompanyLegalDetails = session.get(CompanyLegalDetailsEntity.class, secondCompanyId);
        session.close();

        assertEquals(firstCompanyLegalDetails.getBankName(), secondCompanyLegalDetails.getBankName());
        assertEquals(bankName, firstCompanyLegalDetails.getBankName());
        assertEquals(firstBIC, firstCompanyLegalDetails.getBIC());
        assertEquals(secondBIC, secondCompanyLegalDetails.getBIC());
        assertEquals(firstCompanyId, firstCompanyLegalDetails.getCompanyId());
        assertEquals(secondCompanyId, secondCompanyLegalDetails.getCompanyId());
    }

    @Test
    void testFindAllByBankName_whenBankNameDoesNotExist_thenReturnEmptyCollection() {
        String bankName = "Bank name 9";
        Collection<CompanyLegalDetailsEntity> allByBankName = companyLegalDetailsDao.findAllByBankName(bankName);
        assertNotNull(allByBankName);
        assertTrue(allByBankName.isEmpty());
    }

    @Test
    void testFindAllByBankName_whenBankNameExists_thenReturnCollectionWithBankName() {
        String bankName = "Bank name 10";
        String BIC = "101010101";

        //adding companies with the bank name
        companyLegalDetailsDao.updateLegalDetailInCompany(17, bankName, BIC);
        companyLegalDetailsDao.updateLegalDetailInCompany(18, bankName, BIC);
        companyLegalDetailsDao.updateLegalDetailInCompany(19, bankName, BIC);
        Collection<CompanyLegalDetailsEntity> allByBankName = companyLegalDetailsDao.findAllByBankName(bankName);
        assertNotNull(allByBankName);
        assertEquals(3, allByBankName.size());

        List<CompanyLegalDetailsEntity> filteredByBankName = allByBankName
                .stream()
                .filter(details -> details.getBankName().equals(bankName))
                .collect(Collectors.toList());

        //checking that after filtering by bank name the size doesn't change
        assertEquals(3, filteredByBankName.size());
    }
}