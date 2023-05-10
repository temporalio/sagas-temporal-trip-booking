package vacationapp;

import com.codingrodent.jackson.crypto.Encrypt;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BookingInfo {
    private final CreditCardInfo _creditCardInfo;
    private final String _name;
    private final String _address;

    public BookingInfo(CreditCardInfo creditCardInfo, String name, String address) {
        this._creditCardInfo = creditCardInfo;
        this._name = name;
        this._address = address;
    }

    @JsonProperty
    @Encrypt
    public CreditCardInfo getCreditCardInfo() {
        return _creditCardInfo;
    }

    @JsonProperty
    @Encrypt
    public String getName() {
        return _name;
    }

    @JsonProperty
    @Encrypt
    public String getAddress() {
        return _address;
    }

    public String toString() {
        return String.format("%s %s %s", _name, _address, _creditCardInfo);
    }
}
