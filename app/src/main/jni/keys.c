#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_example_android_1imperative_network_Server_getDevelopment(JNIEnv *env, jobject thiz) {
    return (*env)->  NewStringUTF(env, "https://www.episodate.com/");

}

JNIEXPORT jstring JNICALL
Java_com_example_android_1imperative_network_Server_getProduction(JNIEnv *env, jobject thiz) {
    return (*env)->NewStringUTF(env, "https://www.episodate.com/");
}