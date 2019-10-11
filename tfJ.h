#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>
/************
function to parse the text file based on the number following -j #
-Distribute the words equally given the space (#) provided 
**************/

char *strtok_r(char *, const char *, char **);

void JParse(char *textfile, int LLength)
{

  //get size of the string
  int size = 0;
  int i = 0;

  bool overflow = true;

  //array to hold the string with a certain line length
  char partA[LLength];

  //number of words in line
  int wordsize = 0;

  //print everything word by word
  while (fgets(textfile, 100, stdin))
  {

    //printing space between paragraphs
    if (strlen(textfile) == 1)
    {
      for (int j = 0; j < size; j++)
      {
        printf("%c", partA[j]);
      }
      printf("\n\n");
      //clears
      strcpy(partA, "");
      size = 0;
      wordsize = 0;
    }

    char *toks;
    char *rest;
    toks = strtok_r(textfile, "\n ", &rest);

    //splitting the line by space
    while (toks != NULL)
    {

      //if total token size is less then the length provide
      if (strlen(toks) > LLength)
      {
        i = i + 1;
        overflow = false;
      }

      if (wordsize == 0)
      {
        strcpy(partA, toks);
        wordsize += 1;
        size += strlen(toks);
      }
      else
      {

        if ((size + strlen(toks)) + 1 <= LLength)
        {
          //add token to array
          strcat(partA, " ");
          strcat(partA, toks);
          wordsize += 1;
          size += 1 + strlen(toks);
        }
        else
        {
          //printf("toks:%s, size: %d, wordSize: %d\n\n", toks, size, len);
          int spaceToUse = LLength - size;
          int numofGaps = wordsize - 1;

          int idealGap = spaceToUse / numofGaps;

          int numerator = 0;
          int denominator = numofGaps;

          char *toksA;
          toksA = strtok(partA, "\n ");

          while (toksA != NULL)
          {
            //print one word
            printf("%s ", toksA);

            for (int i = 0; i < idealGap; i++)
            {
              printf(" ");
            }
            //get extra of how many will need to be printed
            numerator += spaceToUse % numofGaps;

            if (numerator >= denominator)
            {
              numerator -= denominator;
              printf(" ");
            }

            toksA = strtok(NULL, "\n ");
            //printf("toksA:%s\n\n", toksA);
          }
          printf("\n");
          strcpy(partA, toks);
          wordsize = 1;
          size = strlen(toks);
        }
        //toks = strtok(NULL, "\n ");
        //toks = strtok_r(NULL, "\n ", &rest);
      }
      toks = strtok_r(NULL, "\n ", &rest);
    }
  }

  for (int j = 0; j < size; j++)
  {
    printf("%c", partA[j]);
  }
  if (!overflow)
  {
    printf("\n\nWarning: %i overfull line(s)", i);
  }

  exit(0);
}
