package fakeStoreAPI;

import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetProducts {
	 public static void main(String[] args) {
	        // Base URI for the API
	        RestAssured.baseURI = "https://fakestoreapi.com";

	        // Send GET request and store the response
	        Response response = RestAssured.get("/products");

	        // Check if the response is successful
	        if (response.getStatusCode() == 200) {
	            String jsonResponse = response.getBody().asString();
	            // Pretty print the JSON response
	            Gson gson = new GsonBuilder().setPrettyPrinting().create();
	            String prettyJson = gson.toJson(gson.fromJson(jsonResponse, Object.class));

	            // Print the formatted JSON to the console
	            System.out.println("Formatted JSON Response:\n" + prettyJson);

	            // Save to a file (optional)
	            try (FileWriter writer = new FileWriter("output.json")) {
	                writer.write(prettyJson);
	                System.out.println("\nFormatted JSON saved to 'output.json'.");
	            } catch (Exception e) {
	                e.printStackTrace();
	            }

	        } else {
	            System.out.println("Failed to fetch products. Status code: " + response.getStatusCode());
	        }
	    }

}
