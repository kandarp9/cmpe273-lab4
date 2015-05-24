package edu.sjsu.cmpe.cache.client;

/**
 * @author Kandarp
 *
 */
public class Client {

	public static void main(String[] args) throws Exception {

		CRDTClient objCRDTClient = new CRDTClient();
		String strInputOne = "a";
		String strInputTwo = "b";
		
		System.out.println("Starting Cache Client.");
		
		System.out.println("Starting Step 1.");
		objCRDTClient.put(1, strInputOne);
		Thread.sleep(30000);
		System.out.println("Step 1 Done.");
		
		System.out.println("Starting Step 2.");
		objCRDTClient.update(1, strInputTwo);
		Thread.sleep(30000);
		System.out.println("Step 2 Done.");
		
		System.out.println("Starting Step 3.");
		objCRDTClient.get(1);
		objCRDTClient.updateValues(1, strInputTwo);
		Thread.sleep(30000);
		System.out.println("Post.");
		objCRDTClient.get(1);
		System.out.println("Step 3 Done");
		
		System.out.println("Cache Client Exists.");

		
	}

}
