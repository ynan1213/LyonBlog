package com.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;


public class CglibMain
{
    public static void main(String[] args)
    {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\code");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Process.class);
        enhancer.setCallback(new ProcessProxy());

        Process p = (Process) enhancer.create();
        p.doFirst();

    }
}
