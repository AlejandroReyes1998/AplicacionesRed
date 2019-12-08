import java.io.Serializable;
public class dato implements Serializable{
	String v1;
	int v2;
	float v3;
	long v4;
	public dato(String v1,int v2,float v3,long v4){
		this.v1=v1;
		this.v2=v2;
		this.v3=v3;
		this.v4=v4;
	}
	String getv1(){
		return this.v1;
	}
	int getv2(){
		return this.v2;
	}
	float getv3(){
		return this.v3;
	}
	long getv4(){
		return this.v4;
	}
}