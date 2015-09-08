import java.awt.Color;
import java.util.ArrayList;

public class poly3d {
	public boolean closed;
	public ArrayList<pos3d> pos= new ArrayList<pos3d>();
	int temp=(int)(223+(Math.random()*32));
	public Color color=new Color(temp,temp,temp,128);
	
	public poly3d(ArrayList<pos3d> list){
		pos=list;
	}
	public poly3d(ArrayList<pos3d> list, Color c){
		color=c;
		pos=list;
	}
	public poly3d(){
	}
	
}
