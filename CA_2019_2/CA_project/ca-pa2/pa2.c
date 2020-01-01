//---------------------------------------------------------------
//
//  4190.308 Computer Architecture (Fall 2019)
//
//  Project #2: Half-precision Floating Points
//
//  October 1, 2019
//
//  Jin-Soo Kim (jinsoo.kim@snu.ac.kr)
//  Systems Software & Architecture Laboratory
//  Dept. of Computer Science and Engineering
//  Seoul National University
//
//---------------------------------------------------------------
#include <stdio.h>
#include "pa2.h"

// Convert 32-bit signed integer to 16-bit half-precision floating point
hfp int2hfp (int n)
{
    int E = 0;
    hfp ans = 0;
    if(n == 0) return ans;
    if(n<0)
    {
        n *= (-1);
        ans += (1<<15);
    }
    int ori_n = n;
    if(!(n && n)) return (ans +(31<<10)+ 1); //nan
    if(n == (1<<31)) return ans + (31<<10); //infinity
    while(n>=2)
    {
        n>>=1;
        if(++E>=16) return ans+(31<<10);
    }
    int exp = E + 15;
    int frac = (E<=10)? ((ori_n - (1<<E))<<(10-E))
              :(((ori_n - (1<<E))>>(E-10)));
    //rounding
    int stb = ((1<<(E-11))-1)&ori_n;
    if((ori_n & (1<<(E-11))) != 0)
    {
        if(stb != 0) ans += 1;
        else if(frac&1) ans += 1;
    }
    return ans+(exp<<10)+frac;
}
// Convert 16-bit half-precision floating point to 32-bit signed integer
int hfp2int (hfp h)
{
    if((h - (1<<15)) == 0 || h == 0) return 0;
    int ret = 0;
    ret = (h>>15)? -1: 1;
    h -= ((h>>15)<<15);
    int exp = (h>>10);
    int E = exp - 15;
    int frac = h-(exp<<10);
    if(exp == 0)
        if(frac != 0) return 0;
    
    union {
        unsigned int u;
        double d;
    }ori_f;
    double d;
    d = 1.0 + 1.1;
    ori_f.d = 1.0 + 1.1;
    if(exp == 31) return 0x80000000;
    if(E<10) return ret*((frac + (1<<10))>>(10-E));
    return ret*((frac + (1<<10))<<(E-10));
}
// Convert 32-bit single-precision floating point to
// 16-bit half-precision floating point
hfp float2hfp (float f)
{
    hfp ans = 0;
    union {
        unsigned int u;
        float fp;
    } ori_f;
    ori_f.fp = f;
    if(ori_f.u & (1<<31))
    {
        ans += (1<<15);
        ori_f.u -= (1<<31);
    }
    int E = (((ori_f.u)&(255<<23))>>23)-127;
    if(ori_f.u > 0x7f800000) return ans+(0x7c01);//nan
    else if(ori_f.u == 0x7f800000 || E>=16)
        return ans+(31<<10);//overflow
    int rb, stb;
    ans+=(((ori_f.u)&(0x7fffff))>>13)+((E+15)<<10);
    rb = (ori_f.u>>12)&1;
    stb = (ori_f.u)&(0xfff);
    if((rb && ((ans&(31<<10))!= 31<<10)) && (stb || (!stb && (ans&1)))) ans += 1;
    return ans;
}
// Convert 16-bit half-precision floating point to
// 32-bit single-precision floating point
float hfp2float (hfp h)
{
    float ret = 1.0;
    if(h&(1<<15))
    {
        h -= (1<<15);
        ret *= -1.0;
    }
    union {
        unsigned int u;
        float f;
    } ori_f;
    
    if(h == 0) return (ret*0.0);
    int exp = ((h&(31<<10))>>10);
    float frac = (h&((1<<10)-1));
    int E = exp-15;
    if(exp == 31) {
        if(frac != 0) {
            if(ret== -1) ori_f.u = (511<<23)+1;
            else ori_f.u = (255<<23)+1;
            return ori_f.f;
        }
        if(ret== -1) ori_f.u = (511<<23);
        else ori_f.u = (255<<23);
        return ori_f.f;
    }
    if(exp == 0) return ret*frac*(1.0/(1<<24));
    if(E<=10) return ret*(frac+(1<<10))*(1.0/(float)(1<<(10-E)));
    else return ret*((frac+(1<<10))*(float)(1<<(E-10)));
}
