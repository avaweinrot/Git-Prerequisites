import java.io.IOException;
import java.security.NoSuchAlgorithmException;


public class UpdatedCommitTester {

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		Commit test1 = new Commit("summary1", "ava w", ""); 
		Index itest = new Index(); 
		itest.add("ava.txt");
		itest.add("jake.txt");
//		itest.remove("ava.txt");
		Commit test2 = new Commit("summary2", "aariz", "0063b6b5ddb0111c75b90c2bfbe5c1eef9bd092e");
		itest.add("secrets.txt");
		Commit test3 = new Commit("summary3", "will", "f3be42e2fa150a93085369da33206b18a5af224b");
//		test3.checkTreeForFile(null, "jake.txt");
//		Commit test4 = new Commit("summary3", "jake", "ed2733f2f759c5d31dfd5b36555757f237473210");
		
		

	}

}
