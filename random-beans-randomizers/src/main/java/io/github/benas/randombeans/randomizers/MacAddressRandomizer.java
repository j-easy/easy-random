package io.github.benas.randombeans.randomizers;

import java.util.Locale;

import io.github.benas.randombeans.api.Randomizer;

/**
 * A {@link Randomizer} that generates random mac addresses.
 *
 * @author Michael Düsterhus
 */
public class MacAddressRandomizer extends FakerBasedRandomizer<String> {

	/**
	 * Create a new {@link MacAddressRandomizer}.
	 */
	public MacAddressRandomizer() {
	}

	/**
	 * Create a new {@link MacAddressRandomizer}.
	 *
	 * @param seed
	 *          the initial seed
	 */
	public MacAddressRandomizer(long seed) {
		super(seed);
	}

	/**
	 * Create a new {@link MacAddressRandomizer}.
	 *
	 * @param seed
	 *          the initial seed
	 * @param locale
	 *          the locale to use
	 */
	public MacAddressRandomizer(final long seed, final Locale locale) {
		super(seed, locale);
	}

	/**
	 * Create a new {@link MacAddressRandomizer}.
	 *
	 * @return a new {@link MacAddressRandomizer}
	 */
	public static MacAddressRandomizer aNewMacAddressRandomizer() {
		return new MacAddressRandomizer();
	}

	/**
	 * Create a new {@link MacAddressRandomizer}.
	 *
	 * @param seed
	 *          the initial seed
	 * @return a new {@link MacAddressRandomizer}
	 */
	public static MacAddressRandomizer aNewMacAddressRandomizer(final long seed) {
		return new MacAddressRandomizer(seed);
	}

	/**
	 * Create a new {@link MacAddressRandomizer}.
	 *
	 * @param seed
	 *          the initial seed
	 * @param locale
	 *          the locale to use
	 * @return a new {@link MacAddressRandomizer}
	 */
	public static MacAddressRandomizer aNewMacAddressRandomizer(final long seed, final Locale locale) {
		return new MacAddressRandomizer(seed, locale);
	}

	@Override
	public String getRandomValue() {
		return faker.internet().macAddress();
	}

}
