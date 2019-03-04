package realestate.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import realestate.domain.entities.Offer;
import realestate.domain.models.binding.OfferFindBindingModel;
import realestate.domain.models.service.OfferServiceModel;
import realestate.repository.OfferRepository;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final Validator validator;
    private final ModelMapper modelMapper;


    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, Validator validator, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }


    @Override
    public void offerRegister(OfferServiceModel offerServiceModel) {
        if (this.validator.validate(offerServiceModel).size() != 0) {
            throw new IllegalArgumentException("Validation failed!");
        }

        this.offerRepository.saveAndFlush(this.modelMapper.map(offerServiceModel, Offer.class));
    }

    @Override
    public List<OfferServiceModel> findAllOffers() {

        List<OfferServiceModel> offers = this.offerRepository.findAll()
                .stream()
                .map(o -> this.modelMapper.map(o, OfferServiceModel.class))
                .collect(Collectors.toList());
        return offers;
    }

    @Override
    public void findOffer(OfferFindBindingModel offerFindBindingModel) {
        if (this.validator.validate(offerFindBindingModel).size() != 0) {
            throw new IllegalArgumentException("Validation failed!");
        }

        MathContext precision = new MathContext(4);
        Offer offer = this.offerRepository
                .findAll()
                .stream()
                .filter(o -> o.getApartmentType().toLowerCase().equals(offerFindBindingModel.getFamilyApartmentType().toLowerCase())
                        && offerFindBindingModel.getFamilyBudget()
                        .compareTo(o.getApartmentRent()
                                .add(o.getAgencyCommission()
                                        .divide(new BigDecimal(100))
                                        .multiply(o.getApartmentRent()))) >= 0)
                .map(o -> this.modelMapper.map(o, Offer.class))
                .findFirst()
                .orElse(null);

        if (offer == null) {
            throw new IllegalArgumentException("Something went wrong!");
        }

        this.offerRepository.delete(offer);
    }
}
