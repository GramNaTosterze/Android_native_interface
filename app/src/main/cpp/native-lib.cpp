#include <jni.h>
#include <string>

extern "C" JNIEXPORT jobject

JNICALL
Java_pl_edu_pg_mso_lab5_MainActivity_cppSort(
        JNIEnv *env,
        jobject /* this */, jintArray array) {
    jsize length = env->GetArrayLength(array);
    jint *c_array = env->GetIntArrayElements((array), NULL);

    std::sort(c_array, c_array+length);

    env->ReleaseIntArrayElements((array), c_array, 0);
    return nullptr;
}