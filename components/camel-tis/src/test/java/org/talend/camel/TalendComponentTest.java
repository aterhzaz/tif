/**
 * Copyright (C) 2010 Talend Inc. - www.talend.com
 */
package org.talend.camel;

import java.io.File;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;

public class TalendComponentTest extends CamelTestSupport {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        deleteDirectory("target/output");
    }

    @Test
    public void testRunJobWithDefaultContext() throws Exception {
        
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMinimumMessageCount(1);       
        
        sendBody("direct:defaultContext", "foo");
        assertMockEndpointsSatisfied();
        assertFileExists("target/output/out.csv");
    }

    @Test
    public void testRunJobWithCustomContext() throws Exception {
        
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMessageCount(1);       
        
        sendBody("direct:customContext", "foo");
        assertMockEndpointsSatisfied();
        assertFileExists("target/output/out.csv");
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("direct:defaultContext")
                    .to("talend://talendesb.jobcamel_0_1.jobCamel")
                    .to("mock:result");

                from("direct:customContext")
                    .to("talend://talendesb.jobcamel_0_1.jobCamel?context=Default")
                    .to("mock:result");
            }
        };
    }
}
