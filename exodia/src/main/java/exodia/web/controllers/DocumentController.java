package exodia.web.controllers;

import exodia.domain.models.binding.DocumentCreateBindingModel;
import exodia.domain.models.service.DocumentServiceModel;
import exodia.domain.models.view.DocumentViewModel;
import exodia.service.DocumentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class DocumentController {

    private final DocumentService documentService;

    private final ModelMapper modelMapper;

    @Autowired
    public DocumentController(DocumentService documentService, ModelMapper modelMapper) {
        this.documentService = documentService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/schedule")
    public ModelAndView getDocument(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") == null) {
            modelAndView.setViewName("redirect:/login");
        } else {
            modelAndView.setViewName("schedule");
        }
        return modelAndView;
    }

    @PostMapping("/schedule")
    public ModelAndView postDocument(@ModelAttribute DocumentCreateBindingModel bindingModel, ModelAndView modelAndView, HttpSession session) {
        DocumentServiceModel documentServiceModel = this.documentService
                .documentCreate(this.modelMapper.map(bindingModel, DocumentServiceModel.class));

        if (documentServiceModel == null){
            throw new IllegalArgumentException("Cant Create Document");
        }

        modelAndView.setViewName("redirect:/details/" + documentServiceModel.getId());
        return modelAndView;
    }

    @GetMapping("/details/{id}")
    public ModelAndView details(@PathVariable(name = "id") String id, ModelAndView modelAndView, HttpSession session){

        DocumentServiceModel model = this.documentService.findDocumentById(id);
        if (model == null){
            throw new IllegalArgumentException("Cant see Details");
        }

        modelAndView.setViewName("details");
        modelAndView.addObject("model", this.modelMapper.map(model, DocumentViewModel.class));
        return modelAndView;
    }
}
