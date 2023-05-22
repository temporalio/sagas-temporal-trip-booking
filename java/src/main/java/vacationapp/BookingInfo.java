package vacationapp;


import java.time.LocalDate;

public class BookingInfo {
    private CreditCardInfo _creditCardInfo;
    private String _name;
    private String _address;
    private LocalDate _start;
    private LocalDate _end;
    private String _clientId;

    // Public no-args constructor needed for Temporal payloads.
    public BookingInfo() {
    }

    public BookingInfo(CreditCardInfo creditCardInfo, String name,
                       String clientId, String address, LocalDate start,
                       LocalDate end) {
        this._creditCardInfo = creditCardInfo;
        this._name = name;
        this._clientId = clientId;
        this._address = address;
        this._start = start;
        this._end = end;
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

    public LocalDate getStart() {
        return _start;
    }

    public void setStart(LocalDate start) {
        this._start = start;
    }

    public LocalDate getEnd() {
        return _end;
    }

    public void setEnd(LocalDate end) {
        this._end = end;
    }

    public String getClientId() {
        return _clientId;
    }

    public void setClientId(String clientId) {
        this._clientId = _clientId;
    }


    public String toString() {
        return String.format("%s %s %s (%s-%s) %s", _name, _clientId, _address,
                             _start, _end, _creditCardInfo);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BookingInfo) {
            BookingInfo info = (BookingInfo) obj;
            return info.getAddress().equals(getAddress()) &&
                    info.getName().equals(getName()) &&
                    info.getClientId().equals(getClientId()) &&
                    info.getCreditCardInfo().equals(getCreditCardInfo()) &&
                    info.getStart().equals(getStart()) &&
                    info.getEnd().equals(getEnd());
        }
        return false;
    }
}