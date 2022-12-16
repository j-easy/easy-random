/*
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
package org.jeasy.random.beans;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public record MyRecord(String id, Optional<String> description, MyReferencedRecord ref,
                       Byte counter, List<String> listString, DayOfWeek day) {
  
  public MyRecord(String id, Optional<String> description, MyReferencedRecord ref,
      Byte counter, List<String> listString, DayOfWeek day) {
    this.id = Objects.requireNonNull(id);
    this.description = description;
    this.ref = Objects.requireNonNull(ref);
    this.counter = counter;
    this.listString = Objects.requireNonNull(listString);
    this.day = day;
  }

  public MyRecord(MyRecord in) {
    this(in.id, 
        Optional.ofNullable(in.description.orElse(null)), 
        new MyReferencedRecord(in.ref), 
        Byte.valueOf(in.counter), 
        List.copyOf(in.listString),
        in.day);
  }
}
