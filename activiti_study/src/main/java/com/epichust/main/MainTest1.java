package com.epichust.main;

import java.util.List;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.junit.Test;

public class MainTest1
{
	
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	//查询流程定义文件
	@Test
	public void test1()
	{
		List<Model> list = processEngine.getRepositoryService().createModelQuery().list();
		
//		BpmnModel model = processEngine.getRepositoryService().getBpmnModel("EX_QC_01:10:18807");
		System.out.println(list.size());
	}
	
	//部署
	@Test
	public void test2()
	{
		Deployment deployment = processEngine.getRepositoryService().createDeployment()
											.name("yyyyy")
											.addClasspathResource("diagrams/fileName.bpmn")
											.addClasspathResource("diagrams/fileName.png")
											.deploy();
		
		
	}
	
}
