#include "matrix.cpp"
#include <iostream>
#include <cstdlib>
#include <fstream>
#include <sstream>

using namespace std;
class VNT{
private:
	int m, n, size = 0;
	SafeMatrix<int> SM;
public:
	//default constructor
	VNT() : m(0), n(0) {}

	VNT(int m, int n) : m(m), n(n)
	{
		SM = SafeMatrix<int>(m, n);
		for (int i = 0; i < m; i++)
		{
			for (int j = 0; j < n; j++)
			{
				SM[i][j] = INT_MAX;
			}
		}
	}

	int add(int i)
	{
		if (SM[m - 1][n - 1] == INT_MAX)
		{
			SM[m - 1][n - 1] = i;
			veryNice(1);
			return 0;
		}
		else
		{
			return 1;
		}
	}

	int getMin()
	{
		int i = SM[0][0];
		SM[0][0] = INT_MAX;
		veryNice(0);
		return i;
	}

	int* sort(int k[], int size)
	{
		if (size > m*n)
		{
			k[0] = INT_MAX;
			return k;
		}
		for (int i = 0; i < size; i++)
		{
			add(k[i]);
		}
		cout << SM << endl;
		for (int i = 0; i < size; i++)
		{
			k[i] = getMin();
		}
		//system("pause");
		return k;
	}

	bool find(int i)
	{
		int row = m, col = 0;
		while (row>0)
		{
			row--;
			if (SM[row][col]<=i)
			{
				break;
			}
		}
		while (col < n)
		{
			if (SM[row][col] == i)
			{
				return true;
			}
			if (row-1>=0 && SM[row-1][col] == i)
			{
				return true;
			}
			if (row+1<m && SM[row+1][col] == i)
			{
				return true;
			}
			col++;
		}
		return false;
	}

	void veryNice(bool flag)
	{
		if (flag)
		{
			//sort after adding a number
			int row, col, markerRow = row = m-1, markerCol = col = n-1, compare = INT_MAX, temp;
			while ((row > 0 || col > 0) && SM[row][col] < compare)
			{
				temp = SM[row][col];
				SM[row][col] = SM[markerRow][markerCol];
				SM[markerRow][markerCol] = temp;
				row = markerRow;
				col = markerCol;
				if (row - 1 >= 0 && SM[row][col] < SM[row-1][col])
				{
					markerRow = row - 1;
					markerCol = col;
				}
				if (col - 1 >= 0 && SM[markerRow][markerCol] < SM[row][col - 1])
				{
					markerRow = row;
					markerCol = col - 1;
				}
				compare = SM[markerRow][markerCol];
			}
		}
		else
		{
			//sort after extracting a number
			int row = m, col = n, markerRow = 0, markerCol = 0, temp;
			while (markerRow != row || markerCol != col)
			{
				row = markerRow;
				col = markerCol;
				if (row + 1 < m && SM[row][col] > SM[row + 1][col])
				{
					markerRow = row + 1;
					markerCol = col;
				}
				if (col + 1 < n && SM[markerRow][markerCol] > SM[row][col + 1])
				{
					markerRow = row;
					markerCol = col + 1;
				}
				if (markerRow != row || markerCol != col)
				{
					temp = SM[row][col];
					SM[row][col] = SM[markerRow][markerCol];
					SM[markerRow][markerCol] = temp;
				}
			}
		}
	}

	friend ostream& operator<<(ostream & os, const VNT & rhs);
};

ostream& operator<<(ostream & os, const VNT & rhs)
{
	os << rhs.SM;
	return os;
};

int main(int argc, char* argv[])
{
	if (argc < 3) {
		cerr << "Error: Missing file parameters" << endl;
		//system("pause");
		return 1;
	}
	string filename = argv[1];
	string ofile = argv[2];
	ofstream outFile(ofile);

	ifstream infile(filename);
	if (!infile){
		cerr << "File not found." << filename << endl;
		//system("pause");
		return 1;
	}

	VNT *A;
	int *array;
	string line;
	int m, n, size;
	while (getline(infile, line))
	{
		istringstream iss(line);
		iss >> m;
		iss >> n;
		A = new VNT(m, n);
		iss >> size;
		array = new int[size];

		getline(infile, line);
		istringstream iss2(line);
		
		for (int i = 0; i < size; i++)
		{
			iss2 >> array[i];
		}

		outFile << "Original array: ";
		for (int i = 0; i < size; i++)
		{
			outFile << array[i] << " ";
		}
		outFile << endl;
		array = A->sort(array, size);
		if (array[0] == INT_MAX)
		{
			outFile << "IMPOSSIBLE";
		}
		else
		{
			outFile << "Sorted array: ";
			for (int i = 0; i < size; i++)
			{
				outFile << array[i] << " ";
			}
		}
		outFile << endl << endl;
		delete A;
		delete[] array;
	}
	infile.close();

	//system("pause");
	exit(0);
}