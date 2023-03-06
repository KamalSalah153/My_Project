#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <queue>
#include <map>
#include <set>
using namespace std;

int n;
int dx[4] = { 0,0,1,-1 };
int dy[4] = { 1,-1,0,0 };


map<string, string>route;


struct state {
	int** Matrix;
	int state_depth, heuristic_value;
	pair<int, int>p;
};

class Compare
{
public:
	bool operator() (state x, state y)
	{
		int sumx = (x.state_depth + x.heuristic_value);
		int sumy = (y.state_depth + y.heuristic_value);
		//if (sumx == sumy)return x.heuristic_value > y.heuristic_value;
		return  sumx > sumy ;
	}
};

void Convert2DTo1D(int* arr[],int*Matrix) {
	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < n; j++)
		{
			Matrix[i * n + j] = arr[i][j];
		}
	}
}

bool IsSolvable(int *arr[]) {
	// O(N^2) where N is the puzzle size 
	int* Matrix = new int[n * n];
	Convert2DTo1D( arr, Matrix);
	int inversions = 0,Blankindex = 0;
	for (int i = 0; i < n*n; i++) {
		if (Matrix[i] == 0) {
			Blankindex = i;
			Blankindex /= n;
			Blankindex=n-Blankindex;
			continue;
		}
		for (int j = i+1; j < n*n; j++) {
			if (Matrix[i] > Matrix[j]&&Matrix[j]!=0) {
				inversions++;
			}
		}
	}
	if ((n % 2 == 1 && inversions % 2 == 1) ||
	(n % 2 == 0 && Blankindex % 2 == 0 && inversions % 2 == 0) ||
	(n % 2 == 0 && Blankindex % 2 == 1 && inversions % 2 == 1)) {
		return false;
	}
	return true;
}

int Hamming(int *arr[]) {
	//O(N) where N is puzzle size
	int* Matrix = new int[n * n];
	Convert2DTo1D( arr, Matrix);
	int cnt = 0;
	for (int i = 0; i < n * n; i++) {
		if (Matrix[i] == 0)continue;
		if (Matrix[i] != i + 1)cnt++;
	}
	return cnt;
}

int Manhattan( int* arr[]) {

	//O(N) where N is puzzle size
	int cnt = 0;
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			if (arr[i][j] == 0)continue;
			cnt += abs(i-((arr[i][j]-1)/n)) + abs(j- ((arr[i][j]-1) % n));
		}
	}
	return cnt;
}

pair<int, int> FindZero(int *arr[]) {
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			if (arr[i][j] == 0)return{ i,j };
		}
	}
}

bool isValid(int x,int y) {
	return (x >= 0 && x < n&& y >= 0 && y < n);
}

void copy2DArray(int* arr[], int* Matrix[]) {
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			Matrix[i][j] = arr[i][j];
		}
	}
}

void PrintPath(string endID) {
	vector<string>path;
	while (endID != "") {
	//	cout << endID << endl;
		path.push_back(endID);
		endID = route[endID];
	}
	reverse(path.begin(), path.end());
	int cnt = 0;
	for (auto p : path) {
		for (int i = 0; i < p.size(); i++) {
			if (p[i] == 'n')cout << '\n';
			else cout << p[i];
		}
		cnt++;
		if (cnt!=path.size())cout << "  || " << endl << "  \\/" << endl;
	}
	cout << endl;
}

string getMatrixID(int*arr[]) {
	string ID = "";
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			ID += to_string(arr[i][j])+" ";
		}
		ID += 'n';
	}
	return ID;
}

