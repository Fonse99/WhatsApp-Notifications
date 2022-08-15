package Data.Domain;

// import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credits {
    String id;
    String firstName;
    String lastName;
    String ruc;
    String address;
    String city;
    String state;
    String phoneNumber;
    int term;
    double amount;
    // Date dueDate;
    // String email;
    // char gender;

}