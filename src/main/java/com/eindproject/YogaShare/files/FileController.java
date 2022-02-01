package com.eindproject.YogaShare.files;

import com.eindproject.YogaShare.users.User;
import com.eindproject.YogaShare.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/file")
public class FileController {

    private FileDBService fileDBService;
    private UserRepository userRepository;

    @Autowired
    public FileController(FileDBService fileDBService, UserRepository userRepository) {
        this.fileDBService = fileDBService;
        this.userRepository = userRepository;
    }

    //methods

    //GET
    @GetMapping("/files")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<FileProperties>> getAllFiles() {
        List<FileProperties> files = fileDBService.getAllFiles().map(fileDB -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/file/")
                    .path(String.valueOf(fileDB.getId()))
                    .toUriString();

            return new FileProperties(
                    fileDB.getUser().getUsername(),
                    fileDB.getId(),
                    fileDB.getName(),
                    fileDownloadUri,
                    fileDB.getType(),
                    fileDB.getData().length);
        }).collect(Collectors.toList());
        return ResponseEntity.ok().body(files);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<byte[]> getFileById(@PathVariable("id") Long id) {
        FileDB fileDB = fileDBService.getFileById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, " attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }


    //POST
    @PostMapping("/upload") //ony admin (teacher)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile multipartFile, Authentication authentication) throws IOException {
        User user = userRepository.findByUsername(authentication.getName()).get();
        FileDB fileDB = fileDBService.uploadFile(multipartFile, user.getId());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(fileDB).toUri();
        return ResponseEntity.created(location).build();
    }

    //DELETE
    @DeleteMapping("/{id}") //only admin
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deleteFileById(@PathVariable("id") Long id) {
        fileDBService.deleteFile(id);
        return ResponseEntity.ok().build();
    }
}
