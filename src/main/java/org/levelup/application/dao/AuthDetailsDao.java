package org.levelup.application.dao;

import org.levelup.application.domain.AuthDetailsEntity;

public interface AuthDetailsDao {

    AuthDetailsEntity findByPasswordAndLogin (String login, String password);

    void updatePasswordByLogin(String login, String password);

    AuthDetailsEntity createAccount(String login, String password);
}
