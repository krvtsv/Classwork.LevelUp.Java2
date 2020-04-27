package org.levelup.application.dao;


import org.hibernate.SessionFactory;
import org.levelup.application.domain.CompanyEntity;

import java.util.Collection;
import java.util.List;


public class CompanyDaoImpl extends AbstractDao implements CompanyDao {

    public CompanyDaoImpl(SessionFactory factory) {
        super(factory);
    }

    @Override

    public CompanyEntity createCompany(String name, String ein, String address) {
        return runInTransaction(session -> {

            CompanyEntity entity = new CompanyEntity();
            entity.setName(name);
            entity.setEin(ein);
            entity.setAddress(address);
            session.persist(entity);
            return entity;
        });

    }

    @Override
    public CompanyEntity findById(Integer id) {

        return runInTransaction(session -> {
            return session.get(CompanyEntity.class, id);
        });
    }

    @Override
    public CompanyEntity findByEin(String ein) {
        return runWithoutTransaction(session -> session.createQuery("from CompanyEntity where ein=:ein", CompanyEntity.class)
                .setParameter("ein", ein)
                .getSingleResult());

    }

    public CompanyEntity findByName(String name) {
        List<CompanyEntity> entities = runWithoutTransaction(session -> session.createQuery("from CompanyEntity where name=:name", CompanyEntity.class)
                .setParameter("name", name)
                .getResultList());
        return entities.isEmpty() ? null : entities.get(0);
    }

    @Override
    public Collection<CompanyEntity> findAll() {
        Collection<CompanyEntity> entities = runWithoutTransaction(session -> session.createQuery("from CompanyEntity", CompanyEntity.class)
                .getResultList());
        return entities.isEmpty() ? null : entities;
    }

}



