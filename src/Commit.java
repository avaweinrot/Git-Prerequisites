import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;

public class Commit {
	private LinkedList<Commit> list=new LinkedList<Commit>();
	private String summary="";
	private String author="";
	public Tree myTree;
	private String contents="";
	private String shaContents = ""; 
	private String parent = ""; 
	private ArrayList<String> modified; 
	
	
	public Commit(String summ, String au, String par) throws IOException {
		modified = new ArrayList<String>();
		summary=summ;
		author=au;
		parent=par;
		contents=getFileContents();
		shaContents = getFileContentsForSHA();
		BufferedReader br = new BufferedReader(new FileReader("index"));
		boolean delete = false; 
		while (br.ready())
		{
			String line = br.readLine(); 
			if (line.substring(0, 1).equals("*"))
			{
				delete = true; 
				return;
			}
		}
		br.close();
		if (delete == true)
		{
			myTree = generateTreeForDelete(); 
		}
		else
		{
			myTree = generateTree(); 
		}
//		System.out.println(myTree.addedUp);
		
		//IF THERE IS A PARENT, CHANGE NEXT COMMIT OF PREVIOUS NODE TO NOW HAVE A NEXT ONE
		if (!parent.equals("")) {
			changeParentFile(parent);
		}
		writeFile();
		clearTheFile2();
		createHead(); 
//		clearTheFile(); 	
		
	}
	
	public void createHead()
	{
		String fileName= "HEAD";
		Path newFilePath=Paths.get(fileName);
		//System.out.println(newFilePath.toAbsolutePath());

		try {
			Files.writeString(newFilePath, generateSHA1(shaContents), StandardCharsets.ISO_8859_1);			
		}
		catch(IOException excpetion) {
			excpetion.printStackTrace();
			System.out.println(excpetion);
			System.out.println("Write failed for "+ fileName);
		}
	}
	
	
	public String checkTreeForFile(String tree, String fileName) throws IOException
	{
		String treeEnd = ""; 
		ArrayList<String> wantedContents = new ArrayList<String>(); 
		String name = "";
		String sha = ""; 
		BufferedReader br = new BufferedReader(new FileReader("objects/" + tree));
		while (br.ready())
		{
			String line = br.readLine(); 
			if (line.contains(fileName))
			{
				treeEnd = tree; 
				modified = wantedContents;
				return treeEnd; 
			}
			else if (line.substring(0, 4).equals("blob"))
			{
				int space = line.indexOf(' ');
				name = line.substring(0, space);
				sha = line.substring(space + 3); 
				wantedContents.add("blob : " + sha + " " + name); 
				
			}
			else if (line.substring(0, 4).equals("tree"))
			{
				String treeSha = line.substring(7, 47); 
				checkTreeForFile(treeSha, fileName); 
			}
		}
		br.close();
		modified = wantedContents;
		return treeEnd; 
	}
	
	
	public Tree generateTreeForDelete() throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader("index"));
		String deletedFile = ""; 
		while (br.ready())
		{
			String line = br.readLine(); 
			if (line.substring(0, 1).equals("*"))
			{
				deletedFile = line.substring(10); 
			}
		}
		br.close();
		
		ArrayList<String> treeContents = new ArrayList<String>();
		String treeEnd = checkTreeForFile(getTreeSha(), deletedFile); 
		treeContents = modified; 
		BufferedReader br2 = new BufferedReader(new FileReader ("objects/" + treeEnd));
		String name = "";
		String sha = ""; 
		while (br2.ready())
		{
			String line = br2.readLine(); 
			if (line.substring(0, 4).equals("blob"))
			{
				if (!treeContents.contains(line))
				{
					int space = line.indexOf(' ');
					name = line.substring(0, space);
					sha = line.substring(space + 3); 
					treeContents.add("blob : " + sha + " " + name);  
				}
			}
		}
		if (!getTreeSha().equals(""))
		{
			treeContents.add("tree : " + getTreeSha()); 
		}
		br2.close();
//		System.out.println(indexContents);
		Tree pTree = new Tree(treeContents); 
//		System.out.println(myTree.getSha1(myTree.addedUp));
		return pTree;
	}
	
