package org.yourorghere;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import static javax.media.opengl.GL.GL_FRONT;
import static javax.media.opengl.GL.GL_SHININESS;
import static javax.media.opengl.GL.GL_SPECULAR;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


/**
 * Zinek.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class Marekszmolda implements GLEventListener {

   int i=0;
   static BufferedImage image1 = null,image2 = null, image3 = null;
static Texture t1 = null, t2 = null, t3 = null;

 private static float xrot = 0.0f, yrot = 0.0f;
 public static float ambientLight[] = { 0.3f, 0.3f, 0.3f, 1.0f };//swiat?o otaczaj?e
public static float diffuseLight[] = { 0.7f, 0.7f, 0.7f, 1.0f };//?wiat?o rozproszone
public static float specular[] = { 1.0f, 1.0f, 1.0f, 1.0f}; //?wiat?o odbite
public static float lightPos[] = { 0.0f, 150.0f, 150.0f, 1.0f };
public static float lightPos2[] = { 0.0f, 150.0f, -150.0f, 1.0f };
public static float direction[]={0,0,0};//pozycja ?wiat?a
    public static void main(String[] args) {
        Frame frame = new Frame("Simple JOGL Application");
        GLCanvas canvas = new GLCanvas();
       /* Scanner sc= new Scanner(System.in);
System.out.println("Podaj wspolrzedna x:");
p1=sc.nextFloat();
System.out.println("Podaj wspolrzedna y:");
p2=sc.nextFloat();
System.out.println("Podaj srednice:");
s=sc.nextFloat();*/
        canvas.addGLEventListener(new Marekszmolda());
        frame.add(canvas);
        frame.setSize(640, 480);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        frame.addKeyListener(new KeyListener()
 {
           
 public void keyPressed(KeyEvent e)
 {
 if(e.getKeyCode() == KeyEvent.VK_UP)
 xrot -= 1.0f;
 if(e.getKeyCode() == KeyEvent.VK_DOWN)
 xrot +=1.0f;
 if(e.getKeyCode() == KeyEvent.VK_RIGHT)
 yrot += 1.0f;
 if(e.getKeyCode() == KeyEvent.VK_LEFT)
 yrot -=1.0f;
if(e.getKeyChar() == 'q')
    ambientLight = new float[]{ambientLight[0]+0.1f, ambientLight[0]+0.1f, ambientLight[0]+0.1f,1.0f};
if(e.getKeyChar() == 'w')
    ambientLight = new float[]{ambientLight[0]+0.1f, ambientLight[0]+0.1f, ambientLight[0]+0.1f,1.0f};
if(e.getKeyChar() == 'a')
    diffuseLight = new float[]{diffuseLight[0]-0.1f, diffuseLight[0]-0.1f, diffuseLight[0]-0.1f,1.0f};
if(e.getKeyChar() == 's')
    diffuseLight = new float[]{diffuseLight[0]+0.1f, diffuseLight[0]+0.1f, diffuseLight[0]+0.1f,1.0f};
if(e.getKeyChar() == 'z')
    specular = new float[]{specular[0]-0.1f, specular[0]-0.1f, specular[0]-0.1f};
if(e.getKeyChar() == 'x')
    specular = new float[]{specular[0]+0.1f, specular[0]+0.1f, specular[0]+0.1f};
if(e.getKeyChar() == 'e')
    lightPos = new float[]{lightPos[0]-0.1f, lightPos[0]-0.1f, lightPos[0]-0.1f};
if(e.getKeyChar() == 'r')
    lightPos = new float[]{lightPos[0]+0.1f, lightPos[0]+0.1f, lightPos[0]+0.1f};

   
    }
 public void keyReleased(KeyEvent e){}
 public void keyTyped(KeyEvent e){}
 });

        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));
     
        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH); 
gl.glEnable(GL.GL_CULL_FACE);// try setting this to GL_FLAT and see what happens.
//warto?i sk?dowe o?ietlenia i koordynaty ??? ?iat?


