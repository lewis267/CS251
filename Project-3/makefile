CC=g++
CFLAGS=-std=c++11 -O2 -DUSE_10_CHARS -o

all: brute symbol encrypt

encrypt: encrypt.cpp
	$(CC) $(CFLAGS) encrypt encrypt.cpp

brute: brute.cpp
	$(CC) $(CFLAGS) brute brute.cpp

symbol: symbol.cpp
	$(CC) $(CFLAGS) symbol symbol.cpp

clean:
	rm brute symbol encrypt
