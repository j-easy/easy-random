package io.github.benas.jpopulator.beans;

public class Wife extends Person {
    private Husband husband;

	public Husband getHusband() {
		return husband;
	}

	public void setHusband(Husband husband) {
		this.husband = husband;
	}
}
