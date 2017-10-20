
/*
 * CS576 - Multimedia System Design:  HW 2
 * This is program for Discreet Cosine transformation and Discreet wavelet transformation
 *
 * input Parameters: input file name, number of coefficients to be taken
 * 			Ex : java imageReader <input_image_name> <number of coefficients>
 						 number of coefficients : 262144, 131072, 16384 or -1
 *
 * output: Program will produce 2 outputs
 * 			a. DCT and DWT image (2 images in one frame)
 *				 when input-number of coefficients = -1 is given,  64 images will be refreshed on the same frame.
 *			 At the end of displaying all the images "Progressive Analysis of images completed" message will be displayed.
 *
 * to compile : javac imageReader.java
 * to run 	  : java imageReader hw_1_high_res 262144
 *
 * Author: Monika Devanga Ravi
 * Date: 10/17/2017
 *
 */

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;

public class imageReader {

	static int width = 512;
	static int height = 512;
	static int Ln = width * height;
	static int N = 8;
	static int w = width / N;
	static int h = height / N;
	static int ln = w * h;
	// matrix for storing input image read, green and blue pixel values
	static double red[][] = new double[width][height];
	static double green[][] = new double[width][height];
	static double blue[][] = new double[width][height];
	// DCT Matrix
	static double[][][] blocksblue = new double[ln][N][N];
	static double[][][] blocksred = new double[ln][N][N];
	static double[][][] blocksgreen = new double[ln][N][N];
	static double[][][] Cr = new double[ln][N][N];
	static double[][][] Cb = new double[ln][N][N];
	static double[][][] Cg = new double[ln][N][N];
	static double[][][] qr = new double[ln][N][N];
	static double[][][] qb = new double[ln][N][N];
	static double[][][] qg = new double[ln][N][N];
	static double[][][] iblocksr = new double[ln][N][N];
	static double[][][] iblocksb = new double[ln][N][N];
	static double[][][] iblocksg = new double[ln][N][N];

	// DWT matrix
	static double redDWT[][] = new double[width][height];
	static double greenDWT[][] = new double[width][height];
	static double blueDWT[][] = new double[width][height];
	static double[][] matr = new double[width][height];
	static double[][] matr2 = new double[width][height];
	static double[][] matg = new double[width][height];
	static double[][] matg2 = new double[width][height];
	static double[][] matb = new double[width][height];
	static double[][] matb2 = new double[width][height];

