package one.digitalinnovation.personapi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import one.digitalinnovation.personapi.dto.MessageResponseDTO;
import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import one.digitalinnovation.personapi.mapper.PersonMapper;
import one.digitalinnovation.personapi.repository.PersonRepository;

@Service
public class PersonService {

	private PersonRepository personRepository;
	
	private final PersonMapper personMapper = PersonMapper.INSTANCE;
	
	@Autowired
	public PersonService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}
	
	public MessageResponseDTO postPerson(PersonDTO personDTO) {
		Person personToSave = personMapper.toModel(personDTO);
		
		Person savedPerson = personRepository.save(personToSave);
		String s = "Created person with ID ";
		return createMessageResponse(savedPerson.getId(), s);
	}

	public List<PersonDTO> listAll() {
		List<Person> allPeople = personRepository.findAll();
		return allPeople.stream()
				.map(personMapper::toDTO)
				.collect(Collectors.toList());
	}
	
	public PersonDTO findById(Long id) throws PersonNotFoundException {
		Person person = verifyIfExists(id);
		
		return personMapper.toDTO(person);
	}
	
	public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException{
		verifyIfExists(id);
		
		Person personToUpdate = personMapper.toModel(personDTO);
		
		Person updatedPerson = personRepository.save(personToUpdate);
		String s = "Updated person with ID ";
		return createMessageResponse(updatedPerson.getId(), s);
	}

	public void delete(Long id) throws PersonNotFoundException {
		verifyIfExists(id);
		
		personRepository.deleteById(id);
	}
	
	private MessageResponseDTO createMessageResponse(Long id, String s) {
		return MessageResponseDTO
				.builder()
				.message(s + id)
				.build();
	}

	private Person verifyIfExists(Long id) throws PersonNotFoundException {
		return personRepository.findById(id)
				.orElseThrow(() -> new PersonNotFoundException(id));
	}
}
