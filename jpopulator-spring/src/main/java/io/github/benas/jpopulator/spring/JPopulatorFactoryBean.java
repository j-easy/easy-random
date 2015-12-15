/*
 * The MIT License
 *
 *   Copyright (c) 2016, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
 *
 */

package io.github.benas.jpopulator.spring;

import io.github.benas.jpopulator.api.Populator;
import io.github.benas.jpopulator.impl.PopulatorBuilder;
import org.springframework.beans.factory.FactoryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring Factory Bean that creates jPopulator instances.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class JPopulatorFactoryBean implements FactoryBean {

    private List<RandomizerBean> randomizers = new ArrayList<RandomizerBean>();

    @Override
    public Object getObject() throws Exception {
        PopulatorBuilder populatorBuilder = new PopulatorBuilder();
        for (RandomizerBean randomizerBean : randomizers) {
            populatorBuilder.registerRandomizer(
                    randomizerBean.getType(),
                    randomizerBean.getFieldType(),
                    randomizerBean.getFieldName(),
                    randomizerBean.getRandomizer());
        }

        return populatorBuilder.build();
    }

    @Override
    public Class<?> getObjectType() {
        return Populator.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setRandomizers(List<RandomizerBean> randomizers) {
        this.randomizers = randomizers;
    }

}