//(czwarty parametr okre?a odleg?? ???:
//0.0f-niesko?zona; 1.0f-okre?ona przez pozosta? parametry)
gl.glEnable(GL.GL_LIGHTING); //uaktywnienie o?ietlenia
//ustawienie parametr? ??? ?iat? nr. 0
gl.glLightfv(GL.GL_LIGHT0,GL.GL_AMBIENT,ambientLight,0); //swiat? otaczaj?e
gl.glLightfv(GL.GL_LIGHT0,GL.GL_DIFFUSE,diffuseLight,0); //?iat? rozproszone
gl.glLightfv(GL.GL_LIGHT0,GL.GL_SPECULAR,specular,0); //?iat? odbite
gl.glLightfv(GL.GL_LIGHT0,GL.GL_POSITION,lightPos,0);
gl.glLightfv( GL.GL_LIGHT0, GL.GL_SPOT_DIRECTION, direction ,0);
gl.glLightf( GL.GL_LIGHT0, GL.GL_SPOT_EXPONENT, 1.0f );
gl.glLightf( GL.GL_LIGHT0, GL.GL_SPOT_CUTOFF, 1.0f);//pozycja ?iat?
gl.glEnable(GL.GL_LIGHT0);

gl.glLightfv(GL.GL_LIGHT1,GL.GL_AMBIENT,ambientLight,0); //swiat? otaczaj?e
gl.glLightfv(GL.GL_LIGHT1,GL.GL_DIFFUSE,diffuseLight,0); //?iat? rozproszone
gl.glLightfv(GL.GL_LIGHT1,GL.GL_SPECULAR,specular,0); //?iat? odbite
gl.glLightfv(GL.GL_LIGHT1,GL.GL_POSITION,lightPos2,0); //pozycja ?iat?
gl.glEnable(GL.GL_LIGHT1);//uaktywnienie ??? ?iat? nr. 0
gl.glEnable(GL.GL_COLOR_MATERIAL); //uaktywnienie ?edzenia kolor?
//kolory b?? ustalane za pomoc? glColor
gl.glColorMaterial(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE);
//Ustawienie jasno?i i odblaskowo?i obiekt?
float specref[] = { 1.0f, 1.0f, 1.0f, 1.0f }; //parametry odblaskowo?ci
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR,specref,0);
        
        gl.glMateriali(GL.GL_FRONT,GL.GL_SHININESS,128);
        gl.glEnable(GL.GL_DEPTH_TEST);
        try
        {
            image1=ImageIO.read(getClass().getResourceAsStream("/bok.jpg"));
            image2=ImageIO.read(getClass().getResourceAsStream("/trawa.jpg"));
            image3=ImageIO.read(getClass().getResourceAsStream("/niebo.jpg"));
            
            
        }
        catch (Exception ex)
        {
            return;
        }
t1 = TextureIO.newTexture(image1, false);
t2 = TextureIO.newTexture(image2, false);
t3 = TextureIO.newTexture(image3, false);
gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE,
 GL.GL_BLEND | GL.GL_MODULATE);
gl.glEnable(GL.GL_TEXTURE_2D);
gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
gl.glBindTexture(GL.GL_TEXTURE_2D, t1.getTextureObject());

    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!
        
            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(100.0f, h, 1.0, 200.0);
       // gl.glViewport(200, 200, 100, 100);

           /*     float ilor;
if(width<=height)
{
ilor = height/width;
gl.glOrtho(-10.0f,10.0f,-10.0f*ilor,10.0f*ilor,-10.0f,10.0f);
}
else
{
ilor = width/height;
 gl.glOrtho(-10.0f*ilor,10.0f*ilor,-10.0f,10.0f,-10.0f,10.0f);
}
    */
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();

    }
    public void display(GLAutoDrawable drawable) {
        
//Tworzenie obiektu
GL gl = drawable.getGL();
//Czyszczenie przestrzeni 3D przed utworzeniem kolejnej klatki
 gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
 //Resetowanie macierzy transformacji

 gl.glLoadIdentity();
 gl.glTranslatef(0.0f, 0.0f, -16.0f); //przesuni?ie o 6 jednostek
 gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f); //rotacja wok? osi X
 gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f); //rotacja wok? osi Y
 gl.glLightfv(GL.GL_LIGHT0,GL.GL_AMBIENT,ambientLight,0); //swiat? otaczaj?e
