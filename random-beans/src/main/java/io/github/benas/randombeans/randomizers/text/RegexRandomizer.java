/**
 * The MIT License
 *
 *   Copyright (c) 2016, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package io.github.benas.randombeans.randomizers.text;

import com.mifmif.common.regex.Generex;

import io.github.benas.randombeans.randomizers.AbstractRandomizer;

/**
 * Generate a random {@link String} that matches a regular expression
 * 
 * @author Rebecca McQuary
 *
 */
public class RegexRandomizer extends AbstractRandomizer<String>{

	Generex generex;
	
	/**
     * Create a new {@link RegexRandomizer}.
     *
     * @param regex to create string from
     */
	public RegexRandomizer(String regex){
		generex = new Generex(regex);
	}
	
	/**
	 * Create a new {@link RegexRandomizer}.
	 * @param seed the initial seed
	 * @param regex to create string from
	 */
	public RegexRandomizer(long seed, String regex){
		generex = new Generex(regex);
		generex.setSeed(seed);
	}
	
	@Override
	public String getRandomValue() {
		return generex.random();
	}

}
