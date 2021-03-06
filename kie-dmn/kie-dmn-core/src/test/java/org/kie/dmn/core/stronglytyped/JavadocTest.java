/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.dmn.core.stronglytyped;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNRuntime;
import org.kie.dmn.core.BaseVariantTest;
import org.kie.dmn.core.DMNRuntimeTest;
import org.kie.dmn.core.util.DMNRuntimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.kie.dmn.core.BaseVariantTest.VariantTestConf.BUILDER_DEFAULT_NOCL_TYPECHECK_TYPESAFE;
import static org.kie.dmn.core.BaseVariantTest.VariantTestConf.KIE_API_TYPECHECK_TYPESAFE;

public class JavadocTest extends BaseVariantTest {

    public static final Logger LOG = LoggerFactory.getLogger(JavadocTest.class);

    public JavadocTest(VariantTestConf testConfig) {
        super(testConfig);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Object[] params() {
        return new Object[]{BUILDER_DEFAULT_NOCL_TYPECHECK_TYPESAFE, KIE_API_TYPECHECK_TYPESAFE};
    }

    @Test
    public void testDateAndTime() throws Exception {
        final DMNRuntime runtime = createRuntime("0007-date-time.dmn", DMNRuntimeTest.class);
        runtime.addListener(DMNRuntimeUtil.createListener());

        final DMNModel dmnModel = runtime.getModel("http://www.trisotech.com/definitions/_69430b3e-17b8-430d-b760-c505bf6469f9", "dateTime Table 58");
        assertThat(dmnModel, notNullValue());
        assertThat(DMNRuntimeUtil.formatMessages(dmnModel.getMessages()), dmnModel.hasErrors(), is(false));

        // Typesafe only test
        Map<String, String> sourceMap = new HashMap<>();
        allSources.forEach((k, v) -> sourceMap.put(k.substring(k.lastIndexOf(".") + 1), v));

        String inputSet = sourceMap.get("InputSet");
        CompilationUnit cu = StaticJavaParser.parse(inputSet);
        assertJavadoc(cu, "dateTimeString", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : string }");
        assertJavadoc(cu, "timezone", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : string }");
        assertJavadoc(cu, "oneHour", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : days and time duration }");
        assertJavadoc(cu, "month", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : number }");
        assertJavadoc(cu, "year", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : number }");
        assertJavadoc(cu, "hours", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : number }");
        assertJavadoc(cu, "timeString", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : string }");
        assertJavadoc(cu, "dateString", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : string }");
        assertJavadoc(cu, "seconds", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : number }");
        assertJavadoc(cu, "day", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : number }");
        assertJavadoc(cu, "minutes", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : number }");
        assertJavadoc(cu, "durationString", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : string }");

        String outputSet = sourceMap.get("OutputSet");
        cu = StaticJavaParser.parse(outputSet);
        assertJavadoc(cu, "timezone", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : string }");
        assertJavadoc(cu, "date_45Time", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : date and time }");
        assertJavadoc(cu, "hours", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : number }");
        assertJavadoc(cu, "time", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : time }");
        assertJavadoc(cu, "minutes", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : number }");
        assertJavadoc(cu, "date_45Time2", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : date and time }");
        assertJavadoc(cu, "years", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : number }");
        assertJavadoc(cu, "dateTimeString", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : string }");
        assertJavadoc(cu, "oneHour", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : days and time duration }");
        assertJavadoc(cu, "d1seconds", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : number }");
        assertJavadoc(cu, "month", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : number }");
        assertJavadoc(cu, "cDay", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : number }");
        assertJavadoc(cu, "sumDurations", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : days and time duration }");
        assertJavadoc(cu, "cYear", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : number }");
        assertJavadoc(cu, "cSecond", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : number }");
        assertJavadoc(cu, "dateString", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : string }");
        assertJavadoc(cu, "cTimezone", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : string }");
        assertJavadoc(cu, "durationString", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : string }");
        assertJavadoc(cu, "cHour", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : number }");
        assertJavadoc(cu, "year", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : number }");
        assertJavadoc(cu, "time2", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : time }");
        assertJavadoc(cu, "timeString", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : string }");
        assertJavadoc(cu, "time3", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : time }");
        assertJavadoc(cu, "hoursInDuration", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : number }");
        assertJavadoc(cu, "dtDuration1", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : days and time duration }");
        assertJavadoc(cu, "seconds", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : number }");
        assertJavadoc(cu, "dtDuration2", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : days and time duration }");
        assertJavadoc(cu, "day", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : number }");
        assertJavadoc(cu, "cMonth", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : number }");
        assertJavadoc(cu, "cMinute", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : number }");
        assertJavadoc(cu, "ymDuration2", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : days and time duration }");

        String tDateVariants = sourceMap.get("TDateVariants");
        cu = StaticJavaParser.parse(tDateVariants);
        assertJavadoc(cu, "fromString", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : date }");
        assertJavadoc(cu, "fromDateTime", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : date }");
        assertJavadoc(cu, "fromYearMonthDay", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : date }");

        String tDateTimeComponents = sourceMap.get("TDateTimeComponents");
        cu = StaticJavaParser.parse(tDateTimeComponents);
        assertJavadoc(cu, "year", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : number }");
        assertJavadoc(cu, "month", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : number }");
        assertJavadoc(cu, "day", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : number }");
        assertJavadoc(cu, "hour", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : number }");
        assertJavadoc(cu, "minute", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : number }");
        assertJavadoc(cu, "second", "DMNType{ http://www.omg.org/spec/FEEL/20140401 : number }");
    }

    private void assertJavadoc(CompilationUnit cu, String field, String expectedJavadocComment) {
        Optional<FieldDeclaration> opt = cu.findFirst(FieldDeclaration.class, fd -> fd.asFieldDeclaration().getVariable(0).getNameAsString().equals(field));
        assertTrue(opt.isPresent());
        Optional<JavadocComment> actual = opt.get().getJavadocComment();
        assertTrue(actual.isPresent());
        assertThat(actual.get().getContent(), containsString(expectedJavadocComment));
    }
}
