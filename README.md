# DCT_DWT_multimedia
----------------------------------------------------------
Instructions for compiling and running the program

Program Name: imageReader

To compile the program type “javac imageReader.java” in command prompt

To run the program type “java imageReader <input_img_name> <number_of_coefficients>”

Here:

➢ input_img_name is the name of the input image file

➢ number_of_coefficients is the integral number that represents the number of coefficients to use for decoding.
o 262144, 131072, 16384
o For progressive analysis, set number_of_coefficients = -1

For example: To perform DCT and DWT on input image (Lenna.rgb) with 131072 coefficients use the following command:

java imageReader Lenna.rgb 131072

Output:

Program will produce 2 images one for DCT and one for DWT in the same panel. Left

panel in the frame is DCT and right panel in the frame is DWT image.

For progressive analysis, new images get replaced on the same JFrame, and there is a delay of 5 milliseconds.
