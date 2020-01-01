`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    20:17:54 12/09/2019 
// Design Name: 
// Module Name:    ControlUnit 
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
module ControlUnit(
   		input [1:0] Instruction,
		output Branch, MemtoReg, MemRead, MemWrite, ALUop, ALUsrc, RegWrite, RegDst, DC_L, DC_R
    );
	 
	 assign Branch = Instruction[1] & Instruction[0];
	 assign MemtoReg = ~Instruction[1] & Instruction[0];
	 assign MemRead = ~Instruction[1] & Instruction[0];
	 assign MemWrite = Instruction[1] & ~Instruction[0];
	 assign ALUop = ~(Instruction[1] & Instruction[0]);
	 assign ALUsrc = Instruction[1]^Instruction[0];
	 assign RegWrite = ~Instruction[1];
	 assign RegDst = ~Instruction[1] & ~Instruction[0];
	 
	 assign DC_L = Instruction[1];
	 assign DC_R = ~Instruction[1]& ~Instruction[0];
	 
endmodule
