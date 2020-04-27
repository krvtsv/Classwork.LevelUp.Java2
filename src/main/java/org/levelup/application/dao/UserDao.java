package org.levelup.application.dao;

import org.levelup.application.domain.UserEntity;

import java.util.Collection;

public interface UserDao {
    UserEntity findById(Integer id);
    UserEntity createUser(Integer id,String name, String lastName, String passport, Collection<String> addresses);

    Collection<UserEntity> findAll();
}

