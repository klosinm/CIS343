#include "Fraction.h"
#include <algorithm>
#include <iomanip>
#include <regex>
#include <sstream>

using namespace std;
/*_________________
Monica Klosin
Nov 13 2019
Fraction Maker
__________________*/

/* Friend functions */
Fraction operator+(int val, const Fraction &f)
{
    return (f).operator+(val);
}
Fraction operator-(int val, const Fraction &f)
{
    return (-f).operator-(-val);
}
Fraction operator*(int val, const Fraction &f)
{
    return f * val;
}
//----------------------------------------------------------------------------------------
//base clarifcation
Fraction::Fraction()
{
    x = 0;
    y = 1;
    w = 0;
    isPos = true;
}
//input of whole number
Fraction::Fraction(int v)
{
    x = 0;
    y = 1;
    w = (v);
    if (v < 0)
    {
        isPos = false;
    }
    else
    {
        isPos = true;
    }
}
//copy
Fraction::Fraction(const Fraction &other)
{
    this->x = other.x;
    this->y = other.y;
    this->w = other.w;
    this->isPos = other.isPos;
}
//move
Fraction::Fraction(Fraction &&other)
{
    this->x = other.x;
    this->y = other.y;
    this->w = other.w;
    this->isPos = other.isPos;
}
//parscing from string output
Fraction::Fraction(std::string s)
{
    int isSpace = s.find(" "); //return location where space is
    int isSlash = s.find("/"); //return location where "/" is
    int startFract = 0;
    std::string delimiter = "/";
    std::string num = "0";
    std::string dem = "1";
    std::string whole = "0";

    // whole number
    if (isSpace != std::string::npos)
    {
        startFract = isSpace + 1;
        whole = s.substr(0, isSpace);
    }
    else
    {
        whole = "0";
    }
    //fraction
    //see if slash
    if (isSlash != std::string::npos)
    {
        num = s.substr(startFract, isSlash - startFract);
        dem = s.substr((isSlash + 1), (s.size()));
    }
    else
    {
        whole = s.substr(0, s.size());
        num = "0";
        dem = "1";
    }

    this->w = stoi(whole);
    this->x = stoi(num);
    this->y = stoi(dem);

    if (this->y == 0)
    {
        throw std::invalid_argument("Can't divide by 0!");
    }
    if (this->w < 0 || this->x < 0)
    {
        this->isPos = false;
    }
    else
    {
        this->isPos = true;
    }
}
//----------------------------------------------------------------------------------------
int Fraction::whole() const
{
    return abs(w);
}
int Fraction::numerator() const
{
    return abs(x);
}
int Fraction::denominator() const
{
    return abs(y);
}
bool Fraction::isPositive() const
{
    return isPos;
}
//----------------------------------------------------------------------------------------
// Copy assignment
Fraction &Fraction::operator=(const Fraction &other)
{
    this->x = other.x;
    this->y = other.y;
    this->w = other.w;
    this->isPos = other.isPos;
    return *this;
}
// Move assignment
Fraction &Fraction::operator=(Fraction &&other)
{
    this->x = other.x;
    this->y = other.y;
    this->w = other.w;
    this->isPos = other.isPos;
    return *this;
}
//----------------------------------------------------------------------------------------
//greatest common factor
int Fraction::gcf(int a, int b) const
{
    if (a % b == 0)
    {
        return abs(b);
    }
    else
    {
        return gcf(b, a % b);
    }
}
//least common mutiple
int Fraction::lcm(int a, int b) const
{
    return (a / gcf(a, b)) * b;
}
//----------------------------------------------------------------------------------------
// Add Fraction + int
Fraction Fraction::operator+(int num) const
{
    Fraction fract = *this;

    //make fraction a improper
    int num1 = fract.x + (fract.w * fract.y);
    int valNum = num * fract.y;

    int newNum = num1 + valNum;

    if (newNum < 0)
    {
        fract.isPos = false;
    }
    else
    {
        fract.isPos = true;
    }

    fract.w = 0;
    fract.x = newNum;
    fract.makeProper();
    fract.reduce();
    return fract;
}
// Add Fraction + Fraction
Fraction Fraction::operator+(const Fraction &other) const
{
    Fraction fract = *this;  //a
    Fraction fract2 = other; //b
    Fraction f;

    //making fract improper
    int num1 = 0;
    if (fract.w < 0)
    {
        num1 = ((fract.w * fract.y) - fract.x);
    }
    else if (fract.w >= 0)
    {
        num1 = ((fract.w * fract.y) + fract.x);
    }
    int dem1 = 0;
    dem1 = fract.y;

    //making fract2 improper
    int num2 = 0;
    if (fract2.w < 0)
    {
        num2 = ((fract2.w * fract2.y) - fract2.x);
    }
    else if (fract2.w >= 0)
    {
        num2 = ((fract2.w * fract2.y) + fract2.x);
    }
    int dem2 = 0;
    dem2 = fract2.y;

    //finding consistent denominator
    int lcd = lcm(dem1, dem2);

    //change fractions if needed
    num1 = (lcd / dem1) * num1;
    dem1 = lcd;
    num2 = (lcd / dem2) * num2;
    dem2 = lcd;

    //New numerator
    int sumNum = num2 + num1;

    if (sumNum < 0 || f.w < 0)
    {
        f.isPos = false;
    }
    else
    {
        f.isPos = true;
    }

    f.x = sumNum;
    f.y = lcd;
    f.w = 0;
    f.reduce();
    f.makeProper();
    return f;
}
//make negative/postive
Fraction Fraction::operator-() const
{
    Fraction fract = *this;

    //value WAS negative, make pos
    if (fract.w < 0 || fract.x < 0)
    {
        fract.isPos = true;
        fract.w = fract.w * -1;
        fract.x = fract.x * -1;
    }
    //value WAS positive, make negative
    else if (fract.w > 0 || fract.x > 0)
    {
        fract.isPos = false;
        fract.x = fract.x * -1;
        fract.w = fract.w * -1;
    }
    return (fract);
}
// binary minus: Frac - int
Fraction Fraction::operator-(int val) const
{
    Fraction fract = *this;
    //make fraction a improper

    int num1 = fract.x + (fract.w * fract.y);

    int valNum = val * fract.y;

    int newNum = num1 - valNum;
    //int newNum = valNum - num1;

    if (newNum < 0)
    {
        fract.isPos = false;
    }
    else
    {
        fract.isPos = true;
    }
    fract.w = 0;
    fract.x = newNum;
    fract.makeProper();
    fract.reduce();
    return fract;
}
// binary minus: Frac - Frac
Fraction Fraction::operator-(const Fraction &other) const
{
    Fraction fract = *this;
    Fraction fract2 = other;

    //make improper
    int imp1 = abs(fract.y * fract.w) + abs(fract.x);
    int imp2 = abs(fract2.y * fract2.w) + abs(fract2.x);
   
    //new  numerator
    int num1 = imp1 * fract2.y;
    int num2 = imp2 * fract.y;
    int sumNum = (num1 - num2);

    //new demoniator
    int dem = fract.y * fract2.y;

    //New whole
    fract.w = 0;
    fract.x = sumNum;
    fract.y = dem;

    if (fract.w < 0 || sumNum < 0 || fract2.w < 0)
    {
        fract.isPos = false;
    }
    else
    {
        fract.isPos = true;
    }
    fract.reduce();
    fract.makeProper();
    return fract;
}
// Multiply: Frac * int
Fraction Fraction::operator*(int val) const
{
    Fraction fract = *this;

    fract.w = fract.w * val;
    fract.x = fract.x * val;

    if (val < 0 || fract.w < 0 || fract.x < 0)
    {
        fract.isPos = false;
    }
    else if ((val < 0 && fract.w < 0) || (val < 0 && fract.x < 0) || (val >= 0 && fract.w >= 0 && fract.x >= 0))
    {
        fract.isPos = true;
    }
    else
    {
        fract.isPos = true;
    }

    fract.makeProper();
    fract.reduce();
    return fract;
}
// Multiply: Frac * Frac
Fraction Fraction::operator*(const Fraction &other) const
{
    Fraction fract = *this;
    Fraction fract2 = other;
    Fraction f{};

    if (fract.w < 0 && fract2.w < 0)
    {
        f.isPos = true;
    }
    else if (fract.x < 0 || fract.w < 0 || fract2.w < 0 || fract2.x < 0)
    {
        f.isPos = false;
    }
    else
    {
        f.isPos = true;
    }

    //make fraction a improper
    int num1 = abs(fract.x) + abs(fract.w * fract.y);
    int num2 = abs(fract2.x) + abs(fract2.w * fract2.y);

    int dem = fract2.y * fract.y; //finding  denominator
    int num = num1 * num2;        //finding  numerator

    //Return Value
    f.x = num;
    f.y = dem;

    f.makeProper();
    f.reduce();
    return f;
}
//----------------------------------------------------------------------------------------
// Component access Frac[i]
optional<int> Fraction::operator[](int pos) const
{
    std::optional<int> NA;

    if (pos == 0){
        if(w != 0){
            return w;
        }
        else{
            return NA;
        }
    }
    else if (pos == 1){
        if (x != 0)
        {
            return x;
        }
        else
        {
            return NA;
        }
    }
    else if (pos == 2)
    {
        if (y != 1)
        {
            return y;
        }
        else
        {
            return NA;
        }
    }
    else{
        return NA;
    } 
}
// Compare Frac < Frac
bool Fraction::operator<(const Fraction &other) const
{
    Fraction fract = *this;
    Fraction fract2 = other;
    //make fraction improper
    int num1 = fract.x + (fract.w * fract.y) * fract2.y;
    int num2 = fract2.x + (fract2.w * fract2.y) * fract.y;

    //finding consistent denominator
    int lcd = lcm(fract.denominator(), fract2.denominator());

    //make numerators match new denominator
    num1 = (lcd / fract.y) * num1;
    num2 = (lcd / fract2.y) * num2;

    if (num1 < num2)
    {
        return true;
    }
    else
    {
        return false;
    }
}
// Compare Frac == Frac
bool Fraction::operator==(const Fraction &other) const
{
    Fraction fract = *this;
    Fraction fract2 = other;
    //make fraction a improper
    int num1 = fract.x + (fract.w * fract.y);
    int num2 = fract2.x + (fract2.w * fract2.y);

    fract.x = num1;
    fract2.x = num2;
    fract.w = 0;
    fract2.w = 0;

    fract.reduce();
    fract2.reduce();

    if (fract.x == fract2.x && fract.y == fract2.y)
    {
        return true;
    }
    else
    {
        return false;
    }
}
//----------------------------------------------------------------------------------------
//Proper
void Fraction::makeProper()
{
    Fraction f;
    int num = 0;
    int whole = 0;

    //just whole number is provided
    if (this->x == 0 && this->y == 1)
    {
        return;
    }
    //numerator is greater than denominator
    else if (abs(this->x) > this->y)
    {

        num = this->x % this->y;
        whole = this->w + (this->x - num) / this->y;
        this->x = num;
        this->w = whole;
        if (this->x == 0)
        {
            this->y = 1;
        }
    }
    else if (this->x == this->y)
    {
        this->x = 0;
        this->y = 1;
        this->w = 1;
    }
    else
    {
        return;
    }
}
Fraction Fraction::toProper() const
{
    Fraction funct = *this;
    funct.makeProper();
    return funct;
}
bool Fraction::isProper() const
{
    Fraction f;

    if (this->x > this->y || this->x == 0)
    {
        return false;
    }
    else
    {
        return true;
    }
}
//----------------------------------------------------------------------------------------
// Reduce
void Fraction::reduce()
{

    int top = 0;
    int bottom = 0;
    int divide = gcf(this->x, this->y);

    top = this->x / divide;
    bottom = this->y / divide;

    this->x = top;
    this->y = bottom;
}
Fraction Fraction::toReduced() const
{
    Fraction funct = *this;
    funct.reduce();
    return funct;
}
bool Fraction::isReduced() const
{
    int a = gcf(this->x, this->y);

    if (a == 1)
    {
        return true;
    }
    else
    {
        return false;
    }
}
//----------------------------------------------------------------------------------------
ostream &Fraction::writeTo(ostream &os) const
{   
   std::string s = "";
//w x/y
 if(this-> w != 0 && this->x != 0 && this->y != 1){
    s = s + to_string(this->w) + " " + to_string(this->x) + "/" + to_string(this->y);
}
//x/y
else if(this->w == 0 && this->x != 0 && this->y != 1){
    s = s + to_string(this->x) + "/" + to_string(this->y);
}
//w
else if (this-> w != 0 && this->x == 0 && this->y == 1){
    s = s + to_string(this->w);
}
//NA
else{
    s = to_string(0);
}

os << "[" << s << "]"; 
 
 return os;  
}

