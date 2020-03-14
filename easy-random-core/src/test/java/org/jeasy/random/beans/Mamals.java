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

public class Mamals {

    private Mammal mamal;
    private MammalImpl mamalImpl;
    private List<Mammal> mamalList;
    private List<MammalImpl> mamalImplList;
    
    public Mammal getMamal() {
        return mamal;
    }

    public void setMamal(Mammal mamal) {
        this.mamal = mamal;
    }

    public MammalImpl getMamalImpl() {
        return mamalImpl;
    }

    public void setMamalImpl(MammalImpl mamalImpl) {
        this.mamalImpl = mamalImpl;
    }

    public List<Mammal> getMamalList() {
        return mamalList;
    }

    public void setMamalList(List<Mammal> mamalList) {
        this.mamalList = mamalList;
    }

    public List<MammalImpl> getMamalImplList() {
        return mamalImplList;
    }

    public void setMamalImplList(List<MammalImpl> mamalImplList) {
        this.mamalImplList = mamalImplList;
    }
}
