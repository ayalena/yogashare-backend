package com.eindproject.YogaShare.files;

import com.eindproject.YogaShare.exceptions.RecordNotFoundException;
import com.eindproject.YogaShare.users.User;
import com.eindproject.YogaShare.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class FileDBService {

    private FileDBRepository fileDBRepository;
    private UserRepository userRepository;

    @Autowired
    public FileDBService(FileDBRepository fileDBRepository, UserRepository userRepository) {
        this.fileDBRepository = fileDBRepository;
        this.userRepository = userRepository;
    }

    //methods

    //GET
    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }

    public FileDB getFileById(Long id) {
        if (fileDBRepository.findById(id).isPresent()) {
            return fileDBRepository.findById(id).get();
        } else {
            throw new RecordNotFoundException();
        }
    }

    //POST
    public FileDB uploadFile(MultipartFile multipartFile, Long id) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        User user = userRepository.findById(id).get();

        FileDB fileDB = new FileDB(fileName, multipartFile.getContentType(), multipartFile.getBytes());
        fileDB.setUser(user);
        userRepository.save(user);
        return fileDBRepository.save(fileDB);
    }

    //DELETE
    public void deleteFile(Long id) {
        if (fileDBRepository.existsById(id)) {
            fileDBRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException();
        }
    }
}
