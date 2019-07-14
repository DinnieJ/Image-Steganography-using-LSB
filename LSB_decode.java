import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.awt.Color;
//import java.util.Arrays;
import java.util.Scanner;

import javax.imageio.ImageIO;
public class LSB_decode {
	static final String STEGIMAGEFILE = "C:\\Users\\MemeLord\\Pictures\\download.jpg";
	static final String DECODEDMESSAGEFILE = "C:\\Users\\MemeLord\\Pictures\\decode.txt";

	public static String b_msg="";
	public static int len = 0;

	public static void main(String[] args) throws Exception {
					
		BufferedImage yImage=readImageFile(STEGIMAGEFILE);

DecodeTheMessage(yImage);
String msg="";
//System.out.println("len is "+len*8);
/**
 * Add all the current char from the bit string
 * Convert to based on the Unicode and add each char to the String
 */
for(int i=0;i<len*8;i=i+8){
	
	String sub=b_msg.substring(i,i+8);
	
	int m=Integer.parseInt(sub,2);
	char ch=(char) m;
	System.out.println("m "+m+" c "+ch);
	msg+=ch;
}
PrintWriter out = new PrintWriter(new FileWriter(DECODEDMESSAGEFILE, true), true);
out.write(msg);
out.close();
}
public static BufferedImage readImageFile(String COVERIMAGEFILE){
BufferedImage theImage = null;
File p = new File (COVERIMAGEFILE);
try{
theImage = ImageIO.read(p);
}catch (IOException e){
e.printStackTrace();
System.exit(1);
}
return theImage;
}


public static void DecodeTheMessage (BufferedImage yImage) throws Exception{

int j=0;
int currentBitEntry=0;
String bx_msg="";
for (int x = 0; x < yImage.getWidth(); x++){
for ( int y = 0; y < yImage.getHeight(); y++){
if(x==0&&y<8){
	//System.out.println("enc "+yImage.getRGB(x, y)+" dec "+yImage.getRGB(x, y)+" "+b_msg);
	Color currentPixel = new Color(yImage.getRGB(x,y));
	int blue = currentPixel.getBlue();

	String x_s=Integer.toBinaryString(blue);
	bx_msg+=x_s.charAt(x_s.length()-1);
	len=Integer.parseInt(bx_msg,2);
	
}
else if(currentBitEntry<len*8){
//System.out.println("enc "+yImage.getRGB(x, y)+" dec "+yImage.getRGB(x, y)+" "+b_msg);
	Color currentPixel = new Color(yImage.getRGB(x, y));
	int blue = currentPixel.getBlue();
	String x_s=Integer.toBinaryString(blue);
	b_msg+=x_s.charAt(x_s.length()-1);

	
	currentBitEntry++;	
	//System.out.println("curre "+currentBitEntry);
}
}
}
System.out.println("bin value of msg hided in img is "+b_msg);
}
}

