package io.github.benas.randombeans.beans;

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

    /*
     * Getters and setters
     */
    
    public boolean[] getBooleanArray() {
        return booleanArray;
    }

    public void setBooleanArray(boolean[] booleanArray) {
        this.booleanArray = booleanArray;
    }

    public Boolean[] getBooleans() {
        return booleans;
    }

    public void setBooleans(Boolean[] booleans) {
        this.booleans = booleans;
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    public Byte[] getBytes() {
        return bytes;
    }

    public void setBytes(Byte[] bytes) {
        this.bytes = bytes;
    }

    public Character[] getCharacters() {
        return characters;
    }

    public void setCharacters(Character[] characters) {
        this.characters = characters;
    }

    public char[] getCharArray() {
        return charArray;
    }

    public void setCharArray(char[] charArray) {
        this.charArray = charArray;
    }

    public double[] getDoubleArray() {
        return doubleArray;
    }

    public void setDoubleArray(double[] doubleArray) {
        this.doubleArray = doubleArray;
    }

    public Double[] getDoubles() {
        return doubles;
    }

    public void setDoubles(Double[] doubles) {
        this.doubles = doubles;
    }

    public float[] getFloatArray() {
        return floatArray;
    }

    public void setFloatArray(float[] floatArray) {
        this.floatArray = floatArray;
    }

    public Float[] getFloats() {
        return floats;
    }

    public void setFloats(Float[] floats) {
        this.floats = floats;
    }

    public int[] getIntArray() {
        return intArray;
    }

    public void setIntArray(int[] intArray) {
        this.intArray = intArray;
    }

    public Integer[] getIntegers() {
        return integers;
    }

    public void setIntegers(Integer[] integers) {
        this.integers = integers;
    }

    public long[] getLongArray() {
        return longArray;
    }

    public void setLongArray(long[] longArray) {
        this.longArray = longArray;
    }

    public Long[] getLongs() {
        return longs;
    }

    public void setLongs(Long[] longs) {
        this.longs = longs;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public short[] getShortArray() {
        return shortArray;
    }

    public void setShortArray(short[] shortArray) {
        this.shortArray = shortArray;
    }

    public Short[] getShorts() {
        return shorts;
    }

    public void setShorts(Short[] shorts) {
        this.shorts = shorts;
    }

    public String[] getStrings() {
        return strings;
    }

    public void setStrings(String[] strings) {
        this.strings = strings;
    }
}
