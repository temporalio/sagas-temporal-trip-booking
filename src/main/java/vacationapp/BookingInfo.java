package vacationapp;


public class BookingInfo {
    private CreditCardInfo _creditCardInfo;
    private String _name;
    private String _address;

    // Public no-args constructor needed for Temporal payloads.
    public BookingInfo() {
    }

    public BookingInfo(CreditCardInfo creditCardInfo, String name,
                       String address) {
        this._creditCardInfo = creditCardInfo;
        this._name = name;
        this._address = address;
    }

    public CreditCardInfo getCreditCardInfo() {
        return _creditCardInfo;
    }

    public void setCreditCardInfo(CreditCardInfo info) {
        _creditCardInfo = info;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public String getAddress() {
        return _address;
    }

    public void setAddress(String address) {
        _address = address;
    }

    public String toString() {
        return String.format("%s %s %s", _name, _address, _creditCardInfo);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BookingInfo) {
            BookingInfo info = (BookingInfo) obj;
            return info.getAddress() == getAddress() &&
                    info.getName() == getName() &&
                    info.getCreditCardInfo() == getCreditCardInfo();
        }
        return false;
    }
}
