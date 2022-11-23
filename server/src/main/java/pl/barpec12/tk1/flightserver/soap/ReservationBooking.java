package pl.barpec12.tk1.flightserver.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import pl.barpec12.tk1.flightserver.model.Flight;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ReservationBooking {
    @WebMethod
    Flight[] getFlights();
}