	public static void main(String[] args) {

		String fileName = args[0];
		int quantNo = Integer.parseInt(args[1]); // total number of co-ordinates

		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		try {
			// read the input image and get R,G,B values in separate matrix
			File file = new File(args[0]);
			InputStream is = new FileInputStream(file);

			long len = file.length();
			byte[] bytes = new byte[(int) len];

			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}

			int ind = 0;
			int ii = 0;
			for (int y = 0; y < height; y++) {

				for (int x = 0; x < width; x++) {

					byte a = 0;
					byte r = bytes[ind];
					red[x][y] = (byte) r & 0xff;
					byte g = bytes[ind + height * width];
					green[x][y] = (byte) g & 0xff;
					byte b = bytes[ind + height * width * 2];
					blue[x][y] = (byte) b & 0xff;

					int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
					// int pix = ((a << 24) + (r << 16) + (g << 8) + b);
					img.setRGB(x, y, pix);
					ind++;

				}
			}
			// call DCT and DWT to quantize the image
			DCTDWTImage(quantNo);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// // Use a panel and label to display the image
		// JPanel panel = new JPanel();
		// panel.add(new JLabel(new ImageIcon(img)));
		//
		// JFrame frame = new JFrame("Original images");
		//
		// frame.getContentPane().add(panel);
		// frame.pack();
		// frame.setVisible(true);
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public static void DCTDWTImage(int quantNo) {
		DCTImage();
		DWTImage();
		IDCTIDWTImage(quantNo);
	}

	// DCT FUNCTION
	public static void DCTImage() {
		// DCT of a image and keeping matrix ready
		int bkk = 0;
		// Divide the blocks in 8 x 8
		// Get DCT blocks
		for (int a = 0; a < width; a += N) {
			// Get 8 lines of image
			for (int b = 0; b < height; b += N) {
				// Get block for FDCT
				for (int c = 0; c < N; c++) {
					for (int d = 0; d < N; d++) {
						blocksred[bkk][c][d] = red[(a + c)][b + d]-128;
						blocksgreen[bkk][c][d] = green[(a + c)][b + d]-128;
						blocksblue[bkk][c][d] = blue[(a + c)][b + d]-128;
					}
				}
				bkk++;
			}
		}
		// totally there are 4096 blocks with 8 rows and 8 columns

		// DCT transformation
		double[] coiff = new double[N];
		for (int co = 1; co < N; co++) {
			coiff[co] = 1;
		}
		coiff[0] = (1 / Math.sqrt(2));

		for (int ck = 0; ck < ln; ck++) {
			for (int k1 = 0; k1 < N; k1++) {
				for (int k2 = 0; k2 < N; k2++) {
					for (int n1 = 0; n1 < N; n1++) {
						for (int n2 = 0; n2 < N; n2++) {
							double temp = Math.cos(((2 * n1 + 1) * k1 * Math.PI) / (16))
									* Math.cos(((2 * n2 + 1) * k2 * Math.PI) / (16));

							Cr[ck][k1][k2] += (blocksred[ck][n1][n2] * temp);
							Cg[ck][k1][k2] += (blocksgreen[ck][n1][n2] * temp);
							Cb[ck][k1][k2] += (blocksblue[ck][n1][n2] * temp);
						}
					}
					Cr[ck][k1][k2] = ((coiff[k1] * coiff[k2]) / 4) * Cr[ck][k1][k2];
					Cg[ck][k1][k2] = ((coiff[k1] * coiff[k2]) / 4) * Cg[ck][k1][k2];
					Cb[ck][k1][k2] = ((coiff[k1] * coiff[k2]) / 4) * Cb[ck][k1][k2];
				}
			}
		}
	}

	// DWT FUNCTION
	public static void DWTImage() {
		int nr = height;
		int nc = width;
		int i1 = 0, i2 = 0, j1 = 0, j2 = 0;
		int nr2 = 0, nc2 = 0;
		int i = 0, j = 0, k = 0;

		// DWT start
		for (k = height; k > 1; k /= 2, nc /= 2) {
			// Horizontal processing:
			nc2 = nc / 2;
			for (i = 0; i < nr; i++) {
				for (j = 0; j < nc; j += 2) {
					j1 = j + 1;
					j2 = j / 2;
					matr[i][j2] = (red[i][j] + red[i][j1]) / 2;
					matr[i][nc2 + j2] = (red[i][j] - red[i][j1]) / 2;
					matg[i][j2] = (green[i][j] + green[i][j1]) / 2;
					matg[i][nc2 + j2] = (green[i][j] - green[i][j1]) / 2;
					matb[i][j2] = (blue[i][j] + blue[i][j1]) / 2;
					matb[i][nc2 + j2] = (blue[i][j] - blue[i][j1]) / 2;
				}
			}
			// Put the image back to source
			for (i = 0; i < nr; i++) {
				for (j = 0; j < nc; j++) {
					red[i][j] = matr[i][j];
					green[i][j] = matg[i][j];
					blue[i][j] = matb[i][j];
				}
			}
		}
		nr = height;
		nc = width;
		// vertical processing:
		for (k = width; k > 1; k /= 2, nr /= 2) {
			nr2 = nr / 2;
			for (i = 0; i < nr; i += 2) {
				for (j = 0; j < nc; j++) {
					i1 = i + 1;
					i2 = i / 2;
					matr[i2][j] = (red[i][j] + red[i1][j]) / 2;
					matr[nr2 + i2][j] = (red[i][j] - red[i1][j]) / 2;
					matg[i2][j] = (green[i][j] + green[i1][j]) / 2;
					matg[nr2 + i2][j] = (green[i][j] - green[i1][j]) / 2;
					matb[i2][j] = (blue[i][j] + blue[i1][j]) / 2;
					matb[nr2 + i2][j] = (blue[i][j] - blue[i1][j]) / 2;
				}
			}
			for (i = 0; i < nr; i++) {
				for (j = 0; j < nc; j++) {
					red[i][j] = matr[i][j];
					green[i][j] = matg[i][j];
					blue[i][j] = matb[i][j];
				}
			}
		}
	}

	public static void IDCTIDWTImage(int quantNo) {
		BufferedImage IDCTimg = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
		BufferedImage IDWTimg = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);

		// quantization - using number of coefficients given as paramenter to
		// display the image
		if (quantNo != -1) {
			zigzagDCT(quantNo);
			IDCTimg = IDCT();
			zigzagDWT(quantNo);
			IDWTimg = IDWT();
			// display the output image
			ImageIcon DCTicon = new ImageIcon(IDCTimg);
			ImageIcon DWTicon = new ImageIcon(IDWTimg);
			JLabel DCTlabel = new JLabel(DCTicon);
			DCTlabel.setText("DCT image");
			DCTlabel.setHorizontalTextPosition(JLabel.CENTER);
    	DCTlabel.setVerticalTextPosition(JLabel.TOP);
			JLabel DWTlabel = new JLabel(DWTicon);
			DWTlabel.setText("DWT image");
			DWTlabel.setHorizontalTextPosition(JLabel.CENTER);
    	DWTlabel.setVerticalTextPosition(JLabel.TOP);

			JPanel panel = new JPanel();
			panel.add(DCTlabel);
			panel.add(DWTlabel);
			JFrame frame = new JFrame("Number of coefficients used- " + quantNo);
			frame.getContentPane().add(panel);
			frame.pack();
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} else {
			JFrame frame = new JFrame();

			for (int xx = 0; xx <= 512 * 512; xx += 4096) {
				zigzagDCT(4096 + xx);
				IDCTimg = IDCT();
				zigzagDWT(4096 + xx);
				IDWTimg = IDWT();
				// display output image
				JPanel panel = new JPanel();
				ImageIcon DCTicon = new ImageIcon(IDCTimg);
				ImageIcon DWTicon = new ImageIcon(IDWTimg);
				JLabel DCTlabel = new JLabel(DCTicon);
				DCTlabel.setText("DCT image");
				DCTlabel.setHorizontalTextPosition(JLabel.CENTER);
	    	DCTlabel.setVerticalTextPosition(JLabel.TOP);
				JLabel DWTlabel = new JLabel(DWTicon);
				DWTlabel.setText("DWT image");
				DWTlabel.setHorizontalTextPosition(JLabel.CENTER);
	    	DWTlabel.setVerticalTextPosition(JLabel.TOP);
				panel.add(DCTlabel);
				panel.add(DWTlabel);
				frame.getContentPane().remove(panel);
				frame.setTitle("Progressive image with coefficient: " + (xx + 4096));
				frame.add(panel);
				frame.getContentPane().invalidate();
				frame.getContentPane().validate();
				frame.pack();
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				try
				{
    			Thread.sleep(500);
				}
				catch(InterruptedException ex)
				{
    			Thread.currentThread().interrupt();
				}
			}
			System.out.println("Progressive Analysis of images completed");
		}
	}