gl.glLightfv(GL.GL_LIGHT0,GL.GL_DIFFUSE,diffuseLight,0); //?iat? rozproszone
gl.glLightfv(GL.GL_LIGHT0,GL.GL_SPECULAR,specular,0);

Rysuj(gl, t1, t2, t3);

// Tu piszemy kod rysuj?y grafik? 3D
/*gl.glBegin(GL.GL_TRIANGLES);
gl.glColor3f(1.0f,0.0f,0.0f);
gl.glVertex3f(-1.0f, 2.0f, -6.0f);
gl.glVertex3f(-3.0f,1.0f, -6.0f);
gl.glVertex3f( 1.0f,1.0f, -6.0f);
gl.glEnd();
gl.glBegin(GL.GL_QUADS);
gl.glColor3f(1.0f,3.0f,0.0f);
gl.glVertex3f(-2.9f,1.0f,-6.0f);
gl.glVertex3f(-2.9f,-1.0f,-6.0f);
gl.glVertex3f(0.9f,-1.0f,-6.0f);
gl.glVertex3f(0.9f,1.0f,-6.0f);
gl.glEnd();
gl.glBegin(GL.GL_QUADS);
gl.glColor3f(0.4f,0.2f,0.2f);
gl.glVertex3f(-2.8f,-0.0f,-6.0f);
gl.glVertex3f(-2.8f,-1.0f,-6.0f);
gl.glVertex3f(-2.2f,-1.0f,-6.0f);
gl.glVertex3f(-2.2f,-0.0f,-6.0f);
gl.glEnd();
gl.glBegin(GL.GL_QUADS);
gl.glColor3f(0.2f,0.4f,1.0f);
gl.glVertex3f(0.2f,0.8f,-6.0f);
gl.glVertex3f(0.2f,0.2f,-6.0f);
gl.glVertex3f(0.8f,0.2f,-6.0f);
gl.glVertex3f(0.8f,0.8f,-6.0f);
gl.glEnd();*/



//kolo(gl,p1,p2,s);
/* float x1=-1;
 float y1=0;
 float x2=1;
 float y2=0;
 float x3=0;
 float y3=1;
 Random rd=new Random();
        
 for(int i=0;i<2;i++){
     
 trojkat(gl,x1,y1,x2,y2,x3,y3,-6,0.1f+i/3,0.1f+i/3,0.1f+i/3);
 y3=y1;
 x1=(x3-x1)/2;
 y1=(y3-y1)/2;
 x2=(x3-x2)/2;
 y2=(y3-y2)/2;
 x3=(x2-x1)/2;
 
 }*/
