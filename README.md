# HelloJni
**什么是jni**:jni是java native interface的缩写，是java平台的一部分，允许java代码和其他语言写的代码进行交互。
**为什么要用jni** ：解决java本地调用、在java程序中调用一个用其他语言已经写好的库或者程序。。。

 - **举例：**
 - 新建项目目录：helloJni
 - 在目录下新建java文件：helloJni.java
	 - 文件内容:
```
public class helloJni{
	public native void displayHelloWorld();
	static{
		System.loadLibrary("hello");//载入本地库
	}
	public static void main(String[] args){
		new helloJni().displayHelloWorld();
	}
}
```
 - javac编译helloJni.java文件生成helloJni.class文件:`javac helloJni.java`
 - javah编译生成头文件helloJni.h:`javah -jni helloJni`
	 - 生成的头文件内容：
```
/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class helloJni */

#ifndef _Included_helloJni
#define _Included_helloJni
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     helloJni
 * Method:    displayHelloWorld
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_helloJni_displayHelloWorld
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif
```
 - 编写C语言代码：在该目录下新建文件：HelloWorld.c
	 - 内容如下：

```
#include "jni.h"
#include "helloJni.h"
#include <stdio.h>
#include <stdlib.h>
//方法名必须为本地方法的全类名点改为下划线，传入的两个参数必须这样写，
//第一个参数为Java虚拟机的内存地址的二级指针，用于本地方法与java虚拟机在内存中交互
//第二个参数为一个java对象，即是哪个对象调用了这个 c方法
/*
函数名及返回值类型需一致
*/
JNIEXPORT void JNICALL Java_helloJni_displayHelloWorld(JNIEnv * env, jobject obj) 

        {

          printf("ok!You have successfully passed the Java call c\n");

        }  
```
 - 生成对象文件
	 `#gcc -Wall -fPIC -c HelloWorld.c -I ./ -I /usr/lib/jvm/jdk1.8.0_172/include/linux/ -I /usr/lib/jvm/jdk1.8.0_172/include/`
	 其中，HelloWorld.c是刚才编写的C文件;/usr/lib/jvm/jdk1.8.0_172/include/linux/和/usr/lib/jvm/jdk1.8.0_172/include/是你电脑上jdk环境对应的目录。
				 运行后，将生成`HelloWorld.o`文件。

 -  生成动态链接库
	 `#gcc -Wall -rdynamic -shared -o libhello.so HelloWorld.o`，运行后将生成libhello.so文件，其中libhello.so文件名由用户指定，但是前缀必须是lib，扩展名为so，hello是第1步载入的本地库名：`System.loadLibrary("hello");//载入本地库`。		 
 - 设置环境变量
	 - 在命令行中输入：`#sudo gedit /etc/profile`,在文件最后添加：`export LD_LIBRARY_PATH=/media/wlf/vol1/wlf/wlf/HelloJni`,其中/media/wlf/vol1/wlf/wlf/HelloJni是你生成的动态链接库所在目录。

	 - 关闭文件，输入`#source /etc/profile`
 - 测试
	 - 在命令行中输入`#java helloJni`
	输出：*ok!You have successfully passed the Java call c*
