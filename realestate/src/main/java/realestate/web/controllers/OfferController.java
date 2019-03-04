package realestate.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import realestate.domain.models.binding.OfferFindBindingModel;
import realestate.domain.models.binding.OfferRegisterBindingModel;
import realestate.domain.models.service.OfferServiceModel;
import realestate.service.OfferService;

@Controller
public class OfferController {

    private final OfferService offerService;
    private final ModelMapper modelMapper;

    public OfferController(OfferService offerService, ModelMapper modelMapper) {
        this.offerService = offerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/reg")
    public String register() {
        return "register.html";
    }

    @PostMapping("/reg")
    public String registerConfirm(@ModelAttribute(name = "model") OfferRegisterBindingModel model) {
        try {
            this.offerService.offerRegister(this.modelMapper.map(model, OfferServiceModel.class));
        }catch (IllegalArgumentException iae){
            iae.printStackTrace();
            return "redirect:/reg";
        }
        return "redirect:/";
    }

    @GetMapping("/find")
    public String find() {
        return "find.html";
    }

    @PostMapping("/find")
    public String findConfirm(@ModelAttribute(name = "model")OfferFindBindingModel model){
        try {
            this.offerService.findOffer(model);
        }catch (IllegalArgumentException iae){
            iae.printStackTrace();
            return "redirect:/find";
        }
        return "redirect:/";
    }
}
