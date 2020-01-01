#----------------------------------------------------------------
# 
#  4190.308 Computer Architecture (Fall 2019)
#
#  Project #3: RISC-V Assembly Programming
#
#  October 29, 2019
#
#  Jin-Soo Kim (jinsoo.kim@snu.ac.kr)
#  Systems Software & Architecture Laboratory
#  Dept. of Computer Science and Engineering
#  Seoul National University
#
#----------------------------------------------------------------


	.text
	.align	2

#----------------------------------------------------------------
#   int mul(int a, int b)
#----------------------------------------------------------------
    .globl  mul
mul:
    mv      a5,a0
    li      a0,0
    j       L1
L2:
    srli    a1,a1,1
    slli    a5,a5,1
L1:
    or      a4,a5,a1
    beq     a4,zero,L3
    andi    a4,a1,1
    beq     a4,zero,L2
    add     a0,a0,a5
    j       L2
L3:
    ret
#----------------------------------------------------------------
#   int mulh(int a, int b)
#----------------------------------------------------------------
    .globl  mulh
mulh:
    mv a2, a0
    li a0, 0
    li a3, 0
    slt a4, a2, x0
    slt a5, a1, x0
    sub a5, a5, a4
    bge a1, zero, M3

M0:
    neg a2, a2
    neg a1, a1
    bne a4, zero, M3
    li a5, -1
    j M3

M6:
    srli a1, a1, 1
    beq a1, zero, L3
    slli a5, a5, 1
    srli a4, a2, 31
    add a5, a5, a4
    slli a2, a2, 1

M3:
    andi a4, a1, 1
    beq a4, zero, M6
    add a3, a3, a2
    add a0, a0, a5
    bgeu a3, a2, M6
    addi a0, a0, 1
    j   M6
#----------------------------------------------------------------
#   int div(int a, int b)
#----------------------------------------------------------------
    .globl  div
div:
    beq a1,zero,D11
    beq a0,zero,L3
    li  a2, 1;
    blt a1, zero, D12
    blt a0, zero, D13
    j   D14

D12:
    neg a2, a2
    neg a1, a1
    bgt a0, zero, D14

D13:
    neg a2, a2
    neg a0, a0

D14:
    mv  a5, a1
    j   D7

D8:
    slli a5,a5,1

D7:
    bgt a5,zero,D8
    mv  a4, a0
    li  a0, 0
    j   D9

D10:
    srli a5,a5,1
    beq  a5,zero,D15

D9:
    bltu a5,a1,D15
    slli a0,a0,1
    bltu a4,a5,D10
    sub  a4,a4,a5
    addi a0,a0,1
    j    D10

D11:
    li a0, -1
    ret

D15:
    bgt a2, zero, L3
    neg a0, a0
    ret

#----------------------------------------------------------------
#   int rem(int a, int b)
#----------------------------------------------------------------
    .globl  rem
rem:
    beq a1, zero, L3
    beq a0, zero, L3
    li  a2, 1
    blt a1, zero, R1
    blt a0, zero, R2
    j  R02

R1:
    neg a1, a1
    bge a0, zero, R02

R2:
    neg a2, a2
    neg a0, a0

R02:
    mv a5, a1
    j R3

R4:
    slli a5, a5, 1

R3:
    bgt a5, zero, R4
    li  a4, 0
    j   R5

R6:
    srli a5, a5, 1
    beq  a5, zero, R9

R5:
    bltu a5, a1, R9
    slli a4, a4, 1
    bltu a0, a5, R6
    sub  a0, a0, a5
    addi a4, a4, 1
    j    R6

R9:
    bgt a2, zero, L3
    neg a0, a0
    ret
