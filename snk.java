import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class snk extends JPanel
{
	int ht=20;
	int l=5;
	int scr=0;								// score
	int posx[]=new int[150];
	int posy[]=new int[150];
	char dir[]=new char[150];				// for direction of body
	int autox=ht;
	int autoy=0;
	int delay=80;
	int kleft=0;
	int kright=0;
	int kup=0;
	int kdown=0;
	int bff=-1;								//bonus food flag
	int bx=-100;
	int by=-100;
	int bongen=0;							// to call genb just once
	int bfp1=0;								// bonus power
	int bfp2=0;
	int bfp3=0;
	int btmr=0;								//bonus timer
	int ptmr1=0;							// power timer
	int ptmr2=0;
	int ptmr3=0;
	static int boundx=660;					//has to be multiple of ht
	static int boundy=480;					// play area ord
	static int bgy=583;						// tot area ord
	Image food;
	Image bg;
	Image score;
	Image head;
	Image body;
	Image tail;
	Image bfi;								// bonus food img
	Image bfib;
	ImageIcon hr = new ImageIcon(this.getClass().getResource("sprites/headr.png"));
	ImageIcon hl = new ImageIcon(this.getClass().getResource("sprites/headl.png"));
	ImageIcon hu = new ImageIcon(this.getClass().getResource("sprites/headu.png"));
	ImageIcon hd = new ImageIcon(this.getClass().getResource("sprites/headd.png"));
	ImageIcon bdx = new ImageIcon(this.getClass().getResource("sprites/bodyx.png"));
	ImageIcon bdy = new ImageIcon(this.getClass().getResource("sprites/bodyy.png"));
	ImageIcon tr = new ImageIcon(this.getClass().getResource("sprites/tailr.png"));
	ImageIcon tl = new ImageIcon(this.getClass().getResource("sprites/taill.png"));
	ImageIcon tu = new ImageIcon(this.getClass().getResource("sprites/tailu.png"));
	ImageIcon td = new ImageIcon(this.getClass().getResource("sprites/taild.png"));
	ImageIcon q = new ImageIcon(this.getClass().getResource("sprites/q.png"));
	ImageIcon w = new ImageIcon(this.getClass().getResource("sprites/w.png"));
	ImageIcon s = new ImageIcon(this.getClass().getResource("sprites/s.png"));
	ImageIcon a = new ImageIcon(this.getClass().getResource("sprites/a.png"));
	ImageIcon bfic1 = new ImageIcon(this.getClass().getResource("sprites/bf1.png"));
	ImageIcon bfic2 = new ImageIcon(this.getClass().getResource("sprites/bf2.png"));
	ImageIcon bfic3 = new ImageIcon(this.getClass().getResource("sprites/bf3.png"));
	ImageIcon bfic1b = new ImageIcon(this.getClass().getResource("sprites/bf1b.png"));
	ImageIcon bfic2b = new ImageIcon(this.getClass().getResource("sprites/bf2b.png"));
	ImageIcon bfic3b = new ImageIcon(this.getClass().getResource("sprites/bf3b.png"));
	int f=0;                     //for food
	int fx;
	int fy;
	GradientPaint gp;
	RenderingHints rh;           // quality quality quality
	Timer timer;



	public static void main(String[] args)
	{
		JFrame jf = new JFrame("Snake");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setResizable(false);									//look into: results into added width & height for window
		jf.setBounds(50,50,boundx-14,bgy-10);
		
		snk so=new snk();
		jf.add(so);

		jf.setVisible(true);
	//	so.grabFocus();         // Imp!!
	
	}
	public snk()
	{
		posx[1]=ht;
		posy[1]=0;
		dir[1]='r';
		
		rh=new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		
		setDoubleBuffered(true);
				
		ImageIcon iid = new ImageIcon(this.getClass().getResource("sprites/apple1.png"));
        food = iid.getImage();
		
		ImageIcon iid2 = new ImageIcon(this.getClass().getResource("sprites/bgb2e1.png"));
        bg = iid2.getImage();
		
		ImageIcon iid3 = new ImageIcon(this.getClass().getResource("sprites/sbb.jpg"));
        score = iid3.getImage();
		
		head = hr.getImage();
			
		gen();
				
		timer = new Timer(delay, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{ 
				if(btmr>50)
				{		
					btmr=0;
					bff=0;
				}
				if(bff>0)
					btmr++; 
				if(bfp1>0)
				{
					if(bfp1==1)
						ptmr1++;
					else
					{
						ptmr1=0;
						bfp1=1;
					}
				}
				if(ptmr1==100)
				{
					bfp1=0;
					ptmr1=0;
					timer.setDelay(delay);
				}
				if(bfp2>0)
				{
					if(bfp2==1)
						ptmr2++;
					else
					{
						ptmr2=0;
						bfp2=1;
					}
				}
				if(ptmr2==200)
				{
					bfp2=0;
					ptmr2=0;
				}
				if(bfp3>0)
				{
					if(bfp3==1)
						ptmr3++;
					else
					{
						ptmr3=0;
						bfp3=1;
					}
				}
				if(ptmr3==200)
				{
					bfp3=0;
					ptmr3=0;
				}
				movesnk();
			}
		});
		timer.start();
				
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "left");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "right");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "down");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "up");
		
		getActionMap().put("left", new AbstractAction() {
        public void actionPerformed(ActionEvent e)
		{
             	if(autox==0)
				{	
					kleft=1;
					kdown=0;
					kup=0;
					head=hl.getImage();
					if(autoy==ht)
						dir[1]='s';
					else
						dir[1]='w';
				}
        }
       	});

		getActionMap().put("right", new AbstractAction() {
        public void actionPerformed(ActionEvent e)
		{
              	if(autox==0)
				{	
					kright=1;
					kdown=0;
					kup=0;
					head=hr.getImage();
					if(autoy==ht)
						dir[1]='a';
					else
						dir[1]='q';
				}	
        }
       	});

		getActionMap().put("down", new AbstractAction() {
        public void actionPerformed(ActionEvent e)
		{
              	if(autoy==0)
				{	
					kdown=1;
					kleft=0;
					kright=0;
					head=hd.getImage();
					if(autox==ht)
						dir[1]='y';
					else
						dir[1]='t';
				}
        }
       	});

		getActionMap().put("up", new AbstractAction() {
        public void actionPerformed(ActionEvent e)
		{
               	if(autoy==0)
				{	
					kup=1;
					kleft=0;
					kright=0;
					head=hu.getImage();
					if(autox==ht)
						dir[1]='h';
					else
						dir[1]='g';
				}			
        }
       	});
	}
	
	public void movesnk()
	{	
		for(int i=l;i>1;i--)         // pos[1] is the head
		{
			posx[i]=posx[i-1];
			posy[i]=posy[i-1];
			dir[i]=dir[i-1];
		}

		if(kleft==1)
		{
			autox=-ht;
			autoy=0;
			dir[1]='l';
		}

		else if(kright==1)
		{
			autox=ht;
			autoy=0;
			dir[1]='r';
		}

		else if(kup==1)
		{
			autox=0;
			autoy=-ht;
			dir[1]='u';
		}

		else if(kdown==1)
		{
			autox=0;
			autoy=ht;
			dir[1]='d';
		}
		
		posx[1]+=autox;
		posy[1]+=autoy;
		
		repaint();
		chk();
	}
	
	public void chk()
	{
		if(posx[1]<0 || posx[1]==(boundx-ht) || posy[1]<0 || posy[1]==(boundy))     // why??!
		{	
			if(bfp2==1)
			{
				if(posx[1]<0)
					posx[1]=(boundx-ht);
				else if(posx[1]==(boundx-ht))
					posx[1]=0;
				else if(posy[1]<0)
					posy[1]=(boundy-ht);
				else if(posy[1]==(boundy))
					posy[1]=0;
			}
			else
			{				
				this.setVisible(false);
				this.setEnabled(false);
				new sm(1);
			}	
		}
		
		for(int i=2;i<=l;i++)
		{
			if(posx[1]==posx[i] && posy[1]==posy[i])
			{
				if(bfp3==1)
					l=i-1;													
				else
				{				
					this.setVisible(false);
					this.setEnabled(false);
					new sm(1);
				}	
					
				break;
			}
		}
		
		if(posx[1]==fx && posy[1]==fy)
		{
			l++;
			posx[l]=posx[l-1];                         // to prevent flicker. Grows at next delay
			posy[l]=posy[l-1];
			gen();
			scr+=10;
			
			if(scr>0 && scr%50==0)
			{
				bff=1+((int)(Math.random()*3));
				genb();
			}
			
		}
		
		if(bff>0)
		{
			if(posx[1]==bx && posy[1]==by)
			{
				if(bff==1)
				{
					bfp1++;
					timer.setDelay(delay*2);
				}
				else if(bff==2)
				{
					bfp2++;
					timer.setDelay(delay);
				}
				else if(bff==3)
				{
					bfp3++;
					timer.setDelay(delay);
				}
				bff=0;
				btmr=0;
			}
				
		}
	}
	
	public void genb()
	{
		bx=(int)(Math.random()*(boundx-ht));                      
		by=(int)(Math.random()*(boundy));           // WHY ??!!??
		bx=bx/ht;
		by=by/ht;
		bx=bx*ht;
		by=by*ht;
		for(int i=1;i<=l;i++)
		{
			if((bx==posx[i] && by==posy[i]) || (bx==fx && by==fy))
			{
				bx=(int)(Math.random()*boundx-ht);
				by=(int)(Math.random()*boundy);
				bx=bx/ht;
				by=by/ht;
				bx=bx*ht;
				by=by*ht;
				i=1;
			}
		}
		
	}
	
	public void gen()
	{
		fx=(int)(Math.random()*(boundx-ht));                      
		fy=(int)(Math.random()*(boundy));           // WHY ??!!??
		fx=fx/ht;
		fy=fy/ht;
		fx=fx*ht;
		fy=fy*ht;
		for(int i=1;i<=l;i++)
		{
			if((fx==posx[i] && fy==posy[i]) || (fx==bx && fy==by))
			{
				fx=(int)(Math.random()*boundx-ht);
				fy=(int)(Math.random()*boundy);
				fx=fx/ht;
				fy=fy/ht;
				fx=fx*ht;
				fy=fy*ht;
				i=1;
			}
		}	
	}
	
	public void paintsnk(Graphics2D g)
	{
		g.drawImage(head,posx[1],posy[1],this);
		
		for(int i=2;i<l;i++)
		{
			if(dir[i]=='u' || dir[i]=='d')
				body=bdy.getImage();
			else if(dir[i]=='l' || dir[i]=='r')
				body=bdx.getImage();
			else if(dir[i]=='q' || dir[i]=='t')
				body=q.getImage();
			else if(dir[i]=='w' || dir[i]=='y')
				body=w.getImage();
			else if(dir[i]=='s' || dir[i]=='h')
				body=s.getImage();
			else if(dir[i]=='a' || dir[i]=='g')
				body=a.getImage();
				
			g.drawImage(body,posx[i],posy[i],this);
		}
		
		if(dir[l]=='u' || dir[l]=='g' || dir[l]=='h')
			tail=tu.getImage();
		else if(dir[l]=='r' || dir[l]=='a' || dir[l]=='q')
			tail=tr.getImage();
		else if(dir[l]=='d' || dir[l]=='t' || dir[l]=='y')
			tail=td.getImage();
		else if(dir[l]=='l' || dir[l]=='w' || dir[l]=='s')
			tail=tl.getImage();
			
		g.drawImage(tail,posx[l],posy[l],this);
	}		
		
	public void paintf(Graphics g)
	{
		g.drawImage(food,fx,fy,this);
	}	
	
	public void paintbf(Graphics g)
	{
		if(bff==1)
			bfi=bfic1.getImage();
		else if(bff==2)
			bfi=bfic2.getImage();
		else if(bff==3)
			bfi=bfic3.getImage();
			
		g.drawImage(bfi,bx,by,this);
	}

	public void paintbfp(Graphics g)
	{
		if(bfp1>0)
		{
			bfib=bfic1b.getImage();
			g.drawImage(bfib,395,483,this);
		}
		if(bfp2>0)
		{
			bfib=bfic2b.getImage();
			g.drawImage(bfib,450,483,this);
		}
		if(bfp3>0)
		{
			bfib=bfic3b.getImage();
			g.drawImage(bfib,510,483,this);
		}	
		
	}
			
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(bg,0,0,this);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHints(rh);
		paintsnk(g2d);
		paintf(g);
		if(bff>0)
			paintbf(g);
		g.drawImage(score,0,480,this);								// the brown board
		if(bfp1>0 || bfp2>0 || bfp3>0)
			paintbfp(g);
		g.setFont(new Font("Chiller",Font.BOLD,50));
		g.setColor(Color.black);
		g.drawString(Integer.toString(scr),22,532);
		g.setColor(Color.white);
		g.drawString(Integer.toString(scr),20,530);
	}

}