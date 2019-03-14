package realestate.domain.models.service;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class OfferServiceModel {
    private String id;
    private BigDecimal apartmentRent;
    private String apartmentType;
    private BigDecimal agencyCommission;

    public OfferServiceModel() {
    }

    public String getId() {
        return this.id;
    }

    @NotNull
    @DecimalMin("0.0001")
    public BigDecimal getApartmentRent() {
        return this.apartmentRent;
    }

    @NotNull
    @NotEmpty
    public String getApartmentType() {
        return this.apartmentType;
    }

    @NotNull
    @DecimalMin("0")
    @DecimalMax("100")
    public BigDecimal getAgencyCommission() {
        return this.agencyCommission;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setApartmentRent(BigDecimal apartmentRent) {
        this.apartmentRent = apartmentRent;
    }

    public void setApartmentType(String apartmentType) {
        this.apartmentType = apartmentType;
    }

    public void setAgencyCommission(BigDecimal agencyCommission) {
        this.agencyCommission = agencyCommission;
    }
}
