#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>
#include "tfW.h"
#include "tfJ.h"

//declaring
void WParse(char *, int);
void JParse(char *, int);
//Points to the width of the text line for the program
//struct PartC{
//int w;
//}width;

/************
function Main, where user puts input for what type of parsing they want
Monica Klosin
**************/
int main(int argc, char *argv[])
{

  //length of file for program
  char lineLength[100];
  int LLength;

  //lorem.txt
  char textfile[100];

  //accpeting the length of the line from terminal -w # or -j #
  int k;
  for (k = 1; k < argc; k++)
  {
    //use -w
    if (strcmp(argv[k], "-w") == 0)
    {
      if (argv[k + 1] == NULL)
      {

        fprintf(stderr, "R U sure?\n");
        exit(0);
      }
      strcpy(lineLength, argv[k + 1]);
      LLength = atoi(lineLength);
      if (LLength == 0)
      {
        fprintf(stderr, "R U sure?\n");
        exit(0);
      }
      //take to tfW.h
      WParse(&textfile[LLength], LLength);
    }
    //use -j
    else if (strcmp(argv[k], "-j") == 0)
    {
      if (argv[k + 1] == NULL)
      {
        fprintf(stderr, "R U sure?\n");
        exit(0);
      }
      strcpy(lineLength, argv[k + 1]);
      LLength = atoi(lineLength);
      if (LLength == 0)
      {
        fprintf(stderr, "R U sure?\n");
        exit(0);
      }
      //take to tfJ.h
      JParse(&textfile[LLength], LLength);
    }
    else
    {
      fprintf(stderr, "R U sure?\n");
      exit(0);
    }
  }
  return 0;
}
