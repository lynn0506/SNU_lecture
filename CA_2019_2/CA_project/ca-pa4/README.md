# 4190.308 Computer Architecture (Fall 2019)
# Project #4: A 3-Stage Pipelined RISC-V Simulator
### Due: 11:59PM, December 10 (Tuesday)

## Introduction

The goal of this project is to understand how a pipelined processor works. You need to build a 3-stage pipelined RISC-V simulator called `snurisc3` in Python that supports most of RV32I base instruction set.

## Processor microarchitecture

The target RISC-V processor `snurisc3` consists of three pipeline stages: FD, EX, and MW. The following briefly summarizes the tasks performed in each stage:

* FD: Instruction fetch and decode, register file read
* EX: Arithmetic/Logical computation
* MW: Data memory access and write back

Compared with the traditional 5-stage pipeline architecture used in `snurisc5`, you can think that IF and ID stages are merged into the FD stage, while MM and WB stages are merged into the MW stage in `snurisc3`.

The `snurisc3` processor has the following characteristics:

* The control logic is located in the FD stage.

* The "always-not-taken" branch prediction scheme is used. 

* The outcome of the conditional branch is determined at the end of the EX stage. If the prediction was wrong, the instruction in the branch target should be fetched immediately in the next cycle. The `jal` and `jalr` instructions are handled similarly. As soon as the branch target address is calculated in the EX stage, the instruction in the target address should be fetched immediately in the next cycle. In any case, those instructions fetched incorrectly should be turned into the BUBBLE.

* The write to the register file is done at the end of the MW stage.

* __No data forwarding used.__ Whenever there is a dependency between instructions, the corresponding pipeline stage is stalled.


## Problem specification

This project consists of two parts.

### Design (40 points)

First, you need to prepare and submit the design document (in PDF file) for the `snurisc3` processor. Your design document should answer the following questions:

1. What does the overall pipeline architecture look like? 

 * We provide you with the `snurisc3-design.pdf` file that has an (empty) diagram of pipeline stages and hardware components. You need to complete this diagram according to your pipeline design. A hand-drawn diagram is OK. You don't have to spend a lot of time to make it fancy. Please take a picture of your diagram and attach it in your design document.

2. When do data hazards occur and how do you deal with them?

 * Show all the possible cases when data hazards can occur and your solutions to them.

3. When do control hazards occur and how do you deal with them?

 * Again, show all the possible cases when control hazards can occur and your solutions to them.


### Implementation (60 points)

You also need to implement the working version of `snurisc3` simulator. We provide you with the skeleton code that can be downloaded from https://github.com/snu-csl/ca-pa4.

To download the skeleton code, please take the following step:

```
$ git clone https://github.com/snu-csl/ca-pa4.git
```

