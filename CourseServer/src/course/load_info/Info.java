package course.load_info;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import course.interf.model.IInfo;


public class Info extends UnicastRemoteObject implements IInfo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Info() throws RemoteException {
		super();
	}
	

	@Override	
	public String getAboutInfo() throws FileNotFoundException{
		
		return read("C:\\softInfo\\about.txt");
	}


	@Override
	public String getHelpInfo() throws RemoteException, FileNotFoundException {

		return read("C:\\softInfo\\help.txt");

	}
	private String read(String fileName) throws FileNotFoundException {
	    
	    StringBuilder sb = new StringBuilder();
	 
	    exists(fileName);
	 
	    try {

	        BufferedReader in = new BufferedReader(new FileReader(fileName));
	        try {

	            String s;
	            while ((s = in.readLine()) != null) {
	                sb.append(s);
	                sb.append("\n");
	            }
	        } finally {
	       
	            in.close();
	        }
	    } catch(IOException e) {
	        throw new RuntimeException(e);
	    }
	 
	    return sb.toString();
	}
	
	private void exists(String fileName) throws FileNotFoundException {
	    File file = new File(fileName);
	    if (!file.exists()){
	        throw new FileNotFoundException(file.getName());
	    }
	}

}
