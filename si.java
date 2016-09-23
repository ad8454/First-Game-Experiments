import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class si extends JPanel
{
	static int boundx=660;
	static int boundy=480;
	int bgx=0;
	int bgy=-468;
	int sx=190;
	int sy=330;
	int kspd=15;                                           //speed of ship
	int kleft=0;
	int kright=0;
	int kup=0;
	int kdown=0;
	int kfire=0;
	int delay=50;
	int b1cnt=-1;
	int shiphit=0;
	int points=0;
	int countdn=60;
	int b1lx[]=new int[50];
	int b1ly[]=new int[50];
	int b1rx[]=new int[50];
	int b1ry[]=new int[50];
	int b1lhit[]=new int[50];
	int b1rhit[]=new int[50];
	int astctr=0;
	int astno=-1;
	int astx[]=new int[50];
	int asty[]=new int[50];
	int asthit[]=new int[50];
	int astexpl[]=new int[50];												// explosion 
	Image bgimg;
	Image ship;
	Image b1;
	Image astimg;
	Image bar;
	Image goback;
	ImageIcon e1 = new ImageIcon(this.getClass().getResource("sprites/expl1.png"));
	ImageIcon e2 = new ImageIcon(this.getClass().getResource("sprites/expl2.png"));
	ImageIcon e3 = new ImageIcon(this.getClass().getResource("sprites/expl3.png"));
	Timer timer;
	RenderingHints rh;
	
	public static void main(String args[])
	{
		JFrame jf = new JFrame("Space Voyage");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.pack();
		si jp=new si();
		jf.add(jp);
		jf.setBounds(50,50,boundx,boundy);
		jf.setVisible(true);
		jp.grabFocus();    
	}
		
	public si()
	{
		this.setIgnoreRepaint( true );
		
		rh=new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		
		setDoubleBuffered(true);
		
		ImageIcon iid = new ImageIcon(this.getClass().getResource("sprites/starbig.png"));
        bgimg=iid.getImage();
		
		ImageIcon iid2 = new ImageIcon(this.getClass().getResource("sprites/sship.png"));
        ship=iid2.getImage();
		
		ImageIcon iid3 = new ImageIcon(this.getClass().getResource("sprites/b1.png"));
        b1=iid3.getImage();
		
		ImageIcon iid4 = new ImageIcon(this.getClass().getResource("sprites/a1t.png"));
        astimg=iid4.getImage();
		
		ImageIcon iid5 = new ImageIcon(this.getClass().getResource("sprites/posv.png"));
		bar=iid5.getImage();
		
		ImageIcon iid6 = new ImageIcon(this.getClass().getResource("sprites/gbtm.png"));
		goback=iid6.getImage();
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "left");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "right");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "down");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "up");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("Z"), "z");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released Z"), "rel z");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released LEFT"), "rel left");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released RIGHT"), "rel right");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released UP"), "rel up");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released DOWN"), "rel down");


        getActionMap().put("left", new AbstractAction() {
        public void actionPerformed(ActionEvent e)
		{
             	kleft=1;			
        }
       	});

		getActionMap().put("right", new AbstractAction() {
        public void actionPerformed(ActionEvent e)
		{
              	kright=1;	
        }
       	});

		getActionMap().put("down", new AbstractAction() {
        public void actionPerformed(ActionEvent e)
		{
              	kdown=1;
        }
       	});

		getActionMap().put("up", new AbstractAction() {
        public void actionPerformed(ActionEvent e)
		{
               	kup=1;			
        }
       	});

		getActionMap().put("z", new AbstractAction() {
        public void actionPerformed(ActionEvent e)
		{
              	kfire=1;  
		}
       	});

		getActionMap().put("rel z", new AbstractAction() {
        public void actionPerformed(ActionEvent e)
		{
              	kfire=0;
        }
       	});

		
		getActionMap().put("rel left", new AbstractAction() {
        public void actionPerformed(ActionEvent e)
		{
               	kleft=0;
        }
       	});

		getActionMap().put("rel right", new AbstractAction() {
        public void actionPerformed(ActionEvent e)
		{
               	kright=0;
        }
       	});

		getActionMap().put("rel up", new AbstractAction() {
        public void actionPerformed(ActionEvent e)
		{
               	kup=0;
        }
       	});

		getActionMap().put("rel down", new AbstractAction() {
        public void actionPerformed(ActionEvent e)
		{
              	kdown=0;
        }
       	});
		
		
		timer = new Timer(delay, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{ 
				repaint();
				
				chk();				
				astctr++;
				if(astctr%20==0)
				{
					astgen();	
				}
				if(astctr%15==0)
				{
					countdn--;	
				}
				chk();
			}
		});
		timer.start();
		
	}
	
	public void astgen()
	{
		for(int i=astno;i>=0;i--)
		{
			astx[i+1]=astx[i];
			asty[i+1]=asty[i];
			asthit[i+1]=asthit[i];
			astexpl[i+1]=astexpl[i];
		}
		astno++;
		astx[0]=(int)(Math.random()*(boundx-60-110)); 
		asty[0]=-20;
		asthit[0]=0;
		astexpl[0]=0;
	}
	
	public void chk()
	{
		if(countdn==-1)
		{
			countdn=-10;
			JOptionPane.showMessageDialog(this,"Your Score "+points+" !");
			this.setSize(646,573);
			this.setVisible(false);
			this.setEnabled(false);
			new svm(1);
		}
	}
	
	public void paintBg(Graphics g)
	{
		g.drawImage(bgimg,bgx,bgy,this);
		bgy+=5;
		if(bgy==2)
			bgy=-468;           
	}
	
	public void paintShip(Graphics g)
	{
		if(kleft==1)
		{
			if(sx>0)
				sx-=kspd;
		}

		if(kright==1)
		{
			if(sx<470)
				sx+=kspd;
		}

		if(kup==1)
		{
			if(sy>0)
				sy-=kspd;
		}

		if(kdown==1)
		{
			if(sy<330)
				sy+=kspd;
		}

		g.drawImage(ship,sx,sy,this);
	}
	
	public void paintB1(Graphics g)
	{
		if(kfire==1)
		{
			for(int i=b1cnt;i>=0;i--)
			{
				b1lx[i+1]=b1lx[i];
				b1ly[i+1]=b1ly[i];
				b1lhit[i+1]=b1lhit[i];
				b1rx[i+1]=b1rx[i];
				b1ry[i+1]=b1ry[i];
				b1rhit[i+1]=b1rhit[i];
			}
			b1cnt++;
			b1lx[0]=sx+5;
			b1ly[0]=sy+30;
			b1lhit[0]=0;
			b1rx[0]=sx+57;
			b1ry[0]=sy+30;
			b1rhit[0]=0;
		}
				
		if(b1cnt>-1)
		{
			for(int i=b1cnt;i>=0;i--)
			{
				if(b1ly[i]>-20)
				{
					if(b1lhit[i]==0)
						g.drawImage(b1,b1lx[i],b1ly[i],this);
					if(b1rhit[i]==0)	
						g.drawImage(b1,b1rx[i],b1ry[i],this);
					
					for(int j=astno;j>=0;j--)
					{
							if(b1lhit[i]==0 && asthit[j]<=20 && b1lx[i]>=astx[j]-20 && b1lx[i]<=astx[j]+55 && b1ly[i]>=asty[j] && b1ly[i]<=asty[j]+47)
							{	
								b1lhit[i]=1;
								asthit[j]++;
							}
							if(b1rhit[i]==0 && asthit[j]<=20 && b1rx[i]>=astx[j]-20 && b1rx[i]<=astx[j]+55 && b1ry[i]>=asty[j] && b1ry[i]<=asty[j]+47)
							{	
								b1rhit[i]=1;
								asthit[j]++;
							}
					}
				
					b1ly[i]-=20;
					b1ry[i]-=20;
				}
				else
					b1cnt--;	
			}
		}
	}
	
	public void paintAst(Graphics g)
	{
		if(astno>-1)
		{
			for(int i=astno;i>=0;i--)
			{
				if(asty[i]<670)
				{
					if(asthit[i]<=19)
					{
						g.drawImage(astimg,astx[i],asty[i],this);
						
						//if(astx[i]-55>=sx && astx[i]<=sx+80 && asty[i]+50>=sy && asty[i]<=sy+100)
							//asthit[i]=20;
						
						asty[i]+=6;
					}
					if(asthit[i]>=20 && astexpl[i]<3)
					{
						g.drawImage(e1.getImage(),astx[i]-28,asty[i]-28,this);
						astexpl[i]++;
						points+=5;
					}
					else if(astexpl[i]>2 && astexpl[i]<5)
					{
						g.drawImage(e2.getImage(),astx[i]-42,asty[i]-38,this);
						astexpl[i]++;
					}
					else if(astexpl[i]>4 && astexpl[i]<7)
					{
						g.drawImage(e3.getImage(),astx[i]-20,asty[i]-20,this);	
						astexpl[i]++;
					}	
				}
				else
					astno--;  
			}
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHints(rh);
		paintBg(g);		
		paintB1(g);
		paintAst(g);
		paintShip(g);
		g.drawImage(bar,555,0,this);
		g.setColor(Color.black);
		g.setFont(new Font("OCR A Extended",Font.PLAIN,36));
		g.drawString(Integer.toString(points),558,90);		
		g.drawString(Integer.toString(countdn),570,200);
		g.drawImage(goback,565,380,this);
	}
}