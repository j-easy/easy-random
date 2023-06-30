/*
 * The MIT License
 *
 *   Copyright (c) 2023, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.random.beans;

public class ArrayBean {

    /*
     * primitive types
     */
    private byte[] byteArray;
    private short[] shortArray;
    private int[] intArray;
    private long[] longArray;
    private float[] floatArray;
    private double[] doubleArray;
    private boolean[] booleanArray;
    private char[] charArray;

    /*
     * wrapper types
     */
    private Byte[] bytes;
    private Short[] shorts;
    private Integer[] integers;
    private Long[] longs;
    private Float[] floats;
    private Double[] doubles;
    private Boolean[] booleans;
    private Character[] characters;
    
    /*
     * custom types
     */
    private String[] strings;
    private Person[] persons;

	public ArrayBean() {
	}


	public byte[] getByteArray() {
		return this.byteArray;
	}

	public short[] getShortArray() {
		return this.shortArray;
	}

	public int[] getIntArray() {
		return this.intArray;
	}

	public long[] getLongArray() {
		return this.longArray;
	}

	public float[] getFloatArray() {
		return this.floatArray;
	}

	public double[] getDoubleArray() {
		return this.doubleArray;
	}

	public boolean[] getBooleanArray() {
		return this.booleanArray;
	}

	public char[] getCharArray() {
		return this.charArray;
	}

	public Byte[] getBytes() {
		return this.bytes;
	}

	public Short[] getShorts() {
		return this.shorts;
	}

	public Integer[] getIntegers() {
		return this.integers;
	}

	public Long[] getLongs() {
		return this.longs;
	}

	public Float[] getFloats() {
		return this.floats;
	}

	public Double[] getDoubles() {
		return this.doubles;
	}

	public Boolean[] getBooleans() {
		return this.booleans;
	}

	public Character[] getCharacters() {
		return this.characters;
	}

	public String[] getStrings() {
		return this.strings;
	}

	public Person[] getPersons() {
		return this.persons;
	}

	public void setByteArray(byte[] byteArray) {
		this.byteArray = byteArray;
	}

	public void setShortArray(short[] shortArray) {
		this.shortArray = shortArray;
	}

	public void setIntArray(int[] intArray) {
		this.intArray = intArray;
	}

	public void setLongArray(long[] longArray) {
		this.longArray = longArray;
	}

	public void setFloatArray(float[] floatArray) {
		this.floatArray = floatArray;
	}

	public void setDoubleArray(double[] doubleArray) {
		this.doubleArray = doubleArray;
	}

	public void setBooleanArray(boolean[] booleanArray) {
		this.booleanArray = booleanArray;
	}

	public void setCharArray(char[] charArray) {
		this.charArray = charArray;
	}

	public void setBytes(Byte[] bytes) {
		this.bytes = bytes;
	}

	public void setShorts(Short[] shorts) {
		this.shorts = shorts;
	}

	public void setIntegers(Integer[] integers) {
		this.integers = integers;
	}

	public void setLongs(Long[] longs) {
		this.longs = longs;
	}

	public void setFloats(Float[] floats) {
		this.floats = floats;
	}

	public void setDoubles(Double[] doubles) {
		this.doubles = doubles;
	}

	public void setBooleans(Boolean[] booleans) {
		this.booleans = booleans;
	}

	public void setCharacters(Character[] characters) {
		this.characters = characters;
	}

	public void setStrings(String[] strings) {
		this.strings = strings;
	}

	public void setPersons(Person[] persons) {
		this.persons = persons;
	}
}
