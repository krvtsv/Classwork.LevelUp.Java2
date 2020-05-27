package org.levelup.application.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;
import org.levelup.application.domain.CompanyEntity;
import org.levelup.application.domain.PositionEntity;
import org.levelup.configuration.HibernateTestConfiguration;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CompanyDaoImplIntegrationTest {

    private static SessionFactory factory;
    private static CompanyDao companyDao;

    @BeforeAll
    public static void setUp() {
        factory = HibernateTestConfiguration.getFactory();
        companyDao = new CompanyDaoImpl(factory);
    }

    @Test
    @Order(1)
    void testFindAll_whenCompaniesHaveNotBeenCreatedYet_thenReturnNull() {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from CompanyEntity").executeUpdate();
        transaction.commit();
        session.close();
        Collection<CompanyEntity> all = companyDao.findAll();
        assertNull(all);
    }

    @Test
    @Order(2)
    void testFindAll_whenSomeCompaniesHaveBeenCreated_thenReturnAllCompanies() {
        companyDao.createCompany("name-1", "ein-1", "address-1");
        companyDao.createCompany("name-2", "ein-2", "address-2");
        companyDao.createCompany("name-3", "ein-3", "address-3");

        Collection<CompanyEntity> all = companyDao.findAll();
        assertEquals(3, all.size());
    }

    @Test
    void testCreateCompany_whenEinExists_thenThrowException() {
        String firstCompanyName = "First Company";
        String ein = "1111111";
        String firstCompanyAddress = "First Address";
        String secondCompanyName = "Second Company";
        String secondCompanyAddress = "Second Address";
        companyDao.createCompany(firstCompanyName, ein, firstCompanyAddress);
        assertThrows(PersistenceException.class, () -> companyDao.createCompany(secondCompanyName, ein, secondCompanyAddress));
    }

    @Test
    void testCreateCompany_whenCompanyNameExists_thenCreateCompanyWithTheSameName() {
        String companyName = "The same name";
        String firstCompanyEin = "2222222";
        String firstCompanyAddress = "First_Address";
        String secondCompanyEin = "3333333";
        String secondCompanyAddress = "Second_Address";
        CompanyEntity company1 = companyDao.createCompany(companyName, firstCompanyEin, firstCompanyAddress);
        CompanyEntity company2 = companyDao.createCompany(companyName, secondCompanyEin, secondCompanyAddress);
        assertEquals(companyName, company1.getName());
        assertEquals(companyName, company2.getName());
        assertEquals(firstCompanyAddress, company1.getAddress());
        assertEquals(secondCompanyAddress, company2.getAddress());
        assertEquals(firstCompanyEin, company1.getEin());
        assertEquals(secondCompanyEin, company2.getEin());
        assertNotNull(company1.getId());
        assertNotNull(company2.getId());
    }

    @Test
    void testCreateCompany_whenCompanyAddressExists_thenCreateCompanyWithTheSameAddress() {
        String firstCompanyName = "First_Company";
        String secondCompanyName = "Second_Company";
        String firstCompanyEin = "4444444";
        String secondCompanyEin = "5555555";
        String companyAddress = "The same address";
        CompanyEntity company1 = companyDao.createCompany(firstCompanyName, firstCompanyEin, companyAddress);
        CompanyEntity company2 = companyDao.createCompany(secondCompanyName, secondCompanyEin, companyAddress);
        assertEquals(firstCompanyName, company1.getName());
        assertEquals(secondCompanyName, company2.getName());
        assertEquals(companyAddress, company1.getAddress());
        assertEquals(companyAddress, company2.getAddress());
        assertEquals(firstCompanyEin, company1.getEin());
        assertEquals(secondCompanyEin, company2.getEin());
        assertNotNull(company1.getId());
        assertNotNull(company2.getId());
    }

    @Test
    void testCreateCompany_whenCompanyDoesNotExistAndValidParams_thenCreateCompany() {
        String name = "Valid company name";
        String ein = "6666666";
        String address = "Valid address";
        CompanyEntity company = companyDao.createCompany(name, ein, address);
        assertEquals(name, company.getName());
        assertEquals(address, company.getAddress());
        assertEquals(ein, company.getEin());
        assertNotNull(company.getId());
    }


    @Test
    void testCreateCompany_whenNameIsNull_thenThrowException() {
        String name = null;
        String ein = "7777777";
        String address = "address";
        assertThrows(PersistenceException.class, () -> companyDao.createCompany(name, ein, address));
    }

    @Test
    void testCreateCompany_whenEinIsNull_thenThrowException() {
        String name = "Name";
        String ein = null;
        String address = "address";
        assertThrows(PersistenceException.class, () -> companyDao.createCompany(name, ein, address));
    }

    @Test
    void testCreateCompany_whenAddressIsNull_thenThrowException() {
        String name = "Name";
        String ein = "8888888";
        String address = null;
        assertThrows(PersistenceException.class, () -> companyDao.createCompany(name, ein, address));
    }

    @Test
    void testFindById_whenCompanyExists_thenFindCompany() {
        String name = "company name to find";
        String ein = "9999999";
        String address = "company address to find";
        CompanyEntity createdCompany = companyDao.createCompany(name, ein, address);
        CompanyEntity foundCompany = companyDao.findById(createdCompany.getId());
        assertEquals(name, foundCompany.getName());
        assertEquals(address, foundCompany.getAddress());
        assertEquals(ein, foundCompany.getEin());
        assertNotNull(foundCompany.getId());
        assertEquals(createdCompany.getId(), foundCompany.getId());
    }

    @Test
    void testFindById_whenIdDoesNotExist_thenFindNull() {
        int id = 123456789;
        CompanyEntity foundCompany = companyDao.findById(id);
        assertNull(foundCompany);
    }

    @Test
    void findByEin_whenEinDoesNotExist_thenThrowException() {
        String ein = "1234567";
        assertThrows(NoResultException.class, () -> companyDao.findByEin(ein));
    }

    @Test
    void findByName_whenNameDoesNotExist_thenThrowException() {
        String name = "findByName";
        assertThrows(NoResultException.class, () -> companyDao.findByEin(name));
    }
}