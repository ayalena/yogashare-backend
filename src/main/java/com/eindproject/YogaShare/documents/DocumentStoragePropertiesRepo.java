package com.eindproject.YogaShare.documents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentStoragePropertiesRepo extends JpaRepository <DocumentStorageProperties, Integer>  {

    //    method to check whether a particular file is present for that user or not

    @Query("Select a from DocumentStorageProperties a where doc_user_id = ?1 and doc_type = ?2")
    DocumentStorageProperties checkDocumentByUserId(Integer userId, String docType);

    @Query("Select fileName from DocumentStorageProperties a where doc_user_id = ?1 and doc_type = ?2")
    String getUploadDocumentPath(Integer userId, String docType);
}
