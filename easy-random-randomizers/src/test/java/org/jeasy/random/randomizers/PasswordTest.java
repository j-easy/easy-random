package org.jeasy.random.randomizers;

import static org.junit.jupiter.api.Assertions.assertEquals;




import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.junit.jupiter.api.Test;

class PasswordTest {
	Pattern SpecialChar = Pattern.compile("[^A-Za-z0-9]");
	Pattern UpperChar = Pattern.compile("[A-Z]");

	
    @Test
    void Password_NotContainsUppercaseSpecial() {
        // given
    	PasswordRandomizer passwordRandomizer = PasswordRandomizer.aNewPasswordRandomizer(123L, 8, 20);
    	
        // when
        String randomValue = passwordRandomizer.getRandomValue();
        Matcher m1=UpperChar.matcher(randomValue);
        Matcher m2=SpecialChar.matcher(randomValue);
       
        // then
        assertEquals(false,m1.find());
        assertEquals(false,m2.find());
    }
    @Test
    void Password_ContainsUppercase() {
    	// given 
    	PasswordRandomizer passwordRandomizer = PasswordRandomizer.aNewPasswordRandomizer(123L, 8, 20,true);

        // when
        String randomValue = passwordRandomizer.getRandomValue();
        Matcher m1=UpperChar.matcher(randomValue);
        Matcher m2=SpecialChar.matcher(randomValue);
       
        // then
        assertEquals(true,m1.find());
        assertEquals(false,m2.find());
    	
    }
    @Test
    void Password_ContainsUppercaseSpecial() {
        // given
    	PasswordRandomizer passwordRandomizer = PasswordRandomizer.aNewPasswordRandomizer(123L, 8, 20,true,true);

        // when
        String randomValue = passwordRandomizer.getRandomValue();
        Matcher m1=UpperChar.matcher(randomValue);
        Matcher m2=SpecialChar.matcher(randomValue);
       
        // then
        assertEquals(true,m1.find());
        assertEquals(true,m2.find());
    }

}
