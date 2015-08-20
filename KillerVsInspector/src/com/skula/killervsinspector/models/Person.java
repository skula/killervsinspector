package com.skula.killervsinspector.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.skula.killervsinspector.R;

public class Person {
	private int id;
	private String name;
	private boolean deceased;
	private boolean innocent;

	private int drawIdSuspect;
	private int drawIdOffside;

	private static List<Person> persons;
	static {
		persons = new ArrayList<Person>();

		persons.add(new Person(0, "Ernest", R.drawable.ernest_suspect, R.drawable.ernest_offside));
		persons.add(new Person(1, "Franklin", R.drawable.franklin_suspect, R.drawable.franklin_offside));
		persons.add(new Person(2, "Phoebe", R.drawable.phoebe_suspect, R.drawable.phoebe_offside));
		persons.add(new Person(3, "Horatio", R.drawable.horatio_suspect, R.drawable.horatio_offside));
		persons.add(new Person(4, "Kristoph", R.drawable.kristoph_suspect, R.drawable.kristoph_offside));
		persons.add(new Person(5, "Ryan", R.drawable.ryan_suspect, R.drawable.ryan_offside));
		persons.add(new Person(6, "Udstad", R.drawable.udstad_suspect, R.drawable.udstad_offside));
		persons.add(new Person(7, "Deidre", R.drawable.deidre_suspect, R.drawable.deidre_offside));
		persons.add(new Person(8, "Linus", R.drawable.linus_suspect, R.drawable.linus_offside));
		persons.add(new Person(9, "Quinton", R.drawable.quinton_suspect, R.drawable.quinton_offside));
		persons.add(new Person(10, "Julian", R.drawable.julian_suspect, R.drawable.julian_offside));
		persons.add(new Person(11, "Alyss", R.drawable.alyss_suspect, R.drawable.alyss_offside));
		persons.add(new Person(12, "Barrin", R.drawable.barrin_suspect, R.drawable.barrin_offside));
		persons.add(new Person(13, "Clive", R.drawable.clive_suspect, R.drawable.clive_offside));
		persons.add(new Person(14, "irma", R.drawable.irma_suspect, R.drawable.irma_offside));
		persons.add(new Person(15, "Trevor", R.drawable.trevor_suspect, R.drawable.trevor_offside));
		persons.add(new Person(16, "Vladimir", R.drawable.vladimir_suspect, R.drawable.vladimir_offside));
		persons.add(new Person(17, "Marion", R.drawable.marion_suspect, R.drawable.marion_offside));
		persons.add(new Person(18, "Niel", R.drawable.niel_suspect, R.drawable.niel_offside));
		persons.add(new Person(19, "Wilhelm", R.drawable.wilhelm_suspect, R.drawable.wilhelm_offside));
		persons.add(new Person(20, "Zachary", R.drawable.zachary_suspect, R.drawable.zachary_offside));
		persons.add(new Person(21, "Geneva", R.drawable.geneva_suspect, R.drawable.geneva_offside));
		persons.add(new Person(22, "Yvonne", R.drawable.yvonne_suspect, R.drawable.yvonne_offside));
		persons.add(new Person(23, "Simon", R.drawable.simon_suspect, R.drawable.simon_offside));
		persons.add(new Person(24, "Ophelia", R.drawable.ophelia_suspect, R.drawable.ophelia_offside));
	}

	public Person(int id, String name, int drawIdSuspect, int drawIdOffside) {
		this.id = id;
		this.name = name;
		this.deceased = false;
		this.innocent = false;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDeceased() {
		return deceased;
	}

	public void setDeceased(boolean deceased) {
		this.deceased = deceased;
	}

	public boolean isInnocent() {
		return innocent;
	}

	public void setInnocent(boolean innocent) {
		this.innocent = innocent;
	}
	
	public int getDrawableId(){
		if(isDeceased() || isInnocent()){
			return drawIdOffside;
		}else{
			return drawIdSuspect;
		}
	}
}
