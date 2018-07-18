package demo.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class Employee {

    public static final Class<? extends Employee> getDataType(String appId) {
        switch (appId) {
        case "abc":
            return AbcEmployee.class;
        case "xyz":
            return XyzEmployee.class;
        default:
            throw new IllegalArgumentException("Unkown appid: " + appId);
        }
    }

    public static final Employee unmarshall(String appId, String jsonStr)
            throws IllegalArgumentException, IOException, IllegalAccessException {
        Class<? extends Employee> clazz = getDataType(appId);
        ObjectMapper mapper = new ObjectMapper();
        Employee result = mapper.readValue(jsonStr, clazz);
        return result;
    }

    public abstract String getUuid();

    public abstract void setUuid(String uuid);

}