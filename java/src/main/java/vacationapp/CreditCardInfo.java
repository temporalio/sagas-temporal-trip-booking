package vacationapp;

import java.time.YearMonth;

public class CreditCardInfo {
    private int _number;
    private YearMonth _expiration;
    private int _ccv;

    // Public no-args constructor needed for Temporal payloads.
    public CreditCardInfo() {
    }

    public CreditCardInfo(int number, YearMonth expiration, int ccv) {
        this._number = number;
        this._expiration = expiration;
        this._ccv = ccv;
    }

    public int getNumber() {
        return _number;
    }

    public void setNumber(int number) {
        _number = number;
    }

    public YearMonth getExpiration() {
        return _expiration;
    }

    public void setExpiration(YearMonth expiration) {
        _expiration = expiration;
    }

    public int getCcv() {
        return _ccv;
    }

    public void setCcv(int ccv) {
        _ccv = ccv;
    }

    public String toString() {
        return String.format("%d %s %d", _number, _expiration, _ccv);
    }

    public boolean equals(Object obj) {
        if (obj instanceof CreditCardInfo) {
            CreditCardInfo info = (CreditCardInfo) obj;
            return info.getCcv() == getCcv() &&
                    info.getNumber() == getNumber() &&
                    info.getExpiration().equals(getExpiration());
        }
        return false;
    }
}
