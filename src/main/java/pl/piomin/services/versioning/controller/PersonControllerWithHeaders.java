package pl.piomin.services.versioning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.piomin.services.versioning.mapper.PersonMapper;
import pl.piomin.services.versioning.model.PersonCurrent;
import pl.piomin.services.versioning.model.PersonOld;
import pl.piomin.services.versioning.repository.PersonRepository;

@RestController
@RequestMapping("/persons-via-headers")
public class PersonControllerWithHeaders {

	@Autowired
	PersonMapper mapper;
	@Autowired
	PersonRepository repository;

	@PostMapping(headers = "X-VERSION=v1.0")
	public PersonOld add(@RequestBody PersonOld person) {
		return (PersonOld) repository.add(person);
	}

	@PostMapping(headers = "X-VERSION=v1.2")
	public PersonCurrent add(@RequestBody PersonCurrent person) {
		return (PersonCurrent) repository.add(person);
	}
	
	@PutMapping(headers = "X-VERSION=v1.0")
	@Deprecated
	public PersonOld update(@RequestBody PersonOld person) {
		return (PersonOld) repository.update(person);
	}
	
	@PutMapping(value = "/{id}", headers = "X-VERSION=v1.1")
	public PersonOld update(@PathVariable("id") Long id, @RequestBody PersonOld person) {
		return (PersonOld) repository.update(person);
	}
	
	@PutMapping(value = "/{id}", headers = "X-VERSION=v1.2")
	public PersonCurrent update(@PathVariable("id") Long id, @RequestBody PersonCurrent person) {
		return mapper.map((PersonOld) repository.update(person));
	}
	
	@GetMapping(value = "/{id}", headers = "X-VERSION=v1.0")
	public PersonOld findByIdOld(@PathVariable("id") Long id) {
		return (PersonOld) repository.findById(id);
	}

	@GetMapping(value = "/{id}", headers = "X-VERSION=v1.2")
	public PersonCurrent findById(@PathVariable("id") Long id) {
		return mapper.map((PersonOld) repository.findById(id));
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		repository.delete(id);
	}
	
}
