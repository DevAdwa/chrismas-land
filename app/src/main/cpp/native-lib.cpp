#include <jni.h>
#include <string>
//#define BASE_API "https://dev.adwa.world/SERVICENEW/ADSHOP/"
#define BASE_API "https://adwshop-ws01.adwa.world/"

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_fecafootdemo_AppLoader_BASE_1API(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF(BASE_API);
}