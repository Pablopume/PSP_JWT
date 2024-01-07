package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Autor {
private int id;
private String nombre;
private String pais;
private LocalDate fechaNacimiento;
private boolean activado;
private LocalDateTime fechaActivacion;
private String codigoActivacion;
private String email;

private String password;
}
