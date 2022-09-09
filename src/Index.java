import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Index {
	//map to hold Blob/content of 
	HashMap<String, String> map= new HashMap<String, String>();


	public static void main (String[]args) throws NoSuchAlgorithmException, FileNotFoundException, IOException {
		Index newIndex=new Index();
	}
	//
	public Index() throws NoSuchAlgorithmException, FileNotFoundException, IOException{
		//create initial file and folder
		initialize();
		add("sha1Tester.txt");
				
    
	}
	
	public void initialize() throws NoSuchAlgorithmException, FileNotFoundException, IOException{
		//CREATING NEW FILE
		String fileName= "/Users/caseylandecker/eclipse-workspace/Git Prerequisites/index";		
		Path newFilePath=Paths.get(fileName);
		//System.out.println(newFilePath.toAbsolutePath());

		try {
			Files.writeString(newFilePath, "", StandardCharsets.ISO_8859_1);			
		}
		catch(IOException excpetion) {
			excpetion.printStackTrace();
			System.out.println(excpetion);
			System.out.println("Write failed for "+ fileName);
		}
		
		//CREATING DIRECTORY
		File f1=new File("objects1");
		if (f1.mkdir()) { 
            System.out.println("Directory is created");
        }
        else {
            System.out.println("Directory cannot be created");
        }
	}
	
	//THIS IS FULLY UNTESTED
	//add fileName and file SHA1
	public void add(String fileName) throws NoSuchAlgorithmException, FileNotFoundException, IOException {
		//CREATE BLOB FOR A GIVEN FILENAME
		//first must get path bc this takes in fileName not path
		Path path=Paths.get(fileName);
		System.out.println(path.toAbsolutePath());// file path isn't correct
		String filePath=""+path.toAbsolutePath();
		//this now creates the blob
		Blob fileBlob= new Blob(filePath);
		map.put(fileName, fileBlob.getSHA1());
		
		//ADD TO HASHMAP
		BufferedWriter bf=null;
		try {
			bf=new BufferedWriter(new FileWriter("index"));
			bf.write(fileName + " : "+ map.get(fileName));
			bf.newLine();
			bf.flush();
		}
		catch (IOException e) {
            e.printStackTrace();
        }
        finally { 
            try {
  
                // always close the writer
                bf.close();
            }
            catch (Exception e) {
            }
        }
		
	}
	
	public void remove(String fileName) {
		
		
		
	}
	
	

}
