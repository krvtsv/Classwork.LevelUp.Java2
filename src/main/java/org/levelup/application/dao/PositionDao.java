package org.levelup.application.dao;

import org.levelup.application.domain.PositionEntity;

import java.util.Collection;

public interface PositionDao {
    PositionEntity createPosition(String name) ;
    Collection<PositionEntity> findAll();
    void deletePositionById(int id);
    void deletePositionByName(String name);
    Collection<PositionEntity> findAllPositionWhichNameLike(String name);
    PositionEntity findPositionById(int id);
    PositionEntity findPositionByName(String name);
}
