package org.levelup.application.dao;

import org.levelup.application.domain.CompanyLegalDetailsEntity;

import java.util.Collection;

public interface CompanyLegalDetailsDao {
    void updateLegalDetailInCompany(Integer companyId, String bankName, String bic);

    Collection<CompanyLegalDetailsEntity> findAllByBankName(String bankName);
}

