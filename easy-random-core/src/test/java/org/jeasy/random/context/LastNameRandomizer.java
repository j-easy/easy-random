/**
 * The MIT License
 *
 *   Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.random.context;

import org.jeasy.random.api.ContextAwareRandomizer;
import org.jeasy.random.api.RandomizerContext;

/**
 * A last name randomizer that depends on the first name of the currently randomized object.
 * The currently randomized object can be retrieved from the randomization context.
 */
public class LastNameRandomizer implements ContextAwareRandomizer<String> {

    private RandomizerContext context;

    @Override
    public void setRandomizerContext(RandomizerContext context) {
        this.context = context;
    }

    @Override
    public String getRandomValue() {
        String firstName = null;
        if (context.getCurrentObject() instanceof Person) {
            Person randomizedObject = (Person) context.getCurrentObject();
            firstName = randomizedObject.getFirstName();
        }
        if (context.getCurrentObject() instanceof Pet) {
            Pet randomizedObject = (Pet) context.getCurrentObject();
            firstName = randomizedObject.getFirstName();
        }
        if (firstName != null && firstName.equalsIgnoreCase("james")) {
            return "bond";
        }
        if (firstName != null && firstName.equalsIgnoreCase("daniel")) {
            return "craig";
        }
        return null;
    }
}
