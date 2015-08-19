package com.skula.killervsinspector.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Person {
	public static final int SUSPECTED = 0;
	public static final int DECEASED = 1;
	public static final int INNOCENT = 2;

	private int id;
	private int state;
	private int drawIdSuspect;
	private int drawIdOffside;

	private static List<Person> persons;

	static {
		persons = new ArrayList<Person>();

		// TODO: a faire pour chaque carte
		for (int i = 0; i < 25; i++) {
			persons.add(new Person(i, Person.SUSPECTED, 0, 0));
		}
	}

	public static void main(String arg[]) {
		Person.shufflePersons();
		System.out.println(Person.getAllPersons());
	}

	public Person() {
	}

	public Person(int id, int state, int drawIdSuspect, int drawIdOffside) {
		this.id = id;
		this.state = state;
		this.drawIdSuspect = drawIdSuspect;
		this.drawIdOffside = drawIdOffside;
	}

	public static void shufflePersons() {
		Collections.shuffle(persons);
	}

	public static List<Person> getAllPersons() {
		return persons;
	}

	public static Person getPerson(int n) {
		return persons.get(n);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getDrawableId() {
		if (state == Person.SUSPECTED) {
			return drawIdSuspect;
		} else {
			return drawIdOffside;
		}
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", state=" + state + "]";
	}
}
