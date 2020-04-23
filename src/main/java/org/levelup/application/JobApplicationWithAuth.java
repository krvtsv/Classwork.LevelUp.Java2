package org.levelup.application;

import lombok.SneakyThrows;
import org.hibernate.SessionFactory;
import org.levelup.Hibernate.JobSessionFactoryConfiguration;
import org.levelup.application.domain.*;
import org.levelup.application.dao.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;


public class JobApplicationWithAuth {

    static SessionFactory factory = new JobSessionFactoryConfiguration ().configure ();
    static BufferedReader br = new BufferedReader (new InputStreamReader (System.in));
    static PositionDao position = new PositionDaoImpl (factory);
    static CompanyDao company = new CompanyDaoImpl (factory);
    static AuthDetailsDao authDetails = new AuthDetailsDaoImpl (factory);
    static UserDao user = new UserDaoImpl (factory);
    static JobListDao jobList = new JobListDaoImpl (factory);

    @SneakyThrows
    public static void main (String[] args) {
        //   authenticate

        System.out.print ("Enter login: ");
        String login = br.readLine ();
        System.out.print ("Enter password: ");
        String password = br.readLine ();

        AuthDetailsEntity detailsEntity = authDetails.findByPasswordAndLogin (login, password);
        UserEntity user = detailsEntity.getUser ();

        System.out.println ("Hello, " + user.getName () + " " + user.getLastName () + "!");
        boolean createNext = true;

        while (createNext) {
            System.out.println ("Please enter what do you need to create: Company / Position / Job record.");
            String answerRecord = br.readLine ().toUpperCase ();
            switch (answerRecord) {
                case "COMPANY":
                    createCompany ();
                    createNext = checkAnswer ();
                    break;
                case "POSITION":
                    createPosition ();
                    createNext = checkAnswer ();
                    break;
                case "JOB RECORD":
                    createJobRecord ();
                    createNext = checkAnswer ();
                    break;
                default:
                    System.out.println ("You cannot create '" + answerRecord + "'.");
                    createNext = checkAnswer ();
                    break;
            }

        }
    }


    @SneakyThrows
    private static boolean checkAnswer () {
        System.out.println ("Do you want to continue? yes/no");
        String answer = br.readLine ();
        if (answer.equalsIgnoreCase ("yes") || answer.equalsIgnoreCase ("y")) return true;
        else return false;
    }

    ;

    @SneakyThrows
    private static void createCompany () {

        System.out.print ("Company name: ");
        String name = br.readLine ();
        System.out.print ("Ein: ");
        String ein = br.readLine ();
        System.out.print ("Address: ");
        String address = br.readLine ();

        company.createCompany (name, ein, address);
        System.out.println ("Company has been created");

    }


    @SneakyThrows
    private static void createPosition () {

        System.out.print ("Position name: ");
        String name = br.readLine ();
        position.createPosition (name);
        System.out.println ("Position has been created");
    }

    @SneakyThrows
    private static void createJobRecord () {

        System.out.println ("Please enter company id:");
        printCompanies ();
        Integer companyId = Integer.valueOf (br.readLine ());

        System.out.println ("Please enter user id:");
        printUsers ();
        Integer userId = Integer.valueOf (br.readLine ());

        System.out.println ("Please enter position id:");
        printPositions ();
        Integer positionId = Integer.valueOf (br.readLine ());


        System.out.println ("Hire date in format yyyy-mm-dd");
        LocalDate startDate = LocalDate.parse (br.readLine ());

        System.out.print ("You can enter termination date.");
        LocalDate endDate = null;
        if (checkAnswer ()) {
            System.out.println ("Termination date in format yyyy-mm-dd");
            endDate = LocalDate.parse (br.readLine ());
        }

        //creating a record
        jobList.createJobRecord (companyId, userId, positionId, startDate, endDate);
        System.out.println ("Job record has been created.");

    }
    private static void printCompanies () {
        Collection<CompanyEntity> companies = company.findAll ();
        Collection<String> printCompanies = companies
                .stream ()
                .map (obj -> (obj.getId () + " - " + obj.getName ()))
                .collect (Collectors.toList ());
        System.out.println (printCompanies);
    }

    public static void printUsers () {
        Collection<UserEntity> users = user.findAll ();
        Collection<String> printUsers = users
                .stream ()
                .map (obj -> (obj.getId () + " - " + obj.getName () + " " + obj.getLastName ()))
                .collect (Collectors.toList ());
        System.out.println (printUsers);
    }

    public static void printPositions () {
        Collection<PositionEntity> positions = position.findAll ();
        Collection<String> printPositions = positions
                .stream ()
                .map (obj -> (obj.getId () + " - " + obj.getName ()))
                .collect (Collectors.toList ());
        System.out.println (printPositions);

    }
}
