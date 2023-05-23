package com.vodafone.service;

import com.vodafone.model.Author;
import com.vodafone.repository.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService{

    @Autowired
    AuthorRepo authorRepo;
    @Override
    public Author getAuthorById(int id) {
        return authorRepo.getReferenceById(id);
    }
}
