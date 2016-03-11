package display;

public abstract class WSpace extends Screen{
	private String myLang;
	
	public WSpace(String lang) {
		myLang = lang;
	}
	
	public abstract void setLang();
	
	protected String getMyLang(){
		return myLang;
	}
	
	protected void setMyLang(String s){
		myLang = s;
		changeLang(myLang);
	}

	public abstract void changeLang(String s);
}
