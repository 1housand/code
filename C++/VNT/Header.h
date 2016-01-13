#include <iostream>
#include <cstdlib>
#include <fstream>
#include <sstream>

template <class T> class SafeMatrix
{
	private:
		int row_low, row_high, col_low, col_high;
		T** p;
	public:
		// default constructor
		SafeMatrix() : row_low(0), row_high(-1), col_low(0), col_high(-1), p(nullptr);
		// 1 parameter constructor
		SafeMatrix(int dim);
		// 2 parameter constructor lets us write
		SafeMatrix(int row, int col);
		// 2 parameter constructor lets us write
		SafeMatrix(int row_low, int row_high, int col_low, int col_high);
		// copy constructor for pass by value and
		SafeMatrix(const SafeMatrix &s);
		// destructor
		~SafeMatrix();

		//Derived class
		class Brackets {
		private:
			T* p1;
			int col_low, col_high;
		public:
			//constructors
			Brackets(T* p2, int col_low, int col_high);
			//operators
			T& operator[](int i);
		};

		// operators
		Brackets operator[](int i);
		SafeMatrix& operator=(const SafeMatrix & s);
		SafeMatrix* operator*(const SafeMatrix & s);

		// friends
		template <class U>
		friend ostream& operator<<(ostream& os, const SafeMatrix<U> s);
};
	template <class U>
	ostream& operator<<(ostream& os, SafeMatrix<U> s);

	int read(queue<int> &q, string filename);


class VNT 
{
	int m, n;
	SafeMatrix<int>** p;
public:
	// constructors-destructor
	VNT();              // default constructor
	VNT(int m, int n);  // copy constructor
	~VNT();             // destructor

	// other methods as functions
	SafeMatrix<int>*& pointer();

	// operators
	VNT& operator=(const VNT &rhs);

	// friends
	friend ostream& operator<<(ostream & os, const VNT & rhs);
};