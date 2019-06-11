package com.news.extend;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import android.os.Debug;
import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * 作者：    shaoshuai
 * 时间：    2017/10/20 11:23
 * 电子邮箱：
 * 描述:
 */
@Aspect
public class DebugLogAspect {
    private static final int METHOD_MILIONS = 300;

    @Around("execution(@com.news.extend.DebugLog * * (..))")
    public Object doDebugLog(ProceedingJoinPoint point) {
        //方法位置(所处类) 方法执行线程 方法耗时
        //入参 /出参  返回值类型 / 返回值
        String className = point.getThis().getClass().getSimpleName();
        MethodSignature signature = (MethodSignature) point.getSignature();
        String methodClass = signature.getMethod().getDeclaringClass().getSimpleName();
        Object[] inParams = point.getArgs();
        Object returnValue = null;
        long startTime = 0;
        long endTime = 0;
        try {
            startTime = System.currentTimeMillis();
            returnValue = point.proceed();
            endTime = System.currentTimeMillis();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        Log.w("打印相关方法参数", "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        Log.w("打印相关方法参数", "┣|-->打印方法被调用类Class:--->" + className);
        Log.w("打印相关方法参数", "┣|-->打印方法所在类Class:--->  " + methodClass);
        Log.w("打印相关方法参数", "┣|-->打印方法执行线程:--->     " + Thread.currentThread().getName());
        String[] paramsType = new String[inParams.length];
        if (0 != inParams.length) {
            for (int i = 0, j = inParams.length; i < j; i++) {
                paramsType[i] = inParams[i].getClass().getSimpleName();
            }
        }
        long consumeTime = endTime - startTime;
        if (consumeTime >= METHOD_MILIONS) {
            Log.e("打印相关方法参数", "┣|-->方法耗时:" + (endTime - startTime) + "毫秒,请优化!!!!");
        } else {
            Log.w("打印相关方法参数", "┣|-->方法耗时:" + (endTime - startTime) + "毫秒");
        }
        Log.w("打印相关方法参数", "┣|   打印方法调用:");
        String result = LogMethodMessage(signature);
        Log.w("打印相关方法参数", "┣|   " + result);
        String[] paramsNames = signature.getParameterNames();
        StringBuilder methodString = new StringBuilder();
        if (paramsNames.length == inParams.length && inParams.length > 0) {
            StringBuilder params;
            for (int i = 0; i < paramsType.length; i++) {
                params = new StringBuilder();
                params.append("  入参--->");
                params.append(paramsType[i]);
                params.append("\u2000");
                params.append(paramsNames[i]);
                params.append("\u2000");
                params.append("=");
                params.append("\u2000");
                if (inParams[i].getClass().isArray()) {
                    Object[] objects = (Object[]) inParams[i];
                    params.append(Arrays.toString(objects));
                } else {
                    params.append(inParams[i]);
                }
                Log.w("打印相关方法参数", "┣|   " + params.toString());
            }
        }
        Log.w("打印相关方法参数", "┣|      ...");
        Log.w("打印相关方法参数", "┣|      doSomething");
        Log.w("打印相关方法参数", "┣|      ...");
        if (null != returnValue) {
            methodString = new StringBuilder();
            methodString.append("  出参--->");
            methodString.append(returnValue.getClass().getSimpleName());
            methodString.append("\u2000");
            methodString.append("=");
            methodString.append("\u2000");
            methodString.append(returnValue.toString());
            Log.w("打印相关方法参数", "┣|   " + methodString.toString());
            methodString = new StringBuilder();
            methodString.append("  return");
            methodString.append("\u2000");
            methodString.append(returnValue.getClass().getSimpleName());
            Log.w("打印相关方法参数", "┣|   " + methodString.toString());
            Log.w("打印相关方法参数", "┣|   }");
        } else {
            Log.w("打印相关方法参数", "┣|   }");
        }
        Log.w("打印方法参数结束", "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
//        Debug.MemoryInfo info = new Debug.MemoryInfo();
//        StringBuilder memory = new StringBuilder();
//        Log.e("打印系统MemoryInfo", info.toString());
        return returnValue;
    }

    /**
     * 获取方法串
     *
     * @param signature
     * @return
     */
    private String LogMethodMessage(MethodSignature signature) {
        Method method = signature.getMethod();
        // 获取方法名
        String methodName = method.getName();
        //获取修饰符
        String embellish = Modifier.toString(method.getModifiers());
        // 获取返回值类型
        Class<?> returnType = method.getReturnType();
        StringBuilder builder = new StringBuilder();
        builder.append(embellish);
        builder.append("\u2000");
        if (returnType.equals(Void.class)) {
            builder.append("void");
        } else {
            builder.append(returnType.getSimpleName());
            builder.append(getMethodReturnParams(signature));
        }
        builder.append("\u2000");
        builder.append(methodName);
        builder.append("(");
        builder.append(getMethodParams(signature));
        builder.append(")");
        builder.append("{");
        builder.append("\n");
        return builder.toString();
    }

    /**
     * 获取返回参数串
     *
     * @param signature
     * @return
     */
    private String getMethodReturnParams(MethodSignature signature) {
        StringBuilder builder = new StringBuilder();
        Type type = signature.getMethod().getGenericReturnType();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] types = parameterizedType.getActualTypeArguments();
            if (0 != types.length) {
                builder.append("<");
                for (Type type1 : types) {
                    builder.append(type1.toString());
                    builder.append(",");
                }
                builder.deleteCharAt(builder.length() - 1);
                builder.append(">");
            }
        }
        return builder.toString();
    }


    /**
     * 拼接method方法的参数String
     *
     * @return
     */
    private String getMethodParams(MethodSignature signature) {
        StringBuilder builder = new StringBuilder();
        Type[] types = signature.getMethod().getGenericParameterTypes();
        Class<?>[] paramsClass = signature.getMethod().getParameterTypes();
        String[] paramName = signature.getParameterNames();
        // 打印 [java.util.List<T>, T[]]
        for (int i = 0; i < types.length; i++) {
            Type type = types[i];
            // 参数类型
            if (type instanceof ParameterizedType) {
                builder.append(paramsClass[i].getSimpleName());
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type[] actualTypeArguments = parameterizedType
                        .getActualTypeArguments();
                if (0 != actualTypeArguments.length) {
                    builder.append("<");
                    for (Type type1 : actualTypeArguments) {
                        builder.append(type1.toString());
                        builder.append(",");
                    }
                    builder.deleteCharAt(builder.length() - 1);
                    builder.append(">");
                }
                builder.append("\u2000");
                builder.append(paramName[i]);
                builder.append(",");
                // 泛型数组类型 T[]
            } else if (type instanceof GenericArrayType) {
                GenericArrayType genericArrayType = (GenericArrayType) type;
                builder.append(genericArrayType.getGenericComponentType().toString());
                builder.append("[");
                builder.append("]");
                builder.append("\u2000");
                builder.append(paramName[i]);
                builder.append(",");
                // 泛型变量
            } else if (type instanceof TypeVariable) {
                builder.append(type.toString());
                builder.append("\u2000");
                builder.append(paramName[i]);
                builder.append(",");
                //Class 类型
            } else if (type instanceof Class) {
                builder.append(((Class) type).getSimpleName());
                builder.append("\u2000");
                builder.append(paramName[i]);
                builder.append(",");
            }
            if (i == types.length - 1) {
                builder.deleteCharAt(builder.length() - 1);
                return builder.toString();
            }
        }
        return "";
    }
}
