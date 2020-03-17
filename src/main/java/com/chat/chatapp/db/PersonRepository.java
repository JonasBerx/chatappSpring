package com.chat.chatapp.db;



import com.chat.chatapp.model.Person;

import java.util.List;

public interface PersonRepository {

	public abstract void add(Person person);

	public abstract void delete(String userId);

	public abstract Person get(String userId);

	public abstract List<Person> getAll();
	
	public abstract Person getAuthenticatedUser(String email, String password);

	public abstract void update(Person person);

}