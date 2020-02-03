package com.sht.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sht.entity.Person;

public interface PersonDao {
	List<Person> getAllPerson(@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);
	
	
	@Select("select count(*) from tb_person")
	int getPersonCount();
	
	@Select("select * from tb_person where user_name like '%${condition}%' or true_name like '%${condition}%' order by priority DESC LIMIT #{rowIndex},#{pageSize}")
	List<Person> getConditionPerson(@Param("condition")String condition,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);
	
	int addPerson(Person person);
	
	int updatePerson(Person person);
	
	@Select("select * from tb_person where user_id=#{id}")
	Person getPersonById(int id);
	
	@Select("select * from tb_person where user_name='${username}'")
	List<Person> getPersonByUserName(@Param("username")String username);
	
	int deletePersonById(int userId);
	
	@Select("select * from tb_person where user_name=#{userName} and pass_word=#{passWord}")
	List<Person> loginCheck(@Param("userName")String userName,@Param("passWord")String passWord);
}
