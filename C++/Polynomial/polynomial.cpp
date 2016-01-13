#include <cstdlib>
#include <iostream>
#include <fstream>
#include <sstream>
#include <queue>

using namespace std;
template<class T>
class node{
private:
	T Data;
	node* Link;
public:
	node(){ Link = 0; }
	node(T i){ Data = i; Link = 0; }
	T& data(){ return Data; }
	node* &link(){ return Link; }
};


class Polynomial{
private:
	node<int>* Head;
public:
	Polynomial(){ Head = 0; }

	Polynomial(string equation)
	{
		int value;
		istringstream iss(equation);
		iss >> value;
		Head = new node<int>(value);
		node<int>* ptr = Head;
		while (iss >> value){
			ptr->link() = new node<int>(value);
			ptr = ptr->link();
		}
		ptr->link() = 0;
	}

	// copy constructor
	Polynomial(const Polynomial &src)
	{
		node<int>* ptr1 = this->Head;
		node<int>* ptr2 = src.Head;
		this->Head = new node<int>(src.Head->data()); 
		while (ptr2->link()){
			ptr1->link() = new node<int>(ptr2->link()->data());
			ptr1 = ptr1->link();
			ptr2 = ptr2->link();
		}
	}

	Polynomial& operator+(Polynomial &p){
		node<int> *currentt = this->head(), *currentp = p.head();
		string equation;
		stringstream is;

		//if one of the polynomials don't have any numbers
		if (p.head()->data() == 0)
		{
			return *this;
		}
		if (this->head()->data() == 0)
		{
			return p;
		}

		//Polynomial sum();
		while (currentt && currentp)
		{
			if (currentt->link()->data() == currentp->link()->data()) {
				is << ((currentt->data() + currentp->data())) << " " 
					<< currentt->link()->data()	<< " ";
				currentt = currentt->link()->link();
				currentp = currentp->link()->link();
			}
			else 
			{
				if (currentt->link()->data() > currentp->link()->data())
				{
					is << currentt->data() << " " 
						<< currentt->link()->data() << " ";
					currentt = currentt->link()->link();
				}
				else
				{
					is << currentp->data() << " " 
						<< currentp->link()->data() << " ";
					currentp = currentp->link()->link();
				}
			}
		}
		//add the rest of the nomials to the end
		while (currentt)
		{
			is << currentt->data() << " "
				<< currentt->link()->data() << " ";
			currentt = currentt->link()->link();
		}
		while (currentp)
		{
			is << currentp->data() << " " 
				<< currentp->link()->data() << " ";
			currentp = currentp->link()->link();
		}
		equation = is.str();
        Polynomial *poly = new Polynomial(equation);
        //if only 1 term and base 0 is zero, make exponent 0 also
        if (!poly->head()->link()->link() && poly->head()->data() == 0)
        {
            poly->head()->link()->data() = 0;
        }
        else
        {
			//get rid of any coefficients with 0
            node<int> *current = poly->head()->link()->link(), *trail = poly->head()->link();
            while (current)
            {
                if (current->data() == 0)
                {
                    trail->link() = trail->link()->link()->link();
                }
                else
                {
                    trail = trail->link()->link();
                }
                current = current->link()->link();
            }
        }
		return *poly;
	}

	Polynomial& operator-(Polynomial &p){
		node<int> *currentt = this->head(), *currentp = p.head();
		string equation;
		stringstream is;
			while (currentt && currentp)
			{
				if (currentt->link()->data() == currentp->link()->data()) {
					is << ((currentt->data() - currentp->data())) << " "
						<< currentt->link()->data() << " ";
					currentt = currentt->link()->link();
					currentp = currentp->link()->link();
				}
				else
				{
					if (currentt->link()->data() > currentp->link()->data())
					{
						is << currentt->data() << " "
							<< currentt->link()->data() << " ";
						currentt = currentt->link()->link();
					}
					else
					{
						is << 0 - currentp->data() << " "
							<< currentp->link()->data() << " ";
						currentp = currentp->link()->link();
					}
				}
			}
		while (currentt)
		{
			is << currentt->data() << " "
				<< currentt->link()->data() << " ";
			currentt = currentt->link()->link();
		}
		while (currentp)
		{
			is << 0 - currentp->data() << " "
				<< currentp->link()->data() << " ";
			currentp = currentp->link()->link();
		}
		equation = is.str();
		Polynomial *poly = new Polynomial(equation);
		//if only 1 term and base 0 is zero, make exponent 0 also
		if (poly->head()->data() == 0)
		{
			if (!poly->head()->link()->link())
			{
				poly->head()->link()->data() = 0;
			}
			else //delete if head is 0
			{
				poly->head() = poly->head()->link()->link();
			}
		}
		else
		{
			//get rid of any coefficients with 0
			node<int> *current = poly->head()->link()->link(), *trail = poly->head()->link();
			while (current)
			{
				if (current->data() == 0)
				{
					trail->link() = trail->link()->link()->link();
				}
				else
				{
					trail = trail->link()->link();
				}
				current = current->link()->link();
			}
		}
		return *poly;
	}

