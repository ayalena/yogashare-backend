package com.eindproject.YogaShare.files;

import com.eindproject.YogaShare.YogaShareApplication;
import com.eindproject.YogaShare.exceptions.RecordNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ContextConfiguration(classes = {YogaShareApplication.class})
class FileDBServiceTest {

    @Mock FileDBRepository fileDBRepository;

    @InjectMocks
    private FileDBService fileDBService;

    @Mock

    FileDB fileDB;

    @Test
    public void getFileByIdTest() {
        fileDB = new FileDB();
        fileDB.setId(1L);
        Mockito
                .when(fileDBRepository.findById(1L))
                .thenReturn(Optional.of(fileDB));
        FileDB found = fileDBService.getFileById(1L);
        assertEquals(1L, found.getId());
    }

    @Test
    public void getFileByIdExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> fileDBService.getFileById(null));
    }

    @Test
    public void deleteFileTest() {
        fileDB = new FileDB();
        fileDB.setId(1L);
        Mockito
                .when(fileDBRepository.existsById(1L))
                .thenReturn(true);
        fileDBService.deleteFile(1L);
        verify(fileDBRepository, times(1)).deleteById(fileDB.getId());
        assertThat(fileDB.getId()).isEqualTo(1L);
    }
}