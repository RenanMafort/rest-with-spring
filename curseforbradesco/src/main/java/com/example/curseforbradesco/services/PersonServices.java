package com.example.curseforbradesco.services;

import com.example.curseforbradesco.controllers.PersonController;
import com.example.curseforbradesco.data.vo.v1.PersonVO;
import com.example.curseforbradesco.exception.ResourceNotFoundException;
import com.example.curseforbradesco.exception.RequireObjectIsNullException;
import com.example.curseforbradesco.mapper.DozerMapper;
import com.example.curseforbradesco.model.Person;
import com.example.curseforbradesco.repositories.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class PersonServices {

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    @Autowired
    PagedResourcesAssembler<PersonVO> assembler;


    public PagedModel<EntityModel<PersonVO>> findAll(Pageable pageable) {
        logger.info("Finding all peoples! ");
        var personPage = repository.findAll(pageable);
        var personVoPage = personPage.map(p -> DozerMapper.parseObject(p,PersonVO.class));
        personVoPage.map(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

        Link link= linkTo(methodOn(PersonController.class).findAll(pageable.getPageNumber(),
                pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(personVoPage,link);
    }

    public PagedModel<EntityModel<PersonVO>> findPersonsByName(String firstname,Pageable pageable) {
        logger.info("Finding all peoples! ");
        var personPage = repository.findPersonsByName(firstname,pageable);
        var personVoPage = personPage.map(p -> DozerMapper.parseObject(p,PersonVO.class));
        personVoPage.map(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

        Link link= linkTo(methodOn(PersonController.class).findAll(pageable.getPageNumber(),
                pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(personVoPage,link);
    }


    public PersonVO findById(Long id) {
        logger.info("Finding one person! ");

        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        var vo = DozerMapper.parseObject(entity,PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public PersonVO create(PersonVO person) {
        if (person==null) {
            throw new RequireObjectIsNullException();
        }
        logger.info("Creating one person! ");
        var entity = DozerMapper.parseObject(person,Person.class);
        var vo = DozerMapper.parseObject(repository.save(entity),PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;

    }



    public PersonVO update(PersonVO person) {
        if (person==null) {
            throw new RequireObjectIsNullException();
        }
        logger.info("Updating one person! ");
        Person entity = repository.findById(person.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var vo = DozerMapper.parseObject(repository.save(entity),PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    @Transactional
    public PersonVO disablePerson(Long id) {
        logger.info("Disabling one person! ");
        repository.disablePerson(id);

        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        var vo = DozerMapper.parseObject(entity,PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deleting one person! ");
        Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        repository.delete(entity);


    }


}
