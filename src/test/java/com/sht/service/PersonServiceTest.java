package com.sht.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sht.BaseTest;
import com.sht.entity.Person;

public class PersonServiceTest extends BaseTest{
	@Autowired
	PersonService personService;
	@Ignore
	@Test
	public void getAllPerson() {
		List<Person> list = personService.getAllPerson(1,10);
		assertEquals(1, list.size());
	}
	@Test
	public void getConditionPerson() {
		List<Person> list = new ArrayList<Person>();
		list = personService.getConditionPerson("a", 1, 10);
		Logger logger = LoggerFactory.getLogger(PersonServiceTest.class);
		for(Person p:list) {
			logger.debug(p.getTrueName());
		}
	}
}
