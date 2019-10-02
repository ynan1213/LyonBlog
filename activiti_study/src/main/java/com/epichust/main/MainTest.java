package com.epichust.main;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class MainTest
{

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	@Test
	public void test()
	{
		processEngine.getName();
	}

	@Test
	public void start()
	{
		RuntimeService service = processEngine.getRuntimeService();
		ProcessInstance instance = service.startProcessInstanceByKey("definitionId");
		System.out.println("getId" + instance.getId());
		System.out.println("getName" + instance.getName());
		System.out.println("getProcessDefinitionId" + instance.getProcessDefinitionId());
	}
	
	@Test
	public void start1()
	{
		TaskService service = processEngine.getTaskService();
		List<Task> list = service.createTaskQuery().taskAssignee("李四").list();
		for (Task task : list)
		{
			System.out.println("getAssignee :" + task.getAssignee());
			System.out.println("getName :" + task.getName());
			System.out.println("getProcessInstanceId :" + task.getProcessInstanceId());
		}
	}
	
	@Test
	public void start2()
	{
		processEngine.getTaskService().complete("17509");
	}

	// 启动流程
	@Test
	public void startProcess()
	{
		RuntimeService service = processEngine.getRuntimeService();
		ProcessInstance instance = service.startProcessInstanceByKey("definitionId");
		System.out.println("getActivityId :" + instance.getActivityId());
		System.out.println("getDeploymentId :" + instance.getDeploymentId());
		System.out.println("getProcessDefinitionId :" + instance.getProcessDefinitionId());
		System.out.println("getProcessDefinitionKey :" + instance.getProcessDefinitionKey());
		System.out.println("getProcessDefinitionName :" + instance.getProcessDefinitionName());
		System.out.println("getProcessInstanceId :" + instance.getProcessInstanceId());
		System.out.println("getId :" + instance.getId());
	}

	// 部署
	@Test
	public void test1()
	{
		RepositoryService service = processEngine.getRepositoryService();

		DeploymentBuilder builder = service.createDeployment().name("yyyyy").addClasspathResource("diagrams/fileName.bpmn").addClasspathResource("diagrams/fileName.png");

		Deployment deployment = builder.deploy();

		System.out.println(deployment.getId() + "=====" + deployment.getName());
	}

	// 查询 部署和流程
	@Test
	public void query()
	{
		RepositoryService repositoryService = processEngine.getRepositoryService();
		List<Deployment> list = repositoryService.createDeploymentQuery().deploymentName("yyyyy").orderByDeploymenTime().desc().list();
		for (Deployment deployment : list)
		{
			System.out.println(deployment.getId() + "====" + deployment.getName() + "====" + deployment.getDeploymentTime());
		}
	}

}
