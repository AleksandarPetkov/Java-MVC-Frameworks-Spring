package realestate.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import realestate.domain.models.view.OfferViewModel;
import realestate.service.OfferService;
import realestate.util.HtmlReader;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final OfferService offerService;
    private final ModelMapper modelMapper;
    private final HtmlReader reader;

    @Autowired
    public HomeController(OfferService offerService, ModelMapper modelMapper, HtmlReader reader) {
        this.offerService = offerService;
        this.modelMapper = modelMapper;
        this.reader = reader;
    }

    @GetMapping("/")
    @ResponseBody
    public String index() throws IOException {
        return this.prepareHtml();
    }

    private String prepareHtml() throws IOException {
        List<OfferViewModel> offers = this.offerService
                .findAllOffers()
                .stream()
                .map(o -> this.modelMapper.map(o, OfferViewModel.class))
                .collect(Collectors.toList());

        StringBuilder offersHtml = new StringBuilder();

        if (offers.size() == 0) {
            offersHtml.append("<div class=\"apartment\" style=\"border: red solid 1px\">")
                    .append("There aren`t any offers")
                    .append("</div>");

            return this.reader.readHtmlFile("C:\\Users\\sstoy\\Desktop\\SoftUni\\Git Hub\\Java-MVC-Frameworks---Spring\\01. SPRING BOOT INTRODUCTION\\realestate\\src\\main\\resources\\static\\index.html")
                    .replace("{{offers}}", offersHtml.toString().trim());
        }

        for (OfferViewModel offer : offers) {
            offersHtml.append("<div class=\"apartment\">")
                    .append("<p>Rent: " + offer.getApartmentRent() + "</p>")
                    .append("<p>Type: " + offer.getApartmentType() + "</p>")
                    .append(("<p>Commission: " + offer.getAgencyCommission() + "</p>"))
                    .append("</div>");
        }

        return this.reader.readHtmlFile("C:\\Users\\sstoy\\Desktop\\SoftUni\\Git Hub\\Java-MVC-Frameworks---Spring\\01. SPRING BOOT INTRODUCTION\\realestate\\src\\main\\resources\\static\\index.html")
                .replace("{{offers}}", offersHtml.toString().trim());
    }
}
