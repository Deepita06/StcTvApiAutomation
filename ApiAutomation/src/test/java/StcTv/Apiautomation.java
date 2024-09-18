package StcTv;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Apiautomation {

    private static final String BASE_URL = "https://api.restful-api.dev/objects";

    @BeforeClass
    public void setup() {
        // Set up the base URI for Rest Assured
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void testAddNewDevice() {
        // Create request payload
        String requestPayload = "{\n" +
            "  \"name\": \"Apple Max Pro 1TB\",\n" +
            "  \"data\": {\n" +
            "    \"year\": 2023,\n" +
            "    \"price\": 7999.99,\n" +
            "    \"CPU model\": \"Apple ARM A7\",\n" +
            "    \"Hard disk size\": \"1 TB\"\n" +
            "  }\n" +
            "}";

        // Send POST request
        RequestSpecification request = RestAssured.given()
            .header("Content-Type", "application/json")
            .body(requestPayload);

        Response response = request.post();

        // Validate the HTTP status code
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 Created");

        // Parse JSON response
        String id = response.jsonPath().getString("id");
        String name = response.jsonPath().getString("name");
        String createdAt = response.jsonPath().getString("createdAt");
        Integer year = response.jsonPath().getInt("data.year");
        Double price = response.jsonPath().getDouble("data.price");
        String cpuModel = response.jsonPath().getString("data.CPU model");
        String hardDiskSize = response.jsonPath().getString("data.Hard disk size");

        // Validate fields
        Assert.assertNotNull(id, "ID should not be null");
        Assert.assertNotNull(createdAt, "CreatedAt should not be null");
        Assert.assertEquals(name, "Apple Max Pro 1TB", "Name should match");
        Assert.assertEquals(year, 2023, "Year should match");
        Assert.assertEquals(price, 7999.99, "Price should match");
        Assert.assertEquals(cpuModel, "Apple ARM A7", "CPU model should match");
        Assert.assertEquals(hardDiskSize, "1 TB", "Hard disk size should match");

        // Print the response for verification
        System.out.println("Response Body: " + response.getBody().asString());
    }
}
