#include <iostream>
#include <algorithm>
#include <vector>
#include <fstream>

using namespace std;

bool check(int a[], int size)
{
	for (int i = 0; i<size; i++)
		if ((a[i] == i) || (a[(i + size - 1) % size] == i))
			return false;
	
	return true;
}

int main()
{
	ofstream outFile("acm-out.txt");
	int counter, *table;
	for (int i = 2; i <= 9; i++)
	{
		table = new int[i];
		for (int j = 0; j < i; j ++)
		{
			table[j] = j;
		}
		outFile << "case " << i;

		//for (int l = 0; l < i; l++)
		//{
		//	cout << table[l] << " ";
		//}
		//cout << endl;

		counter = 0;

		do{
			if (check(table, i)){
				++counter;
			}
		} while (next_permutation(table, table + i));
		outFile << ": " << counter << endl << endl;

		delete[] table;
	}

	//system("PAUSE");
	return 0;
}

