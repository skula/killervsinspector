package com.skula.killervsinspector.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.skula.killervsinspector.R;

public class Person {
	public static final int SUSPECT = 0;
	public static final int DECEASED = 1;
	public static final int INNOCENT = 2;

	private int id;
	private int state;
	private int drawIdSuspect;
	private int drawIdOffside;

	private static List<Person> persons;

	static {
		persons = new ArrayList<Person>();

		persons.add(new Person(0, Person.SUSPECT, R.drawable.ernest_suspect, R.drawable.ernest_offside));
		persons.add(new Person(0, Person.SUSPECT, R.drawable.franklin_suspect, R.drawable.franklin_offside));
		persons.add(new Person(0, Person.SUSPECT, R.drawable.phoebe_suspect, R.drawable.phoebe_offside));
		persons.add(new Person(0, Person.SUSPECT, R.drawable.horatio_suspect, R.drawable.horatio_offside));
		persons.add(new Person(0, Person.SUSPECT, R.drawable.kristoph_suspect, R.drawable.kristoph_offside));
		persons.add(new Person(0, Person.SUSPECT, R.drawable.ryan_suspect, R.drawable.ryan_offside));
		persons.add(new Person(0, Person.SUSPECT, R.drawable.udstad_suspect, R.drawable.udstad_offside));
		persons.add(new Person(0, Person.SUSPECT, R.drawable.deidre_suspect, R.drawable.deidre_offside));
		persons.add(new Person(0, Person.SUSPECT, R.drawable.linus_suspect, R.drawable.linus_offside));
		persons.add(new Person(0, Person.SUSPECT, R.drawable.quinton_suspect, R.drawable.quinton_offside));
		persons.add(new Person(0, Person.SUSPECT, R.drawable.julian_suspect, R.drawable.julian_offside));
		persons.add(new Person(0, Person.SUSPECT, R.drawable.alyss_suspect, R.drawable.alyss_offside));
		persons.add(new Person(0, Person.SUSPECT, R.drawable.barrin_suspect, R.drawable.barrin_offside));
		persons.add(new Person(0, Person.SUSPECT, R.drawable.clive_suspect, R.drawable.clive_offside));
		persons.add(new Person(0, Person.SUSPECT, R.drawable.irma_suspect, R.drawable.irma_offside));
		persons.add(new Person(0, Person.SUSPECT, R.drawable.trevor_suspect, R.drawable.trevor_offside));
		persons.add(new Person(0, Person.SUSPECT, R.drawable.vladimir_suspect, R.drawable.vladimir_offside));
		persons.add(new Person(0, Person.SUSPECT, R.drawable.marion_suspect, R.drawable.marion_offside));
		persons.add(new Person(0, Person.SUSPECT, R.drawable.niel_suspect, R.drawable.niel_offside));
		persons.add(new Person(0, Person.SUSPECT, R.drawable.wilhelm_suspect, R.drawable.wilhelm_offside));
		persons.add(new Person(0, Person.SUSPECT, R.drawable.zachary_suspect, R.drawable.zachary_offside));
		persons.add(new Person(0, Person.SUSPECT, R.drawable.geneva_suspect, R.drawable.geneva_offside));
		persons.add(new Person(0, Person.SUSPECT, R.drawable.yvonne_suspect, R.drawable.yvonne_offside));
		persons.add(new Person(0, Person.SUSPECT, R.drawable.simon_suspect, R.drawable.simon_offside));
		persons.add(new Person(0, Person.SUSPECT, R.drawable.ophelia_suspect, R.drawable.ophelia_offside));
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
		if (state == Person.SUSPECT) {
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
