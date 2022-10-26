package com.example.curseforbradesco.controllers;

import com.example.curseforbradesco.data.vo.v1.UploadoFileResponseVO;
import com.example.curseforbradesco.services.FileStoregeService;
import com.sun.source.tree.TryTree;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Tag(name = "File Endpoint")
@RestController
@RequestMapping("/api/file/v1")
public class FileController {

    private Logger logger = Logger.getLogger(FileController.class.getName());

    @Autowired
    private FileStoregeService service;

    @PostMapping("/uploadFile")
    public UploadoFileResponseVO uploadFile(@RequestParam("file")MultipartFile file){
        logger.info("Storing file to disk");
        var filename = service.storeFile(file);
        String fieDownoadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/file/v1/downloadFile/")
                .path(filename).toUriString();
        return new UploadoFileResponseVO(filename,fieDownoadUri,file.getContentType(),file.getSize());

    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadoFileResponseVO> uploadMultipleFies(@RequestParam("files")MultipartFile[] files){
        logger.info("Storing mutiples file to disk");

        return Arrays.asList(files).stream().map(file -> uploadFile(file))
                .collect(Collectors.toList());

    }

    @GetMapping("/downloadFile/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename, HttpServletRequest request){
        logger.info("Reading a file on disk");

        Resource resource = service.loadFileAsResource(filename);
        String contentType = "";

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        }catch (Exception e){
            logger.info("Could not determine file type!");
        }

        if (contentType.isBlank()){
            contentType = "application/octet-sream";
        }


        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