	Polynomial& operator*(Polynomial &p){
		node<int> *currentt = this->head(), *currentp = p.head();
		string equation;
		int count = 0;
		//count the number of nomials in p
		while (currentp)
		{
			count++;
			currentp = currentp->link()->link();
		}
		stringstream *is = new stringstream[count];
		currentp = p.head();
		for (int i = 0; i < count; i++)
		{
			while (currentt)
			{
				is[i] << currentp->data()*currentt->data() << " "
					<< currentp->link()->data()+currentt->link()->data() << " ";
				currentt = currentt->link()->link();
			}
			currentt = this->head();
			currentp = currentp->link()->link();
		}
		Polynomial *poly1 = new Polynomial(), *poly2;
		if (count > 1)
		{
			equation = is[0].str();
			poly1 = new Polynomial(equation);
			canonical(*poly1);
			equation = is[1].str();
			poly2 = new Polynomial(equation);
			canonical(*poly2);
			*poly1 = (*poly1 + *poly2);
			if (count > 2)
			{
				for (int i = 2; i < count; i++)
				{
					equation = is[i].str();
					poly2 = new Polynomial(equation);
					canonical(*poly2);
					*poly1 = *poly1 + *poly2;
				}
			}
		}
		else
		{
			equation = is[0].str();
			poly1 = new Polynomial(equation);
		}
		//if only 1 term and base 0 is zero, make exponent 0 also
		if (!poly1->head()->link()->link() && poly1->head()->data() == 0)
		{
			poly1->head()->link()->data() = 0;
		}
		return *poly1;
	}

	Polynomial& operator=(const Polynomial &src)
	{
		if (this != &src)
		{
			int i = src.Head->data();
			this->Head = new node<int>(i);
			node<int> *ptr1 = this->Head;
			node<int> *ptr2 = src.Head;
			while (ptr2->link()){
				ptr1->link() = new node<int>(ptr2->link()->data());
				ptr1 = ptr1->link();
				ptr2 = ptr2->link();
			}
		}
		return *this;
	}

	~Polynomial()
	{
		//fix to delete every node.
        delete Head;
	}

	node<int>*& head(){ return Head; }

	friend ostream& operator<<(ostream& os, const Polynomial & src);
	
	Polynomial& canonical(Polynomial & p)
	{
		//sort the polynomial by exponents
		node<int> *current = p.head(), *trail, *temp;
		int count = 0, i;
		while (current)
		{
			count++;
			current = current->link();
		}
		for (i = 0; i <= count; i += 2)
		{
			current = trail = p.head();
			while (current->link()->link())
			{
				if (current->link()->link()->link()->data() > current->link()->data())
				{
					temp = current->link()->link();
					current->link()->link() = current->link()->link()->link()->link();
					temp->link()->link() = current;

					if (current == p.head())
						p.head() = trail = temp;
					else
						trail->link()->link() = temp;
					current = temp;
				}
				trail = current;
				current = current->link()->link();
			}
		}

		//add up like exponents
		current = p.head();
		while (current->link()->link())
		{
			if (current->link()->link()->link()->data() == current->link()->data()) {
				current->data() = current->link()->link()->data() + current->data();
				current->link()->link() = current->link()->link()->link()->link();
			}
			else {
				current = current->link()->link();
			}
		}

		//if only 1 term and base 0 is zero, make exponent 0 also
		if (!p.head()->link()->link() && p.head()->data() == 0)
		{
			p.head()->link()->data() = 0;
		}
		else
		{
			current = p.head()->link()->link(), trail = p.head()->link();
			while (current)
			{
				if (current->data() == 0)
				{
					trail->link() = trail->link()->link()->link();
				}
				else
				{
					trail = trail->link()->link();
				}
				current = current->link()->link();
				//cout << "while loop" << endl;
			}
		}
		return p;
	}
};


ostream& operator<<(ostream& os, const Polynomial & src){
	node<int>* ptr = src.Head;
	while (ptr){
		os << ptr->data() << " ";
		ptr = ptr->link();
	}
	return os;
};


int main(int argc, char* argv[])
{
	if (argc < 3) {
		cerr << "Error: Missing file parameters" << endl;
		system("pause");
		return 0;
	}
	string filename = argv[1];
	string ofile = argv[2];
	/*string filename = "polynomial-in.txt";
	string ofile = "polynomial-out.txt";*/

	ifstream infile(filename);
	if (!infile){
		cerr << "Error opening file " << filename << endl;
		//system("pause");
		exit(1);
	}

	int counter1 = 1, counter2 = 2;
	string line;
	ofstream outFile(ofile);
	getline(infile, line);
	Polynomial *poly1 = new Polynomial(line);
	getline(infile, line);
	Polynomial *poly2 = new Polynomial(line);
	while (!infile.eof())
	{

		outFile << "Original " << counter1 << ": " << *poly1 << endl;
		outFile << "Original " << counter2 << ": " << *poly2 << endl;
		poly1->canonical(*poly1);
		poly2->canonical(*poly2);
		outFile << "Canonical " << counter1 << ": " << *poly1 << endl;
		outFile << "Canonical " << counter2 << ": " << *poly2 << endl;
		outFile << "Sum: " << *poly1 + *poly2 << endl;
		outFile << "Difference: " << *poly1 - *poly2 << endl;
		outFile << "Product: " << *poly1**poly2 << endl;
		outFile << endl;

		counter1 += 2;
		counter2 += 2;

		getline(infile, line);
		poly1 = new Polynomial(line);
		getline(infile, line);
		poly2 = new Polynomial(line);
	}

	//system("PAUSE");
	//return EXIT_SUCCESS;
}
