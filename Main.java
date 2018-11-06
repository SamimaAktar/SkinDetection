package skin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {
	
	public static void main(String[] args) {
		
		Trainer tr=new Trainer();
		tr.trainingData();
		
		
		Tester ts =new Tester();
		ts.skinDetect();
	}

}
