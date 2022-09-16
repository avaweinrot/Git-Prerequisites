import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CaseyTester {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		File file = new File ("test1.txt");
		PrintWriter writer = new PrintWriter(file);
		writer.append("lacaca");
		writer.close();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		File file = new File("test1.txt");
		file.delete();
	}

	@Test
	void test() throws NoSuchAlgorithmException, FileNotFoundException, IOException {
		testInitialize();
		testRemove();
		testAdd();
	}
	
	void testInitialize() throws NoSuchAlgorithmException, FileNotFoundException, IOException {
		Index g = new Index();
		g.initialize();
		
		File file = new File("index");
		assertTrue(file.exists());
		
		Path path = Paths.get("objects");
		assertTrue(Files.exists(path));
	}
	
	void testAdd() throws NoSuchAlgorithmException, FileNotFoundException, IOException {
		Index test1 = new Index();
		try {
			test1.add("test1.txt");
		} catch (Exception e) {
			System.out.println("File to add not found.");
		}
		File file1 = new File("objects/EB07E0A104E3D1B9220B49FCBD36A03428414AC1");
		assertTrue(file1.exists());
	}
	
	void testRemove() throws NoSuchAlgorithmException, FileNotFoundException, IOException {
		Index test2 = new Index();
		test2.remove("test1.txt");
		File file1 = new File("objects/eb07e0a104e3d1b9220b49fcbd36a03428414ac1");
		assertFalse(file1.exists());
	}
	
}
