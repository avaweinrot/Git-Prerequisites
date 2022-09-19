
	import java.io.FileInputStream;
	import java.io.FileNotFoundException;
	import java.io.IOException;
	import java.io.PrintWriter;
	import java.security.DigestInputStream;
	import java.security.MessageDigest;
	import java.security.NoSuchAlgorithmException;
	import java.io.File;
	import java.nio.charset.Charset;
	import java.nio.charset.StandardCharsets;
	import java.nio.file.Files;
	import java.nio.file.Path;
	import java.nio.file.Paths;

	
	public class Blob {
		private String sha1;
		
		public Blob(String filePath) throws NoSuchAlgorithmException, FileNotFoundException, IOException{
			//gets sha1 code 
			sha1=sha1Code(filePath);
			
			//stores content of og file
			String contents= Blob.readFile(filePath, StandardCharsets.ISO_8859_1);
			
			//copies content of og file to a new file
			String fileName= "/Users/caseylandecker/eclipse-workspace/Git Prerequisites/objects/"+sha1;
			
			Path newFilePath=Paths.get(fileName);
			System.out.println(newFilePath.toAbsolutePath());
			try {
				Files.writeString(newFilePath, contents, StandardCharsets.ISO_8859_1);
				String testContent=Files.readString(newFilePath);			
				
			}
			catch(IOException excpetion) {
				excpetion.printStackTrace();
				System.out.println(excpetion);
				System.out.println("Write failed for "+ fileName);
			}			
			
		}
		
		public String getSHA1(){
			return sha1;
		}
		
		//TESTER
	    /*public static void main(String[] args) throws IOException, NoSuchAlgorithmException, FileNotFoundException{
	       //ADDED TRY CATCH
	    	try {
	        	Blob fileHash = new Blob("/Users/caseylandecker/Desktop/testfile.txt");
	        }
	        catch (Exception e) {
	        	System.out.println("Blob couldn't find file");
	        }
	        	        	   
	    }*/
	    
	    //CODE FOR GETTING SHA1
	    /**
	     * Generate a file 's sha1 hash code.
	     * @param filePath file path
	     * @return sha1 hash code of this file
	     * @throws IOException if file doesn't or other IOException
	     * @throws NoSuchAlgorithmException
	     */
	    public String sha1Code(String filePath) throws IOException, NoSuchAlgorithmException, FileNotFoundException {
	        FileInputStream fileInputStream = new FileInputStream(filePath);
	        MessageDigest digest = MessageDigest.getInstance("SHA-1");
	        DigestInputStream digestInputStream = new DigestInputStream(fileInputStream, digest);
	        byte[] bytes = new byte[1024];
	        // read all file content
	        while (digestInputStream.read(bytes) > 0);

//	        digest = digestInputStream.getMessageDigest();
	        byte[] resultByteArry = digest.digest();
	        return bytesToHexString(resultByteArry);
	    }

	    /**
	     * Convert a array of byte to hex String. <br/>
	     * Each byte is covert a two character of hex String. That is <br/>
	     * if byte of int is less than 16, then the hex String will append <br/>
	     * a character of '0'.
	     *
	     * @param bytes array of byte
	     * @return hex String represent the array of byte
	     */
	    public static String bytesToHexString(byte[] bytes) {
	        StringBuilder sb = new StringBuilder();
	        for (byte b : bytes) {
	            int value = b & 0xFF;
	            if (value < 16) {
	                // if value less than 16, then it's hex String will be only
	                // one character, so we need to append a character of '0'
	                sb.append("0");
	            }
	            sb.append(Integer.toHexString(value).toUpperCase());
	        }
	        return sb.toString();
	    }
	    
	    //CODE TO READ CONTENTS OF ORIGINAL FILE
	    public static String readFile(String path, Charset encoding) throws IOException
	    {
	        byte[] encoded = Files.readAllBytes(Paths.get(path));
	        return new String(encoded, encoding);
	    } 
	    
	    	

}
