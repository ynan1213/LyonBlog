package com.epichust.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class TimerMainTest
{

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	@Test
	public void test()
	{
		processEngine.getName();
	}

	// 启动流程
	@Test
	public void startProcess()
	{
		RuntimeService service = processEngine.getRuntimeService();
		
		Map<String,Object> map = new HashMap<>();
		map.put("onelevel", "PT1M");
		// 启动流程实例
		ProcessInstance instance = service.startProcessInstanceByKey("deployment_id_yuan",map);
		
		
	}

	// 部署
	@Test
	public void test1()
	{
		RepositoryService service = processEngine.getRepositoryService();

		DeploymentBuilder builder = service.createDeployment().name("yyyyy").addClasspathResource("diagrams/fileName.bpmn").addClasspathResource("diagrams/fileName.png");

		Deployment deployment = builder.deploy();

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

//		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId("5001").singleResult();
//		System.out.println(processDefinition.getDeploymentId());
//		System.out.println(processDefinition.getId());
//		System.out.println(processDefinition.getKey());
//		System.out.println(processDefinition.getName());

	}

	// 查询最新版本的流程定义
	@Test
	public void query1()
	{
		RepositoryService repositoryService = processEngine.getRepositoryService();
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().processDefinitionKey("deployment_id_yuan")// 根据流程定义key查询
				.orderByProcessDefinitionVersion()// 按版本进行进行降序
				.desc().listPage(0, 1);

		for (ProcessDefinition pro : list)
		{
			System.out.println(pro.getId());
		}
	}

	// 查询任务
	@Test
	public void start1()
	{
		TaskService service = processEngine.getTaskService();
//		List<Task> list2 = service.createTaskQuery().list();
//		for (Task task : list2)
//		{
//			System.out.println("getAssignee :" + task.getAssignee());
//			System.out.println("getName :" + task.getName());
//			System.out.println("getProcessInstanceId :" + task.getProcessInstanceId());
//			System.out.println(task.getExecutionId());
//		}
		
//		List<Task> list = service.createTaskQuery().taskAssignee("张三").list();
//		for (Task task : list)
//		{
//			System.out.println("getAssignee :" + task.getAssignee());
//			System.out.println("getName :" + task.getName());
//			System.out.println("getProcessInstanceId :" + task.getProcessInstanceId());
//		}
		
		//根据流程实例id查询任务
		List<Task> list = service.createTaskQuery().processInstanceId("10001").list();
		for (Task task : list)
		{
			System.out.println(task.getName());
		}
	}

	//完成任务
	@Test
	public void taskCompelete()
	{
		processEngine.getTaskService().complete("7504");
	}
	
}
