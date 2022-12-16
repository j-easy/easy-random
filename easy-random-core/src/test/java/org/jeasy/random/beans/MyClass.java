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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MyClass {
  String id;
  String description;
  MyReferencedClass ref;
  List<String> listString;
  Optional<Integer> optInteger;

  public MyClass(String id, String description, MyReferencedClass ref, List<String> listString,
      Optional<Integer> optInteger) {
    this.id = id;
    this.description = description;
    this.ref = ref;
    this.listString = listString;
    this.optInteger = optInteger;
  }

  public MyClass(MyClass in) {
    this(in.id, in.description, new MyReferencedClass(in.ref), in.listString, in.optInteger);
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public MyReferencedClass getRef() {
    return this.ref;
  }

  public void setRef(MyReferencedClass ref) {
    this.ref = ref;
  }

  public List<String> getListString() {
    return this.listString;
  }

  public void setListString(List<String> listString) {
    this.listString = listString;
  }

  public Optional<Integer> getOptInteger() {
    return this.optInteger;
  }

  public void setOptInteger(Optional<Integer> optInteger) {
    this.optInteger = optInteger;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || this.getClass() != obj.getClass()) {
      return false;
    }
    MyClass other = (MyClass) obj;
    return Objects.equals(this.id, other.id)
        && Objects.equals(this.description, other.description)
        && Objects.equals(this.ref, other.ref)
        && Objects.equals(this.listString, other.listString)
        && Objects.equals(this.optInteger, other.optInteger);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.description, this.ref, this.listString, this.optInteger);
  }

  @Override
  public String toString() {
    return "{" +
        "\"class\": \"MyClass\"," +
        "\"id\": \"" + this.id + "\"" + "," +
        "\"description\": \"" + this.description + "\"" + "," +
        "\"ref\": " + this.ref + "," +
        "\"listString\": " + this.listString + "," +
        "\"optInteger\": " + this.optInteger +
        "}";
  }
}
