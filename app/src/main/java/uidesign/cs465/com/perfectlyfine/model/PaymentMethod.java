package uidesign.cs465.com.perfectlyfine.model;

/**
 * Created by oberpete on 04.12.2017.
 */

public class PaymentMethod {

    public enum Provider {
        VISA,
        MASTERCARD,
        AMERICANEXPRESS
    }

    private String name;
    private String cardNo;
    private String securityCode;
    private String expirationMonth;
    private String expirationYear;
    private Provider provider;

    public PaymentMethod(String name, String cardNo, String securityCode, String expirationMonth, String expirationYear, Provider provider) {
        this.name = name;
        this.cardNo = cardNo;
        this.securityCode = securityCode;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.provider = provider;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(String expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public String getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(String expirationYear) {
        this.expirationYear = expirationYear;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

}
