package com.project.itservicedesk.repositories;

import com.project.itservicedesk.models.Company;
import com.project.itservicedesk.models.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query(value = "SELECT c FROM Company c WHERE c.companyName =?1")
    public Project findCompanyByName(String name);

    @Query(value = "SELECT * FROM companies WHERE CONCAT(id, company_name) ILIKE (CONCAT('%', ?1,'%'))", nativeQuery = true)
    Page<Company> searchRepository(String keyword, Pageable pageable);

    Page<Company> findAll(Pageable pageable);

    @Query(value = "SELECT c.id FROM Company c WHERE c.companyName =?1")
    Long findByCompanyName(String companyName);
}
