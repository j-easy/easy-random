package io.github.benas.jpopulator.beans;

public class Husband extends Person {
    private Wife wife;

	public Wife getWife() {
		return wife;
	}

	public void setWife(Wife wife) {
		this.wife = wife;
	}
}
