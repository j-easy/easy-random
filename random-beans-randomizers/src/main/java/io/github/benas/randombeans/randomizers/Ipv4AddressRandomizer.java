package io.github.benas.randombeans.randomizers;

import java.util.Locale;

import io.github.benas.randombeans.api.Randomizer;

/**
 * A {@link Randomizer} that generates random IPv4 addresses.
 *
 * @author Michael Düsterhus
 */
public class Ipv4AddressRandomizer extends FakerBasedRandomizer<String> {

	/**
	 * Create a new {@link Ipv4AddressRandomizer}.
	 */
	public Ipv4AddressRandomizer() {
	}

	/**
	 * Create a new {@link Ipv4AddressRandomizer}.
	 *
	 * @param seed
	 *          the initial seed
	 */
	public Ipv4AddressRandomizer(long seed) {
		super(seed);
	}

	/**
	 * Create a new {@link Ipv4AddressRandomizer}.
	 *
	 * @param seed
	 *          the initial seed
	 * @param locale
	 *          the locale to use
	 */
	public Ipv4AddressRandomizer(final long seed, final Locale locale) {
		super(seed, locale);
	}

	/**
	 * Create a new {@link Ipv4AddressRandomizer}.
	 *
	 * @return a new {@link Ipv4AddressRandomizer}
	 */
	public static Ipv4AddressRandomizer aNewIpv4AddressRandomizer() {
		return new Ipv4AddressRandomizer();
	}

	/**
	 * Create a new {@link Ipv4AddressRandomizer}.
	 *
	 * @param seed
	 *          the initial seed
	 * @return a new {@link Ipv4AddressRandomizer}
	 */
	public static Ipv4AddressRandomizer aNewIpv4AddressRandomizer(final long seed) {
		return new Ipv4AddressRandomizer(seed);
	}

	/**
	 * Create a new {@link Ipv4AddressRandomizer}.
	 *
	 * @param seed
	 *          the initial seed
	 * @param locale
	 *          the locale to use
	 * @return a new {@link Ipv4AddressRandomizer}
	 */
	public static Ipv4AddressRandomizer aNewIpv4AddressRandomizer(final long seed, final Locale locale) {
		return new Ipv4AddressRandomizer(seed, locale);
	}

	@Override
	public String getRandomValue() {
		return faker.internet().ipV4Address();
	}

}
