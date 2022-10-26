package com.example.curseforbradesco.controllers;

import com.example.curseforbradesco.data.vo.v1.BookVO;
import com.example.curseforbradesco.services.BookServices;
import com.example.curseforbradesco.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book/v1")
@Tag(name = "Book", description = "Endpoints for Managing Book")
public class BookController {
    @Autowired
    private BookServices service;
    //@Autowired cuida das instânciações
//    private PersonServices service = new PersonServices();


    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,MediaType.APPLICATION_YML})
    @Operation(summary = "Finds a Book",description ="Finds a Book",tags = {"Book"},responses = {
            @ApiResponse(description = "Sucess",responseCode = "200",
                    content = @Content(schema = @Schema(implementation =BookVO.class))),
            @ApiResponse(description = "No Content",responseCode = "204",content = {@Content}),
            @ApiResponse(description = "Bad Request",responseCode = "400",content = {@Content}),
            @ApiResponse(description = "Unauthorized",responseCode = "401",content = {@Content}),
            @ApiResponse(description = "Not Found",responseCode = "404",content = {@Content}),
            @ApiResponse(description = "Internal Error",responseCode = "500",content = {@Content})})
    public BookVO findById(@PathVariable(value = "id") Long id) {
        return service.findById(id);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a Book",description ="Delete a Person",tags = {"Book"},responses = {
            @ApiResponse(description = "No content",responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request",responseCode = "400",content = {@Content}),
            @ApiResponse(description = "Unauthorized",responseCode = "401",content = {@Content}),
            @ApiResponse(description = "Not Found",responseCode = "404",content = {@Content}),
            @ApiResponse(description = "Internal Error",responseCode = "500",content = {@Content})})
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.POST,produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,MediaType.APPLICATION_YML},consumes ={ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.APPLICATION_YML})
    @Operation(summary = "Adds a new Person",description ="Adds a new Person",tags = {"People"},responses = {
            @ApiResponse(description = "Create",responseCode = "200",
                    content = @Content(schema = @Schema(implementation =BookVO.class))),
            @ApiResponse(description = "Bad Request",responseCode = "400",content = {@Content}),
            @ApiResponse(description = "Unauthorized",responseCode = "401",content = {@Content}),
            @ApiResponse(description = "Internal Error",responseCode = "500",content = {@Content})})
    public BookVO create(@RequestBody BookVO book) {
        return service.create(book);
    }


    @PutMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,MediaType.APPLICATION_YML}, consumes ={ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.APPLICATION_YML})
    @Operation(summary = "Update a  Book",description ="Update a new Book",tags = {"Book"},responses = {
            @ApiResponse(description = "Update",responseCode = "200",
                    content = @Content(schema = @Schema(implementation =BookVO.class))),
            @ApiResponse(description = "Bad Request",responseCode = "400",content = {@Content}),
            @ApiResponse(description = "Unauthorized",responseCode = "401",content = {@Content}),
            @ApiResponse(description = "Not Found",responseCode = "404",content = {@Content}),
            @ApiResponse(description = "Internal Error",responseCode = "500",content = {@Content})})
    public BookVO update(@RequestBody BookVO book) {
        return service.update(book);
    }

    @GetMapping(value = "/findall", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,MediaType.APPLICATION_YML})
   @Operation(summary = "Finds all Book",description ="Finds all Book",tags = {"Book"},responses = {
           @ApiResponse(description = "Sucess",responseCode = "200",
                   content = {
                   @Content(
                           mediaType = "application/json",
                           array = @ArraySchema(schema = @Schema(implementation =BookVO.class))
                   )
                   }),
           @ApiResponse(description = "Bad Request",responseCode = "400",content = {@Content}),
           @ApiResponse(description = "Unauthorized",responseCode = "401",content = {@Content}),
           @ApiResponse(description = "Not Found",responseCode = "404",content = {@Content}),
           @ApiResponse(description = "Internal Error",responseCode = "500",content = {@Content}),
   })
    public ResponseEntity<PagedModel<EntityModel<BookVO>>> findAll(
            @RequestParam(value = "page",defaultValue = "0") Integer page,
            @RequestParam(value = "size",defaultValue = "12") Integer size,
            @RequestParam(value = "direction",defaultValue = "0") String direction) {

        var sortDirection = direction.equalsIgnoreCase("desc")? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page,size,Sort.by(sortDirection,"author"));


        return ResponseEntity.ok(service.findAll(pageable));
    }


}