/*
//szescian\/
    gl.glScalef(4, 4, 4);
    gl.glBindTexture(GL.GL_TEXTURE_2D, t1.getTextureObject());
 gl.glBegin(GL.GL_QUADS);
//?iana g?na
    
 gl.glNormal3f(0.0f,1.0f,0.0f);
    gl.glTexCoord2f(1,1);
      gl.glVertex3f( 1.0f, 1.0f, -1.0f);
      gl.glTexCoord2f(0,1);
      gl.glVertex3f(-1.0f, 1.0f, -1.0f);
      gl.glTexCoord2f(0,0);
      gl.glVertex3f(-1.0f, 1.0f,  1.0f);
      gl.glTexCoord2f(1,0);
      gl.glVertex3f( 1.0f, 1.0f,  1.0f);
     gl.glEnd(); 
      
      
      //sciana przednia
     gl.glBindTexture(GL.GL_TEXTURE_2D, t2.getTextureObject());
 gl.glBegin(GL.GL_QUADS);
 gl.glNormal3f(0.0f,0.0f,1.0f);
 gl.glTexCoord2f(2,2);
gl.glVertex3f(-1.0f,-1.0f,1.0f);
gl.glTexCoord2f(0,2);
gl.glVertex3f(1.0f,-1.0f,1.0f);
gl.glTexCoord2f(0,0);
gl.glVertex3f(1.0f,1.0f,1.0f);
gl.glTexCoord2f(2,0);
gl.glVertex3f(-1.0f,1.0f,1.0f);
 gl.glEnd(); 
//sciana tylnia
 gl.glBindTexture(GL.GL_TEXTURE_2D, t1.getTextureObject());
 gl.glBegin(GL.GL_QUADS);
 gl.glNormal3f(0.0f,0.0f,1.0f);
 gl.glTexCoord2f(0,0);
gl.glVertex3f(-1.0f,1.0f,-1.0f);
gl.glTexCoord2f(2,0);
gl.glVertex3f(1.0f,1.0f,-1.0f);
gl.glTexCoord2f(2,2);
gl.glVertex3f(1.0f,-1.0f,-1.0f);
gl.glTexCoord2f(0,2);
gl.glVertex3f(-1.0f,-1.0f,-1.0f);
gl.glEnd();
//?iana lewa
  gl.glBegin(GL.GL_QUADS);
 gl.glNormal3f(1.0f,0.0f,0.0f);
gl.glVertex3f(-1.0f,-1.0f,-1.0f);
gl.glVertex3f(-1.0f,-1.0f,1.0f);
gl.glVertex3f(-1.0f,1.0f,1.0f);
gl.glVertex3f(-1.0f,1.0f,-1.0f);
gl.glEnd();
//?iana prawa
 gl.glBegin(GL.GL_QUADS);
 gl.glNormal3f(1.0f,0.0f,0.0f);
gl.glVertex3f(1.0f,1.0f,-1.0f);
gl.glVertex3f(1.0f,1.0f,1.0f);
gl.glVertex3f(1.0f,-1.0f,1.0f);
gl.glVertex3f(1.0f,-1.0f,-1.0f);
gl.glEnd();
//?iana dolna
 gl.glBegin(GL.GL_QUADS);
 gl.glNormal3f(0.0f,1.0f,0.0f);
gl.glVertex3f(-1.0f,-1.0f,1.0f);
gl.glVertex3f(-1.0f,-1.0f,-1.0f);
gl.glVertex3f(1.0f,-1.0f,-1.0f);
gl.glVertex3f(1.0f,-1.0f,1.0f);
gl.glEnd();*/
//ostroslup\/
/*
gl.glScalef(4, 4, 4);
gl.glBegin(GL.GL_QUADS);
gl.glBindTexture(GL.GL_TEXTURE_2D, t1.getTextureObject());
gl.glNormal3f(0.0f,1.0f,0.0f);
gl.glTexCoord2f(0,1);
gl.glVertex3f(-1.0f,-1.0f,1.0f);
gl.glTexCoord2f(0,0);
gl.glVertex3f(-1.0f,-1.0f,-1.0f);
gl.glTexCoord2f(1,0);
gl.glVertex3f(1.0f,-1.0f,-1.0f);
gl.glTexCoord2f(1,1);
gl.glVertex3f(1.0f,-1.0f,1.0f);
gl.glEnd();
gl.glBindTexture(GL.GL_TEXTURE_2D, t1.getTextureObject());
gl.glBegin(GL.GL_TRIANGLES);
float[] scianka1={-1.0f, -1.0f, 1.0f, //wp?rz?ne pierwszego punktu
                    1.0f, -1.0f, 1.0f, //wsp?rz?ne drugiego punktu
 0.0f, 1.0f, 0.0f}; //wsp?rz?ne trzeciego punktu
float[] normalna1 = WyznaczNormalna(scianka1,0,3,6);
gl.glNormal3fv(normalna1,0);
gl.glTexCoord2f(0,1);
gl.glVertex3f(-1.0f,-1.0f,1.0f);
gl.glTexCoord2f(1,1);
gl.glVertex3f(1.0f,-1.0f,1.0f);
gl.glTexCoord2f(0,0);
gl.glVertex3f(0.0f,1.0f,0.0f);
gl.glEnd();
gl.glBindTexture(GL.GL_TEXTURE_2D, t1.getTextureObject());
gl.glBegin(GL.GL_TRIANGLES);
float[] scianka2={1.0f, -1.0f, -1.0f, //wp?rz?ne pierwszego punktu
                    -1.0f, -1.0f, -1.0f, //wsp?rz?ne drugiego punktu
                    0.0f, 1.0f, 0.0f}; //wsp?rz?ne trzeciego punktu
float[] normalna2 = WyznaczNormalna(scianka1,0,3,6);
gl.glNormal3fv(normalna2,0);
gl.glTexCoord2f(0,1);
gl.glVertex3f(1.0f,-1.0f,-1.0f);
gl.glTexCoord2f(1,1);
gl.glVertex3f(-1.0f,-1.0f,-1.0f);
gl.glTexCoord2f(0,0);
gl.glVertex3f(0.0f,1.0f,0.0f);
gl.glEnd();
gl.glBindTexture(GL.GL_TEXTURE_2D, t1.getTextureObject());
gl.glBegin(GL.GL_TRIANGLES);
float[] scianka3={-1.0f, -1.0f, -1.0f, //wp?rz?ne pierwszego punktu
                    -1.0f, -1.0f, 1.0f, //wsp?rz?ne drugiego punktu
                    0.0f, 1.0f, 0.0f}; //wsp?rz?ne trzeciego punktu
float[] normalna3 = WyznaczNormalna(scianka1,0,3,6);
gl.glNormal3fv(normalna3,0);
gl.glTexCoord2f(0,1);
gl.glVertex3f(-1.0f,-1.0f,-1.0f);
gl.glTexCoord2f(1,1);
gl.glVertex3f(-1.0f,-1.0f,1.0f);
gl.glTexCoord2f(0,0);
gl.glVertex3f(0.0f,1.0f,0.0f);
gl.glEnd();
gl.glBindTexture(GL.GL_TEXTURE_2D, t1.getTextureObject());
gl.glBegin(GL.GL_TRIANGLES);
float[] scianka4={1.0f, -1.0f, 1.0f, //wp?rz?ne pierwszego punktu
                    1.0f, -1.0f, -1.0f, //wsp?rz?ne drugiego punktu
                    0.0f, 1.0f, 0.0f}; //wsp?rz?ne trzeciego punktu
float[] normalna4 = WyznaczNormalna(scianka1,0,3,6);
gl.glNormal3fv(normalna4,0);
gl.glTexCoord2f(0,1);
gl.glVertex3f(1.0f,-1.0f,1.0f);
gl.glTexCoord2f(1,1);
gl.glVertex3f(1.0f,-1.0f,-1.0f);
gl.glTexCoord2f(0,0);
gl.glVertex3f(0.0f,1.0f,0.0f);
gl.glEnd();
*/
 
