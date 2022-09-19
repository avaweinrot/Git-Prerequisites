import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
//IF THERE IS A PARENT, CHANGE NEXT COMMIT OF PREVIOUS NODE TO NOW HAVE A NEXT ONE
public class Commit {
	private LinkedList<Commit> list=new LinkedList<Commit>();
	private String summary="";
	private String author="";
	private String pTree="";
	private String contents="";
	private String parent=null; //parents and next commit should just be strings bc its a filename
	private String nextCommit=null;
	
	public Commit(String pTr, String summ, String au, String par) {
		pTree=pTr;
		summary=summ;
		author=au;
		parent=par;
		
		//UHHHH IDK WHAT TO DO
		
		
		contents=getFileContents();
			
		
	}
	//not sure what to do
	//edits parent file to now have a child
	public void changeParent() {
		
		
	}
	public String getPTree() {
		return pTree;
	}
	
	public String getFileContents(){
		String contents="objects/"+pTree+"\n";
		if(parent!=null) {
			contents+="objects/"+parent+"\n";
		}
		if(nextCommit!=null) {
			contents+="objects/"+nextCommit;
		}
		contents+="\n"+author+"\n"+ getDate() + "\n"+summary;	
		return contents;
		
	}
	
	public static String generateSHA1(String st) throws IOException {
		String str=st;
		String SHA="";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
	        digest.reset();
	        digest.update(str.getBytes("utf8"));
	        SHA = String.format("%040x", new BigInteger(1, digest.digest()));
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return SHA;
		
    }
	public static void main (String[]args) {
		
		
	}
	
	public String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String date=dateFormat.format(cal.getTime());
		return date;
		
	}
	
	public void writeFile() throws IOException {
		//creating new file
		String fileName= generateSHA1(contents);// file name is sha1
		Path newFilePath=Paths.get(fileName);
		//System.out.println(newFilePath.toAbsolutePath());

		try {
			Files.writeString(newFilePath, contents, StandardCharsets.ISO_8859_1);			
		}
		catch(IOException excpetion) {
			excpetion.printStackTrace();
			System.out.println(excpetion);
			System.out.println("Write failed for "+ fileName);
		}
		
	}
	
	
	
	

}
