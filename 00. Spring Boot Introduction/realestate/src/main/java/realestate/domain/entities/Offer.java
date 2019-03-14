package realestate.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity(name = "offers")
public class Offer extends BaseEntity {

    private BigDecimal apartmentRent;
    private String apartmentType;
    private BigDecimal agencyCommission;

    public Offer() {
    }

    @Column(name = "apartment_rent")
    public BigDecimal getApartmentRent() {
        return this.apartmentRent;
    }
    @Column(name = "apartment_type")
    public String getApartmentType() {
        return this.apartmentType;
    }
    @Column(name = "agency_commission")
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
