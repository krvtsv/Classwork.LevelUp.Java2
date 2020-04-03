package org.levelup.Hibernate;

import org.hibernate.SessionFactory;
import org.levelup.Hibernate.Service.UserService;
import org.levelup.Hibernate.domain.User;

public class HibernateApp {

    public static void main(String[] args) {
        SessionFactory factory = new JobSessionFactoryConfiguration().configure();
        UserService userService = new UserService(factory);
//        User user = userService.createUserPersist("Oleg","Olegov","0933 658362");
//        System.out.println(user);
//        Integer id = userService.createUserSave("Dmitry", "Protsko", "4343 954583");
//        System.out.println(id);
//        Integer cloneId = userService.cloneUser(2, "9677 327284");
//        System.out.println(cloneId);
//        User user = userService.updateUserNameWithMerge(2, "Kirill");
//        System.out.println("copied user: "+ Integer.toHexString(user.hashCode()));

//        User user = userService.mergeNewUser("Ulya", "Ulina", "7436 394839");
//        System.out.println(user);

        userService.updateUser("Tanya", "yshdsj", "hud");

        factory.close();
    }
}
