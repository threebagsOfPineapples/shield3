#include <jni.h>

JNIEXPORT jstring JNICALL Java_com_tachyon5_spectrum_activity_CheckActivity_getmessage
(JNIEnv * env, jobject obj){
	char * pStr = "hello ndkyyyyff";

	//			jstring     (*NewStringUTF)(JNIEnv*, const char*);
		return (*env)->NewStringUTF(env,pStr);
};

JNIEXPORT jstring JNICALL Java_com_tachyon5_spectrum_activity_HomeActivity_getmessage
(JNIEnv * env, jobject obj){
	char * pStr = "hello ndkyyyyff";

	//			jstring     (*NewStringUTF)(JNIEnv*, const char*);
		return (*env)->NewStringUTF(env,pStr);
};
