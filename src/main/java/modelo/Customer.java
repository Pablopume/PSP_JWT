package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
private int id;
private String name;
private String lastName;
private String email;
private String phone;
private LocalDate birthDate;

}
