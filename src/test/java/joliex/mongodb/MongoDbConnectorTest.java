package joliex.mongodb;

import jolie.js.JsUtils;
import jolie.runtime.FaultException;
import jolie.runtime.Value;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MongoDbConnectorTest {

    @Test
    void insert() throws FaultException, IOException {

        MongoDbConnector mongoDbConnector = new MongoDbConnector();
        Value connectValue = Value.create();
        connectValue.getFirstChild("host").setValue("localhost");
        connectValue.getFirstChild("port").setValue(27017);
        connectValue.getFirstChild("dbname").setValue("test");
        connectValue.getFirstChild("username").setValue("test");
        connectValue.getFirstChild("password").setValue("test");
        connectValue.getFirstChild("timeZone").setValue("Europe/Berlin");
        connectValue.getFirstChild("jsonStringDebug").setValue(false);
        mongoDbConnector.connect(connectValue);

        Value insertValue = Value.create();
        insertValue.getFirstChild("collection").setValue("test");
        insertValue.getFirstChild("document").getFirstChild("numberInt").setValue(100);
        insertValue.getFirstChild("document").getFirstChild("numberDouble").setValue(100.0);
        insertValue.getFirstChild("document").getFirstChild("string").setValue("this is a test string");
        StringBuilder sb = new StringBuilder();
        JsUtils.valueToJsonString(insertValue , false ,null , sb);
        System.out.println(sb.toString());

        Value response = mongoDbConnector.insert(insertValue);
        assertEquals(true,response.hasChildren());
        sb = new StringBuilder();
        JsUtils.valueToJsonString(response , false ,null , sb);
        System.out.println(sb.toString());

        Value deleteRequest = Value.create();
        deleteRequest.getFirstChild("collection").setValue("test");
        deleteRequest.getFirstChild("filter").setValue("{ _id:{$eq: '$id'}}");
        deleteRequest.getFirstChild("filter").getFirstChild("id").setValue(response.getFirstChild("_id").strValue());
        deleteRequest.getFirstChild("filter").getFirstChild("id").getFirstChild("@type").setValue("ObjectId");
        sb = new StringBuilder();
        JsUtils.valueToJsonString(deleteRequest , false ,null , sb);
        System.out.println(sb.toString());

        response = mongoDbConnector.delete(deleteRequest);
        assertEquals(1 , response.getFirstChild("deleteCount").intValue());
        sb = new StringBuilder();
        JsUtils.valueToJsonString(response , true ,null , sb);
        System.out.println(sb.toString());

    }


    @Test
    void find()  throws FaultException, IOException {
        MongoDbConnector mongoDbConnector = new MongoDbConnector();
        Value connectValue = Value.create();
        connectValue.getFirstChild("host").setValue("localhost");
        connectValue.getFirstChild("port").setValue(27017);
        connectValue.getFirstChild("dbname").setValue("test");
        connectValue.getFirstChild("username").setValue("test");
        connectValue.getFirstChild("password").setValue("test");
        connectValue.getFirstChild("timeZone").setValue("Europe/Berlin");
        connectValue.getFirstChild("jsonStringDebug").setValue(true);
        mongoDbConnector.connect(connectValue);

        Value insertValue = Value.create();
        insertValue.getFirstChild("collection").setValue("test");
        insertValue.getFirstChild("document").getFirstChild("numberInt").setValue(100l);
        insertValue.getFirstChild("document").getFirstChild("numberDouble").setValue(100.0);
        insertValue.getFirstChild("document").getFirstChild("string").setValue("this is a test string");
        StringBuilder sb = new StringBuilder();
        JsUtils.valueToJsonString(insertValue , false ,null , sb);
        System.out.println(sb.toString());

        Value response = mongoDbConnector.insert(insertValue);

        sb = new StringBuilder();
        JsUtils.valueToJsonString(response , false ,null , sb);
        System.out.println(sb.toString());


        insertValue = Value.create();
        insertValue.getFirstChild("collection").setValue("test");
        insertValue.getFirstChild("document").getFirstChild("numberInt").setValue(110l);
        insertValue.getFirstChild("document").getFirstChild("numberDouble").setValue(100.0);
        insertValue.getFirstChild("document").getFirstChild("string").setValue("this is a test string");
        sb = new StringBuilder();
        JsUtils.valueToJsonString(insertValue , false ,null , sb);
        System.out.println(sb.toString());
        response = mongoDbConnector.insert(insertValue);
        assertEquals(true,response.hasChildren());

        sb = new StringBuilder();
        JsUtils.valueToJsonString(response , false ,null , sb);
        System.out.println(sb.toString());


        Value findValue = Value.create();
        findValue.getFirstChild("collection").setValue("test");
        findValue.getFirstChild("filter").setValue("{ numberInt:{$gt: '$numberInt'}}");
        findValue.getFirstChild("filter").getFirstChild("numberInt").setValue(90l);

        sb = new StringBuilder();
        JsUtils.valueToJsonString(findValue , false ,null , sb);
        System.out.println(sb.toString());
        response = mongoDbConnector.find(findValue);

        sb = new StringBuilder();
        JsUtils.valueToJsonString(response , false ,null , sb);
        System.out.println(sb.toString());

        assertEquals(2 , response.getChildren("document").size());

        Value deleteRequest = Value.create();
        deleteRequest.getFirstChild("collection").setValue("test");
        deleteRequest.getFirstChild("filter").setValue("{ numberInt:{$gt: '$numberInt'}}");
        deleteRequest.getFirstChild("filter").getFirstChild("numberInt").setValue(90);
        sb = new StringBuilder();
        JsUtils.valueToJsonString(deleteRequest , false ,null , sb);
        System.out.println(sb.toString());

        response = mongoDbConnector.deleteMany(deleteRequest);
        assertEquals(2 , response.getFirstChild("deleteCount").intValue());
        sb = new StringBuilder();
        JsUtils.valueToJsonString(response , true ,null , sb);
        System.out.println(sb.toString());


    }
}