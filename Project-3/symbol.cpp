#include <cstdlib>
#include <fstream>
#include <iostream>
#include <string>
#include <bitset>
#include <unordered_map>
using namespace std;

#include "key.hpp"
#include "symbol.hpp"
#include "timer.hpp"

//start startcode
std::string me;
std::string encrypted;
std::string table_filename;
bool verbose = false;
//end startcode


key_type * LT;								// the main table
unordered_map<key_type, value_type> ST;		// the symbol table structure

const int START = 0; // subset start index in LT
const int END = N / 2;	 // subset end index in LT
const int S_SIZE = END - START;
const key_type ONE = 1;
const key_type MASK = (ONE << N) - ONE;



key_type to_key_type(const std::string&);	// converts a string to a ulonglong
std::string to_str(key_type);    			// converts a ulonglong to a string
key_type add(key_type, key_type);			// adds two key_types appropriately
key_type subtract(key_type, key_type);		// subtracts two key_types appropriately
key_type my_encrypt(key_type, key_type *);	// encrypts a key_type like encrypt.cpp
uint getval(char);							// converts a char to a 5-bit value
void print_key_type(key_type);				// prints a key_type (like Key::show())
void on_find(value_type, key_type);		// performs actions when a decrypted is found
void create_symbol_table(); 				// fills an existing symbol table
void insert(key_type, key_type);			// inserts a value into the symbol table

// Constructor

Symbol::Symbol(const std::string& filename) {

	// Distribute Memory

	LT = new key_type[ N ]; //std::cout << ".";
		
	// Verify Memory and Fill

	if (LT) {
		std::string buffer;
	    std::fstream input(filename.c_str(), std::ios::in);
	    for (int i = 0; i < N; i++) {
	        std::getline(input, buffer);
	        LT[i] = to_key_type(buffer);
	    }
	    input.close();
	} else { std::cout << "The allocation of LT failed!"; }

	create_symbol_table();
}

// Destructor

Symbol::~Symbol() {
	if ( LT ) {
		delete[] LT;
	}
}

// Decryption

void Symbol::decrypt(const std::string& encrypted) {

	key_type E = to_key_type(encrypted);

	key_type x = 0;						//where the brute begins
	key_type max = (ONE << (N - S_SIZE));	//where the brute ends

	key_type UX; // unencrypted x
	key_type EX; // encrypted x

	while (x < max) {

		// Define the two values
		key_type firstpart = x & ((ONE << START) - 1); // mask with bitstring the length of Start
		key_type seconpart = (x >> START) << ( END ); //shift right to strip off firstpart, left to pad with zeros

		UX = (firstpart + seconpart); //put the two parts together
		EX = my_encrypt(UX, LT); //encrypt UX

		// Search the sub-table for the needed sum
		key_type k = subtract(E, EX);

		auto it = ST.find(k);

		if (it != ST.end()) {
			on_find(it->second, UX);
		}

		// Increment x
		x++;
	}
}

// Helper Functions

void Symbol::print_table() {
	for (int j = 0; j < N; ++j) {
		std::cout << std::setw(2) << j << " ";
		print_key_type(LT[j]);
	}
}

void insert(key_type key, key_type value) {

	unordered_map<key_type,value_type>::const_iterator it = ST.find(key);
	if (it == ST.end()) {
		value_type v;
		v.push_back(value);
		pair<key_type, value_type> p(key, v);
		ST.insert(p);
	}
	else {
		value_type v = it->second;
		v.push_back(value);
		pair<key_type, value_type> p(key, v);
		ST.insert(p);
	}
}

void create_symbol_table() {
	
	//brute force to fill symbol table
	
	key_type x = 0;
	key_type max = (ONE << (S_SIZE)) & MASK;

	key_type UX; // unencrypted value of x
	key_type EX; // encrypted value of x

	while (x < max) {

		UX = (x << START);
		EX = my_encrypt(UX, LT);

		insert(EX, UX);

		x++;
	}
}

