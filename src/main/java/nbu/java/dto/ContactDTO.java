package nbu.java.dto;

import lombok.Getter;
import lombok.Setter;
import nbu.java.entity.AdditionalField;
import nbu.java.entity.Contact;
import nbu.java.entity.User;
import java.util.List;

@Getter
@Setter
public class ContactDTO {

    private int id;
    private String firstname;
    private String lastname;
    private String companyname;
    private String address;
    private String phonenumber;
    private String email;
    private String faxnumber;
    private String mobilephone;
    private String comment;

    private Contact.Label label;
    private List<AdditionalField> additionalFields;
    private User user;

    public ContactDTO() {
    }
}
