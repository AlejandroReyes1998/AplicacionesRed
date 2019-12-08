public class euc{
	public static void main(String args[]){

		System.out.println(mcd(192,18));
	}

	public static int mcd(int m,int n){
		int r;
		while(n!=0){
			r=m % n;
			m=n;
			n=r;
		}
		return m;	
	}
}