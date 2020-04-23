package org.levelup.Hibernate;

import org.hibernate.SessionFactory;
import org.levelup.Hibernate.Service.UserService;
import org.levelup.application.domain.UserEntity;

public class HibernateApp {

    public static void main(String[] args) {
        SessionFactory factory = new JobSessionFactoryConfiguration().configure();
        UserService userService = new UserService(factory);
        UserEntity user = userService.createUserPersist("Oleg","Olegov","0933 658362");
        System.out.println(user);
        Integer id = userService.createUserSave("Dmitry", "Protsko", "4343 954583");
        System.out.println(id);
        Integer cloneId = userService.cloneUser(2, "9677 327284");
        System.out.println(cloneId);
        UserEntity user1 = userService.updateUserNameWithMerge(2, "Kirill");
        System.out.println("copied user: "+ Integer.toHexString(user1.hashCode()));

        UserEntity user2 = userService.mergeNewUser("Ulya", "Ulina", "7436 394839");
        System.out.println(user2);

        userService.updateUser("Tanya", "yshdsj", "hud");

        factory.close();



    }
}
