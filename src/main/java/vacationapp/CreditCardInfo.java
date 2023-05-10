package vacationapp;

import com.codingrodent.jackson.crypto.Encrypt;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.time.YearMonth;

public class CreditCardInfo {
    private final int _number;
    private final YearMonth _expiration;
    private final int _ccv;

    public CreditCardInfo(int number, YearMonth expiration, int ccv) {
        this._number = number;
        this._expiration = expiration;
        this._ccv = ccv;
    }

    @JsonProperty
    @Encrypt
    public int getNumber() {
        return _number;
    }

    @JsonProperty
    @Encrypt
    public YearMonth getExpiration() {
        return _expiration;
    }

    @JsonProperty
    @Encrypt
    public int getCcv() {
        return _ccv;
    }

    public String toString() {
        return String.format("%d %s %d", _number, _expiration, _ccv);
    }
}
