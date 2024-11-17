package fakeStoreAPI;

	import io.restassured.RestAssured;
	import io.restassured.response.Response;
	import io.restassured.http.ContentType;

	public class PostProduct {

	    public static void main(String[] args) {
	        // Base URI for the API
	        RestAssured.baseURI = "https://fakestoreapi.com";

	        // Data to POST
	        String productData = """
	            {
	        		 "id": 21.0,
	                "title": "New Stylish Backpack",
	                "price": 34.99,
	                "description": "A perfect backpack for daily and travel use.",
	                "category": "bags",
	                "image": "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
	                "rating": {
	                "rate": 4,
	                "count": 120.0
	            }
	            }
	        """;

	        // Sending POST request
	        Response response = RestAssured
	                .given()
	                .contentType(ContentType.JSON)
	                .body(productData) // Attach JSON body
	                .post("/products");

	        // Print the response
	        System.out.println("Status Code: " + response.getStatusCode());
	        System.out.println("Response Body: " + response.getBody().prettyPrint());
	    }

}
