public class helloJni{
	public native void displayHelloWorld();
	static{
		System.loadLibrary("hello");//载入本地库
	}
	public static void main(String[] args){
		new helloJni().displayHelloWorld();
	}
}