//walec\/

//float x,y,kat;
//gl.glScalef(4, 4, 4);
//gl.glBindTexture(GL.GL_TEXTURE_2D, t1.getTextureObject());
//gl.glBegin(GL.GL_QUAD_STRIP);
////gl.glVertex3f(0.0f,0.0f,-6.0f); //?odek
//
//for(kat = 0.0f; kat < (2.0f*Math.PI);
//kat+=(Math.PI/32.0f))
//{
//x = 1.0f*(float)Math.sin(kat);
//y = 1.0f*(float)Math.cos(kat);
//gl.glNormal3f(x,y,0f);
//gl.glTexCoord2f(kat/6,0);
//gl.glVertex3f(x, 2.0f, y);
//gl.glTexCoord2f(kat/6,1);
//gl.glVertex3f(x, -2.0f, y);//kolejne punkty
//}
//gl.glEnd();
//
//float xx,yy,katt;
//gl.glBegin(GL.GL_TRIANGLE_FAN);
//
//gl.glVertex3f(0.0f,2.0f,0.0f); //?odek
//for(katt = 0.0f; katt < (2.0f*Math.PI);
//katt+=(Math.PI/32.0f))
//{
//xx = 1.0f*(float)Math.sin(katt);
//yy = 1.0f*(float)Math.cos(katt);
//gl.glNormal3f(xx,yy,0f);
//gl.glVertex3f(xx, 2.0f, yy); //kolejne punkty
//}
//gl.glEnd();
//
//float xxx,yyy,kattt;
//gl.glBegin(GL.GL_TRIANGLE_FAN);
//
//gl.glVertex3f(0.0f,-2.0f,0.0f); //?odek
//for(kattt = (float) (2.0f*Math.PI); kattt > 0.0f;
//kattt-=(Math.PI/32.0f))
//{
//xxx = 1.0f*(float)Math.sin(kattt);
//yyy = 1.0f*(float)Math.cos(kattt);
//gl.glNormal3f(xxx,yyy,0f);
//gl.glVertex3f(xxx, -2.0f, yyy); //kolejne punkty
//}
//gl.glEnd();


