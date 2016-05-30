package io.github.benas.randombeans.api;

import io.github.benas.randombeans.util.Constants;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * Parameters of an {@link EnhancedRandom} instance.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class EnhancedRandomParameters {

    private long seed;

    private int maxCollectionSize;

    private int maxStringLength;

    private Charset charset;

    private boolean scanClasspathForConcreteTypes;

    public EnhancedRandomParameters() {
        scanClasspathForConcreteTypes = false;
        seed = new Random().nextLong();
        maxCollectionSize = Constants.MAX_COLLECTION_SIZE;
        maxStringLength = Constants.MAX_STRING_LENGTH;
        charset = StandardCharsets.US_ASCII;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public int getMaxCollectionSize() {
        return maxCollectionSize;
    }

    public void setMaxCollectionSize(int maxCollectionSize) {
        this.maxCollectionSize = maxCollectionSize;
    }

    public boolean isScanClasspathForConcreteTypes() {
        return scanClasspathForConcreteTypes;
    }

    public void setScanClasspathForConcreteTypes(boolean scanClasspathForConcreteTypes) {
        this.scanClasspathForConcreteTypes = scanClasspathForConcreteTypes;
    }

    public int getMaxStringLength() {
        return maxStringLength;
    }

    public void setMaxStringLength(int maxStringLength) {
        this.maxStringLength = maxStringLength;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }
}
