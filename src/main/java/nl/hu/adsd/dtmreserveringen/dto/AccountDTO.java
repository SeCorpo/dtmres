package nl.hu.adsd.dtmreserveringen.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



//ACCOUNT-DTO SHOULD BE USED FOR ACCOUNT (ENTITY) RELATED STUFF (LIKE CHANGE PASSWORD OR ),
//NOT FOR OTHER DATA HANDLING, THEREFOR USE SECURE KEY REPRESENTING ACCOUNT

@Getter
@Setter
@ToString
public class AccountDTO {

    private String email;

    public AccountDTO(String email) {
        this.email = email;
    }
}
