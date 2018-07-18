package demo.model;

import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@DynamoDBTable(tableName = "AbcEmployee")
public class AbcEmployee extends Employee {

    @DynamoDBHashKey(attributeName = "Uuid")
    private String uuid;

    @DynamoDBAttribute(attributeName = "Name")
    private String name;

    @DynamoDBAttribute(attributeName = "Skills")
    private Set<String> skills;

}