package nl.hu.adsd.dtmreserveringen.contoller;

import nl.hu.adsd.dtmreserveringen.dto.ReservationDTO;
import nl.hu.adsd.dtmreserveringen.entity.Reservation;
import nl.hu.adsd.dtmreserveringen.services.MailService;
import nl.hu.adsd.dtmreserveringen.services.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(originPatterns = "http://localhost:[*]")
@RestController
@RequestMapping(path = "/api/reservation")
public class ReservationController {
    private final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    private final ReservationService reservationService;
    
    @Autowired
    private MailService mailService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        Iterable<Reservation> reservationIterable = reservationService.getAllReservations();

        List<Reservation> reservationList = new ArrayList<>();

        for (Reservation reservation : reservationIterable) {
            reservationList.add(reservation);
        }

        return ResponseEntity.ok(reservationList);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteReservationById(@PathVariable Long id) {
        try {
            reservationService.deleteReservationById(id);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    //TODO: move reservation to acceptedReservation table when this table is realised
    @GetMapping("/accept/{email}/{id}")
    public ResponseEntity<HttpStatus> acceptReservationById(@PathVariable String email, @PathVariable long id) {
        try {
            logger.info("Accepted reservation");
            sendEmail(email, "Reservation accepted", "Your reservation has been accepted!");
            deleteReservationById(id);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("\n".repeat(15));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addReservation(@RequestBody ReservationDTO reservationDTO) {
        logger.info(reservationDTO.toString());
        HttpStatus httpStatus;
        try {
            httpStatus = reservationService.addReservation(reservationDTO);
        } catch (Exception e) {
            logger.info("something went wrong in addReservation reservation controller\nError: {}", e.toString());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(httpStatus);
    }

    @PostMapping("/sendEmail/{email}/{subject}/{text}")
    public ResponseEntity<HttpStatus> sendEmail(@PathVariable String email, @PathVariable String subject, @PathVariable String text) {
        try {
            mailService.sendEmail(email, subject, text);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
   
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
