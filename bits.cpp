#include <iostream>
#include <vector>

using namespace std;

long to_long(vector<int> v) {
	long ans = 0;
	for(int i = 0; i < v.size(); i++) {
		long x = v[i] ? 1 : 0;
		ans = ans << 1;
		ans = ans | 1;
	}
	return ans;
}

int main() {
	int n;
	cin >> n;
	vector<int> v(n);
	for(int i = 0; i < n; i++) {
		cin >> v[i];
	}
	cout << to_long(v) << endl;
}