void Astar(state root,int choice) {
	priority_queue<state, vector<state>, Compare>pq;
	pq.push(root);
	
	if (n == 3) {
		string rootID = getMatrixID(root.Matrix);
		route[rootID] = "";
	}
	int cnt = 0;
	while (!pq.empty()) {
		state currentstate = pq.top();
		pq.pop();

		string parentID;
		if (n == 3) { parentID=getMatrixID(currentstate.Matrix); }
		pair<int, int>cur = FindZero(currentstate.Matrix);// N
		int x = cur.first, y = cur.second;
		for (int i = 0; i < 4; i++) {
			pair<int, int>xy = { dx[i] + x, dy[i] + y };
			if (isValid(dx[i] + x, dy[i] + y)&& xy!= currentstate.p)
			{
				
				int h=0;
				if (choice == 1)h = Hamming(currentstate.Matrix);
				else if (choice == 2)h = Manhattan(currentstate.Matrix);

				int**newState = new int* [n];
				for (int i = 0; i < n; i++) {
					newState[i] = new int[n];
				}

				copy2DArray(currentstate.Matrix,newState);	
				swap(newState[x][y], newState[dx[i] + x][dy[i] + y]);
					
				state st = { newState,currentstate.state_depth + 1,h,cur};
				string ID;
				if (n == 3) {
					ID=getMatrixID(currentstate.Matrix);
					if (route.find(ID)==route.end())route[ID] = parentID;
				}

				if (h == 0) {
					cout << "Distance = " << currentstate.state_depth+1 << endl;
					if (n == 3) {
						PrintPath(ID);
					}
					return;
				}
				pq.push(st);
			}
		}
		for (int i = 0; i < n; i++) {
			delete[] currentstate.Matrix[i];
		}
		delete[] currentstate.Matrix;
	}
}

void BFS(int **root) {
	map<string, bool>vis;
	queue<pair<int**,int>>q;
	q.push({ root,0 });
	string rootID = getMatrixID(root);
	vis[rootID] = true;
	while (!q.empty()) {
		int** currentstate = q.front().first;
		int depth = q.front().second;
		q.pop();
		if (Hamming(currentstate) == 0) {
			cout << "Distance = " << depth << endl;
			string endID = getMatrixID(currentstate);
			if (n == 3)PrintPath(endID);
			break;
		}
		pair<int, int>cur = FindZero(currentstate);
		int x = cur.first, y = cur.second;
		for (int i = 0; i < 4; i++) {
			if (isValid(dx[i] + x, dy[i] + y))
			{
				int** newState = new int* [n];
				for (int i = 0; i < n; i++) {
					newState[i] = new int[n];
				}
				copy2DArray(currentstate, newState);
				swap(newState[x][y], newState[dx[i] + x][dy[i] + y]);
				string ID = getMatrixID(newState);
				if (!vis[ID]) {
					string curID = getMatrixID(currentstate);
					if (n==3)route[ID] = curID;
					vis[ID] = true;
					q.push({ newState,depth + 1 });
				}
			}
		}
	}
}


int main()
{

	int** arr;
	string tests[20] = { "TEST.txt","15 Puzzle 1.txt","15 Puzzle 3.txt","15 Puzzle 4.txt","15 Puzzle 5.txt",
		"8 Puzzle (2).txt","15 Puzzle - 1.txt",
	"50 Puzzle.txt","99 Puzzle - 1.txt","99 Puzzle - 2.txt","9999 Puzzle.txt","TEST.txt"};
	
	for (int t =4 ; t < 5; t++) {
		//clock_t Totaltime = clock();
		//path.clear();
		route.clear();
		ifstream input(tests[t]);
		input >> n;
		
		//2D array dynamic allocation 
		arr = new int* [n];
		for (int i = 0; i < n; i++) {
			arr[i] = new int[n];
		}

		// taking 2D array from file
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				input >> arr[i][j];
			}
		}

		if (!IsSolvable(arr)) {
			cout << "UnSolvable" << endl;
			return 0;
		}
		cout << "Solavable" << endl;

		int choice =2; 	//1 for Hamming 2 for Manhatten
		// Solve 
		state root;
		root.Matrix = arr;

		if (choice == 1)root.heuristic_value = Hamming(root.Matrix);
		else if (choice == 2)root.heuristic_value = Manhattan(root.Matrix);
		root.p=FindZero(root.Matrix);
		root.state_depth = 0;
		clock_t Totaltime = clock();
		Astar(root,choice);
		//BFS(root.Matrix);
		Totaltime = clock() - Totaltime;
		cout << (float)Totaltime / CLOCKS_PER_SEC << endl;
	}
	return 0;
}

