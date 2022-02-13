package one.digitalinnovation.personapi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {

	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private String cpf;
	
	private LocalDate birthDate;
	
	private List<Phone> phones;
}
