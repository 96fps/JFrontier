
public class Quad3d {
	pos3d a;
	pos3d b;
	pos3d c;
	pos3d d;
	pos3d color;
	
	public Quad3d(){
		a=new pos3d();
		b=new pos3d();
		c=new pos3d();
		d=new pos3d();
		color= new pos3d(Math.random(),Math.random(),Math.random());
	}
	public Quad3d(pos3d a, pos3d b, pos3d c, pos3d d){
		this.a=a;
		this.b=b;
		this.c=c;
		this.d=d;
		double col=Math.random()-Math.random();
		double bri=Math.sqrt(Math.random()/2)+.5;
		color= pos3d.mult(new pos3d(.5+col/4, .75-col/4, .2), bri);
	}
	public Quad3d(pos3d a, pos3d b, pos3d c, pos3d d, pos3d colour){
		this.a=a;
		this.b=b;
		this.c=c;
		this.d=d;
		color=colour;
	}
	public Quad3d(pos3d a, pos3d b, pos3d c, pos3d d, pos3d colour, double roughness){
		this.a=a;
		this.b=b;
		this.c=c;
		this.d=d;
		double bri=Math.sqrt(Math.random())*roughness+0.5*(1-roughness);
		color= pos3d.mult(colour, bri);
	}
	public Quad3d transform(pos3d t){
		a.add(t);
		b.add(t);
		c.add(t);
		d.add(t);
		return new Quad3d(a,b,c,d,color);
	}
	public Quad3d rotate(pos3d t){
		a=a.rotate(a,t);
		b=b.rotate(b,t);
		c=c.rotate(c,t);
		d=d.rotate(d,t);
		return new Quad3d(a,b,c,d,color);
	}
	public Quad3d multiply(double n){
		a=a.mult(n);
		b=b.mult(n);
		c=c.mult(n);
		d=d.mult(n);
		return new Quad3d(a,b,c,d,color);
	}
	public Quad3d multiply(pos3d n){
		a=a.mult(n);
		b=b.mult(n);
		c=c.mult(n);
		d=d.mult(n);
		return new Quad3d(a,b,c,d,color);
	}
	
	public String toString(){
		return new String("A.x="+a.x+" y="+a.y+" z="+a.z);
	}	
}
