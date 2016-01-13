#include <iostream>
#include <fstream>
#include <sstream>
#include <map>

using namespace std;

ostream& operator<<(ostream &os, map < string, map<int, int> > &m)
{
	for (map < string, map<int, int> >::iterator i = m.begin(); i != m.end(); ++i)
	{
		os << i->first << ": ";
		for (map<int, int>::iterator j = i->second.begin(); j != i->second.end(); ++j)
		{
			if (j == i->second.begin())
			{
				os << j->first;
			}
			else
			{
				os << ", " << j->first;
			}
			if (j->second > 1)
			{
				os << "(" << j->second << ")";
			}
		}
		//print(os, i->second);
		os << endl;
	}

	return os;
}


int main(int argc, char* argv[])
{
	if (argc < 3) {
		cerr << "Error: Missing file parameters" << endl;
		//system("pause");
		return 1;
	}
	ifstream infile(argv[1]);
	ofstream outFile(argv[2]);
	
	string line, word;
	int lineCounter = 0;
	map<string, map<int, int>> wordCount;
	map<string, map<int, int>>::iterator wordIt = wordCount.begin();
	map<int, int>::iterator lineIt;

	while (getline(infile, line))
	{
		//outFile << "line: " << line << " " << endl;
		istringstream iss(line);
		++lineCounter;
		while (iss >> word)
		{
			//outFile << "word: " << word << endl;
			wordIt = wordCount.find(word);
			if (wordIt == wordCount.end())
			{
				map<int, int> lineHits;
				lineHits[lineCounter] = 1;
				wordCount[word] = lineHits;
			}
			else
			{
				lineIt = wordIt->second.find(lineCounter);
				if (lineIt == wordIt->second.end())
				{
					wordIt->second[lineCounter] = 1;
				}
				else
				{
					lineIt->second++;
				}
			}
		}
	}
	
	outFile<<wordCount;
	infile.close();

	//system("pause");
}