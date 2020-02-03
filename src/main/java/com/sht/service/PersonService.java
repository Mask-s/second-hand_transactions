package com.sht.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sht.entity.Person;

public interface PersonService {
	List<Person> getAllPerson(int pageIndex,int pageSize);
	
	int getPersonCount();
	
	List<Person> getConditionPerson(String condition,int pageIndex,int pageSize);
	
	int addPerson(Person person);
	
	int updatePerson(Person person);
	
	Person getPersonById(int id);
	
	List<Person> getPersonByUserName(String username);
	
	int deletePersonById(int userId);
	
	List<Person> loginCheck(String userName,String passWord);
}
