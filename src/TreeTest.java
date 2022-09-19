import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TreeTest {
	static ArrayList<String> array= new ArrayList<String>();
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		array.add("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f");
		array.add("blob : 01d82591292494afd1602d175e165f94992f6f5f");
		array.add("blob : f1d82236ab908c86ed095023b1d2e6ddf78a6d83");
		array.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
		array.add("tree : e7d79898d3342fd15daf6ec36f4cb095b52fd976");
		
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		//deletes file
		File file= new File("objects/dd4840f48a74c1f97437b515101c66834b59b1be");
		file.delete();
	}

	@Test
	void testTree() throws IOException {
		Tree tree=new Tree(array);
	}
	@Test
	void testSHA1() {	
		//getting contents from array
		String arrayContents="";
		int len = array.size();
	      for (int i = 0; i < len; i++) {
	         arrayContents+=array.get(i);
	         if(i!=len-1) {
	        	 arrayContents+="\n";
	         }
	      }
	      
	      //getting correct SHA1 from array
			String SHA="";
			try {
				MessageDigest digest = MessageDigest.getInstance("SHA-1");
		        digest.reset();
		        digest.update(arrayContents.getBytes("utf8"));
		        SHA = String.format("%040x", new BigInteger(1, digest.digest()));
			} catch (Exception e){
				e.printStackTrace();
			}
			
	      //asserting the SHA equals whats on the hub
	      assertEquals(SHA,("dd4840f48a74c1f97437b515101c66834b59b1be"));		
	}
	@Test
	void testFileExists() throws NoSuchAlgorithmException, FileNotFoundException, IOException {
		//asserting that file is created in correct place
		File f=new File("objects/dd4840f48a74c1f97437b515101c66834b59b1be");
		Index ind=new Index();		
		assertTrue(f.exists());
						
	}
	
	@Test
	void testFileContents() throws IOException {
		BufferedReader buff=new BufferedReader(new FileReader("objects/dd4840f48a74c1f97437b515101c66834b59b1be"));
		//adding all old contents to a new string
		String contents="";
		contents=buff.readLine();
		assertEquals(contents, "blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f");
		contents=buff.readLine();
		assertEquals(contents, "blob : 01d82591292494afd1602d175e165f94992f6f5f");
		contents=buff.readLine();
		assertEquals(contents, "blob : f1d82236ab908c86ed095023b1d2e6ddf78a6d83");
		contents=buff.readLine();
		assertEquals(contents, "tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
		contents=buff.readLine();
		assertEquals(contents, "tree : e7d79898d3342fd15daf6ec36f4cb095b52fd976");

	}
	
	
	
	
	

}
