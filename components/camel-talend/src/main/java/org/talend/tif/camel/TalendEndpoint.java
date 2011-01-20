/**
 * Copyright (C) 2010 Talend Inc. - www.talend.com
 */

package org.talend.tif.camel;

import java.lang.reflect.Method;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.impl.DefaultEndpoint;

/**
 * Represents a Talend endpoint.
 */
public class TalendEndpoint extends DefaultEndpoint {
    private String clazz;
    private String context;
    private Object jobInstance;
    private Method jobMethod;

    public TalendEndpoint(String uri, String clazz, TalendComponent component) {
        super(uri, component);
        this.setClazz(clazz);
    }

    public TalendEndpoint(String endpointUri) {
        super(endpointUri);
    }

    public Producer createProducer() throws Exception {
        instantiateJob();
        return new TalendProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
        throw new RuntimeCamelException("No support for exposing Camel as Talend job yet");
    }

    public boolean isSingleton() {
        return true;
    }

    protected void instantiateJob() throws ClassNotFoundException, SecurityException, NoSuchMethodException {
        if (jobMethod == null) {
            Class<?> jobType = this.getCamelContext().getClassResolver().resolveMandatoryClass(clazz);
            jobInstance = getCamelContext().getInjector().newInstance(jobType);
            jobMethod = jobType.getMethod("runJobInTOS", new Class[]{String[].class});
        }
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getClazz() {
        return clazz;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getContext() {
        return context;
    }

    public void setJobInstance(Object jobInstance) {
        this.jobInstance = jobInstance;
    }

    public Object getJobInstance() {
        return jobInstance;
    }

    public void setJobMethod(Method jobMethod) {
        this.jobMethod = jobMethod;
    }

    public Method getJobMethod() {
        return jobMethod;
    }
}