Note that the `snurisc3` simulator is based on the 5-stage pipelined simulator (`snurisc5`) available in [the PyRISC project](https://github.com/snu-csl/pyrisc). Currently, `snurisc3` just supports ALU operations without implementing any hazard detection and control logic. Please refer to the `snurisc3-skel.pdf` file for the current pipeline structure of the `snurisc3` simulator.

Your task is to make it work correctly for any combination of instructions. You may find the [GUIDE.md](https://github.com/snu-csl/pyrisc/blob/master/pipe5/GUIDE.md) file in the PyRISC project useful, which describes the overall architecture and implementation details of the `snurisc5` simulator.

In the PyRISC project, several RISC-V executable files are available such as `fib`, `sum100`, `forward`, `branch`, and `loaduse`. You can test your simulator with these programs. Also, it is highly recommended to write your own test programs to see how your simulator works in a particular situation. Note that, for the given RISC-V executable file, `snurisc` (ISA simulator), `snurisc5` (5-stage implementation), and your `snurisc3` (3-stage implementation) all should have the same results in terms of register values and memory states. The only difference will be the number of cycles you need to execute the program.

The following example shows how you can run the executable file `sum100` on the `snurisc3` simulator (We assume that `pyrisc` is also downloaded in the same directory as `ca-pa4`).

```
$ cd ca-pa4
$ ./snurisc3.py -l 4 ../pyrisc/asm/sum100   
or
$ python3 ./snurisc3.py -l 4 ../pyrisc/asm/sum100
Loading file ../pyrisc/asm/sum100
--------------------------------------------------
0 [FD] 0x80000000: addi   t0, zero, 1
0 [EX] 0x00000000: BUBBLE
0 [MW] 0x00000000: BUBBLE
--------------------------------------------------
1 [FD] 0x80000004: addi   t1, zero, 100
1 [EX] 0x80000000: addi   t0, zero, 1
1 [MW] 0x00000000: BUBBLE
--------------------------------------------------
2 [FD] 0x80000008: addi   t6, zero, 0
2 [EX] 0x80000004: addi   t1, zero, 100
2 [MW] 0x80000000: addi   t0, zero, 1
--------------------------------------------------
3 [FD] 0x8000000c: add    t6, t6, t0
3 [EX] 0x80000008: addi   t6, zero, 0
3 [MW] 0x80000004: addi   t1, zero, 100
--------------------------------------------------
4 [FD] 0x80000010: addi   t0, t0, 1
4 [EX] 0x8000000c: add    t6, t6, t0
4 [MW] 0x80000008: addi   t6, zero, 0
--------------------------------------------------
5 [FD] 0x80000014: bge    t1, t0, 0x8000000c
5 [EX] 0x80000010: addi   t0, t0, 1
5 [MW] 0x8000000c: add    t6, t6, t0
--------------------------------------------------
6 [FD] 0x80000018: ecall
6 [EX] 0x80000014: bge    t1, t0, 0x8000000c
6 [MW] 0x80000010: addi   t0, t0, 1
--------------------------------------------------
7 [FD] 0x8000001c: BUBBLE
7 [EX] 0x80000018: ecall
7 [MW] 0x80000014: bge    t1, t0, 0x8000000c
--------------------------------------------------
8 [FD] 0x80000020: BUBBLE
8 [EX] 0x8000001c: BUBBLE
8 [MW] 0x80000018: ecall
Execution completed
Registers
=========
zero ($0): 0x00000000    ra ($1):   0x00000000    sp ($2):   0x00000000    gp ($3):   0x00000000
tp ($4):   0x00000000    t0 ($5):   0x00000002    t1 ($6):   0x00000064    t2 ($7):   0x00000000
s0 ($8):   0x00000000    s1 ($9):   0x00000000    a0 ($10):  0x00000000    a1 ($11):  0x00000000
a2 ($12):  0x00000000    a3 ($13):  0x00000000    a4 ($14):  0x00000000    a5 ($15):  0x00000000
a6 ($16):  0x00000000    a7 ($17):  0x00000000    s2 ($18):  0x00000000    s3 ($19):  0x00000000
s4 ($20):  0x00000000    s5 ($21):  0x00000000    s6 ($22):  0x00000000    s7 ($23):  0x00000000
s8 ($24):  0x00000000    s9 ($25):  0x00000000    s10 ($26): 0x00000000    s11 ($27): 0x00000000
t3 ($28):  0x00000000    t4 ($29):  0x00000000    t5 ($30):  0x00000000    t6 ($31):  0x00000001
Memory 0x80010000 - 0x8001ffff
==============================
7 instructions executed in 9 cycles. CPI = 1.286
Data transfer:    0 instructions (0.00%)
ALU operation:    5 instructions (71.43%)
Control transfer: 2 instructions (28.57%)
```

## Restrictions

* You should not change any files other than `stages.py`. 

* Your simulator should minimize the number of stalled cycles.

* __The number of submissions to the `sys` server will be limited to 20 times__.

## Hand in instructions

* Submit only the `stages.py` file to the submission server.

* Also, submit the design document (in PDF file only) to the submission server.

## Logistics

* You will work on this project alone.

* Only the upload submitted before the deadline will receive the full credit. 25% of the credit will be deducted for every single day delay.

* You can use up to 5 slip days during this semester. If your submission is delayed by 1 day and if you decided to use 1 slip day, there will be no penalty. In this case, you should explicitly declare the number of slip days you want to use in the QnA board of the submission server after each submission. Saving the slip days for later projects is highly recommended!

* Any attempt to copy others' work will result in heavy penalty (for both the copier and the originator). Don't take a risk.


This is the final project. I hope you enjoyed!



---
Jin-Soo Kim<br>
Systems Software and Architecture Laboratory<br>
Seoul National University<br>
http://csl.snu.ac.kr
