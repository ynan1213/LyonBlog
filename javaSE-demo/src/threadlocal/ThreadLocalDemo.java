package threadlocal;

public class ThreadLocalDemo {

	public static void main(String[] args) throws Exception {
		
		User user = new User();
		
		new Test(user).start();
		new Test(user).start();
		new Test(user).start();
		
		Thread.sleep(2500);
		user.setName("main线程");
		user.setAge(30);
		System.out.println("-----------------main线程输出："+user+"------------------");
	}

}

class Test extends Thread{
	
	ThreadLocal<User> tl = new ThreadLocal<User>();
	User user;
	
	public Test(User user) {
		this.user = user;
	}
	
	@Override
	public void run() {
		tl.set(user);
		User user1 = tl.get();
		
		user1.setName("小明"+Thread.currentThread().getName());
		user1.setAge(20);
		
		System.out.println(Thread.currentThread().getName()+"第一次输出："+user1);
		
		try {
			System.out.println(Thread.currentThread().getName()+"线程开始睡眠");
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		System.out.println(Thread.currentThread().getName()+"第二次输出："+user1);
		System.out.println(Thread.currentThread().getName()+"结束睡眠");
	}
}

class User {
	
	private String name;
	private int age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + "]";
	}
	
	
}