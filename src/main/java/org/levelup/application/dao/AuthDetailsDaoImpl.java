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
        runInTransaction(session -> { session.createQuery("update AuthDetailsEntity set password=:password where login=:login")
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .executeUpdate();
        });
    }

    @Override
    public AuthDetailsEntity createAccount(String login, String password) {
        return runInTransaction(session -> {
            AuthDetailsEntity account = new AuthDetailsEntity(login,password);
            session.persist(account);
            return account;
        });

    }


}

