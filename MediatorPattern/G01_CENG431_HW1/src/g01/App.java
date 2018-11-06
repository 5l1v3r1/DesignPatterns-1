package g01;

import java.io.FileNotFoundException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class App {

	public static void main(String[] args){
		// TODO Auto-generated method stub
		ProjectManagementSystem pms = new ProjectManagementSystem();		
		pms.start();
		
		pms.getProjects();
		

	}

}
