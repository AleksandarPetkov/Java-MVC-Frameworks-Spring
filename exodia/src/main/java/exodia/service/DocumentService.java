package exodia.service;

import exodia.domain.models.service.DocumentServiceModel;

import java.util.List;

public interface DocumentService {

    DocumentServiceModel documentCreate(DocumentServiceModel model);

    DocumentServiceModel findDocumentById(String id);

    List<DocumentServiceModel> allDocuments();

    boolean printDocumentById(String id);
}
