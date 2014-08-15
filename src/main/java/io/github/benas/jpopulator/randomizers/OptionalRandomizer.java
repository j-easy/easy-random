/**
 * 
 */
package io.github.benas.jpopulator.randomizers;

import java.util.Random;

import io.github.benas.jpopulator.api.Randomizer;

/**
 * A randomizer which, according to the optional percent, returns the random value from a delegate or null.<br>
 * If the returned value is null then the field is not set
 * @author etaix
 */
public class OptionalRandomizer<T> implements Randomizer<T> {

	private final Random randomPercent = new Random(System.currentTimeMillis());
	private Randomizer<T> delegate;
	private int optionalPercent;
	
	/**
	 * Default contructor
	 * @param delegate The delegate to use to retrieve a random value
	 * @param optionalPercent The percent of randomized value to return (between 0 and 100)
	 */
	public OptionalRandomizer(Randomizer<T> delegate, int optionalPercent) {
		this.delegate = delegate;
		this.optionalPercent = optionalPercent > 100 ? 100 : (optionalPercent < 0 ? 0 : optionalPercent);
	}
	
	@Override
	public T getRandomValue() {
		if (randomPercent.nextInt(100)+1 <= optionalPercent) {
			return delegate.getRandomValue();
		}
		return null;
	}
	
	/**
	 * Factory method to help creating an optional randomizer
	 * @param delegate
	 * @param optionalPercent
	 * @return
	 */
	public static <T> Randomizer<T> option(Randomizer<T> delegate, int optionalPercent) {
		return new OptionalRandomizer<T>(delegate, optionalPercent);
	}

}
