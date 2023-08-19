package com.project.itservicedesk.services;

import com.project.itservicedesk.models.Company;
import com.project.itservicedesk.repositories.CompanyRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }

    public Optional<Company> findCompanyById(Long id){
        return companyRepository.findById(id);
    }

    public Company save(Company company){
        return companyRepository.save(company);
    }

    public void delete(Company company){
        companyRepository.delete(company);
    }

    public void deleteById(Long id){
        companyRepository.deleteById(id);
    }

    public Page<Company> searchRepository(String keyword, Pageable pageable) {
        return companyRepository.searchRepository(keyword, pageable);
    }


    /**
     * Methods to return Pagination
     */
    public Page<Company> getAll(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    public Long findCompanyIdByCompanyName(String companyName) {
        return companyRepository.findByCompanyName(companyName);
    }
}
