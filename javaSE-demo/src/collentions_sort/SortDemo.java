package collentions_sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortDemo {

	public static void main(String[] args) {
		List<User> list = new ArrayList<User>() {
			
			private static final long serialVersionUID = 1L;

			{	
				add(new User("李四", 24));
				add(new User("丁一", 21));
				add(new User("赵二", 22));
				add(new User("张三", 23));
			}
		};
		

		System.out.println("排序前：" + list.toString());
		Collections.sort(list , new Comparator<User>() {

			@Override
			public int compare(User o1, User o2) {
				
				return o1.getAge() - o2.getAge();
			}

			
		});
		System.out.println("排序后：" + list.toString());

	}
}
