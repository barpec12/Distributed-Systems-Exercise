package pl.barpec12.tk1.flightclient.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import pl.barpec12.tk1.flightclient.model.Flight;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ReservationBooking {
    @WebMethod
    Flight[] getFlights();
}