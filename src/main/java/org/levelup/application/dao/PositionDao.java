package org.levelup.application.dao;

import org.levelup.application.domain.PositionEntity;

import java.util.Collection;

public interface PositionDao {
    PositionEntity createPosition(String name) ;


    Collection<PositionEntity> findAll();
}
