package com.ederfmatos.library.service.impl;

import com.ederfmatos.library.bean.loan.LoanFilterDTO;
import com.ederfmatos.library.exception.BusinessException;
import com.ederfmatos.library.model.Book;
import com.ederfmatos.library.model.Loan;
import com.ederfmatos.library.repository.LoanRepository;
import com.ederfmatos.library.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private final LoanRepository repository;

    public LoanServiceImpl(LoanRepository repository) {
        this.repository = repository;
    }

    @Override
    public Loan save(Loan loan) {
        if(repository.existsByBookAndReturnedFalse(loan.getBook())) {
            throw new BusinessException("Book already loaned");
        }

        return repository.save(loan);
    }

    @Override
    public Optional<Loan> getById(long id) {
        return repository.findById(id);
    }

    @Override
    public Loan update(Loan loan) {
        return repository.save(loan);
    }

    @Override
    public Page<Loan> find(LoanFilterDTO loan, Pageable pageable) {
        return repository.findByBookIsbnOrCustomer(loan.getIsbn(), loan.getCustomer(), pageable);
    }

    @Override
    public Page<Loan> getLoanByBook(Book book, Pageable pageable) {
        return repository.findByBook(book, pageable);
    }

    @Override
    public List<Loan> getAllLateLoans() {
        final int loanDays = 4;
        LocalDate threeDaysAgo = LocalDate.now().minusDays(loanDays);

        return repository.findByDateLessThanTodayAndReturnedFalse(threeDaysAgo);
    }

}
