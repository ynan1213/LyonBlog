/**
 * Logback: the reliable, generic, fast and flexible logging framework.
 * Copyright (C) 1999-2015, QOS.ch. All rights reserved.
 *
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 *
 *   or (per the licensee's choosing)
 *
 * under the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation.
 */
package ch.qos.logback.core.joran;

import ch.qos.logback.core.Context;
import ch.qos.logback.core.joran.event.SaxEvent;
import ch.qos.logback.core.joran.event.SaxEventRecorder;
import ch.qos.logback.core.joran.spi.*;
import ch.qos.logback.core.joran.util.ConfigurationWatchListUtil;
import ch.qos.logback.core.joran.util.beans.BeanDescriptionCache;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.status.StatusUtil;

import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import static ch.qos.logback.core.CoreConstants.SAFE_JORAN_CONFIGURATION;

public abstract class GenericConfigurator extends ContextAwareBase {

    private BeanDescriptionCache beanDescriptionCache;

    protected Interpreter interpreter;

    public final void doConfigure(URL url) throws JoranException {
        InputStream in = null;
        try {
            // 将该url添加到watch list中
            informContextOfURLUsedForConfiguration(getContext(), url);
            URLConnection urlConnection = url.openConnection();
            // per http://jira.qos.ch/browse/LBCORE-105
            // per http://jira.qos.ch/browse/LBCORE-127
            // 如果设置成true的话，当这个配置文件在一个jar中的时候，jar被锁住。如果应用需要重新加载的话会出现问题。所以设置成false。
            urlConnection.setUseCaches(false);

            in = urlConnection.getInputStream();
            doConfigure(in, url.toExternalForm());
        } catch (IOException ioe) {
            String errMsg = "Could not open URL [" + url + "].";
            addError(errMsg, ioe);
            throw new JoranException(errMsg, ioe);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ioe) {
                    String errMsg = "Could not close input stream";
                    addError(errMsg, ioe);
                    throw new JoranException(errMsg, ioe);
                }
            }
        }
    }

    public final void doConfigure(String filename) throws JoranException {
        doConfigure(new File(filename));
    }

    public final void doConfigure(File file) throws JoranException {
        FileInputStream fis = null;
        try {
            URL url = file.toURI().toURL();
            informContextOfURLUsedForConfiguration(getContext(), url);
            fis = new FileInputStream(file);
            doConfigure(fis, url.toExternalForm());
        } catch (IOException ioe) {
            String errMsg = "Could not open [" + file.getPath() + "].";
            addError(errMsg, ioe);
            throw new JoranException(errMsg, ioe);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (java.io.IOException ioe) {
                    String errMsg = "Could not close [" + file.getName() + "].";
                    addError(errMsg, ioe);
                    throw new JoranException(errMsg, ioe);
                }
            }
        }
    }

    public static void informContextOfURLUsedForConfiguration(Context context, URL url) {
        ConfigurationWatchListUtil.setMainWatchURL(context, url);
    }

    public final void doConfigure(InputStream inputStream) throws JoranException {
        doConfigure(new InputSource(inputStream));
    }

    public final void doConfigure(InputStream inputStream, String systemId) throws JoranException {
        // 将InputStream转换成sax的InputSource对象
        InputSource inputSource = new InputSource(inputStream);
        inputSource.setSystemId(systemId);
        doConfigure(inputSource);
    }

    protected BeanDescriptionCache getBeanDescriptionCache() {
        if (beanDescriptionCache == null) {
            beanDescriptionCache = new BeanDescriptionCache(getContext());
        }
        return beanDescriptionCache;
    }

    protected abstract void addInstanceRules(RuleStore rs);

    protected abstract void addImplicitRules(Interpreter interpreter);

    protected void addDefaultNestedComponentRegistryRules(DefaultNestedComponentRegistry registry) {

    }

    protected ElementPath initialElementPath() {
        return new ElementPath();
    }

    protected void buildInterpreter() {
        RuleStore rs = new SimpleRuleStore(context);
        addInstanceRules(rs);
        this.interpreter = new Interpreter(context, rs, initialElementPath());
        InterpretationContext interpretationContext = interpreter.getInterpretationContext();
        interpretationContext.setContext(context);
        addImplicitRules(interpreter);
        addDefaultNestedComponentRegistryRules(interpretationContext.getDefaultNestedComponentRegistry());
    }

    // this is the most inner form of doConfigure whereto other doConfigure
    // methods ultimately delegate
    public final void doConfigure(final InputSource inputSource) throws JoranException {

        long threshold = System.currentTimeMillis();
        // if (!ConfigurationWatchListUtil.wasConfigurationWatchListReset(context)) {
        // informContextOfURLUsedForConfiguration(getContext(), null);
        // }
        // 将xml的<tag>bodyStr</tag>解析成StartEvent，BodyEvent，EndEvent,保存到recorder.saxEventList中
        // 如果解析期间遇到warn、error、fatalError情况则会生成信息存入StaticLoggerBinder.defaultLoggerContext.sm中后期将会将这些日志输出
        SaxEventRecorder recorder = new SaxEventRecorder(context);
        recorder.recordEvents(inputSource);
        // 根据xml每个element对应的event来进行解析
        doConfigure(recorder.saxEventList);
        // no exceptions a this level
        StatusUtil statusUtil = new StatusUtil(context);
        if (statusUtil.noXMLParsingErrorsOccurred(threshold)) {
            addInfo("Registering current configuration as safe fallback point");
            registerSafeConfiguration(recorder.saxEventList);
        }
    }

    public void doConfigure(final List<SaxEvent> eventList) throws JoranException {
        // 将大部分的element path包装成ElementSelector并映射一个Action,比如xml中configuration/logger element path映射成LoggerAction
        // 当解析到<configuration><logger>时会触发LoggerAction的begin方法
        // 当解析到<configuration><logger>bodyStr 时会触发LoggerAction的body方法，当然logger element是没有body的
        // 当解析到<configuration><logger>bodyStr</logger> 时会触发LoggerAction的end方法
        // action的映射关系存在JoranConfigurator#interpreter中
        buildInterpreter();
        // disallow simultaneous configurations of the same context
        synchronized (context.getConfigurationLock()) {
            interpreter.getEventPlayer().play(eventList);
        }
    }

    /**
     * Register the current event list in currently in the interpreter as a safe
     * configuration point.
     *
     * @since 0.9.30
     */
    public void registerSafeConfiguration(List<SaxEvent> eventList) {
        context.putObject(SAFE_JORAN_CONFIGURATION, eventList);
    }

    /**
     * Recall the event list previously registered as a safe point.
     */
    @SuppressWarnings("unchecked")
    public List<SaxEvent> recallSafeConfiguration() {
        return (List<SaxEvent>) context.getObject(SAFE_JORAN_CONFIGURATION);
    }
}
