import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;

public class imageReader_working_full {

	static int X = 512;
	static int Y = 512;
	static int Ln = X * Y;
	static int N = 8;
	static int w = X / N;
	static int h = Y / N;
	static int ln = w * h;
	// matrix for storing input image read, green and blue pixel values
	static double red[][] = new double[512][512];
	static double green[][] = new double[512][512];
	static double blue[][] = new double[512][512];
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
	static double redDWT[][] = new double[512][512];
	static double greenDWT[][] = new double[512][512];
	static double blueDWT[][] = new double[512][512];
	static double[][] matr = new double[512][512];
	static double[][] matr2 = new double[512][512];
	static double[][] matg = new double[512][512];
	static double[][] matg2 = new double[512][512];
	static double[][] matb = new double[512][512];
	static double[][] matb2 = new double[512][512];

	public static void main(String[] args) {

		String fileName = args[0];
		int quantNo = Integer.parseInt(args[1]); // total number of co-ordinates
													// needed to display the
													// transformed image
		int height = 512;// height and width of the input image
		int width = 512;

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
			// call DCT to quantize the image
			DCTDWTImage(quantNo);


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// // Use a panel and label to display the image
		//  JPanel panel = new JPanel();
		//  panel.add(new JLabel(new ImageIcon(img)));
		//
		//  JFrame frame = new JFrame("Original images");
		//
		//  frame.getContentPane().add(panel);
		//  frame.pack();
		//  frame.setVisible(true);
		//  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	public static void DCTDWTImage(int quantNo)
	{
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
		for (int a = 0; a < 512; a += 8) {
			// Get 8 lines of image
			for (int b = 0; b < 512; b += 8) {
				// Get block for FDCT
				for (int c = 0; c < N; c++) {
					for (int d = 0; d < N; d++) {
						blocksred[bkk][c][d] =  red[(a + c)][b + d];
						blocksgreen[bkk][c][d] = green[(a + c)][b + d];
						blocksblue[bkk][c][d] = blue[(a + c)][b + d] ;
					}
				}
				bkk++;
			}
		}
		// totally there are 4096 blocks with 8 rows and 8 columns

		// DCT transformation
		// these are 3 matrix to store R, G, B values after DCT conversion.
		double[] coiff = new double[N];
		for(int co=1;co<N;co++)
		{
			coiff[co]=1;
		}
		coiff[0]=(1/Math.sqrt(2));

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
					Cr[ck][k1][k2] = ((coiff[k1]*coiff[k2])/4) * Cr[ck][k1][k2];
					Cg[ck][k1][k2] = ((coiff[k1]*coiff[k2])/4)* Cg[ck][k1][k2];
					Cb[ck][k1][k2] = ((coiff[k1]*coiff[k2])/4) * Cb[ck][k1][k2];
				}
			}
		}
	}

	// DWT FUNCTION
	public static void DWTImage() {

		int h = 512;
		int w = 512;
		int Ln = w * h;
		int nr = h;
		int nc = w;
		int i1 = 0, i2 = 0, j1 = 0, j2 = 0;
		int nr2 = 0, nc2 = 0;
		int i = 0, j = 0, k = 0;

		// DWT start
		for (k = 512; k > 1; k /= 2, nr /= 2, nc /= 2) {
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
			// vertical processing:
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
	public static void IDCTIDWTImage(int quantNo)
	{
		BufferedImage IDCTimg = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);
		BufferedImage IDWTimg = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);

		// quantization - using number of coefficients given as paramenter to
		// display the image
		if (quantNo != -1) {
			quantizeDCT(quantNo);
			IDCTimg = IDCT();
			quantizeDWT(quantNo);
			IDWTimg = IDWT();
			// display the quantized image

			ImageIcon DCTicon = new ImageIcon(IDCTimg);
			ImageIcon DWTicon = new ImageIcon(IDWTimg);
			JLabel DCTlabel = new JLabel(DCTicon);
			JLabel DWTlabel = new JLabel(DWTicon);
			JPanel panel = new JPanel();
			panel.add(DCTlabel);
			panel.add(DWTlabel);
			JFrame frame = new JFrame("DCT(left)-DWT(right): Number of coefficients used- "+quantNo);
			frame.getContentPane().add(panel);
			frame.pack();
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} else {
			JFrame frame = new JFrame();

			for (int xx = 0; xx <= 512 * 512; xx += 4096) {
				int itr=1;
				quantizeDCT(4096 + xx);
				IDCTimg = IDCT();
				quantizeDWT(4096 + xx);
				IDWTimg = IDWT();
				JPanel panel = new JPanel();
				ImageIcon DCTicon = new ImageIcon(IDCTimg);
				ImageIcon DWTicon = new ImageIcon(IDWTimg);
				JLabel DCTlabel = new JLabel(DCTicon);
				JLabel DWTlabel = new JLabel(DWTicon);
				panel.add(DCTlabel);
				panel.add(DWTlabel);
				frame.getContentPane().remove(panel);
				frame.setTitle("DCT(left) - DWT(right): Progressive image with coefficient: "+(xx+4096));
				frame.add(panel);
				frame.getContentPane().invalidate();
				frame.getContentPane().validate();
				frame.pack();
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
			System.out.println("Progressive Analysis of images completed");
		}
	}
	// quantize DCT function
	public static void quantizeDCT(int quantNo) {
		int pixPerBlock = quantNo / 4096;
		double print[][] = new double[512][512];
		int pp=0;
		// traversing the 8 x 8 matrix in zigzag manner
		for (int ck = 0; ck < ln; ck++) {
			int no = 0;pp=0;
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

		public static void quantizeDWT(int quantNo) {
			int no = 0;
			for (int line = 1; line <= (512 + 512 - 1); line++) {
				int start_col = Math.max(0, line - 512);
				int count = Math.min(Math.min(line, (512 - start_col)), 512);
				if (count % 2 == 1) {
					for (int ms = 0; ms < count; ms++) {
						if (no < quantNo) {
							matr2[Math.min(512, line) - ms - 1][start_col
									+ ms] = red[Math.min(512, line) - ms - 1][start_col + ms];
							matg2[Math.min(512, line) - ms - 1][start_col
									+ ms] = green[Math.min(512, line) - ms - 1][start_col + ms];
							matb2[Math.min(512, line) - ms - 1][start_col
									+ ms] = blue[Math.min(512, line) - ms - 1][start_col + ms];
							no++;
						} else {
							matr2[Math.min(512, line) - ms - 1][start_col + ms] = 0.0;
							matg2[Math.min(512, line) - ms - 1][start_col + ms] = 0.0;
							matb2[Math.min(512, line) - ms - 1][start_col + ms] = 0.0;
							no++;
						}
					}
				} else {
					for (int ms = 0; ms < count; ms++) {
						if (no < quantNo) {
							matr2[start_col + ms][Math.min(512, line) - ms - 1] = red[start_col + ms][Math.min(512, line)
									- ms - 1];
							matg2[start_col + ms][Math.min(512, line) - ms - 1] = green[start_col + ms][Math.min(512, line)
									- ms - 1];
							matb2[start_col + ms][Math.min(512, line) - ms - 1] = blue[start_col + ms][Math.min(512, line)
									- ms - 1];
							no++;
						} else {
							matr2[start_col + ms][Math.min(512, line) - ms - 1] = 0.0;
							matg2[start_col + ms][Math.min(512, line) - ms - 1] = 0.0;
							matb2[start_col + ms][Math.min(512, line) - ms - 1] = 0.0;
							no++;
						}
					}
				}
			}
			for (int i = 0; i < 512; i++) {
				for (int j = 0; j < 512; j++) {
					redDWT[i][j] = matr2[i][j];
					greenDWT[i][j] = matg2[i][j];
					blueDWT[i][j] = matb2[i][j];
				}
			}
		}

	public static BufferedImage IDCT() {
		BufferedImage img = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);

		double[] w2 = new double[N];
		w2[0] = (1/Math.sqrt(2));
		for (int i = 1; i < N; i++) {
			w2[i] = 1;
		}
		// matrix to store the IDCT converted values

		for (int ck = 0; ck < ln; ck++) {
			for (int n1 = 0; n1 < N; n1++) {
				for (int n2 = 0; n2 < N; n2++) {
					for (int k1 = 0; k1 < N; k1++) {
						for (int k2 = 0; k2 < N; k2++) {
							double temp = Math.cos(((2 * n1 + 1) * k1 * Math.PI) / (16))
									* Math.cos(((2 * n2 + 1) * k2 * Math.PI) / (16));

								iblocksr[ck][n1][n2]+=(w2[k1]*w2[k2]*qr[ck][k1][k2]*temp);
								iblocksg[ck][n1][n2]+=(w2[k1]*w2[k2]*qg[ck][k1][k2]*temp);
								iblocksb[ck][n1][n2]+=(w2[k1]*w2[k2]*qb[ck][k1][k2]*temp);
						}
					}
					iblocksr[ck][n1][n2] = iblocksr[ck][n1][n2] / (4.0);
					iblocksg[ck][n1][n2] = iblocksg[ck][n1][n2] / (4.0);
					iblocksb[ck][n1][n2] = iblocksb[ck][n1][n2] / (4.0);
				}
			}
		}

		// convert image back to color
		int bkk = 0;
		for (int a = 0; a < (h * N); a += N) {
			for (int b = 0; b < (w * N); b += N) {
				for (int c = 0; c < N; c++) {
					for (int d = 0; d < N; d++) {

						if (iblocksr[bkk][c][d] < 0)
							iblocksr[bkk][c][d] =  0.0;
						else
							if(iblocksr[bkk][c][d] > 255)
								iblocksr[bkk][c][d]=255.0;

						if (iblocksg[bkk][c][d] < 0)
							iblocksg[bkk][c][d] =  0.0;
						else
							if(iblocksg[bkk][c][d] > 255)
								iblocksg[bkk][c][d]=255.0;

						if (iblocksb[bkk][c][d] < 0)
							iblocksb[bkk][c][d] =  0;
						else
							if(iblocksb[bkk][c][d] > 255)
								iblocksb[bkk][c][d]=255;

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

	public static BufferedImage IDWT() {
		BufferedImage img = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);

		int level, nrows, ncols;
		int i, j, i2, j2, k, nr, nc, nr2, nc2, tmp;
		nrows = 512 * 2;
		ncols = 512 * 2;

		tmp = (int) Math.pow(2, 10 - 1);
		nr = (nrows) / tmp;

		nc = (ncols) / tmp;

		for (k = 1; k < 512; k *= 2, nr *= 2, nc *= 2) {
			// Vertical processing:
			nr2 = nr / 2;
			for (i = 0; i < nr2; i++) {
				for (j = 0; j < nc; j++) {
					i2 = i * 2;
					matr[i2][j] = (redDWT[i][j] + redDWT[nr2 + i][j]);
					matr[i2 + 1][j] = (redDWT[i][j] - red[nr2 + i][j]);
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
			// convert back image
		for (int c = 0; c < 512; c++) {
			for (int d = 0; d < 512; d++) {

				if (redDWT[c][d] < 0 )
					redDWT[c][d] = 0;
				else
					if(redDWT[c][d] > 255)
						redDWT[c][d] = 255;

				if (greenDWT[c][d] < 0 )
					greenDWT[c][d] = 0;
				else
					if(greenDWT[c][d] > 255)
						greenDWT[c][d] = 255;

				if (blueDWT[c][d] < 0 )
					blueDWT[c][d] = 0;
				else
					if(blueDWT[c][d] > 255)
						blueDWT[c][d] = 255;

				int pix = (0xff000000) | ((((byte) redDWT[c][d]) & 0xff) << 16) | ((((byte) greenDWT[c][d]) & 0xff) << 8)
						| (((byte) blueDWT[c][d]) & 0xff);
				img.setRGB(c, d, pix);
			}
		}
		return img;
	}
}
