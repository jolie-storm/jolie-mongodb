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
        mongoDbConnector.connect(connectValue);

        Value insertValue = Value.create();
        insertValue.getFirstChild("collection").setValue("test");
        insertValue.getFirstChild("document").getFirstChild("numberInt").setValue(100);
        insertValue.getFirstChild("document").getFirstChild("numberDouble").setValue(100.0);
        insertValue.getFirstChild("document").getFirstChild("string").setValue("this is a test string");

        Value response = mongoDbConnector.insert(insertValue);
        assertEquals(true,response.hasChildren());
        StringBuilder sb = new StringBuilder();
        JsUtils.valueToJsonString(response , true ,null , sb);
        System.out.println(sb.toString());

    }
}