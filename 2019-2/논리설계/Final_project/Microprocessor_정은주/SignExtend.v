`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    20:18:36 12/09/2019 
// Design Name: 
// Module Name:    SignExtend 
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
module SignExtend(
  		input [1:0] Instruction,
		output [7:0] SignExtOutput
    );
	 
	 assign SignExtOutput = { {7{Instruction[1]}}, Instruction[0]}; 


endmodule
