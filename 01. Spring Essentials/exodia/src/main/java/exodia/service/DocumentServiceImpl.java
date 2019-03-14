package exodia.service;

import exodia.domain.entities.Document;
import exodia.domain.models.service.DocumentServiceModel;
import exodia.repository.DocumentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository, ModelMapper modelMapper) {
        this.documentRepository = documentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DocumentServiceModel documentCreate(DocumentServiceModel model) {
        Document document = this.modelMapper.map(model, Document.class);

        try {
            this.documentRepository.saveAndFlush(document);
            return this.modelMapper.map(document, DocumentServiceModel.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public DocumentServiceModel findDocumentById(String id) {
        Document document = this.documentRepository.findById(id).orElse(null);
        if (document == null) {
            return null;
        }
        return this.modelMapper.map(document, DocumentServiceModel.class);
    }

    @Override
    public List<DocumentServiceModel> allDocuments() {
        List<Document> documents = this.documentRepository.findAll();

        return documents.stream()
                .map(x -> this.modelMapper.map(x, DocumentServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean printDocumentById(String id) {
        try {
            this.documentRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