istream &Fraction::readFrom(istream &sr) 
{
    char ch;
    string s = "";
    ch = sr.get(); //

 while(ch != '['){
     //if you get a close bracket before open one
     if (ch == ']')
        {
            throw(std::invalid_argument)("No open [ bracket!");
        }
        ch = sr.get(); 
 }
    ch = sr.get();
    

    while (ch != ']'){
        //get to end of stream and find no ]
        if(ch == ' ' || isdigit(ch) || ch == '/' || ch == '-'){
            s = s + ch;
            //cout << "String: " << s << endl;
            if (sr.fail())
            {
                throw(std::invalid_argument)("No closing ] bracket!");
            }
        }
        else{
            throw(std::invalid_argument)("Not a valid character!");           
        }
        ch = sr.get();
    }

    if (s == ""){
        throw(std::invalid_argument)("Empty String!");
    }
       
   
    Fraction f = Fraction{s};
    this->x = f.x;
    this->y = f.y;
    this->w = f.w;
    this->isPos = f.isPos;
    return sr;
}

ostream &operator<<(ostream &os, const Fraction &f)
{
    return f.writeTo(os);
}

istream &operator>>(istream &s, Fraction &f)
{
    return f.readFrom(s);
}

#if I_DO_EXTRA_CREDIT
optional<string> Fraction::isRepeating() const
{
    return {};
}

string Fraction::operator()(int len) const { return {}; }
#endif