/*
gl.glBegin(GL.GL_QUAD_STRIP);
gl.glVertex3f(0.0f,0.0f,-6.0f); //?odek
gl.glColor3f(1.0f,1.0f,0.0f);
for(kat = 0.0f; kat < (2.0f*Math.PI);
kat+=(Math.PI/32.0f))
{
x = 1.0f*(float)Math.sin(kat);
y = 1.0f*(float)Math.cos(kat);
gl.glNormal3f(x+4,y,0f);
gl.glVertex3f(x+4, 2.0f, y);
gl.glVertex3f(x+4, -2.0f, y);//kolejne punkty
}
gl.glEnd();
gl.glBegin(GL.GL_TRIANGLE_FAN);
gl.glColor3f(1.0f,0.0f,0.0f);
gl.glVertex3f(4.0f,2.0f,0.0f); //?odek
for(katt = 0.0f; katt < (2.0f*Math.PI);
katt+=(Math.PI/32.0f))
{
xx = 1.0f*(float)Math.sin(katt);
yy = 1.0f*(float)Math.cos(katt);
gl.glNormal3f(xx+4,yy,0f);
gl.glVertex3f(xx+4, 2.0f, yy); //kolejne punkty
}
gl.glEnd();
gl.glBegin(GL.GL_TRIANGLE_FAN);
gl.glColor3f(0.0f,1.0f,0.0f);
gl.glVertex3f(4.0f,-2.0f,0.0f); //?odek
for(kattt = (float) (2.0f*Math.PI); kattt > 0.0f;
kattt-=(Math.PI/32.0f))
{
xxx = 1.0f*(float)Math.sin(kattt);
yyy = 1.0f*(float)Math.cos(kattt);
gl.glNormal3f(xxx+4,yyy,0f);
gl.glVertex3f(xxx+4, -2.0f, yyy); //kolejne punkty
}
gl.glEnd();
 */
/*float xx,yy,katt;
gl.glBegin(GL.GL_TRIANGLE_FAN);
gl.glColor3f(1.0f,0.0f,0.0f);
gl.glVertex3f(0.0f,2.0f,0.0f); //?odek
for(katt = 0.0f; katt < (2.0f*Math.PI);
katt+=(Math.PI/32.0f))
{
xx = 1.0f*(float)Math.sin(katt);
yy = 1.0f*(float)Math.cos(katt);
gl.glVertex3f(xx, -2.0f, yy); //kolejne punkty
}
gl.glEnd();
float xxx,yyy,kattt;
gl.glBegin(GL.GL_TRIANGLE_FAN);
gl.glColor3f(0.0f,1.0f,0.0f);
gl.glVertex3f(0.0f,-2.0f,0.0f); //?odek
for(kattt = (float) (2.0f*Math.PI); kattt > 0.0f;
kattt-=(Math.PI/32.0f))
{
xxx = 1.0f*(float)Math.sin(kattt);
yyy = 1.0f*(float)Math.cos(kattt);
gl.glVertex3f(xxx, -2.0f, yyy); //kolejne punkty
}
gl.glEnd();*/
 
 

/* koparka\/
        gl.glScalef(2,2,2);
koparka.Rysuj(gl);
 if(koparka.trzy<-70.0f&&i==0)
 {     
     
     i=1;
 }
 if(koparka.trzy>=23.0f&&i==1)
 {     
     
     i=0;
 }
if(koparka.jeden>-15.0f&&i==0)
{
    koparka.jeden-=0.1f;
    
}
if(koparka.jeden<=-5.0f&&i==0&&koparka.dwa>-70.0f)
    {
        koparka.dwa-=0.1f;
    }
 if(koparka.dwa<=-55.0f&&i==0&&koparka.trzy>-70.0f)
    {
        koparka.trzy-=0.1f;
        System.out.println(koparka.trzy);
    }
 if(koparka.jeden<65.0f&&i==1)
{
    koparka.jeden+=0.1f;
    
}
 if(koparka.jeden>40&&i==1&&koparka.trzy<25.0f)
     koparka.trzy+=0.1f;
        */
