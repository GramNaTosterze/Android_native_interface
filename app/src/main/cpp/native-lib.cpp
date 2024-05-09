#include <jni.h>
#include <string>
#include <chrono>

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

extern "C"
JNIEXPORT jlong JNICALL
Java_pl_edu_pg_mso_lab5_MainActivity_cppMeasure(
        JNIEnv *env,
        jobject /* this */, jintArray array) {

    using std::chrono::high_resolution_clock;
    using std::chrono::duration_cast;
    using std::chrono::duration;
    using std::chrono::milliseconds;

    jsize length = env->GetArrayLength(array);
    jint *c_array = env->GetIntArrayElements((array), NULL);



    auto t1 = high_resolution_clock::now();
    std::sort(c_array, c_array+length);
    auto t2 = high_resolution_clock::now();

    duration<double, std::nano> ms_double = t2 - t1;


    env->ReleaseIntArrayElements((array), c_array, 0);
    return ms_double.count();
}