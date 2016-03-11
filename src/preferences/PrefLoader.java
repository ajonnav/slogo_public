package preferences;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class PrefLoader {
	private saveState e;
	
	public PrefLoader(){
	}
	
	public saveState load(File loadingFile){
		e = null;
	    try
	    {
	       FileInputStream fileIn = new FileInputStream(loadingFile);
	       ObjectInputStream in = new ObjectInputStream(fileIn);
	       e = (saveState) in.readObject();
	       in.close();
	       fileIn.close();
	    }catch(IOException i)
	    {
	       i.printStackTrace();
	       return null;
	    }catch(ClassNotFoundException c)
	    {
	       System.out.println("Parameter not found");
	       c.printStackTrace();
	       return null;
	    }
	    return e;
	}
}
