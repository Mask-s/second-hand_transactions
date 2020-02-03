package com.sht.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sht.dao.PersonDao;
import com.sht.entity.Person;
import com.sht.exceptions.MyRuntimeException;
import com.sht.service.PersonService;
import com.sht.util.PageCalculator;

import ch.qos.logback.classic.pattern.Util;

@Service
public class PersonServiceImpl implements PersonService{
	@Autowired
	private PersonDao personDao;
	

	@Override
	public List<Person> getAllPerson(int pageIndex, int pageSize) {
		int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
		return personDao.getAllPerson(rowIndex, pageSize);
	}


	@Override
	public int getPersonCount() {
		return personDao.getPersonCount();
	}


	@Override
	public List<Person> getConditionPerson(String condition, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
		return personDao.getConditionPerson(condition, rowIndex, pageSize);
	}


	/**
	 * count:成功，0：数据为空，-1：数据已存在
	 */
	@Override
	@Transactional
	public int addPerson(Person person) {
		if(person==null) {
			return 0;
		}
		List<Person> persons = new ArrayList<Person>();
		persons = getPersonByUserName(person.getUserName());
		if(persons == null || persons.size()==0) {
			try {
				person.setMoney(10);
				person.setPriority(1);
				int count = personDao.addPerson(person);
				return count;
			} catch (RuntimeException e) {
				new MyRuntimeException("addPerson Err"+e.getMessage());
			}			
		}else {
			return -1;
		}		
		return -2;
	}


	/**
	 * 0:数据为空，count：成功
	 */
	
	@Override
	@Transactional
	public int updatePerson(Person person) {
		if(person==null) {
			return 0;
		}
		try {
			Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);
			logger.debug(""+person.getMoney()+"???"+person.getUserId());
			int count = personDao.updatePerson(person);
			return count;
		} catch (RuntimeException e) {
			new MyRuntimeException("updatePerson Err"+e.getMessage());
		}
		return 0;
	}


	@Override
	public Person getPersonById(int id) {		
		return personDao.getPersonById(id);
	}


	@Override
	public List<Person> getPersonByUserName(String username) {		
		return personDao.getPersonByUserName(username);
	}


	@Override
	@Transactional
	public int deletePersonById(int userId) {
		int count = 0;
		try {
			count = personDao.deletePersonById(userId);
		}catch (MyRuntimeException e) {
			new MyRuntimeException("del err"+e.getMessage());
		}
		return count;
	}


	@Override
	public List<Person> loginCheck(String userName, String passWord) {
		return personDao.loginCheck(userName, passWord);
	}
}
