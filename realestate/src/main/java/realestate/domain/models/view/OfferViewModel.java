package realestate.domain.models.view;

import java.math.BigDecimal;

public class OfferViewModel {
    private BigDecimal apartmentRent;
    private String apartmentType;
    private BigDecimal agencyCommission;

    public OfferViewModel() {
    }

    public BigDecimal getApartmentRent() {
        return this.apartmentRent;
    }

    public String getApartmentType() {
        return this.apartmentType;
    }

    public BigDecimal getAgencyCommission() {
        return this.agencyCommission;
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
