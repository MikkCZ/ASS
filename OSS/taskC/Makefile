all: lib program.c
	gcc -L$(CURDIR) -Wall -o program program.c -lnsd
#	gcc -o program program.c

lib: nsd.c nsd.h nd.c nd.h
	gcc -fPIC -c -Wall nsd.c nd.c
#	gcc -fPIC -g -c -Wall nsd.c
#	gcc -fPIC -g -c -Wall nd.c
	gcc -shared -o libnsd.so nsd.o nd.o
#	gcc -shared -Wl,-soname,libnsd.so -o libnsd.so nsd.o nd.o

clean:
	rm -f $(wildcard *.o)
	rm -f libnsd.so
	rm -f program