	// taking zigzag coefficients of DCT function
	public static void zigzagDCT(int quantNo) {
		int pixPerBlock = quantNo / 4096;
		// traversing the 8 x 8 matrix in zigzag manner
		for (int ck = 0; ck < ln; ck++) {
			int no = 0;
			for (int line = 1; line <= (N + N - 1); line++) {
				int start_col = Math.max(0, line - N);

				int count = Math.min(Math.min(line, (N - start_col)), N);

				if (count % 2 == 1) {
					for (int j = 0; j < count; j++) {
						if (no < pixPerBlock) {
							qr[ck][Math.min(N, line) - j - 1][start_col
									+ j] = Cr[ck][Math.min(N, line) - j - 1][start_col + j];
							qg[ck][Math.min(N, line) - j - 1][start_col
									+ j] = Cg[ck][Math.min(N, line) - j - 1][start_col + j];
							qb[ck][Math.min(N, line) - j - 1][start_col
									+ j] = Cb[ck][Math.min(N, line) - j - 1][start_col + j];
							no++;
						} else {
							qr[ck][Math.min(N, line) - j - 1][start_col + j] = 0.0;
							qg[ck][Math.min(N, line) - j - 1][start_col + j] = 0.0;
							qb[ck][Math.min(N, line) - j - 1][start_col + j] = 0.0;
							no++;
						}
					}
				} else {
					for (int j = 0; j < count; j++) {
						if (no < pixPerBlock) {
							qr[ck][start_col + j][Math.min(N, line) - j - 1] = Cr[ck][start_col + j][Math.min(N, line)
									- j - 1];
							qg[ck][start_col + j][Math.min(N, line) - j - 1] = Cg[ck][start_col + j][Math.min(N, line)
									- j - 1];
							qb[ck][start_col + j][Math.min(N, line) - j - 1] = Cb[ck][start_col + j][Math.min(N, line)
									- j - 1];
							no++;
						} else {
							qr[ck][start_col + j][Math.min(N, line) - j - 1] = 0.0;
							qg[ck][start_col + j][Math.min(N, line) - j - 1] = 0.0;
							qb[ck][start_col + j][Math.min(N, line) - j - 1] = 0.0;
							no++;
						}
					}
				}
			}
		}
	}