//	public String getTreeFromParent() throws IOException
//	{
//		BufferedReader br = new BufferedReader(new FileReader("objects/" + parent));
//		String treeSha = br.readLine(); 
//		return treeSha; 
//	}
	
//	public ArrayList<String> makeArrayListOfModifications() throws IOException
//	{
//		ArrayList<String> modifications = new ArrayList<String>();
//		BufferedReader br = new BufferedReader(new FileReader("index"));
//		while (br.ready()) 
//		{
//			String line = br.readLine(); 
//			if (line.substring(0, 1).equals("*"))
//			{
//				modifications.add(line.substring(10));
//			}
//		}
//		return modifications; 
//	}
	
	public Tree generateTree() throws IOException
	{
		ArrayList<String> indexContents = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader("index"));
		String fileName = "";
		String sha = ""; 
		while (br.ready())
		{
			String line = br.readLine(); 
			int space = line.indexOf(' ');
			fileName = line.substring(0, space);
			sha = line.substring(space + 3); 
			indexContents.add("blob : " + sha + " " + fileName); 
		}
		if (!getTreeSha().equals(""))
		{
			indexContents.add("tree : " + getTreeSha()); 
		}
		br.close();
//		System.out.println(indexContents);
		Tree pTree = new Tree(indexContents); 
//		System.out.println(myTree.getSha1(myTree.addedUp));
		return pTree;
	}
	
	public String getTreeSha() throws FileNotFoundException, IOException
	{
		String treeShaLine = ""; 
		String realSha = ""; 
//		System.out.println(parent); 
		if (!parent.equals(""))
		{
			BufferedReader br = new BufferedReader(new FileReader ("objects/" + parent)); 
			treeShaLine = br.readLine();
			realSha = treeShaLine.substring(treeShaLine.indexOf("/") + 1);
			
		}
		return realSha; 
		
	}
	
	public static void clearTheFile() throws IOException {
	    FileWriter fwOb = new FileWriter("index", false); 
	    PrintWriter pwOb = new PrintWriter(fwOb, false);
	    pwOb.flush();
	    pwOb.close();
	    fwOb.close();
	}
	
	public static void clearTheFile2() throws IOException
	{
		 FileWriter fwOb = new FileWriter("HEAD", false); 
		    PrintWriter pwOb = new PrintWriter(fwOb, false);
		    pwOb.flush();
		    pwOb.close();
		    fwOb.close();
	}
	
	//edits parent file to now have a child
	public void changeParentFile(String par) throws IOException {
		BufferedReader buff=new BufferedReader(new FileReader("objects/"+par));
		//adding all old contents to a new string
		String parContents="";
		parContents+=buff.readLine()+"\n";
		parContents+=buff.readLine()+"\n";
		//changes third line to be the SHA1 of this current commit (its new child)
		parContents+="objects/"+generateSHA1(shaContents)+"\n";
//		System.out.println(parContents); 
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
	
//	public String getPTree() {
//		return pTree;
//	}
	
	public String getFileContents(){
		String contents="objects/"+myTree.getSha1(myTree.addedUp)+"\n";
		if(!parent.equals("")) {
			contents+="objects/"+parent;
		}
		contents+="\n"+"\n";
		
		contents+=author+"\n"+ getDate() + "\n"+summary;	
		return contents;
		
	}
	
	public String getFileContentsForSHA(){
		if(!parent.equals("")) {
			shaContents+="objects/"+parent;
		}
		shaContents+="\n"+"\n";
		
		shaContents+=author+"\n"+ getDate() + "\n"+summary;	
		return shaContents;
		
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
//	public static void main (String[]args) throws IOException {
//		Commit com1=new Commit("pTree", "summary 1", "Casey Landecker", "");
//		Commit com2=new Commit("pTree2", "summary 2", "Casey Landecker", "9f95d9dc8a04721c58b9912c0ed9ac84ad46f5d6");
//		Commit com3=new Commit("pTree3", "summary 3", "Casey Landecker", "1fb2c6c15d31f11a2e5955f56c5008028ceb8fae");
//	}
	
	public String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		String date=dateFormat.format(cal.getTime());
		return date;
		
	}
	
	public void writeFile() throws IOException {
		//creating new file
		String fileName= generateSHA1(shaContents);// file name is sha1
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
