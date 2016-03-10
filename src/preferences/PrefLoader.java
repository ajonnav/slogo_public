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
	       //return e;
	    }catch(IOException i)
	    {
	       i.printStackTrace();
	       return null;
	    }catch(ClassNotFoundException c)
	    {
	       System.out.println("Employee class not found");
	       c.printStackTrace();
	       return null;
	    }
//	    System.out.println(e.backColorIndex);
//	    System.out.println(e.language);
//	    System.out.println(e.turtleNumber);
//	    
//	    for (Double n: e.images.keySet()){
//	    	System.out.println(n + " " + e.images.get(n));
//	    }
//	    
//	    for (Double n: e.colorMap.keySet()){
//	    	System.out.println(n + " " + e.colorMap.get(n));
//	    }
	    return e;
	}
}
