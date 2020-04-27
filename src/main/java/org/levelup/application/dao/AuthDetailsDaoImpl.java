package org.levelup.application.dao;


import org.hibernate.SessionFactory;
import org.levelup.application.domain.AuthDetailsEntity;

public class AuthDetailsDaoImpl extends AbstractDao implements AuthDetailsDao {
    public AuthDetailsDaoImpl(SessionFactory factory) {
        super(factory);
    }

    @Override
    public AuthDetailsEntity findByPasswordAndLogin(String login, String password) {
        return runWithoutTransaction(session -> session.createQuery("from AuthDetailsEntity where login=:login and password=:password", AuthDetailsEntity.class)
                .setParameter("login", login).setParameter("password", password)
                .getSingleResult());
    }

    @Override
    public void updatePasswordByLogin(String login, String password) {
        runInTransaction(session -> {
            return session.createQuery("update AuthDetailsEntity set password=:password where login=:login", AuthDetailsEntity.class)
                    .setParameter("login", login).setParameter("password", password);

        });
    }


}

