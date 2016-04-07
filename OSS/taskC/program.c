#include <stdio.h>
#include <stdlib.h>
#include "nd.h"
#include "nsd.h"

int main(int argc, char *argv[])
{
    if(argc<3) {
        fprintf(stderr, "%s\n", "Missing arguments.");
        exit(1);
    }
    int a, b;
    a = strtol(argv[1], NULL, 0);
    b = strtol(argv[2], NULL, 0);
    int nd_a, nd_b;
    nd_a = nd(a);
    nd_b = nd(b);
    if(nd_a==1 && nd_b==1) {
        printf("%s\n", "prvocisla");
    } else {
        printf("%d\n", nsd(a,b));
    }
    
    return 0;
}
