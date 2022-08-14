package Data.Domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credits {
    int id;
    String firstName;
    String lastName;
    double amount;
    Date dueDate;
    String phoneNumber;
    String email;
    char gender;

}