package se.vgregion.webbtidbok.domain.sectra;

import se.vgregion.webbtidbok.domain.BookingTime;
import se.vgregion.webbtidbok.domain.Surgery;

public class BookingTimeSectra extends BookingTime {

    private static final long serialVersionUID = 1L;
    
    private int length;
    private Surgery surgery;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Surgery getSurgery() {
        return surgery;
    }

    public void setSurgery(Surgery surgery) {
        this.surgery = surgery;
    }

}
