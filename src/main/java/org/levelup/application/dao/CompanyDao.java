package org.levelup.application.dao;

import org.levelup.application.domain.CompanyEntity;

import java.util.Collection;

public interface CompanyDao {

    void createCompany(String name, String ein, String address);

    CompanyEntity findById(Integer id);

    CompanyEntity findByEin(String ein);

    CompanyEntity findByName(String name);

    Collection<CompanyEntity> findAll();
}
