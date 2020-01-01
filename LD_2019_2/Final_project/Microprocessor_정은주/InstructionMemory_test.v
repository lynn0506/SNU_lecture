`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    20:23:51 12/09/2019 
// Design Name: 
// Module Name:    InstructionMemory_test 
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
module InstructionMemory_test(
   output [7:0] Instruction,
    input [7:0] Read_Address
    );
	 wire [7:0] MemBytes[5:0];

	 assign MemBytes[0] = {2'b01, 2'b00, 2'b01, 2'b00};
	 assign MemBytes[1] = {2'b01, 2'b00, 2'b10, 2'b01};
	 assign MemBytes[2] = {2'b00, 2'b01, 2'b10, 2'b00};
	 assign MemBytes[3] = {2'b10, 2'b00, 2'b10, 2'b01};
	 assign MemBytes[4] = {2'b11, 4'b0000, 2'b11};
	 
	 assign Instruction = MemBytes[Read_Address];

endmodule
