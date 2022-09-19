import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

public class Commit {
	private LinkedList<Commit> list=new LinkedList<Commit>();
	private String summary="";
	private String author="";
	private String pTree="";
	private String contents="";
	private String parent=""; 
	private String nextCommit="";
	
	public Commit(String pTr, String summ, String au, String par) throws IOException {
		pTree=pTr;
		summary=summ;
		author=au;
		parent=par;
		contents=getFileContents();
		
		//IF THERE IS A PARENT, CHANGE NEXT COMMIT OF PREVIOUS NODE TO NOW HAVE A NEXT ONE
		if (!parent.equals("")) {
			changeParentFile(parent);
		}
		writeFile();
			
		
	}
	//edits parent file to now have a child
	public void changeParentFile(String par) throws IOException {
		BufferedReader buff=new BufferedReader(new FileReader("objects/"+par));
		//adding all old contents to a new string
		String parContents="";
		parContents+=buff.readLine()+"\n";
		parContents+=buff.readLine()+"\n";
		//changes third line to be the SHA1 of this current commit (its new child)
		parContents+="objects/"+generateSHA1(contents)+"\n";
		buff.readLine();
		parContents+=buff.readLine()+"\n";
		parContents+=buff.readLine()+"\n";
		parContents+=buff.readLine()+"\n";
		
		//rewriting the parent file w/ a child 
		Path p = Paths.get("objects/"+par);
        try {
            Files.writeString(p, parContents, StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }		
		
	}
	
	public String getPTree() {
		return pTree;
	}
	
	public String getFileContents(){
		String contents="objects/"+pTree+"\n";
		if(!parent.equals("")) {
			contents+="objects/"+parent;
		}
		contents+="\n";
		
		if(!nextCommit.equals("")) {
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
	public static void main (String[]args) throws IOException {
		//Commit com1=new Commit("pTree", "summary 1", "Casey Landecker", "");
		//Commit com2=new Commit("pTree2", "summary 2", "Casey Landecker", "174ff82235d9fa8418bbd1ae8bf58969c9a12eef");
		Commit com3=new Commit("pTree3", "summary 3", "Casey Landecker", "bbfd64feca61251fd3e05615ffe50e1b2ebbe435");
	}
	
	public String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		String date=dateFormat.format(cal.getTime());
		return date;
		
	}
	
	public void writeFile() throws IOException {
		//creating new file
		String fileName= generateSHA1(contents);// file name is sha1
		Path newFilePath=Paths.get("objects/"+fileName);
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
