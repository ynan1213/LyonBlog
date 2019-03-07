package enum01;

public enum SeasonEnum {
	
	SPRING("春天",1),SUMMER("夏天",2),FALL("秋天",3),WINTER("冬天",4);
	
	private final String name;
	private final int value;
	
	private SeasonEnum(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	
}
