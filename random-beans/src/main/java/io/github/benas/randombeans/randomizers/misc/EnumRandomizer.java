/**
 * The MIT License
 *
 *   Copyright (c) 2017, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */
package io.github.benas.randombeans.randomizers.misc;

import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.randomizers.AbstractRandomizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A {@link Randomizer} that generates a random value from a given {@link Enum}.
 *
 * @param <E> the type of elements in the enumeration
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class EnumRandomizer<E extends Enum<E>> extends AbstractRandomizer<E> {

    private final Class<E> enumeration;
	private E[] excludedValues;

    /**
     * Create a new {@link EnumRandomizer}.
     *
     * @param enumeration the enumeration from which this randomizer will generate random values
     */
    public EnumRandomizer(final Class<E> enumeration) {
        super();
        this.enumeration = enumeration;
    }

    /**
     * Create a new {@link EnumRandomizer}.
     *
     * @param enumeration the enumeration from which this randomizer will generate random values
     * @param seed        the initial seed
     */
    public EnumRandomizer(final Class<E> enumeration, final long seed) {
        super(seed);
        this.enumeration = enumeration;
    }

	/**
	 * Create a new {@link EnumRandomizer}.
	 *
	 * @param enumeration
	 *            the enumeration from which this randomizer will generate
	 *            random values
	 * @param seed
	 *            the initial seed
	 */
	public EnumRandomizer(final Class<E> enumeration, E... excludedValues) {
		super();
		this.enumeration = enumeration;
		this.excludedValues = excludedValues;
	}

    /**
     * Create a new {@link EnumRandomizer}.
     *
     * @param enumeration the enumeration from which this randomizer will generate random values
     * @param <E>         the type of elements in the enumeration
     * @return a new {@link EnumRandomizer}.
     */
    public static <E extends Enum<E>> EnumRandomizer<E> aNewEnumRandomizer(final Class<E> enumeration) {
        return new EnumRandomizer<>(enumeration);
    }

    /**
     * Create a new {@link EnumRandomizer}.
     *
     * @param enumeration the enumeration from which this randomizer will generate random values
     * @param seed        the initial seed
     * @param <E>         the type of elements in the enumeration
     * @return a new {@link EnumRandomizer}.
     */
    public static <E extends Enum<E>> EnumRandomizer<E> aNewEnumRandomizer(final Class<E> enumeration, final long seed) {
        return new EnumRandomizer<>(enumeration, seed);
    }

	/**
	 * Create a new {@link EnumRandomizer}.
	 *
	 * @param enumeration
	 *            the enumeration from which this randomizer will generate
	 *            random values
	 * @param excludedValues
	 *            the values to exclude from the subset in which the random
	 *            value will be picked
	 * @param <E>
	 *            the type of elements in the enumeration
	 * @return a new {@link EnumRandomizer}.
	 */
	public static <E extends Enum<E>> EnumRandomizer<E> aNewEnumRandomizer(final Class<E> enumeration, E... excludedValues) {
		return new EnumRandomizer<>(enumeration, excludedValues);
	}

	/**
	 * Get a random value within an enumeration or an enumeration subset (when
	 * values are excluded)
	 *
	 * @param enumClass
	 *            enum class in which a random value is picked
	 * @param excludedValues
	 *            the values to exclude from the random picking
	 * @return a random value within the enumeration
	 * @throws NoSuchElementException
	 *             when no value is available in the subset
	 */
	@Override
	public E getRandomValue() throws NoSuchElementException {
		List<E> enumConstants = getFilteredList();
		if (enumConstants.isEmpty()) {
			throw new NoSuchElementException("No enum element available for random picking.");
		}
		int randomIndex = random.nextInt(enumConstants.size());
		return enumConstants.get(randomIndex);
	}

	/**
	 * Get a subset of enumeration
	 *
	 * @param enumClass
	 *            the enumeration class in which values are picked
	 * @param excludedValues
	 *            the values to exclude from the subset
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<E> getFilteredList() {
		List<E> filteredValues = new ArrayList<>();
		Collections.addAll(filteredValues, enumeration.getEnumConstants());
		if (excludedValues != null) {
			for (E element : excludedValues) {
				filteredValues.remove(element);
			}
		}
		return filteredValues;
	}
}
