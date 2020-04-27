package org.levelup.application.dao;

import org.hibernate.SessionFactory;
import org.levelup.application.domain.PositionEntity;

import java.util.Collection;

public class PositionDaoImpl extends AbstractDao implements PositionDao {


    public PositionDaoImpl (SessionFactory factory) {
        super (factory);
    }

    @Override
    public PositionEntity createPosition (String name) {
        return runInTransaction (session -> {
            PositionEntity position = new PositionEntity ();
            position.setName (name);
            session.persist (position);
            return position;
        });
    }

    @Override
    public Collection<PositionEntity> findAll () {
        Collection<PositionEntity> entities = runWithoutTransaction (session -> session.createQuery ("from PositionEntity", PositionEntity.class)
                .getResultList ());
        return entities.isEmpty () ? null : entities;
    }

    @Override
    public void deletePositionById (int id) {
        runInTransaction (session -> {
            return session.createQuery("delete from PositionEntity where id=:id").setParameter("id", id);
        });
    }


    @Override
    public void deletePositionByName (String name) {
        runInTransaction (session -> {
            return session.createQuery("delete from PositionEntity where name=:name").setParameter("name", name);
        });
    }

    @Override
    public Collection<PositionEntity> findAllPositionWhichNameLike (String name) {
        Collection<PositionEntity> entities = runWithoutTransaction (session -> session.createQuery ("from PositionEntity where name like :name", PositionEntity.class)
                .setParameter("name",name)
                .getResultList ());
        return entities.isEmpty () ? null : entities;
    }

    @Override
    public PositionEntity findPositionById (int id) {
        PositionEntity position = runWithoutTransaction(session -> session.get(PositionEntity.class, id));
        return position;
    }

    @Override
    public PositionEntity findPositionByName (String name) {
        PositionEntity position = runWithoutTransaction(session -> session.createQuery("from PositionEntity where name =:name", PositionEntity.class)
                .setParameter("name",name)
                .getSingleResult());
        return position;
    }
}

