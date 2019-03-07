package Array;
//二分查找法
public class TestArray4 {
	public static void main(String[] args){
		int[] a={1,2,3,4,5,6,7,8,9,10};
		System.out.println(search(a,12));
	}
	
	public static int search(int[] a,int num){
		if(a.length==0) return -1;
		int start=0;
		int end=a.length-1;
		int m=(start+end)/2;
		while(start<end){
			if(a[m]==num) return m;
			if(a[m]>num){
				end=m-1;
			}
			if(a[m]<num){
				start=m+1;
			}
			m=(start+end)/2;
		}
		return 0;
	} 
}
