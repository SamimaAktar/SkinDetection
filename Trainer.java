package skin;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Trainer {

	public int[][][] skin = new int[256][256][256];
	public int[][][] nonSkin = new int[256][256][256];
	public double[][][] prob = new double[256][256][256];
	File imageFolder = null;
	File[] imagePaths;
	File maskFolder = null;
	File[] maskPaths;

	private String FILENAME = "training\\training.txt";
	
	public void initialize() {
		for (int i = 0; i < 256; i++) {
			for (int j = 0; j < 256; j++) {
				for (int k = 0; k < 256; k++) {
					skin[i][j][k] = 0;
					nonSkin[i][j][k] = 0;
				}
			}
		}
	}
	
	public void trainingData() {
		initialize();
		try {

			imageFolder = new File("image\\");
			maskFolder = new File("mask\\");

			
			imagePaths = imageFolder.listFiles();
			maskPaths = maskFolder.listFiles();
			for (int k = 0; k < imagePaths.length; k++) {
					BufferedImage img1 = ImageIO.read(new File(imagePaths[k].toString())); // rgb
																							// image
					BufferedImage img2 = ImageIO.read(new File(maskPaths[k].toString())); // mask
																							// image
				

					int col = img1.getWidth();
					int row = img1.getHeight();

					for (int i = 0; i < row; i++) {
						for (int j = 0; j < col; j++) {
							Color c1 = new Color(img1.getRGB(j, i));
							Color c2 = new Color(img2.getRGB(j, i));
							// RGB
							int green1 = (int) c1.getGreen();
							int red1 = (int) c1.getRed();
							int blue1 = (int) c1.getBlue();
							// MASK
							int green2 = (int) c2.getGreen();
							int red2 = (int) c2.getRed();
							int blue2 = (int) c2.getBlue();

							if (red2 > 150 && green2 > 150 && blue2 > 150) {
								skin[red1][green1][blue1]++;
							} else {
								nonSkin[red1][green1][blue1]++;
							}

						}
					}

				}
				trainerFile();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void trainerFile() {
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			fw = new FileWriter(FILENAME, true);

			bw = new BufferedWriter(fw);

			for (int i = 0; i < 256; i++) {
				for (int j = 0; j < 256; j++) {
					for (int k = 0; k < 256; k++) {
						if (((double) skin[i][j][k] + (double) nonSkin[i][j][k]) == 0) {
							prob[i][j][k] = 0;
						} else {
							prob[i][j][k] = (double) skin[i][j][k]
									/ ((double) skin[i][j][k] + (double) nonSkin[i][j][k]);
						}

						bw.write(prob[i][j][k] + "\n");
					
					}
				}
			}
			System.out.println("Training Complete");

			if (bw != null)
				bw.close();

			if (fw != null)
				fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
