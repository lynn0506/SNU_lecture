//---------------------------------------------------------------
//
//  4190.308 Computer Architecture (Fall 2019)
//
//  Project #1: 64-bit Integer Arithmetic using 32-bit Operations
//
//  September 9, 2019
//
//  Jin-Soo Kim (jinsoo.kim@snu.ac.kr)
//  Systems Software & Architecture Laboratory
//  Dept. of Computer Science and Engineering
//  Seoul National University
//
//---------------------------------------------------------------

#include <stdio.h>
#include "pa1.h"
// NOTE!!!!!
// You should use only 32-bit integer operations inside Uadd64(), Usub64(),
// Umul64(), and Udiv64() functions.


// Uadd64() implements the addition of two 64-bit unsigned integers.
// Assume that A and B are the 64-bit unsigned integer represented by
// a and b, respectively. Uadd64() should return x, where x.hi and x.lo
// contain the upper and lower 32 bits of (A + B), respectively.

HL64 Uadd64 (HL64 a, HL64 b)
{
    HL64 x;
    x.lo = a.lo + b.lo;
    x.hi = a.hi + b.hi;
    
    u32 tmp = (a.lo >> 1) + (b.lo >> 1);
    if(a.lo%2 + b.lo%2 == 2) tmp += 1;
    if(tmp >= (1<<31)) x.hi += 1;
    
    return x;
}

// Usub64() implements the subtraction between two 64-bit unsigned integers.
// Assume that A and B are the 64-bit unsigned integer represented by
// a and b, respectively. Usub64() should return x, where x.hi and x.lo
// contain the upper and lower 32 bits of (A - B), respectively.


HL64 Usub64 (HL64 a, HL64 b)
{
    HL64 x;
    x.hi = a.hi - b.hi;
    x.lo = a.lo - b.lo;
    u32 tmp = b.lo - (1<<31);
    
    if(b.lo > a.lo) {
        x.lo = (1<<31) - tmp;
        x.lo += a.lo;
        x.hi -= 1;
    }
    return x;
}

// Umul64() implements the multiplication of two 64-bit unsigned integers.
// Assume that A and B are the 64-bit unsigned integer represented by
// a and b, respectively.  Umul64() should return x, where x.hi and x.lo
// contain the upper and lower 32 bits of (A * B), respectively.

HL64 Umul64 (HL64 a, HL64 b)
{
    HL64 x;
    x.lo = 0;
    x.hi = 0;
    
    while(!(b.hi == 0 && b.lo == 0) || !(a.hi == 0 && a.lo == 0))
    {
        if((a.lo == 0 && a.hi == 0) || (b.lo == 0 && b.hi == 0)) return x;
        
        if(b.lo&1)
        {
            u32 tmp = (x.lo>>1) + (a.lo>>1);
            if(x.lo & 1 + a.lo & 1 == 2) tmp += 1;
            x.hi += (tmp>>31);
            x.hi += a.hi;
            x.lo += a.lo;
        }
        b.lo >>= 1;
        b.lo += (1<<31)*(b.hi & 1);
        b.hi >>= 1;
        
        a.hi <<= 1;
        a.hi += (a.lo>>31);
        a.lo <<= 1;
    }
    return x;
}

// Udiv64() implements the division of two 64-bit unsigned integers.
// Assume that A and B are the 64-bit unsigned integer represented by
// a and b, respectively.  Udiv64() should return x, where x.hi and x.lo
// contain the upper and lower 32 bits of the quotient of (A / B),
// respectively.

HL64 Udiv64 (HL64 a, HL64 b)
{
    HL64 Q;
    Q.lo = 0;
    Q.hi = 0;
    
    HL64 ori = b;
    
    if((b.hi == 0 && b.lo == 0) || (a.lo == 0 && a.hi == 0)) return Q;
    
    while(!(b.hi>>31))
    {
        b.hi <<= 1;
        b.hi += (b.lo>>31);
        b.lo <<= 1;
    }
    
    while(((b.hi == ori.hi) && (b.lo >= ori.lo))|| (b.hi > ori.hi)) {
        Q.hi <<= 1;
        Q.hi += (Q.lo>>31);
        Q.lo <<= 1;
        
        if(((a.hi == b.hi) && (a.lo >= b.lo)) || (a.hi > b.hi)) {
            a.hi -=  b.hi;
            if(a.lo < b.lo)
            {
                a.hi -= 1;
                a.lo += ((1<<31) - (b.lo - (1<<31)));
            }
            else { a.lo -= b.lo; }
            Q.lo += 1;
        }
        
        b.lo >>= 1;
        b.lo += (b.hi&1)<<31;
        b.hi >>= 1;
        
        if(b.hi == 0 && b.lo == 0) break;
    }
    return Q;
}
