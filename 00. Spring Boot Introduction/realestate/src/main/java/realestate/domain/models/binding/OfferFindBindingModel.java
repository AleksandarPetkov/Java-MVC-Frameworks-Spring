package realestate.domain.models.binding;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class OfferFindBindingModel {
    private BigDecimal familyBudget;
    private String familyApartmentType;
    private String familyName;

    public OfferFindBindingModel() {
    }

    @NotNull
    @DecimalMin("0.001")
    public BigDecimal getFamilyBudget() {
        return this.familyBudget;
    }

    @NotNull
    @NotEmpty
    public String getFamilyApartmentType() {
        return this.familyApartmentType;
    }

    @NotNull
    @NotEmpty
    public String getFamilyName() {
        return this.familyName;
    }

    public void setFamilyBudget(BigDecimal familyBudget) {
        this.familyBudget = familyBudget;
    }

    public void setFamilyApartmentType(String familyApartmentType) {
        this.familyApartmentType = familyApartmentType;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }
}
