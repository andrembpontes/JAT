package edu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class PacketExe implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private static int MAX_SIZE = 10000000;
	
	byte[] exe;
	String name, path;
	File file;
	
	public PacketExe(){
		this.exe = new byte[MAX_SIZE];
	}
	
	public void writeToDisk() throws FileNotFoundException, IOException{
		this.file = new File(path + name);
		FileOutputStream fileStream = new FileOutputStream(file);
		fileStream.write(this.exe);
		fileStream.flush();
		fileStream.close();
		
	}
	
	public void readFromDisk(String file) throws IOException{
		FileInputStream in = new FileInputStream(file);
		in.read(this.exe);
		
		this.name = "test.bat";
		this.path = "";
		in.close();
	}
	
	public void execute() throws IOException{
		Runtime.getRuntime().exec("cmd /c start " + this.file.getAbsolutePath());
	}
}
