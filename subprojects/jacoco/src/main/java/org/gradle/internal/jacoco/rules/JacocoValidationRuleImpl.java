/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.internal.jacoco.rules;

import groovy.lang.Closure;
import org.gradle.api.Action;
import org.gradle.api.internal.ClosureBackedAction;
import org.gradle.testing.jacoco.tasks.rules.JacocoRuleScope;
import org.gradle.testing.jacoco.tasks.rules.JacocoThreshold;
import org.gradle.testing.jacoco.tasks.rules.JacocoValidationRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JacocoValidationRuleImpl implements JacocoValidationRule {

    private boolean enabled = true;
    private JacocoRuleScope scope;
    private List<String> includes = new ArrayList<String>();
    private List<String> excludes = new ArrayList<String>();
    private final List<JacocoThreshold> thresholds = new ArrayList<JacocoThreshold>();

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void setScope(JacocoRuleScope scope) {
        this.scope = scope;
    }

    @Override
    public JacocoRuleScope getScope() {
        return scope;
    }

    @Override
    public void setIncludes(List<String> includes) {
        this.includes = includes;
    }

    @Override
    public List<String> getIncludes() {
        return Collections.unmodifiableList(includes);
    }

    @Override
    public void setExcludes(List<String> excludes) {
        this.excludes = excludes;
    }

    @Override
    public List<String> getExcludes() {
        return Collections.unmodifiableList(excludes);
    }

    @Override
    public List<JacocoThreshold> getThresholds() {
        return Collections.unmodifiableList(thresholds);
    }

    @Override
    public JacocoThreshold threshold(Closure configureClosure) {
        return threshold(ClosureBackedAction.of(configureClosure));
    }

    @Override
    public JacocoThreshold threshold(Action<? super JacocoThreshold> configureAction) {
        JacocoThreshold threshold = new JacocoThresholdImpl();
        configureAction.execute(threshold);
        thresholds.add(threshold);
        return threshold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JacocoValidationRuleImpl that = (JacocoValidationRuleImpl) o;

        if (enabled != that.enabled) {
            return false;
        }
        if (scope != that.scope) {
            return false;
        }
        if (includes != null ? !includes.equals(that.includes) : that.includes != null) {
            return false;
        }
        if (excludes != null ? !excludes.equals(that.excludes) : that.excludes != null) {
            return false;
        }
        return thresholds != null ? thresholds.equals(that.thresholds) : that.thresholds == null;
    }

    @Override
    public int hashCode() {
        int result = enabled ? 1 : 0;
        result = 31 * result + (scope != null ? scope.hashCode() : 0);
        result = 31 * result + (includes != null ? includes.hashCode() : 0);
        result = 31 * result + (excludes != null ? excludes.hashCode() : 0);
        result = 31 * result + (thresholds != null ? thresholds.hashCode() : 0);
        return result;
    }
}
