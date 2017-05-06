#ifndef _SYMBOL_HPP_
#define _SYMBOL_HPP_

#include <fstream>
#include <iostream>
#include <string>
#include <vector>

#include "key.hpp"

// a node in the symbol array which will contain the decoded values

struct symbol_node {

	symbol_node() : value(0), isfull(0), next(NULL) {}
	symbol_node(int value, symbol_node * n = NULL) : value(0), isfull(1), next(n) {}

	key_type value;
	bool isfull;
	symbol_node * next; 	// pointer to next node

	/* 
	 * When delete is used to deallocate memory for a C++ class object, 
	 * the object's destructor is called before the object's memory is 
	 * deallocated (if the object has a destructor).
	 * 
	 * <https://msdn.microsoft.com/en-us/library/h6227113.aspx>
	 */

	// Takes care of the linked list dynamic allocation
	~symbol_node() {
		std::cout << "del";
		if (next) { delete next; }
	}
};

// array of symbol_nodes to store the values

//typedef unordered_map<key_type, symbol_node> symbol_table;
using value_type = std::vector<key_type>;
//using symbol_table = std::unordered_map<key_type, value_type>;

// class def

class Symbol {
private:
	std::vector<Key> T;

public:
	Symbol(const std::string&);
	~Symbol();
	void decrypt(const std::string&);
	void print_table();

};

#endif