gl.glFlush();



}

    void Rysuj(GL gl, Texture t1, Texture t2, Texture t3)
 {
//szescian
gl.glColor3f(1.0f,1.0f,1.0f);
//za³adowanie tekstury wczytanej wczeœniej z pliku krajobraz.bmp
gl.glBindTexture(GL.GL_TEXTURE_2D, t1.getTextureObject());
gl.glBegin(GL.GL_QUADS);
//œciana przednia
gl.glNormal3f(0.0f,0.0f,-1.0f);
gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(-100.0f,100.0f,100.0f);
gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(100.0f,100.0f,100.0f);
gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(100.0f,-100.0f,100.0f);
gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(-100.0f,-100.0f,100.0f);
//œciana tylnia
gl.glNormal3f(0.0f,0.0f,1.0f);
gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(-100.0f,-100.0f,-100.0f);
gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(100.0f,-100.0f,-100.0f);
gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(100.0f,100.0f,-100.0f);
gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(-100.0f,100.0f,-100.0f);
//œciana lewa
gl.glNormal3f(1.0f,0.0f,0.0f);
gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(-100.0f,100.0f,-100.0f);
gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(-100.0f,100.0f,100.0f);
gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(-100.0f,-100.0f,100.0f);
gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(-100.0f,-100.0f,-100.0f);
//œciana prawa
gl.glNormal3f(-1.0f,0.0f,0.0f);
gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(100.0f,-100.0f,-100.0f);
gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(100.0f,-100.0f,100.0f);
gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(100.0f,100.0f,100.0f);
gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(100.0f,100.0f,-100.0f);
gl.glEnd();

//œciana dolna
//za³adowanie tekstury wczytanej wczeœniej z pliku niebo.bmp
 gl.glBindTexture(GL.GL_TEXTURE_2D, t2.getTextureObject());
 //ustawienia aby tekstura siê powiela³a
 gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
 gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
gl.glBegin(GL.GL_QUADS);
gl.glNormal3f(0.0f,1.0f,0.0f);
 //koordynaty ustawienia 16 x 16 kwadratów powielonej tekstury na œcianie dolnej
gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(100.0f,-100.0f,100.0f);
gl.glTexCoord2f(0.0f, 16.0f);gl.glVertex3f(100.0f,-100.0f,-100.0f);
gl.glTexCoord2f(16.0f, 16.0f);gl.glVertex3f(-100.0f,-100.0f,-100.0f);
gl.glTexCoord2f(16.0f, 0.0f);gl.glVertex3f(-100.0f,-100.0f,100.0f);
gl.glEnd();

 //œciana gorna
//za³adowanie tekstury wczytanej wczeœniej z pliku trawa.bmp
gl.glBindTexture(GL.GL_TEXTURE_2D, t3.getTextureObject());
gl.glBegin(GL.GL_QUADS);
gl.glNormal3f(0.0f,-1.0f,0.0f);
gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(-100.0f,100.0f,100.0f);
gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(-100.0f,100.0f,-100.0f);
gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(100.0f,100.0f,-100.0f);
gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(100.0f,100.0f,100.0f);
gl.glEnd();
 }

    

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
    public void kolo(GL gl, float p1, float p2, float s)
    {
      
        gl.glBegin(GL.GL_TRIANGLE_FAN);
float x,y,kat;

gl.glVertex3f(p1,p2,-6.0f); //?odek
for(kat = 0.0f; kat < (2.0f*Math.PI);
kat+=(Math.PI/32.0f))
{
x = s/2*(float)Math.sin(kat);
y = s/2*(float)Math.cos(kat);
gl.glVertex3f(x+p1, y+p2, -6.0f); //kolejne punkty
}
gl.glEnd(); 
    }
    public void trojkat(GL gl, float x1,float y1,float x2, float y2, float x3, float y3, float z,float c1, float c2, float c3)
    {
        
       gl.glBegin(GL.GL_TRIANGLES);
       gl.glColor3f(c1,c2,c3);
        gl.glVertex3f(x1, y1, z);
        gl.glVertex3f(x2,y2, z);
        gl.glVertex3f(x3,y3, z);
        gl.glEnd(); 
    }
    void walec(GL gl)
 {
//wywo?jemy automatyczne normalizowanie normalnych
//bo operacja skalowania je zniekszta?i
gl.glEnable(GL.GL_NORMALIZE);
float x,y,kat;
gl.glBegin(GL.GL_QUAD_STRIP);
for(kat = 0.0f; kat < (2.0f*Math.PI); kat += (Math.PI/32.0f))
{
x = 0.5f*(float)Math.sin(kat);
y = 0.5f*(float)Math.cos(kat);
gl.glNormal3f((float)Math.sin(kat),(float)Math.cos(kat),0.0f);
gl.glVertex3f(x, y, -1.0f);
gl.glVertex3f(x, y, 0.0f);
}
gl.glEnd();
gl.glNormal3f(0.0f,0.0f,-1.0f);
x=y=kat=0.0f;
gl.glBegin(GL.GL_TRIANGLE_FAN);
gl.glVertex3f(0.0f, 0.0f, -1.0f); //srodek kola
for(kat = 0.0f; kat < (2.0f*Math.PI); kat += (Math.PI/32.0f))
{
x = 0.5f*(float)Math.sin(kat);
y = 0.5f*(float)Math.cos(kat);
gl.glVertex3f(x, y, -1.0f);
}
gl.glEnd();
gl.glNormal3f(0.0f,0.0f,1.0f);
x=y=kat=0.0f;
gl.glBegin(GL.GL_TRIANGLE_FAN);
gl.glVertex3f(0.0f, 0.0f, 0.0f); //srodek kola
for(kat = 2.0f*(float)Math.PI; kat > 0.0f ; kat -= (Math.PI/32.0f))
{
x = 0.5f*(float)Math.sin(kat);
y = 0.5f*(float)Math.cos(kat);
gl.glVertex3f(x, y, 0.0f);
}
gl.glEnd();
}

