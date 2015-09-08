import java.awt.Color;

import javax.swing.Timer;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import javax.imageio.*;

import org.ietf.jgss.GSSContext;

import java.awt.event.KeyListener;
import java.awt.Robot;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GamePanelEC extends JPanel implements MouseInputListener, KeyListener, ActionListener {
	static public boolean running=true;
	
	static public boolean keyW=false; //move forward
	static public boolean keyA=false; //move left
	static public boolean keyS=false; //move back
	static public boolean keyD=false; //move right
	static public boolean keyR=false; //general reset
	static public boolean keyShift=false; //move up  -run
	static public boolean keySpace=false; //move up  -jump
	static public boolean keyCtrl=false; //move down
	static public boolean trackShip=false; //move down
	static public boolean showControls=false; //
	private double ExposureControl=0;

	private pos3d position= new pos3d(-4,0,0); //x y z
	private pos3d velocity= new pos3d(); //x y z
	private pos3d playerControl= new pos3d(); //x y z
	private pos3d gazeControl= new pos3d(); //x y z
	private pos3d gazeTarget= new pos3d(0,-Math.PI/6,0); //x y z
	private pos3d gaze= new pos3d(); //roll pitch yaw
	
	static public boolean keyUp=false;   //pitch up
	static public boolean keyDown=false; //pitch down
	static public boolean keyLeft=false; //yaw left
	static public boolean keyRight=false; //yaw right
	static public boolean keyCW=false; //roll CW
	static public boolean keyCCW=false;//roll CCW
	static public boolean mouselock=false;   //pitch up
	static boolean oversample=false;
	static boolean clearBG=false;
	static boolean LCam=true;
	static public double sealevel=0;
	double it=4;
	public int tickspassed=0;
	
	MouseEvent mouselook;

	double cameraDistort=2;
	
	static public ArrayList<Line3d> lines= new ArrayList<Line3d>();
	
	static public ArrayList<Quad3d> quads= new ArrayList<Quad3d>();
//	
//	int gameWidth=800;
//	int gameHeight=480;
	
	int gameWidth=1280;
	int gameHeight=800;

//	int gameWidth=480;
//	int gameHeight=320;

//	int gameWidth=320;
//	int gameHeight=240;
	
	double frametime=10;
	Timer clock= new Timer((int)(frametime), this);
	
	
	@Override
	public void actionPerformed(ActionEvent e) {//new game "tick"
		cameraDistort=1.2;
		cameraDistort=1.6;
		tickspassed++;
		if(running){
			playerControl=new pos3d();
			gazeControl= new pos3d();
			if(keyW){
				playerControl.x=100;
				}
			if(keyS){
				playerControl.x=-100;
				}
			if(keyA){
				playerControl.y=100;
				}
			if(keyD){
				playerControl.y=-100;
			}
			if(keyShift);
			
			if(keyCtrl);
	
			if(keyUp){
				gazeControl.y+=10;
				}
			if(keyDown){
				gazeControl.y-=10;
				}
			if(keySpace){
//				gazeControl.x+=10;
				playerControl.z=+40;//->currently break rendering
				}
			if(keyShift){
//				gazeControl.x-=10;
				playerControl.z=-40;//->currently break rendering
				}
			if(keyLeft){
				gazeControl.z-=10;
				}
			if(keyRight){
				gazeControl.z+=10;
				}
		
		}
		if(running){
			gazeTarget.add(gazeControl.mult(frametime/1000));
			gaze=gaze.mult(0.5);
			gaze.add(pos3d.mult(gazeTarget, 0.5));
			
			velocity.add(pos3d.rotate(playerControl.mult(frametime/10000),new pos3d(0,0,-gaze.z)));
			velocity=velocity.mult(0.94);
			position.add(pos3d.mult(velocity,1));
			if(position.magnitude()>1000000*.9){
				position=pos3d.mult(position, 1000000*.9/position.magnitude());
//				position.x*=-.8;
//				position.y*=-.8;
//				position.z*=-.8;
			}
//			if(position.x<-16){position.x=Math.abs(position.x);}
//			if(position.x>16){position.x=Math.abs(position.x);}
//			if(position.y<-16){position.y=Math.abs(position.y);}
//			if(position.y>-16){position.y=Math.abs(position.y);}
		}
		
		repaint();
	}
	
	public GamePanelEC(){
//		 frametime=1;
		clock.start();
		addKeyListener(this);
		setFocusable(true);
		
		addMouseListener(this);
		
		setPreferredSize(new Dimension(gameWidth, gameHeight));
//		generateQuadGrid(1024, 16, new pos3d(0,0,4), 64);
//		generateQuadGrid(1024, 16, new pos3d(0,0,-4), 64);
//		
//		generateQuadGrid(256, 8, new pos3d(0,0,4), 64);
//		generateQuadGrid(256, 8, new pos3d(0,0,-4), 64);
//		
//		generateQuadGrid(128, 4, new pos3d(0,0,4), 64);
//		generateQuadGrid(128, 4, new pos3d(0,0,-4), 64);
		
//		generateQuadGrid(64, 2, new pos3d(0,0,4), 16, new pos3d(.3,.5,0), .2);
//		generateQuadGrid(64, 2, new pos3d(0,0,-4), 16,new pos3d(.3,.5,0), .2);
//		
//		
//		generateQuadGrid(16, 1, new pos3d(0,0,4), 0);
//		generateQuadGrid(16, 1, new pos3d(0,0,-4), 0);
//		
//		generateQuadGrid(1, 1, new pos3d(0,0,1), 0);
//		
		
		generateGeometry();
		
	}
	public void generateQuadGrid(int size, double detail, pos3d center, int hole){
		generateQuadGrid(size,detail,center,hole,new pos3d(.5,.5,.5), .2);
	}
	
	public void generateQuadGrid(int size, double detail, pos3d center, int hole, pos3d colour, double roughness){
		double height= center.z;
		double shiftx= center.x;
		double shifty= center.y;
		
		for(double x=-size; x<=size;x+=detail){
			for(double y=-size; y<=size;y+=detail){
				if(Math.abs(x)>=hole||Math.abs(y)>=hole)
				quads.add(new Quad3d(  new pos3d(shiftx+x  ,shifty+ y  ,height), new pos3d(shiftx+x+detail,shifty+ y  ,height),
									   new pos3d(shiftx+x+detail,shifty+ y+detail,height), new pos3d(shiftx+x  ,shifty+ y+detail,height),
									   colour, roughness));
			}
		}
	}
	public void generateSlopedQuadGrid(int size, double detail, pos3d center, int hole, pos3d colour, double roughness){
		double height= center.z;
		double shiftx= center.x;
		double shifty= center.y;
		double slope=height-hole;
		for(double x=-size; x<=size;x+=detail){
			for(double y=-size; y<=size;y+=detail){
				slope=height-hole;
				if(Math.abs(x)>Math.abs(y)){slope+=Math.abs(x);}
				else{slope+=Math.abs(y);}
				
				if(Math.abs(x)>=hole||Math.abs(y)>=hole){
//				quads.add(new Quad3d(  new pos3d(shiftx+x  ,shifty+ y  ,slope), new pos3d(shiftx+x+detail,shifty+ y  ,slope),
//									   new pos3d(shiftx+x+detail,shifty+ y+detail,slope), new pos3d(shiftx+x  ,shifty+ y+detail,slope),
//									   colour, roughness));
				}
			}
		}
	}
//	public void generateQuadWall(int size, double detail, pos3d center, int hole){
//		double height= center.z;
//		for(double x=-size; x<=size;x+=detail){
//			
//		}
//		for(double y=-size; y<=size;y+=detail){
//			if(Math.abs(x)>=hole||Math.abs(y)>=hole)
//			quads.add(new Quad3d(  new pos3d(x  , y  ,height), new pos3d(x+detail, y  ,height),
//								   new pos3d(x+detail, y+detail,height), new pos3d(x  , y+detail,height)  ));
//		}
//	}
	
	public void generateGeometry(){
		quads.clear();
		double subdivisions=16;
		double sub=Math.PI/(subdivisions);
		for(double r=0;r<Math.PI*2;r+=sub){ //around
			
			double dif=Math.PI/(subdivisions*12) ; 
			for(double i=0; i<Math.PI+dif/2; i+=dif){//up/down
				dif*=1.;
//				quads.add(new Quad3d(   new pos3d(pos2d.rotX(new pos2d(1000,0), r),			pos2d.rotY(new pos2d(1000,0), r),		-100*(i-dif)), 
//						new pos3d(pos2d.rotX(new pos2d(1000,0), r),			pos2d.rotY(new pos2d(1000,0), r),		-100*	i	), 
//						new pos3d(pos2d.rotX(new pos2d(1000,0), r+sublen),	pos2d.rotY(new pos2d(1000,0), r+sublen),-100*	i	), 
//						new pos3d(pos2d.rotX(new pos2d(1000,0), r+sublen),	pos2d.rotY(new pos2d(1000,0), r+sublen),-100*(i-dif)), 
//						pos3d.mult(new pos3d(1,5,9),.15/i), 0.001));
				quads.add(new Quad3d(   pos3d.rotate(pos3d.rotate(new pos3d(1000000,0,0),new pos3d(r,		i-dif,0)),new pos3d(0,Math.PI/2,0)),
										pos3d.rotate(pos3d.rotate(new pos3d(1000000,0,0),new pos3d(r,		i,	  0)),new pos3d(0,Math.PI/2,0)),
										pos3d.rotate(pos3d.rotate(new pos3d(1000000,0,0),new pos3d(r+sub, i,	  0)),new pos3d(0,Math.PI/2,0)),
										pos3d.rotate(pos3d.rotate(new pos3d(1000000,0,0),new pos3d(r+sub, i-dif,0)),new pos3d(0,Math.PI/2,0)),
										pos3d.mult(new pos3d(5,6,9),new pos3d(1,1,1).mult(Math.sqrt(.06/(Math.abs(i-Math.PI/2)+.02))/4)), 0.000));
				
			}
			
			
		}
		
		
		
	
		if(true){
//			generateQuadGrid(6400, 400, new pos3d(0,0,400), 0,pos3d.mult(new pos3d(1,5,9),.2), .2);
//			generateQuadGrid(1600, 100, new pos3d(0,0,400), 0,pos3d.mult(new pos3d(1,5,9),.2), .2);
//			generateQuadGrid(6400, 400, new pos3d(0,0,-400), 0,new pos3d(.5,.5,.5), .2);
//			generateQuadGrid(3200, 200, new pos3d(0,0,-400), 0,new pos3d(.5,.5,.5), .2);
	//		
	//		
	//		generateQuadGrid(32, 2, new pos3d(0,0,4), 16,new pos3d(.5, .9,.2), .2);
	//		generateQuadGrid(512, 32, new pos3d(0,0,-32), 16,new pos3d(2, 2,2), .2);
	//		
	
			generateQuadGrid(8, .5, new pos3d(0,0,12), 0,new pos3d(4, 2,0), .2);
			for(double i =12; i>=4; i-=1){
				generateQuadGrid(8, 1, new pos3d(0,0,i), 5,new pos3d((i-4)/2+.5, (i-4)/4+.5, .5), .2);
				generateQuadGrid(1, 3, new pos3d(0,0,i), 0,new pos3d((i-4)/2+.5, (i-4)/4+.5, .5), .2);
			}
			
	//		generateSlopedQuadGrid(64, 4, new pos3d(0,0,4), 32,new pos3d(.5, .9,.2), .2);
			generateQuadGrid(32, 2, new pos3d(0,0,4), 5,new pos3d(.5, 1,.4), .1);
	//		generateQuadGrid(1280, 128, new pos3d(0,0,-320), 0,new pos3d(4, 4,4), .1);
			
			for(double i =4; i>1; i-=.25){
				generateQuadGrid(1, 1, new pos3d(-8,2,i), 0,new pos3d(.9,.9,.9), .2);
				generateQuadGrid(1, 1, new pos3d(-8,2,-i), 0,new pos3d(.9,.9,.9), .2);
			
				generateQuadGrid(1, 1, new pos3d(0,0,i), 0,new pos3d(.9,.9,.9), .2);
				generateQuadGrid(1, 1, new pos3d(0,0,-i), 0,new pos3d(.9,.9,.9), .2);
			}
	
			
			
			for(double i =.5; i>.1; i-=.1){
				generateQuadGrid(1, .5, new pos3d(0,0,i), 0,new pos3d(1,.8,.2), .2);
				generateQuadGrid(1, .5, new pos3d(0,0,-i), 0,new pos3d(1,.8,.2), .2);
			}
		}

		
		
	}
	public void paintComponent(Graphics g)
	{	
		g.setColor(Color.black);
//		g.setColor(new Color(0,0,0,32));

//		g.setColor(new Color(0,0,0,0));
		if(keyR){
			g.setColor(Color.black);
			keyR=false;
		}
		g.fillRect(0, 0, gameWidth, gameHeight);

		g.setColor(Color.white);
		g.setColor(new Color(255,255,255,127));
//		String print=tickspasesed+"Pos "+position.x+","+position.y+","+position.z+
//					 "In "+playerControl.x+","+playerControl.y+","+playerControl.z+"\n";
//		g.drawString(print, 16, gameHeight/2);
		//drawlines
		
		ArrayList<Quad3d> drawQuads=(ArrayList<Quad3d>) quads.clone();
		

//		cameraDistort=(Math.sin((double)tickspassed/50)+1)*2;
//		cameraDistort=0.01; //glitchyier!
//		cameraDistort=.5; //wider still, moderate clipping!
//		cameraDistort=1; //ultra-wide, no clipping
//		cameraDistort=1.5; //wide
//		cameraDistort=2; //rectlinear
//		cameraDistort=4; //spyglass -glitches with big/near polygons (skybox)
		
		for(Quad3d l: drawQuads){
			
//			l.transform(position.negate());
			
			//gaze=new pos3d(((double)1)/1000,0,0);
			pos3d a=l.a;
			pos3d b=l.b;
			pos3d c=l.c;
			pos3d d=l.d;
			pos3d color=l.color;
			
//			position.add(new pos3d(0,0,tickspasesed));
//			System.out.print(""+a.x+"+"+position.negate().x+"=");
			a=pos3d.add(position,l.a);
			b=pos3d.add(position,l.b);
			c=pos3d.add(position,l.c);
			d=pos3d.add(position,l.d);
//			System.out.println(""+a.x+";");
			
			a=pos3d.rotate(a,new pos3d(0,0,gaze.z));
			b=pos3d.rotate(b,new pos3d(0,0,gaze.z));
			c=pos3d.rotate(c,new pos3d(0,0,gaze.z));
			d=pos3d.rotate(d,new pos3d(0,0,gaze.z));
			
			a=pos3d.rotate(a,new pos3d(gaze.x,gaze.y,0));
			b=pos3d.rotate(b,new pos3d(gaze.x,gaze.y,0));
			c=pos3d.rotate(c,new pos3d(gaze.x,gaze.y,0));
			d=pos3d.rotate(d,new pos3d(gaze.x,gaze.y,0));
			
			a=ProjectionMath.convert3dtoStretchedPolar(a, cameraDistort);
			b=ProjectionMath.convert3dtoStretchedPolar(b, cameraDistort);
			c=ProjectionMath.convert3dtoStretchedPolar(c, cameraDistort);
			d=ProjectionMath.convert3dtoStretchedPolar(d, cameraDistort);

			if(a!=null&&b!=null&&c!=null&&d!=null){
				a=a.mult(gameHeight/Math.PI);
				b=b.mult(gameHeight/Math.PI);
				c=c.mult(gameHeight/Math.PI);
				d=d.mult(gameHeight/Math.PI);
				
				double dist=pos3d.add(a, position).magnitude()/* /Math.abs(l.a.z)*/;
				double refl=pos3d.add(a, position).latMagnitude()/* /Math.abs(l.a.z)*/;
				int limit=gameHeight*10000/480;
				double light=200000/dist+ 1000/refl+32;
				light=light/2+200;
				light=100;
//				ExposureControl=(Math.sin((double)tickspassed/8))*1-1;
				light= Math.pow(2,ExposureControl)*200;
				if(a.latMagnitude()<limit
						&&b.latMagnitude()<limit
						&&c.latMagnitude()<limit
						&&d.latMagnitude()<limit){
					
					
					a=pos3d.add(a, new pos3d(gameWidth/2, gameHeight/2, 0));
					b=pos3d.add(b, new pos3d(gameWidth/2, gameHeight/2, 0));
					c=pos3d.add(c, new pos3d(gameWidth/2, gameHeight/2, 0));
					d=pos3d.add(d, new pos3d(gameWidth/2, gameHeight/2, 0));
	
					int[] xpos={(int) a.x, (int) b.x, (int) c.x, (int) d.x};
					int[] ypos={(int) a.y, (int) b.y, (int) c.y, (int) d.y};
					
	//				System.out.println(color.x+","+color.y+","+color.z);
	
					g.setColor(color.toColor(light));
	//				g.setColor(color.toColor(256));
	//				g.setColor(Color.white);
					
	
					//g.setColor(g.getColor().brighter());
					g.fillPolygon(xpos, ypos, 4);
					g.setColor(g.getColor().darker());
	//				g.setColor(new Color(0,0,0,64));
	//				g.drawPolygon(xpos, ypos, 4);
					g.setColor(Color.white);
	//				g.drawLine((int)a.x, (int)a.y,
	//						   (int) b.x,(int) b.y);
				}
			}
			
		}
		g.setColor(Color.white);
		g.drawString("\"Eye in the Sky\" prototype", 8, 16);
		g.drawString("Made for LD 32 by Péter Finta", 8, 16*2);
		if(showControls){
			g.drawString("Controls: ('C' to hide)", 8, 16*3);
			g.drawString("WASD - lateral movement", 8, 16*4);
			g.drawString("Space/Shift - vertical movement (breaks rendering)", 8, 16*5);
			g.drawString("J/L - 'yaw' turning control", 8, 16*6);
			g.drawString("I/K - pitch camera control", 8, 16*7);}
		else//Math.sin((double)tickspassed/100)+1.5
			g.drawString("Press 'C' to show controls", 8, 16*3);
		
		g.setColor(Color.white);
//		g.drawString("Distort Value: "+cameraDistort,gameWidth/2, gameHeight/2);
		g.drawString("Exp adjust: "+ExposureControl,8,gameHeight-16);
		
			
		
	}
	public void mouseClicked(MouseEvent e)
	{	
		repaint();
	}

	@Override
	
	
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
	
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_W)
		{
		//	System.out.println("WWWWW");
			keyW=true;
		}
		if(e.getKeyCode()==KeyEvent.VK_A)
		{
			keyA=true;
		}
		if(e.getKeyCode()==KeyEvent.VK_S)
		{
			keyS=true;
		}
		if(e.getKeyCode()==KeyEvent.VK_D)
		{
			keyD=true;
		}
		if(e.getKeyCode()==KeyEvent.VK_C)
		{
			showControls=!showControls;
		}
		if(e.getKeyCode()==KeyEvent.VK_SHIFT)
		{
			keyShift=true;
		}if(e.getKeyCode()==KeyEvent.VK_SPACE)
		{
			keySpace=true;
		}
		if(e.getKeyCode()==KeyEvent.VK_CONTROL)
		{
			keyCtrl=true;
		}
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
		{
			mouselock=false;
			running=!running;
			if(running)mouselock=true;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_R)
		{
			keyR=true;
			generateGeometry();
		}
		
		if(e.getKeyCode()==KeyEvent.VK_I)
		{
			keyUp=true;
		}
		if(e.getKeyCode()==KeyEvent.VK_K)
		{
			keyDown=true;
		}
		if(e.getKeyCode()==KeyEvent.VK_T)
		{
			trackShip=!trackShip;
		}
		if(e.getKeyCode()==KeyEvent.VK_J)
		{
			keyLeft=true;
		}
		if(e.getKeyCode()==KeyEvent.VK_L)
		{
			keyRight=true;
		}
		if(e.getKeyCode()==KeyEvent.VK_E)
		{
			keyCW=true;
		}
		if(e.getKeyCode()==KeyEvent.VK_Q)
		{
			keyCCW=true;
		}
		if(e.getKeyCode()==KeyEvent.VK_O)
		{
			gaze.x%=Math.PI*2;
			gaze.y%=Math.PI*2;
			gaze.z%=Math.PI*2;
			gazeTarget=new pos3d(0,-Math.PI/2,0);
		}
		if(e.getKeyCode()==KeyEvent.VK_0)
		{
		
		}
		if(e.getKeyCode()==KeyEvent.VK_9)
		{
			
		}
		if(e.getKeyCode()==KeyEvent.VK_7)
		{
			
		}
		if(e.getKeyCode()==KeyEvent.VK_8)
		{
			
		}
		
		if(e.getKeyCode()==KeyEvent.VK_5)
		{
			
		}
		if(e.getKeyCode()==KeyEvent.VK_6)
		{
			
		}
		if(e.getKeyCode()==KeyEvent.VK_COMMA)
		{
			
		}
		if(e.getKeyCode()==KeyEvent.VK_PERIOD)
		{
			
		}
		if(e.getKeyCode()==KeyEvent.VK_MINUS)
		{
			
		}
		if(e.getKeyCode()==KeyEvent.VK_EQUALS)
		{
			
		}
		
		
		if(e.getKeyCode()==KeyEvent.VK_1)
		{			
			
		}
		
		
		
		if(e.getKeyCode()==KeyEvent.VK_2){
			
		}
		if(e.getKeyCode()==KeyEvent.VK_3)
		{
		
		}
		if(e.getKeyCode()==KeyEvent.VK_4)
		{
		
		}	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_W)
		{
			keyW=false;
		}
		if(e.getKeyCode()==KeyEvent.VK_A)
		{
			keyA=false;
}
		if(e.getKeyCode()==KeyEvent.VK_S)
		{
			keyS=false;
		}
		if(e.getKeyCode()==KeyEvent.VK_D)
		{
			keyD=false;
		}
		if(e.getKeyCode()==KeyEvent.VK_SHIFT)
		{
			keyShift=false;
		}if(e.getKeyCode()==KeyEvent.VK_SPACE)
		{
			keySpace=false;
		}
		if(e.getKeyCode()==KeyEvent.VK_CONTROL)
		{
			keyCtrl=false;
		}
		
		
		if(e.getKeyCode()==KeyEvent.VK_I)
		{
			keyUp=false;
		}
		if(e.getKeyCode()==KeyEvent.VK_K)
		{
			keyDown=false;
		}
		if(e.getKeyCode()==KeyEvent.VK_J)
		{
			keyLeft=false;
		}
		if(e.getKeyCode()==KeyEvent.VK_L)
		{
			keyRight=false;
		}
		if(e.getKeyCode()==KeyEvent.VK_E)
		{
			keyCW=false;
		}
		if(e.getKeyCode()==KeyEvent.VK_Q)
		{
			keyCCW=false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		}

	public void drawQuad(pos3d e1, pos3d e2, pos3d e3, pos3d e4, Graphics g, Color c, boolean l){
		g.setColor(c);
		e1.x-=position.x;
		e1.y-=position.y;
		e1.z+=position.z;
		
		e2.x-=position.x;
		e2.y-=position.y;
		e2.z+=position.z;
		
		e3.x-=position.x;
		e3.y-=position.y;
		e3.z+=position.z;
		
		e4.x-=position.x;
		e4.y-=position.y;
		e4.z+=position.z;

		//insert code
		
		//System.out.println("BEEP BEEP BEEP"+e1.x+","+e1.y+","+e1.z);
		if (e1.z>0.125&&e2.z>0.125&&e3.z>0.125&&e4.z>0.125)
		{
		
			int[]xp={0,0,0,0}; //insert code
			int[]yp={0,0,0,0}; 
				
				
			g.drawPolygon(xp, yp, 4);//xp, yp, np
				
		}
	}
	
	public void drawTerrainTile(Graphics g, double x, double y, double it){
		
	}
	
}