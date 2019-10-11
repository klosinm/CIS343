#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>
/************
function to parse the text file based on the number following -w #
-print out the text file with a certain length (#)
-call out any words that are longer then the certain length (#)
**************/
void WParse(char *textfile, int LLength)
{

  //confirming we have size of line length from terminal
  // struct PartC width = LLength;
  //printf("Line length: %d\n",LLength);

  //array to hold the string with a certain line length
  //char array[width.w];
  //char array[LLength];
  //get size of the string
  int size = 0;
  int i = 0;
  bool overflow = true;
  //print everything word by word
  while (fgets(textfile, 100, stdin))
  {

    //printing space between paragraphs
    if (strlen(textfile) == 1)
    {
      printf("\n\n");
      size = 0;
    }

    char *toks;
    toks = strtok(textfile, "\n ");

    //splitting the line by space
    while (toks != NULL)
    {

      //if total token size is less then the length provided

      if (strlen(toks) > LLength)
      {
        //printf("bad tok: %s", toks); //test
        i = i + 1;
        overflow = false;
      }
      if ((size + strlen(toks)) <= LLength)
      {

        size = size + 1 + strlen(toks);
        printf("%s ", toks);
      }
      else
      {
        // printf("| max width: %i, size: %i, next token: %s\n", LLength, size, toks); //test
        printf("\n%s ", toks);
        size = strlen(toks) + 1;

        //  printf("(size of tok %s: %i, size: %i)",toks, (strlen(toks), size)); //test
      }
      toks = strtok(NULL, "\n ");
    }
  }
  if (!overflow)
  {
    printf("\n\nWarning: %i overfull line(s)", i);
  }
  printf("\n");
  exit(0);
}