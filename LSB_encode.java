import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
//import java.util.Arrays;
import java.util.Scanner;

import javax.imageio.ImageIO;
public class LSB_encode {
	static final String MESSAGEFILE = "C:\\Users\\MemeLord\\Documents\\message.txt";
	static final String COVERIMAGEFILE = "C:\\Users\\MemeLord\\Pictures\\download.jpg";	
	static final String STEGIMAGEFILE = "C:\\Users\\MemeLord\\Pictures\\download.jpg";
	public static void main(String[] args) throws Exception {
		
		String contentOfMessageFile = (readMessageFile());
int[] bits=bit_Msg(contentOfMessageFile);
System.out.println("msg in file "+contentOfMessageFile);
for(int i=0;i<bits.length;i++)
System.out.print(bits[i]);
System.out.println();
BufferedImage theImage=readImageFile(COVERIMAGEFILE);
hideTheMessage(bits, theImage);

}
	
public static String readMessageFile () throws FileNotFoundException{
	String contentOfMessageFile = "";
	File a = new File (MESSAGEFILE);
	Scanner scan = new Scanner (a);
	while (scan.hasNextLine()){
	String next = scan.nextLine();
	contentOfMessageFile += next;
	if (scan.hasNextLine()){
	contentOfMessageFile += "\n";
	}
	}
	scan.close();
	return contentOfMessageFile;
	}
public static int[] bit_Msg(String msg){
	int j=0;
	int[] b_msg=new int[msg.length()*8];
	for(int i=0;i<msg.length();i++){
		int x=msg.charAt(i);
		String x_s=Integer.toBinaryString(x);
		while(x_s.length()!=8){
			x_s='0'+x_s;
		}
		System.out.println("dec value for "+x +" is "+x_s);

		for(int i1=0;i1<8;i1++) {
		    b_msg[j] = Integer.parseInt(String.valueOf(x_s.charAt(i1)));
		    j++;
		  };
	}
	
	return b_msg;
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


public static void hideTheMessage (int[] bits, BufferedImage theImage) throws Exception{
	File f = new File (STEGIMAGEFILE);
	int bit_l=bits.length/8;
	int[] bl_msg=new int[8];
	System.out.println("bit lent "+bit_l);
	String bl_s=Integer.toBinaryString(bit_l);
	while(bl_s.length()!=8){
		bl_s='0'+bl_s;
	}
	for(int i1=0;i1<8;i1++) {
		bl_msg[i1] = Integer.parseInt(String.valueOf(bl_s.charAt(i1)));
	  };
int j=0;
int b=0;
int currentBitEntry=8;

for (int x = 0; x < theImage.getWidth(); x++){
for ( int y = 0; y < theImage.getHeight(); y++){
	if(x==0&&y<8){
		Color currentPixel = new Color(theImage.getRGB(x, y));	
		int red = currentPixel.getRed();
		int green = currentPixel.getGreen();
		int blue = currentPixel.getBlue();
		String x_s=Integer.toBinaryString(blue);
		String sten_s=x_s.substring(0, x_s.length()-1);
		sten_s=sten_s+Integer.toString(bl_msg[b]);

		//j++;
		int new_blue=Integer.parseInt(sten_s, 2);
		int a=255;
		theImage.setRGB(x, y, new Color(red, green, new_blue).getRGB());
		//System.out.println("original "+ori+" after "+theImage.getRGB(x, y));
		ImageIO.write(theImage, "png", f);
b++;

	}
	else if (currentBitEntry < bits.length+8 ){

	Color currentPixel = new Color(theImage.getRGB(x, y));
	int red = currentPixel.getRed();
	int green = currentPixel.getGreen();
	int blue = currentPixel.getBlue();

	String x_s=Integer.toBinaryString(blue);
	String sten_s=x_s.substring(0, x_s.length()-1);
	sten_s=sten_s+Integer.toString(bits[j]);
	j++;
	int new_blue=Integer.parseInt(sten_s, 2);

	theImage.setRGB(x, y, new Color(red,green,new_blue).getRGB());
	//System.out.println("original "+ori+" after "+theImage.getRGB(x, y));
	ImageIO.write(theImage, "png", f);

	currentBitEntry++;	
	//System.out.println("curre "+currentBitEntry);
	}
}
}
}
}
