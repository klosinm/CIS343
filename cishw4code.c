int a,b,c;

void p(){
    int a = 3;
    b = 1;
    c = a+b;
    q();
}

void print(){
    printf("%d %d %d\n",a,b,c);
}

void q(){
    int b = 4;
    a = 5;
    c = a + b;
    print();
}

main(){
    int c = 5;
    p();
}