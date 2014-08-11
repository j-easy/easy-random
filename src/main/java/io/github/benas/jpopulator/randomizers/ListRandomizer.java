/**
 * 
 */
package io.github.benas.jpopulator.randomizers;

import java.util.ArrayList;
import java.util.List;

import io.github.benas.jpopulator.api.Randomizer;

/**
 * A custom randomizer that generates a list of random values from another {@link Randomizer}
 * @author Eric Taix (eric.taix@gmail.com)
 */
public class ListRandomizer<T> implements Randomizer<List<T>> {

	private Randomizer<T> elementRenadomizer;
	private int nb;
	
	/**
	 * Default contructor 
	 * @param elementRandomizer The randomizer used to generate each element
	 * @param nb The number of elements in the list to generate
	 */
	public ListRandomizer(Randomizer<T> elementRandomizer, int nb) {
		this.elementRenadomizer = elementRandomizer;
		this.nb = nb;
	}
	
	@Override
	public List<T> getRandomValue() {
		List<T> result = new ArrayList<T>();
		for(int i = 0; i < nb; i++) {
			result.add(elementRenadomizer.getRandomValue());
		}
		return result;
	}

}
