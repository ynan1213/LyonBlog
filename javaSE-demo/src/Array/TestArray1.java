package Array;

public class TestArray1 {
	public static void main(String[] args){
		Date[] arr=new Date[5];
		arr[0]=new Date(2018,2,5);
		arr[1]=new Date(1990,12,13);
		arr[2]=new Date(1991,1,28);
		arr[3]=new Date(2018,1,23);
		arr[4]=new Date(2018,2,7);	
		arrSort(arr);
		for(int i=0;i<arr.length;i++){
			System.out.println(arr[i]);
		}
	}


	public static void arrSort(Date[] a){
		for(int i=a.length-1;i>0;i--){
			for(int j=0;j<i;j++){
				if(a[j].compare(a[j+1])>0){
					Date temp=a[j];
					a[j]=a[j+1];
					a[j+1]=temp;
				}
			}
		}//return a;
	}
}

class Date{
	int year,month,day;
	
	Date(int year,int month,int day){
		this.year=year;
		this.month=month;
		this.day=day;
	}
	public int compare(Date a){//为什么是不是数组类型的？ 无需定义数组类型，后面输入的arr[i]默认等于a类型
		/*
		if(this.year>a.year)return 1;
		else if(this.year<a.year)return -1;
		else if(this.month>a.month)return 1;
		else if(this.month<a.month)return -1;
		else if(this.day>a.day)return 1;
		else if(this.day<a.day)return -1;
		else return 0;
		*/
		return this.year>a.year?1
		:this.year<a.year?-1
		:this.month>a.month?1
		:this.month<a.month?-1
		:this.day>a.day ?1
		:this.day<a.day?-1
		:0;
	}
	public String toString(){
		return +year+"年"+month+"月"+day+"日";
	}
}