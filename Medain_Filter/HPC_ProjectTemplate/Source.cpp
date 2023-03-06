#include <iostream>
#include <mpi.h>
#include <math.h>
#include <stdlib.h>
#include<string.h>
#include<msclr\marshal_cppstd.h>
#include<algorithm>
#include <ctime>// include this header
#include <vector>
#pragma once

#using <mscorlib.dll>
#using <System.dll>
#using <System.Drawing.dll>
#using <System.Windows.Forms.dll>
using namespace std;
using namespace msclr::interop;


int* inputImage(int* w, int* h, System::String^ imagePath) //put the size of image in w & h
{
	int* input;
	int OriginalImageWidth, OriginalImageHeight;
	//*********************************************************Read Image and save it to local arrayss*************************
	//Read Image and save it to local arrayss
	System::Drawing::Bitmap BM(imagePath);
	OriginalImageWidth = BM.Width;
	OriginalImageHeight = BM.Height;
	*w = BM.Width;
	*h = BM.Height;
	int* Red = new int[BM.Height * BM.Width];
	int* Green = new int[BM.Height * BM.Width];
	int* Blue = new int[BM.Height * BM.Width];
	input = new int[BM.Height * BM.Width];
	for (int i = 0; i < BM.Height; i++)
	{
		for (int j = 0; j < BM.Width; j++)
		{
			System::Drawing::Color c = BM.GetPixel(j, i);
			Red[i * BM.Width + j] = c.R;
			Blue[i * BM.Width + j] = c.B;
			Green[i * BM.Width + j] = c.G;
			input[i * BM.Width + j] = ((c.R + c.B + c.G) / 3); //gray scale value equals the average of RGB values
		}
	}
	return input;
}



void createImage_squ(int start, int end, int* image, int width, int height, int window_size, int index)
{
	for (int k = start; k < end; k++)
	{
		//15 ->2,3
		int i = k / width; // rows
		int j = k % width; // columns
		vector <int> median_filter;
		int startx = i - (window_size / 2);
		int starty = j - (window_size / 2);

		int endx = (window_size / 2) + i;
		int endy = (window_size / 2) + j;

		for (int x = startx; x <= endx; x++)
		{
			for (int y = starty; y <= endy; y++)
			{
				if (x < 0 || y < 0 || x >= height || y >= width)
				{
					median_filter.push_back(0);
				}
				else {
					median_filter.push_back(image[x * width + y]);
				}
			}
		}
		sort(median_filter.begin(), median_filter.end());
		int median_index = (window_size * window_size) / 2;
		image[i * width + j] = median_filter[median_index];
	}
}



void drawing(int* image, int width, int height, int index) {
	System::Drawing::Bitmap MyNewImage(width, height);
	cout << "Drawing " << endl;
	for (int i = 0; i < MyNewImage.Height; i++)
	{
		for (int j = 0; j < MyNewImage.Width; j++)
		{
			if (image[i * width + j] < 0)
			{
				image[i * width + j] = 0;
			}
			if (image[i * width + j] > 255)
			{
				image[i * width + j] = 255;
			}
			System::Drawing::Color c = System::Drawing::Color::FromArgb(image[i * MyNewImage.Width + j], image[i * MyNewImage.Width + j], image[i * MyNewImage.Width + j]);
			MyNewImage.SetPixel(j, i, c);
		}
	}
	MyNewImage.Save("..//Data//Output//outputRes1" + index + ".png");
	cout << "result Image Saved " << index << endl;
}

// start = (Rank)*(P/proccesors)
// end = start + (P/Procc)
bool x = false;
int main()
{
	MPI_Init(NULL, NULL);
	int Rank, Size;
	MPI_Comm_rank(MPI_COMM_WORLD, &Rank);
	MPI_Comm_size(MPI_COMM_WORLD, &Size);
	int ImageWidth = 3, ImageHeight = 4;
	int start_s, stop_s, TotalTime = 0;


	System::String^ imagePath;
	std::string img;
	img = "..//Data//Input//5N.png";
	imagePath = marshal_as<System::String^>(img);
	int* imageData = inputImage(&ImageWidth, &ImageHeight, imagePath);

	int window_size;
	if (Rank == 0) {
		cout << "Enter Window Size" << endl;
		cin >> window_size;
	}
	MPI_Bcast(&window_size, 1, MPI_INT, 0, MPI_COMM_WORLD);
	start_s = clock();
	long long pixels = long long(ImageHeight) * long long(ImageWidth);
	long long pixels_per_processor = pixels / Size;

	int start = Rank * pixels_per_processor;
	int end = (Rank + 1) * pixels_per_processor;

	createImage_squ(start, end, imageData, ImageWidth, ImageHeight, window_size, 0);
	int* image = new int[pixels];

	//cout << cnt << endl;
	MPI_Gather(imageData + start, pixels_per_processor, MPI_INT, image, pixels_per_processor, MPI_INT, 0, MPI_COMM_WORLD);

	stop_s = clock();
	if (Rank == 0) {
		drawing(image, ImageWidth, ImageHeight, Rank);
		int Time = stop_s - start_s;
		cout << "Time: " << Time / (double)CLOCKS_PER_SEC;
	}
	MPI_Finalize();
	free(imageData);
	return 0;

}