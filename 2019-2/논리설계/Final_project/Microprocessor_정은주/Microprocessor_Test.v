`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    20:29:42 12/09/2019 
// Design Name: 
// Module Name:    Microprocessor_Test 
// Project Name: 
// Target Devices: 
// Tool versions: 
// Description: 
//
// Dependencies: 
//
// Revision: 
// Revision 0.01 - File Created
// Additional Comments: 
//
//////////////////////////////////////////////////////////////////////////////////
module Microprocessor_Test(
    	input CLK, reset,
		output [6:0] RegWriteR, RegWriteL, MemWriteR, MemWriteL
    );
	 
	 wire[7:0] Instruction, pcout;
	 
	 microprocessor U1(.CLK(CLK), .reset(reset), .Instruction(Instruction), .RegWriteR(RegWriteR),
											.RegWriteL(RegWriteL), .MemWriteR(MemWriteR), .MemWriteL(MemWriteL), .MemAddress(pcout));
												
	InstructionMemory_test U2(.Instruction(Instruction), .Read_Address(pcout));


endmodule
