#include <cstdlib>
#include <fstream>
#include <iostream>
#include <string>
#include <bitset>

#include "key.hpp"
#include "brute.hpp"
#include "timer.hpp"

std::string me;
std::string encrypted;
std::string table_filename;
bool verbose = false;
const key_type ONE = 1;

void increment(std::string*); 						// increments the value of a word_type
key_type to_ull(const std::string& str); 			// converts a string to a keytype
std::string to_str(key_type number);    			// converts a keytype to a string
key_type add(key_type first, key_type second);		// adds two kt's together appropriately
key_type my_encrypt(key_type a, key_type table[]);	// encrypts a kt like encrypt.cpp
char getval(char c);								// converts a char to a 5-bit value
void print_ull(key_type a);							// prints a kt (like Key::show())
void on_find(key_type x);							// performs actions when a decrypted is found


Brute::Brute(const std::string& filename) {
	T.resize(N);
	std::string buffer;
    std::fstream input(filename.c_str(), std::ios::in);
    for (int i = 0; i < N; i++) {
        std::getline(input, buffer);
        T[i].set_string(buffer);
    }
    input.close();
}

void Brute::decrypt(const std::string& encrptd) {
	
	key_type encrypted_value = to_ull(encrptd);
	key_type table[N];

	for (int i = 0; i < N; i++) {
		table[i] = to_ull(T[i].str());
	}

	key_type x = 0;
	key_type max = (ONE << N);

	while (x < max) {

		// excrypt x
		key_type x_encrypted = my_encrypt(x, table);
		
		// compare encrypted x to given value
		if (x_encrypted == encrypted_value) {
			on_find(x);
		}

		x++;
	}
}

void on_find(key_type x) {

	std::cout << to_str(x) << std::endl << std::flush;
}

key_type to_ull(const std::string& str) {

	key_type value = 0;

    //if (str.size() > C) throw std::out_of_range("The string <" + str + "> is too long to convert.");

    for (int i = 0; i < C; i++) {
    	
    	//strip off a char
    	char c = str[i];
    	
    	//get the index for our alphabet
    	char idx = (c > 96) ? c - 'a' : c + 26 - '0'; 

    	//check if the idx is in our alphabet
    	if (idx > R) throw std::out_of_range("The string <" + str + "> contains some invalid character(s).");

    	//shift the existing value by the number of bits in a character of our alphabet
    	value = value << B;

    	//add our new letter
    	value += idx;
    }

    return value;
}

std::string to_str(key_type number) {
	char value[C];

	for (int i = 0; i < C; i++) {

		int alpha_idx = (number >> (B*i)) & (R-1);  // mask the number

		value[C - i - 1] = ALPHABET[alpha_idx]; 	// select the char
	}

	std::string str(value);
	return str;
}

key_type add(key_type first, key_type second) {
	
	return (first + second) % ((ONE << N));
}

char getval(char c) {

	//get the index for our alphabet
    char idx = (c > 96) ? c - 'a' : c + 26 - '0';
	return idx;
}

void Brute::print_table(key_type _table[]) {
	for (int i = 0; i < N; i++) {
		std::cout << T[i].str() << "  ";
		std::cout << std::bitset< N >( _table[i] ) << std::endl;
	}
}

key_type my_encrypt(key_type a, key_type _table[]) {
	key_type total = 0;

	for (int i = 0; i < N; ++i) {
		if ((a >> (N - (i + 1))) & 1) {
			total = add(total, _table[i]);
		}
	}

	return total;
}

void print_ull(key_type a) {
	std::cout << to_str(a) << "  ";
	for (int j = 0; j < C; j++) {
    	std::cout << std::setw(2) << getval(to_str(a)[j]) << ' ';
	}
	std::cout << "  ";
	std::cout << std::bitset< N >( a ) << std::flush;
	std::cout << "\n";
}


void usage(const std::string& error_msg="") {
	if (!error_msg.empty()) std::cout << "ERROR: " << error_msg << '\n';
	std::cout << me << ": Brute force cracking of Subset-sum password with " 
		<< B << " bits precision\n"
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
	Brute brute(table_filename);
	brute.decrypt(encrypted);
	t.toc();

	if (verbose) std::cout << t.elapsed() << std::endl;
	
	return 0;
}
