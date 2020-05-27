package org.levelup.application;

import org.hibernate.SessionFactory;
import org.levelup.Hibernate.JobSessionFactoryConfiguration;
import org.levelup.application.domain.*;
import org.levelup.application.dao.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class JobApplication {
    public static void main(String[] args) {
        SessionFactory factory = new JobSessionFactoryConfiguration().configure();


        // Creating company entity and company details, respectively
//        CompanyDao companyDao = new CompanyDaoImpl(factory);
//        CompanyLegalDetailsDao legalDetailsDao = new CompanyLegalDetailsDaoImpl(factory);
//
//        companyDao.create("HY-98", "687-6708", "Address 155");
//        CompanyEntity company = companyDao.findByEin("687-6648");
//
//        legalDetailsDao.updateLegalDetailInCompany(company.getId(), "Sberbank", "954545412");

        AuthDetailsDao authDetailsDao = new AuthDetailsDaoImpl(factory);
        authDetailsDao.updatePasswordByLogin("OlegOlegov1", "newPassword");

//        Collection<CompanyLegalDetailsEntity> companyLegalDetails = legalDetailsDao.findAllByBankName("Sberbank");
//        for (CompanyLegalDetailsEntity detail : companyLegalDetails) {
//            System.out.println(detail.getCompany().getName());
//        }
//
//
//        UserDao userDao = new UserDaoImpl(factory);
//        UserEntity user = userDao.createUser("Peter", "Parker", "56244 555", new ArrayList<>(Arrays
//                .asList("Pushkina 20", "Popova 34", "Lenina 7")));
//        for (UserAddressEntity addressEntity : user.getAddresses()) {
//            System.out.println(addressEntity.getId() + " " + addressEntity.getAddress());
//
//        }
//        PositionDao positionDao = new PositionDaoImpl(factory);
//        Integer positionId = positionDao.createPosition("Manager").getId();
//
//        JobListDao jobListDao = new JobListDaoImpl(factory);
//        JobListEntity jobListEntity = jobListDao.createJobRecord(1, 23, 1, LocalDate.of(2019, 12, 4), null);
//
//        JobListEntity jobRecord = jobListDao.findJobRecord(1, 26, 1);
//        System.out.println(jobRecord);
//        factory.close();

    }


}