	// take zigzag coefficients for DWT image
	public static void zigzagDWT(int quantNo) {
		int no = 0;
		for (int line = 1; line <= (width + height - 1); line++) {
			int start_col = Math.max(0, line - height);
			int count = Math.min(Math.min(line, (width - start_col)), height);
			if (count % 2 == 1) {
				for (int ms = 0; ms < count; ms++) {
					if (no < quantNo) {
						matr2[Math.min(width, line) - ms - 1][start_col
								+ ms] = red[Math.min(width, line) - ms - 1][start_col + ms];
						matg2[Math.min(width, line) - ms - 1][start_col
								+ ms] = green[Math.min(width, line) - ms - 1][start_col + ms];
						matb2[Math.min(width, line) - ms - 1][start_col
								+ ms] = blue[Math.min(width, line) - ms - 1][start_col + ms];
						no++;
					} else {
						matr2[Math.min(width, line) - ms - 1][start_col + ms] = 0.0;
						matg2[Math.min(width, line) - ms - 1][start_col + ms] = 0.0;
						matb2[Math.min(width, line) - ms - 1][start_col + ms] = 0.0;
						no++;
					}
				}
			} else {
				for (int ms = 0; ms < count; ms++) {
					if (no < quantNo) {
						matr2[start_col + ms][Math.min(width, line) - ms
								- 1] = red[start_col + ms][Math.min(width, line) - ms - 1];
						matg2[start_col + ms][Math.min(width, line) - ms
								- 1] = green[start_col + ms][Math.min(width, line) - ms - 1];
						matb2[start_col + ms][Math.min(width, line) - ms
								- 1] = blue[start_col + ms][Math.min(width, line) - ms - 1];
						no++;
					} else {
						matr2[start_col + ms][Math.min(width, line) - ms - 1] = 0.0;
						matg2[start_col + ms][Math.min(width, line) - ms - 1] = 0.0;
						matb2[start_col + ms][Math.min(width, line) - ms - 1] = 0.0;
						no++;
					}
				}
			}
		}
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				redDWT[i][j] = matr2[i][j];
				greenDWT[i][j] = matg2[i][j];
				blueDWT[i][j] = matb2[i][j];
			}
		}
	}

	// perform IDCT for a image
	public static BufferedImage IDCT() {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		double[] w2 = new double[N];
		w2[0] = (1 / Math.sqrt(2));
		for (int i = 1; i < N; i++) {
			w2[i] = 1;
		}
		for (int ck = 0; ck < ln; ck++) {
			for (int n1 = 0; n1 < N; n1++) {
				for (int n2 = 0; n2 < N; n2++) {
					for (int k1 = 0; k1 < N; k1++) {
						for (int k2 = 0; k2 < N; k2++) {
							double temp = Math.cos(((2 * n1 + 1) * k1 * Math.PI) / (16))
									* Math.cos(((2 * n2 + 1) * k2 * Math.PI) / (16));

							iblocksr[ck][n1][n2] += (w2[k1] * w2[k2] * qr[ck][k1][k2] * temp);
							iblocksg[ck][n1][n2] += (w2[k1] * w2[k2] * qg[ck][k1][k2] * temp);
							iblocksb[ck][n1][n2] += (w2[k1] * w2[k2] * qb[ck][k1][k2] * temp);
						}
					}
					iblocksr[ck][n1][n2] = iblocksr[ck][n1][n2] / (4.0);
					iblocksg[ck][n1][n2] = iblocksg[ck][n1][n2] / (4.0);
					iblocksb[ck][n1][n2] = iblocksb[ck][n1][n2] / (4.0);
				}
			}
		}

		// merging back r,g,b for output display
		int bkk = 0;
		for (int a = 0; a < (h * N); a += N) {
			for (int b = 0; b < (w * N); b += N) {
				for (int c = 0; c < N; c++) {
					for (int d = 0; d < N; d++) {
						iblocksr[bkk][c][d]+=128;
						iblocksg[bkk][c][d]+=128;
						iblocksb[bkk][c][d]+=128;

						if (iblocksr[bkk][c][d] < 0)
							iblocksr[bkk][c][d] = 0.0;
						else if (iblocksr[bkk][c][d] > 255)
							iblocksr[bkk][c][d] = 255.0;

						if (iblocksg[bkk][c][d] < 0)
							iblocksg[bkk][c][d] = 0.0;
						else if (iblocksg[bkk][c][d] > 255)
							iblocksg[bkk][c][d] = 255.0;

						if (iblocksb[bkk][c][d] < 0)
							iblocksb[bkk][c][d] = 0;
						else if (iblocksb[bkk][c][d] > 255)
							iblocksb[bkk][c][d] = 255;

						int pix = 0xff000000 | ((((byte) iblocksr[bkk][c][d]) & 0xff) << 16)
								| ((((byte) iblocksg[bkk][c][d]) & 0xff) << 8) | (((byte) iblocksb[bkk][c][d]) & 0xff);

						img.setRGB(a + c, b + d, pix);
					}
				}
				bkk++;
			}
		}
		return img;
	}

	// perform IDWT for the image
	public static BufferedImage IDWT() {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		int level, nrows, ncols;
		int i, j, i2, j2, k, nr, nc, nr2, nc2, tmp;
		nrows = height * 2;
		ncols = width * 2;
		nr = 2;
		nc = width;

		for (k = 1; k < height; k *= 2, nr *= 2) {
			// Vertical processing:
			nr2 = nr / 2;
			for (i = 0; i < nr2; i++) {
				for (j = 0; j < nc; j++) {
					i2 = i * 2;
					matr[i2][j] = (redDWT[i][j] + redDWT[nr2 + i][j]);
					matr[i2 + 1][j] = (redDWT[i][j] - redDWT[nr2 + i][j]);
					matg[i2][j] = (greenDWT[i][j] + greenDWT[nr2 + i][j]);
					matg[i2 + 1][j] = (greenDWT[i][j] - greenDWT[nr2 + i][j]);
					matb[i2][j] = (blueDWT[i][j] + blueDWT[nr2 + i][j]);
					matb[i2 + 1][j] = (blueDWT[i][j] - blueDWT[nr2 + i][j]);
				}
			}

			// Copy to source:
			for (i = 0; i < nr; i++) {
				for (j = 0; j < nc; j++) {
					redDWT[i][j] = matr[i][j];
					greenDWT[i][j] = matg[i][j];
					blueDWT[i][j] = matb[i][j];
				}
			}
		}
		// nr=2;
		nr = height;
		nc = 2;

		for (k = 1; k < width; k *= 2, nc *= 2) {
			// Horizontal processing:
			nc2 = nc / 2;
			for (i = 0; i < nr; i++) {
				for (j = 0; j < nc2; j++) {
					j2 = j * 2;
					matr[i][j2] = (redDWT[i][j] + redDWT[i][nc2 + j]);
					matr[i][j2 + 1] = (redDWT[i][j] - redDWT[i][nc2 + j]);
					matg[i][j2] = (greenDWT[i][j] + greenDWT[i][nc2 + j]);
					matg[i][j2 + 1] = (greenDWT[i][j] - greenDWT[i][nc2 + j]);
					matb[i][j2] = (blueDWT[i][j] + blueDWT[i][nc2 + j]);
					matb[i][j2 + 1] = (blueDWT[i][j] - blueDWT[i][nc2 + j]);
				}
			}
			// Copy to source:
			for (i = 0; i < nr; i++) {
				for (j = 0; j < nc; j++) {
					redDWT[i][j] = matr[i][j];
					greenDWT[i][j] = matg[i][j];
					blueDWT[i][j] = matb[i][j];
				}
			}
		} // End of "for k ..."
			// merge R,G,B for the output image
		for (int c = 0; c < height; c++) {
			for (int d = 0; d < width; d++) {

				if (redDWT[c][d] < 0)
					redDWT[c][d] = 0;
				else if (redDWT[c][d] > 255)
					redDWT[c][d] = 255;

				if (greenDWT[c][d] < 0)
					greenDWT[c][d] = 0;
				else if (greenDWT[c][d] > 255)
					greenDWT[c][d] = 255;

				if (blueDWT[c][d] < 0)
					blueDWT[c][d] = 0;
				else if (blueDWT[c][d] > 255)
					blueDWT[c][d] = 255;

				int pix = (0xff000000) | ((((byte) redDWT[c][d]) & 0xff) << 16)
						| ((((byte) greenDWT[c][d]) & 0xff) << 8) | (((byte) blueDWT[c][d]) & 0xff);
				img.setRGB(c, d, pix);
			}
		}
		return img;
	}
}
