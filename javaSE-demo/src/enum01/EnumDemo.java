package enum01;

public class EnumDemo {
	
	public static void main(String[] args) {
		
		SeasonEnum s = SeasonEnum.SPRING;
		
		System.out.println(s.getName());
		System.out.println(s.getValue());
		
		SeasonEnum[] values = SeasonEnum.values();
		for (SeasonEnum ss : values) {
			System.out.println(ss);
		}
		
	}
}
