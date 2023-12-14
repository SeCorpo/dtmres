package nl.hu.adsd.dtmreserveringen.contoller;

import nl.hu.adsd.dtmreserveringen.dto.ItemReservationDTO;
import nl.hu.adsd.dtmreserveringen.dto.ReservationDTO;
import nl.hu.adsd.dtmreserveringen.entity.Item;
import nl.hu.adsd.dtmreserveringen.entity.ItemReservation;
import nl.hu.adsd.dtmreserveringen.entity.Reservation;
import nl.hu.adsd.dtmreserveringen.services.ItemService;
import nl.hu.adsd.dtmreserveringen.services.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(originPatterns = "http://localhost:[*]")
@RestController
@RequestMapping(path = "/api/reservation")
public class ReservationController {
    private final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    private final ReservationService reservationService;
    private final ItemService itemService;

    public ReservationController(ReservationService reservationService, ItemService itemService) {
        this.reservationService = reservationService;
        this.itemService = itemService;
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

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addReservation(@RequestBody ReservationDTO reservationDTO) {
        logger.info(reservationDTO.toString());
        HttpStatus httpStatus;
        try {
            boolean enoughProductsAvailable = true;

            // get dates in question
            ItemReservationDTO firstItemReservationDTO = reservationDTO.getItemReservationDTOS().get(0);
            LocalDate startDate = firstItemReservationDTO.getReservationDate();
            long duration = (long) firstItemReservationDTO.getReservationPeriod();
            ArrayList<LocalDate> dates = new ArrayList<>();
            for(int i = 0; i < duration; i++){
                dates.add(startDate.plusDays(i));
            }

            // get list of items in question, tally amount per reservation item
            ArrayList<Long> productIds = new ArrayList<>();
            List<ItemReservationDTO> itemReservationList = reservationDTO.getItemReservationDTOS();
            HashMap<Long, Integer> inventoryPerID = new HashMap<>();
            HashMap<Long, Integer> reservedItemCountPerID = new HashMap<>();
            HashMap<Long, Integer> reservationItemCountPerID = new HashMap<>();
            for(ItemReservationDTO item : itemReservationList){
                long id = item.getItemDTO().getId();
                inventoryPerID.putIfAbsent(id, 0);
                reservedItemCountPerID.putIfAbsent(id, 0);
                reservationItemCountPerID.putIfAbsent(id, 0);
                reservationItemCountPerID.put(id, reservationItemCountPerID.get(id) + 1);
                productIds.add(id);
            }

            // get relevant reservations from all existing reservations in items
            Iterable<Reservation> reservationIterable = reservationService.getAllReservations();
            for (Reservation reservation : reservationIterable) {
                for(ItemReservation item : reservation.getItemReservations()){
                    long id = item.getItem().getId();
                    if(productIds.contains(id)){
                        boolean datesOverlap = false;
                        for(int i = 0; i < item.getReservationPeriod(); i++){
                            LocalDate dateToCheck = item.getReservationDate().plusDays(i);

                            if (dates.contains(dateToCheck)) {
                                datesOverlap = true;
                                break;
                            }
                        }

                        if(datesOverlap){
                            reservedItemCountPerID.put(id, reservedItemCountPerID.get(id) + 1);
                        }
                    }
                }
            }

            // iterate through items, tally the inventory
            Iterable<Item> allItems = itemService.getAllItems();
            for(Item invItem : allItems){
                long id = invItem.getProduct().getId();
                if(productIds.contains(id)) {
                    inventoryPerID.put(id, inventoryPerID.get(id) + 1);
                }
            }

            // if amount of any reservation item exceeds inventory amount, cancel
            for(long id : productIds){
                int inventoryAmount = inventoryPerID.get(id);
                int reservedAmount = reservedItemCountPerID.get(id);
                int reservationAmount = reservationItemCountPerID.get(id);
                if(inventoryAmount - (reservedAmount + reservationAmount) < 0){
                    enoughProductsAvailable = false;
                    break;
                }
            }

            if(enoughProductsAvailable) {
                httpStatus = reservationService.addReservation(reservationDTO);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.info("something went wrong in addReservation reservation controller\nError: {}", e.toString());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(httpStatus);
    }

}
