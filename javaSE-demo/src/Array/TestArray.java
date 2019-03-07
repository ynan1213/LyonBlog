package Array;

public class TestArray {
	public static void main(String[] args){
		int[] a={5,9,1,7,3,8,6,4,2};
		Array.print(a);
		
		//Array.arr1(a);//直接排序
		//Array.print(a);
		
		//Array.arr2(a);//直接排序的优化版本
		//Array.print(a);//
		
		//Array.arr3(a);//冒泡排序
		//Array.print(a);//
		
		Array.arr4(a);//插入排序
		Array.print(a);//
		
		//Array.arr5(a);//反转排序
		//Array.print(a);
	}	
}


class Array{							
	public static void arr1(int[] a){		//选择排序
		for(int i=0;i<a.length-1;i++){
			for(int j=i+1;j<a.length;j++){
				if(a[i]>a[j]){
					int temp=a[i];
					a[i]=a[j];
					a[j]=temp;
				}
			}
		}
	}
	public static void arr2(int[] a){		//选择排序的优化版本（记录下标）
		int k,temp;
		for(int i=0;i<a.length-1;i++){
			k=i;
			for(int j=i+1;j<a.length;j++){
				if(a[k]>a[j]){
					k=j;
				}
			}
			if(i!=k){
				temp=a[i];
				a[i]=a[k];
				a[k]=temp;	
			}
		}
	}
	public static void arr3(int[] a){		//冒泡排序
		//for(int i=0;i<a.length-1;i++){
			//for(int j=0;j<a.length-i-1;j++){
	  for(int i = a.length - 1; i > 0; i--){	//另一种表达形式
		 for(int j=0;j<i;j++){
				if(a[j]>a[j+1]){
					int temp=0;
					temp=a[j];
					a[j]=a[j+1];
					a[j+1]=temp;	
				}
			}
		}
	}
	/*
	 public static void arr4(int[] a){	//直接插入排序①
		for(int i=1;i<a.length;i++){
			int j=i-1;
			int temp=a[i];
			for(j=i-1;j>=0&&a[j]>temp;j--){
				a[j+1]=a[j];
			}
			a[j+1]=temp;
		}
	}
	*/
	public static void arr4(int[] a){	//直接插入排序②
		for(int i=1;i<a.length;i++){
			int j=i-1;
			int temp=a[i];
			while(j>=0&&a[j]>temp){
				a[j+1]=a[j];
				j--;
			}
			a[j+1]=temp;
		}
	}
	public static void arr5(int[] a){ //反转排序
		for(int i=0;i<a.length/2;i++){
			int temp=a[a.length-i-1];
			a[a.length-i-1]=a[i];
			a[i]=temp;
		}
	}
	
	
	public static void print(int[] a){
		for(int i=0;i<a.length;i++){
			System.out.print(a[i]+" ");
		}
		System.out.println();
	}	
}

