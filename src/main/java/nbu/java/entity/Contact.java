package nbu.java.entity;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private int id;

    @Expose
    private String firstname;
    @Expose
    private String lastname;
    @Expose
    private String companyname;
    @Expose
    private String address;
    @Expose
    private String phonenumber;
    @Expose
    private String email;
    @Expose
    private String faxnumber;
    @Expose
    private String mobilephone;
    @Expose
    private String comment;

    @Expose
    @Enumerated(EnumType.STRING)
    private Label label;

    @OneToMany(mappedBy = "contact")
    private List<AdditionalField> additionalFields;

    @ManyToOne
    private User user;

    public enum Label {
        WHITE, YELLOW, GREEN, RED, BLUE, GREY, BLACK
    }
}
