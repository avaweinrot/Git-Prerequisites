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
//		Index newIndex=new Index();
//		newIndex.add("sha1Tester.txt");
//		newIndex.add("sha1Tester2.txt");
//		newIndex.add("sha1Tester3.txt");
//		
//		newIndex.remove("sha1Tester.txt");
//		newIndex.remove("sha1Tester3.txt");
		
	}
	//
	public Index() throws NoSuchAlgorithmException, FileNotFoundException, IOException{
		//create initial file and folder
		initialize();			
    
	}
	
	public void initialize() throws NoSuchAlgorithmException, FileNotFoundException, IOException{
		//CREATING NEW FILE
		String fileName= "index";
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
		File f1=new File("objects");
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
		String filePath=""+path.toAbsolutePath();
		
		//this now creates the blob
		Blob fileBlob= new Blob(filePath);
		
		//adds to hashmap
		map.put(fileName, fileBlob.getSHA1());
		
		//putting into file
		BufferedWriter bf=null;
		try {
			bf=new BufferedWriter(new FileWriter("index"));
			for (Map.Entry<String, String> entry : map.entrySet()) {
 
               // put key and value separated by a colon
               bf.write(entry.getKey() + " : "+ entry.getValue());
 
               // new line
               bf.newLine();
			}
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
		//removes set from hashMap and saves hash
		String hash=map.get(fileName);
		map.remove(fileName);
		
		//deletes file
		File myObj = new File("objects/"+hash);
		if (myObj.delete()) {
            System.out.println("File deleted successfully");
        }
        else {
            System.out.println("Failed to delete the file");
        }

		
		//rewrites hashmap to file		  
        BufferedWriter bf = null;
  
        try {
  
            // create new BufferedWriter for the output file
            bf = new BufferedWriter(new FileWriter("index"));
  
            // iterate map entries
            for (Map.Entry<String, String> entry :
                 map.entrySet()) {
  
                // put key and value separated by a colon
                bf.write(entry.getKey() + " : "
                         + entry.getValue());
  
                // new line
                bf.newLine();
            }
  
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
				
		
	
}
	



