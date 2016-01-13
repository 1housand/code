#include <iostream>
#include <cstdlib>
#include <fstream>
#include <sstream>
#include <queue>


using namespace std;
template <class T> class SafeMatrix{
private:
	int row_low, row_high, col_low, col_high;
	T** p;
public:

	// default constructor
	// allows for writing things like SafeMatrix a;
	SafeMatrix() : row_low(0), row_high(-1)
		, col_low(0), col_high(-1), p(nullptr){ };


	// 1 parameter constructor
	// SafeMatrix (10);
	SafeMatrix(int dim){
		if (dim <= 0){
			cerr << "constructor error in bounds definition" << endl;
			system("pause");
			exit(1);
		}
		row_low = 0; row_high = dim - 1;
		col_low = 0; col_high = dim - 1;
		p = new T*[dim];
		for (int i = 0; i < dim; i++)
			p[i] = new T[dim]{};
	};


	// 2 parameter constructor lets us write
	// SafeMatrix (10,20);
	SafeMatrix(int row, int col){
		if (row <= 0 || col <= 0){
			cerr << "constructor error in bounds definition" << endl;
			system("pause");
			exit(1);
		}
		row_low = 0; row_high = row - 1;
		col_low = 0; col_high = col - 1;
		p = new T*[row];
		for (int i = 0; i < row; i++)
			p[i] = new T[col]{};
	};


	// 2 parameter constructor lets us write
	// SafeMatrix (10,20);
	SafeMatrix(int row_low, int row_high, int col_low, int col_high)
		: row_low(row_low), row_high(row_high), col_low(col_low), col_high(col_high)
	{
		if ((row_high - row_low + 1) <= 0 || (col_high - col_low + 1) <= 0){
			cerr << "constructor error in bounds definition" << endl;
			exit(1);
		}
		p = new T*[(row_high - row_low) + 1];
		for (int i = 0; i <= row_high - row_low; i++)
			p[i] = new T[(col_high - col_low) + 1]{};
	};


	// copy constructor for pass by value and
	// initialization
	SafeMatrix(const SafeMatrix &s){
		int row = (s.row_high - s.row_low) + 1,
			col = (s.col_high - s.col_low) + 1;
		p = new T*[row];
		for (int i = 0; i < row; i++)
			p[i] = new T[col]{};
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++)
				p[i][j] = s.p[i][j];
		this->row_low = s.row_low; this->row_high = s.row_high;
		this->col_low = s.col_low; this->col_high = s.col_high;
	};


	// destructor
	~SafeMatrix(){
		for (int i = 0; i < (row_high - row_low) + 1; i++)
			delete[] p[i];
		delete[] p;
	};


	//overloaded [] lets us write
	//SafeMatrix x(10,20); x[1][2]= 100;
	class Brackets {
	private:
		T* p1;
		int col_low, col_high;
	public:
		Brackets(T* p2, int col_low, int col_high)
			: p1(p2), col_low(col_low), col_high(col_high) {}

		T& operator[](int i) {
			if (i<col_low || i>col_high) {
				cerr << "index " << i << " out of COLUMN range" << endl;
				exit(1);
			}
			return p1[i - col_low];
		}
	};

	Brackets operator[](int i) {
		if (i<row_low || i>row_high) {
			cerr << "index " << i << " out of ROW range" << endl;
			exit(1);
		}
		return Brackets(p[i - row_low], col_low, col_high);
	};


	// overloaded assignment lets us assign
	// one SafeMatrix to another
	SafeMatrix& operator=(const SafeMatrix & s){
		if (this != &s){
			delete[] p;
			int row = (s.row_high - s.row_low) + 1,
				col = (s.col_high - s.col_low) + 1;
			p = new T*[row];
			for (int i = 0; i < row; i++)
				p[i] = new T[col]{};
			for (int i = 0; i < row; i++)
				for (int j = 0; j < col; j++)
					p[i][j] = s.p[i][j];
			this->row_low = s.row_low; this->row_high = s.row_high;
			this->col_low = s.col_low; this->col_high = s.col_high;
		}
		return *this;
	};

	SafeMatrix* operator*(const SafeMatrix & s){
		int row1 = (this->row_high - this->row_low) + 1,
			col1 = (this->col_high - this->col_low) + 1,
			row2 = (s.row_high - s.row_low) + 1,
			col2 = (s.col_high - s.col_low) + 1;
		if (col1 != row2){
			cerr << "Error: these 2 matrices' do not match for matrix multiplication" << endl;
			return this;
		}
		SafeMatrix *product = new SafeMatrix(row1, col2);
		for (int i = 0; i < row1; i++){
			for (int j = 0; j < col2; j++){
				for (int k = 0; k < row2; k++){
					(*product)[i][j] += ((*this)[i][k] * s.p[k][j]);
				}
			}
		}
		return product;
	};

	// overloads << so we can directly print SafeMatrix
	template <class U>
	friend ostream& operator<<(ostream& os, const SafeMatrix<U> s);
};

template <class U>
ostream& operator<<(ostream& os, SafeMatrix<U> s){
	int row = (s.row_high - s.row_low) + 1,
		col = (s.col_high - s.col_low) + 1;
	for (int i = 0; i < row; i++){
		for (int j = 0; j < col; j++){
			os << s.p[i][j] << " ";
		}
		os << endl;
	}
	return os;
};

