package com.eindproject.YogaShare.documents;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.persistence.*;

@ConfigurationProperties(prefix = "file")
@Entity
@Table(name = "documents")
public class DocumentStorageProperties {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "doc_id")
    private Integer documentId;

    @Column(name = "doc_user_id")
    private Integer UserId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "doc_type")
    private String documentType;

    @Column(name = "doc_format")
    private String documentFormat;

    //    upload_dir variable will get initialized by the value which we have set in application.properties
    @Column(name = "upload_dir")
    private String uploadDir;

    //getters and setters

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentFormat() {
        return documentFormat;
    }

    public void setDocumentFormat(String documentFormat) {
        this.documentFormat = documentFormat;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

}
