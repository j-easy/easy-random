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
package org.jeasy.random;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

@Disabled("Dummy test to see possible reasons of randomization failures")
class ClassPathScannerTest {

    @Test
    void randomizeAllPublicConcreteTypesInTheClasspath(){
        EasyRandomParameters easyRandomParameters = new EasyRandomParameters()
                .scanClasspathForConcreteTypes(true);
        EasyRandom easyRandom = new EasyRandom(easyRandomParameters);
        ClassPathScanner classPathScanner = new ClassPathScanner();
        int success = 0;
        int failure = 0;
        List<Class<?>> publicConcreteTypes = classPathScanner.getPublicConcreteSubTypesOf(Object.class);
        System.out.println("Found " + publicConcreteTypes.size() + " public concrete types in the classpath");
        for (Class<?> aClass : publicConcreteTypes) {
            try {
                easyRandom.nextObject(aClass);
                System.out.println(aClass.getName() + " has been successfully randomized");
                success++;
            } catch (Throwable e) {
                System.err.println("Unable to populate a random instance of type: " + aClass.getName());
                e.printStackTrace();
                System.err.println("----------------------------------------------");
                failure++;
            }
        }
        System.out.println("Success: " + success);
        System.out.println("Failure: " + failure);
    }

    @Test
    void randomizeAllPublicConcreteTypesInGivenPackages(){
        String[] packages = {"org.jeasy.random.beans", "org.jeasy.random.context"};
        EasyRandomParameters easyRandomParameters = new EasyRandomParameters()
                .scanClasspathForConcreteTypes(true, packages);
        EasyRandom easyRandom = new EasyRandom(easyRandomParameters);
        ClassPathScanner classPathScanner = new ClassPathScanner(packages);
        int success = 0;
        int failure = 0;
        List<Class<?>> publicConcreteTypes = classPathScanner.getPublicConcreteSubTypesOf(Object.class);
        System.out.println("Found " + publicConcreteTypes.size() + " public concrete types in: " + String.join(", ", packages));
        for (Class<?> aClass : publicConcreteTypes) {
            try {
                easyRandom.nextObject(aClass);
                System.out.println(aClass.getName() + " has been successfully randomized");
                success++;
            } catch (Throwable e) {
                System.err.println("Unable to populate a random instance of type: " + aClass.getName());
                e.printStackTrace();
                System.err.println("----------------------------------------------");
                failure++;
            }
        }
        System.out.println("Success: " + success);
        System.out.println("Failure: " + failure);
    }

}