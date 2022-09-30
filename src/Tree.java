import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;

public class Tree {
	
	//FINISH THIS TESTER
	public static void main(String[] args) throws IOException {
	
			ArrayList<String> array= new ArrayList<String>();
			array.add("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f");
			array.add("blob : 01d82591292494afd1602d175e165f94992f6f5f");
			array.add("blob : f1d82236ab908c86ed095023b1d2e6ddf78a6d83");
			array.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
			array.add("tree : e7d79898d3342fd15daf6ec36f4cb095b52fd976");
			Tree tree=new Tree(array);
			//check that file name is valid SHA1
			//check that 
						
		}
	
	
	ArrayList<String> list = new ArrayList<String>();
	String addedUp;
	String sha1;
	
	public Tree(ArrayList<String> list) throws IOException {
		//Making a huge mash of all the strings
		addedUp = "";
		for (int i = 0; i < list.size(); i++) {
			if (i < list.size() - 1) {
				addedUp += list.get(i);
				addedUp += "\n";
			}
			else if (i == list.size() - 1) {
				addedUp += list.get(i);
			}
		}
		
		sha1 = getSha1(addedUp);
		
		File file = new File("./objects/" + sha1);
		file.createNewFile();
		PrintWriter writer = new PrintWriter("./objects/" + sha1);
		System.out.println(addedUp);
		writer.print(addedUp);
		writer.close();
	}
	
	static String getSha1(String starter)
	{
//		return "asdf";
	    String sha1 = "";
	    try
	    {
	        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(starter.getBytes("UTF-8"));
	        sha1 = byteToHex(crypt.digest());
	    }
	    catch(NoSuchAlgorithmException e)
	    {
	        e.printStackTrace();
	    }
	    catch(UnsupportedEncodingException e)
	    {
	        e.printStackTrace();
	    }
	    return sha1;
	}

	private static String byteToHex(final byte[] hash)
	{
	    Formatter formatter = new Formatter();
	    for (byte b : hash)
	    {
	        formatter.format("%02x", b);
	    }
	    String result = formatter.toString();
	    formatter.close();
	    return result;
	}

}
