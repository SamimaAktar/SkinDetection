package skin;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Tester {
	private String FILENAME = "training\\training.txt";
	public double[][][] prob = new double[256][256][256];
	int count = 0;

	File file = new File("s.jpg");
	
	public void readTrainingData() {

		Scanner scan;
		File file = new File(FILENAME);
		try {
			scan = new Scanner(file);

			for (int i = 0; i < 256; i++) {
				for (int j = 0; j < 256; j++) {
					for (int k = 0; k < 256; k++) {
						if (scan.hasNextDouble()) {
							prob[i][j][k] = scan.nextDouble();
							count++;
						}
					}
				}
			}
			System.out.println(count);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	
	public void skinDetect() {
		readTrainingData();
		try {
			BufferedImage inputImage = ImageIO.read(file);

			int col = inputImage.getWidth();
			int row = inputImage.getHeight();

			for (int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {
					Color c = new Color(inputImage.getRGB(j, i));
					// input image
					int green = (int) c.getGreen();
					int red = (int) c.getRed();
					int blue = (int) c.getBlue();

					if (prob[red][green][blue] > 0.25) {
						Color edited = new Color(255, 255, 255);
						inputImage.setRGB(j, i, edited.getRGB());
					} else {
						Color edited = new Color(0, 0, 0);
						inputImage.setRGB(j, i, edited.getRGB());
					}
				}
			}
			ImageIO.write(inputImage, "png", new File("s1" + ".png"));
			System.out.println("Your mask is ready ....");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
