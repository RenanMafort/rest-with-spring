package com.example.curseforbradesco.services;

import com.example.curseforbradesco.controllers.BookController;
import com.example.curseforbradesco.controllers.PersonController;
import com.example.curseforbradesco.data.vo.v1.BookVO;
import com.example.curseforbradesco.data.vo.v1.PersonVO;
import com.example.curseforbradesco.exception.RequireObjectIsNullException;
import com.example.curseforbradesco.exception.ResourceNotFoundException;
import com.example.curseforbradesco.mapper.DozerMapper;
import com.example.curseforbradesco.model.Book;
import com.example.curseforbradesco.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServices {

    private final Logger logger = Logger.getLogger(BookServices.class.getName());

    @Autowired
    BookRepository repository;

    @Autowired
    PagedResourcesAssembler<BookVO> assembler;


    public PagedModel<EntityModel<BookVO>> findAll(Pageable pageable) {
        logger.info("Finding all Book! ");

        var bookPage = repository.findAll(pageable);
        var bookVoPage = bookPage.map(p -> DozerMapper.parseObject(p, BookVO.class));

        bookVoPage.map(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));

        Link link= linkTo(methodOn(BookController.class).findAll(pageable.getPageNumber(),
                pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(bookVoPage,link);
    }


    public BookVO findById(Long id) {
        logger.info("Finding one person! ");

        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        var vo = DozerMapper.parseObject(entity,BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return vo;
    }

    public BookVO create(BookVO book) {
        if (book==null) {
            throw new RequireObjectIsNullException();
        }
        logger.info("Creating one Book! ");
        var entity = DozerMapper.parseObject(book, Book.class);
        var vo = DozerMapper.parseObject(repository.save(entity),BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;

    }



    public BookVO update(BookVO book) {
        if (book==null) {
            throw new RequireObjectIsNullException();
        }
        logger.info("Updating one Book! ");
        Book entity = repository.findById(book.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        entity.setAuthor(book.getAuthor());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());
        entity.setLauchDate(book.getLauchDate());

        var vo = DozerMapper.parseObject(repository.save(entity),BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deleting one Book! ");
        Book entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        repository.delete(entity);


    }


}
