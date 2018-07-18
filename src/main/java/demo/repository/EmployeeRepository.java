package demo.repository;

import java.io.IOException;

import com.amazonaws.annotation.ThreadSafe;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;

import demo.model.Employee;

@ThreadSafe
public final class EmployeeRepository {

    private DynamoDBMapper dynamodbMapper;

    public EmployeeRepository() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
        dynamodbMapper = new DynamoDBMapper(client,
                DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES.config());
    }

    public EmployeeRepository(DynamoDBMapper mapper) {
        this.dynamodbMapper = mapper;
    }

    public Employee getEmployee(String appId, String uuid) throws IllegalAccessException, InstantiationException {
        Class<? extends Employee> clazz = Employee.getDataType(appId);
        Employee result = dynamodbMapper.load(clazz, uuid);
        if (result == null) {
            result = clazz.newInstance();
            result.setUuid(uuid);
        }
        return result;
    }

    public void removeEmployee(String appId, String uuid)
            throws IllegalArgumentException, IllegalAccessException, InstantiationException {
        Employee employee = Employee.getDataType(appId).newInstance();
        employee.setUuid(uuid);
        dynamodbMapper.delete(employee);
    }

    public void updateEmployee(String appId, String uuid, String jsonStr)
            throws IllegalArgumentException, IOException, IllegalAccessException, InstantiationException {

        Employee employee = Employee.unmarshall(appId, jsonStr);
        employee.setUuid(uuid);
        dynamodbMapper.save(employee);
    }

}