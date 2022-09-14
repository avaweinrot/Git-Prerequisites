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

	public static void main(String[] args) throws IOException {
		ArrayList<String> listy = new ArrayList<String>();
		listy.add("blob : eb07e0a104e3d1b9220b49fcbd36a03428414ac1");
		listy.add("tree : f65ab9ebff94f507b1b67b225b369068681ec8bb");
		Tree eltri = new Tree(listy);
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
	
	private static String getSha1(String starter)
	{
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
