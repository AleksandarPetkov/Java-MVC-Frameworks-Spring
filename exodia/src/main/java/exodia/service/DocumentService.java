package exodia.service;

import exodia.domain.models.service.DocumentServiceModel;

public interface DocumentService {

    DocumentServiceModel documentCreate(DocumentServiceModel model);

    DocumentServiceModel findDocumentById(String id);
}
