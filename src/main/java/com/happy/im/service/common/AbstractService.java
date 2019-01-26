package com.happy.im.service.common;

import com.happy.im.annotation.Operation;
import com.happy.im.model.BaseModel;
import com.happy.im.serialization.Serializer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

/**
 * Created by youngwa on 2019/01/16. 22:27
 */
public abstract class AbstractService<R extends BaseModel> {

    public String excute(String req, String operate){
        if (verification(convertToModel(req))) {

            Method[] declaredMethods = this.getClass().getDeclaredMethods();
            for (Method method : declaredMethods) {
                Operation mapping = method.getDeclaredAnnotation(Operation.class);
                if (mapping != null && mapping.value().equals(operate)) {
                    try {
                        Object resp = method.invoke(this, req);
                        return (String) resp;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return null;
    }

    /**
     * json转换为实体类
     *
     * @param req
     * @return
     */
    public R convertToModel(String req) {
        return Serializer.deserialize(req, getRClass());
    }

    public Class<R> getRClass() {
        Class<R> tClass = (Class<R>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }

    /**
     * 实体类转为json字符串
     *
     * @param r
     * @return
     */
    public String convertToString(R r) {
        return Serializer.serialize(r);
    }

    public abstract boolean verification(R r);
}
