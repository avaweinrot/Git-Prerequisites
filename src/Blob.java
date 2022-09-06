
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
	import java.nio.file.Paths;
	

	/**
	 * User: zeroleaf
	 * Date: 13-10-3
	 * Time: 21:04
	 *
	 * Generate a sha1 hash code of a file.
	 */
	public class Blob {
		
		//call all methods into this
		//call fileHash
		//call readFile
		// call whatever to create new file in correct place
		public Blob(String filePath) throws NoSuchAlgorithmException, FileNotFoundException, IOException{
			//gets sha one code 
			String sha1=sha1Code(filePath);
			
			//stores content of og file
			String contents= Blob.readFile(filePath, Charset.forName("US-ASCII"));
			
			//copies content of og file to a new file			
			PrintWriter out = new PrintWriter("/Users/caseylandecker/eclipse-workspace/Git\\ Prerequisites/objects"+sha1);
			out.println(contents);
			out.close();
			
			
			
						
			
		}
		//Take a file on disk and... 
		//Creates a SHA1 String given the whole file data
	    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, FileNotFoundException {
	        Blob fileHash = new Blob("/Users/caseylandecker/Desktop/testfile.txt");
	        //MUST TAKE IN FILE PATH NOT FILE NAME	        

	   
	    }

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
	    
	    //READS CONTENTS OF ORIGINAL FILE
	    public static String readFile(String path, Charset encoding) throws IOException
	    {
	        byte[] encoded = Files.readAllBytes(Paths.get(path));
	        return new String(encoded, encoding);
	    } 
	    
	    	

}
