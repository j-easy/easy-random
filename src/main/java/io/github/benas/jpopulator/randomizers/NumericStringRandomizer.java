package io.github.benas.jpopulator.randomizers;

import java.util.Random;

public class NumericStringRandomizer extends GenericStringRandomizer {

	private static String[] words = new String[1];
	private static Random random = new Random();

	public NumericStringRandomizer() {
		super(addNumbersToWords(null, null));
	}

	public NumericStringRandomizer(Integer minNumericValue, Integer maxNumericValue) {
		super(addNumbersToWords(minNumericValue, maxNumericValue));
	}

	private static  String[] addNumbersToWords(Integer minNumericValue, Integer maxNumericValue) {
		Integer randomNum;
		if (maxNumericValue != null && minNumericValue != null){
			randomNum = random.nextInt((maxNumericValue - minNumericValue) + 1) + minNumericValue;
		}
		else {
			randomNum = random.nextInt();
		}
		words[0] = randomNum.toString();
		return words;
	}
	
}