void stozek(GL gl)
{
//wywo?jemy automatyczne normalizowanie normalnych
gl.glEnable(GL.GL_NORMALIZE);
float x,y,kat;
gl.glBegin(GL.GL_TRIANGLE_FAN);
gl.glVertex3f(0.0f, 0.0f, -2.0f); //wierzcholek stozka
for(kat = 0.0f; kat < (2.0f*Math.PI); kat += (Math.PI/32.0f))
{
x = (float)Math.sin(kat);
y = (float)Math.cos(kat);
gl.glNormal3f((float)Math.sin(kat),(float)Math.cos(kat),-2.0f);
gl.glVertex3f(x, y, 0.0f);
}
gl.glEnd();
gl.glBegin(GL.GL_TRIANGLE_FAN);
gl.glNormal3f(0.0f,0.0f,1.0f);
gl.glVertex3f(0.0f, 0.0f, 0.0f); //srodek kola
for(kat = 2.0f*(float)Math.PI; kat > 0.0f; kat -= (Math.PI/32.0f))
{
x = (float)Math.sin(kat);
y = (float)Math.cos(kat);
gl.glVertex3f(x, y, 0.0f);
}
gl.glEnd();
}

    private float[] WyznaczNormalna(float[] punkty, int ind1, int ind2, int ind3)
{
 float[] norm = new float[3];
 float[] wektor0 = new float[3];
 float[] wektor1 = new float[3];

 for(int i=0;i<3;i++)
 {
 wektor0[i]=punkty[i+ind1]-punkty[i+ind2];
 wektor1[i]=punkty[i+ind2]-punkty[i+ind3];
 }

 norm[0]=wektor0[1]*wektor1[2]-wektor0[2]*wektor1[1];
 norm[1]=wektor0[2]*wektor1[0]-wektor0[0]*wektor1[2];
 norm[2]=wektor0[0]*wektor1[1]-wektor0[1]*wektor1[0];
 float d=
(float)Math.sqrt((norm[0]*norm[0])+(norm[1]*norm[1])+ (norm[2]*norm[2]) );
 if(d==0.0f)
 d=1.0f;
 norm[0]/=d;
 norm[1]/=d;
 norm[2]/=d;

 return norm;
}
}
