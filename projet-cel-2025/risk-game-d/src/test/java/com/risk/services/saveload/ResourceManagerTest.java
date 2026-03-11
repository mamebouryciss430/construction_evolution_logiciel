package com.risk.services.saveload;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for ResourceManager.
 * 
 * @author Neha Pal
 * @author Ruthvik Shandilya
 *
 */
public class ResourceManagerTest {

	/**
	 * Test to check name and code saved.
	 * @throws ClassNotFoundException ClassNotFoundException
	 * @throws IOException IOException
	 */
	@Test
	@Disabled("Tests should not create files (too slow)")
	// FIXME: Correct test: if a file is created during the test, it should be deleted after the test.
	public void saveLoadTest() throws IOException,ClassNotFoundException{

		SaveData test = new SaveData();
		test.setCode(374);
		test.setName("Canada");

		FileOutputStream fileOutputStream = new FileOutputStream("OUTPUT_FILE");
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		test.writeExternal(objectOutputStream);

		objectOutputStream.flush();
		objectOutputStream.close();
		fileOutputStream.close();

		FileInputStream fileInputStream = new FileInputStream("OUTPUT_FILE");
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);


		SaveData test1 = new SaveData();
		test1.readExternal(objectInputStream);

		objectInputStream.close();
		fileInputStream.close();

		assertTrue(test1.getCode()== test.getCode());
		assertEquals(test1.getName(),(test.getName()));

	}

}