void on_find(value_type x, key_type to_add) {
	//if (!x.size()) std::cout << "tried to print empty symbol_node" << std::endl;

	for (int i =0; i < x.size(); i++) {

		/*
		if (verbose) {
			std::cout << to_str(x[i])
					  << " + "
					  << to_str(to_add & MASK)
					  << " = ";
		}*/

		std::cout << to_str(add(x[i], to_add)) << std::endl;
	}

	std::cout << std::flush;
}

key_type to_key_type(const std::string& str) {

    key_type value = 0;

    if (str.size() > C) throw std::out_of_range("The string <" + str + "> is too long to convert.");

    for (int i = 0; i < str.size(); i++) {
    	
    	//strip off a char
    	char c = str[i];
    	
    	//get the index for our alphabet
    	uint idx = (c > 96) ? c - 'a' : c + 26 - '0'; 

    	//sanity check if the idx is in our alphabet
    	if (idx > R) throw std::out_of_range("The string <" + str + "> contains some invalid character(s).");

    	//shift the existing value by the number of bits in a character of our alphabet
    	value = value << B;

    	//add our new letter
    	value += idx;
    }

    return value & MASK;
}

std::string to_str(key_type number) {
	number = number & MASK;

	std::string value(C, 'a');

	for (int i = 0; i < C; i++) {

		int alpha_idx = (number >> (B*i)) & (R-1);  // mask the number

		value[C - i - 1] = ALPHABET[alpha_idx]; 	// select the char
	}

	return value;
}

key_type add(key_type first, key_type second) {
	
	return ((first + second) % (ONE << N));
}

key_type subtract(key_type first, key_type second) {

	if (second < first) {
		return ((ONE << N) - (second - first)) & MASK;
	}
	else {
		return (first - second) & MASK;
	}
}

uint getval(char c) {
	//get the index in our alphabet of the given char
    uint idx = (c > 96) ? c - 'a' : c + 26 - '0';
	return idx & 31;
}

key_type my_encrypt(key_type a, key_type table[]) {

	key_type total = 0;

	for (int i = 0; i < N; ++i) {
		if ((a >> (N - (i + 1))) & 1) {
			total = add(total, table[i]);
		}
	}
	return total;
}

void print_key_type(key_type a) {

	std::cout << to_str(a) << "  ";
	for (int j = 0; j < C; j++) {
    	std::cout << std::setw(2) << getval(to_str(a)[j]) << ' ';
	}
	std::cout << "  ";
	std::cout << std::bitset< N >( a ) << std::flush;
	std::cout << endl;
}




void usage(const std::string& error_msg="") {
	if (!error_msg.empty()) std::cout << "ERROR: " << error_msg << '\n';
	std::cout << me << ": Symbol table-based cracking of Subset-sum password"
		<< " with " << B << " bits precision\n"
	    << "USAGE: " << me << " <encrypted> <table file> [options]\n"
		<< "\nArguments:\n"
		<< " <encrypted>:   encrypted password to crack\n"
		<< " <table file>:  name of file containing the table to use\n"
		<< "\nOptions:\n"
		<< " -h|--help:     print this message\n"
		<< " -v|--verbose:  select verbose mode\n\n";
	exit(0);
}

void initialize(int argc, char* argv[]) {
	me = argv[0];
	if (argc < 3) usage("Missing arguments");
	encrypted = argv[1];
	table_filename = argv[2];
	for (int i=3; i<argc; ++i) {
		std::string arg = argv[i];
		if (arg == "-h" || arg == "--help") usage();
		else if (arg == "-v" || arg == "--verbose") verbose = true;
		else usage("Unrecognized argument: " + arg);
	}
}

int main(int argc, char *argv[]){
	
	initialize(argc, argv);

	CPU_timer t;

	t.tic();	
	Symbol symbol(table_filename);
	symbol.decrypt(encrypted);
	t.toc();

	if (verbose) std::cout << t.elapsed() << std::endl;	
	return 0;
}
