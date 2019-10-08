#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>
/************
function to parse the text file based on the number following -j #

-Distribute the words equally given the space (#) provided 
**************/
void JParse(char* textfile, int LLength){

//printf("Welcome to part J!\n");


//confirming we have size of line length from terminal
  printf("Line length: %d\n",LLength);

  

  printf("----+----1----+----2----+----3----+----4----+--\n");
  //get size of the string
  int size  =  0; 
  int i = 0;

    //number of words
   int numOfToks = 1;  
   //number of characters
   int numOfChar = 0;

   int Dif = LLength - size;
   float seperator = Dif/numOfToks;
  bool overflow = true;

 //array to hold the string with a certain line length
  char partA[LLength];



  //print everything word by word
  while(fgets(textfile, 100, stdin)) {
    
    //printing space between paragraphs
    if (strlen(textfile) == 1){
      printf("\n\n");
      size = 0;
    }

    char * toks;
    toks = strtok(textfile, "\n ");
    
    //splitting the line by space
    while(toks != NULL ){
        
         //if total token size is less then the length provide
         if(strlen(toks)>LLength){
            i = i + 1;    
            overflow = false;       
         } 

        
        int k = 0;
        for(k = 0; k < LLength; k++){
              partA[k] = toks[k];
              //numofChar = numOfChar + strlen(toks);

             // printf("Array is: %c", partA);
        }
        printf("Array is: %s\n", partA);
        
          
      
            if((size + strlen(toks)) < (LLength)){
           
              size = size  + 1 + strlen(toks);
             //printf("%s ", toks);
            // printf("%s ", toks);
            
             numOfToks = 1 + numOfToks;
             }
             else{
           //  printf("| max width: %i, size: %i, number of tokens: %i, next token: %s\n", LLength, size, numOfToks, toks); //test
              //printf("\n%s ", toks);
              size = strlen(toks);  
              numOfToks = 1;                                                       
             // printf("(size of tok %s: %i, size: %i)",toks, (strlen(toks), size)); //test
             } 
           toks = strtok(NULL, "\n ");    
           
        
               
      } 
  }
  if(!overflow){
  printf("\n\nWarning: %i overfull line(s)", i);
  }


printf("\n----+----1----+----2----+----3----+----4----+--\n");
exit(0);
}