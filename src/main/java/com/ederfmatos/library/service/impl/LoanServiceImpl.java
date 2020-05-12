package com.ederfmatos.library.service.impl;

import com.ederfmatos.library.exception.BusinessException;
import com.ederfmatos.library.model.Loan;
import com.ederfmatos.library.repository.LoanRepository;
import com.ederfmatos.library.service.LoanService;
import org.springframework.stereotype.Service;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository repository;

    public LoanServiceImpl(LoanRepository repository) {
        this.repository = repository;
    }

    @Override
    public Loan save(Loan loan) {
        if(repository.existsByBookAndReturned(loan.getBook())) {
            throw new BusinessException("Book already loaned");
        }

        return repository.save(loan);
    }

}