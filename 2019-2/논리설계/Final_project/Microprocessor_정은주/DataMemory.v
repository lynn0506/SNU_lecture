`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    20:16:25 12/09/2019 
// Design Name: 
// Module Name:    DataMemory 
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
module DataMemory(
input CLK, reset,
	input MemWrite, MemRead,
	input [7:0] Address, WriteData,
	output [7:0] ReadData
    );
	 
	 reg [7:0] Memory [31:0];
	 assign ReadData = (MemRead) ? Memory[Address] : 7'b0;
	 always @(posedge CLK or posedge reset) begin 
		if(reset) begin
			Memory[0] <= 8'd0;  Memory[1] <= 8'd1; Memory[2] <= 8'd2; Memory[3] <= 8'd3; 
			Memory[4] <= 8'd4; Memory[5] <= 8'd5; Memory[6] <= 8'd6; Memory[7] <= 8'd7;
			Memory[8] <= 8'd8; Memory[9] <= 8'd9; Memory[10] <= 8'd10; Memory[11] <= 8'd11;
			Memory[12] <= 8'd12; Memory[13] <= 8'd13; Memory[14] <= 8'd14; Memory[15] <= 8'd15;
			Memory[16] <= 8'd0; Memory[17] <= -8'd1; Memory[18] <= -8'd2; Memory[19] <= -8'd3;
			Memory[20] <= -8'd4; Memory[21] <= -8'd5; Memory[22] <= -8'd6; Memory[23] <= -8'd7;
			Memory[24] <= -8'd8; Memory[25] <= -8'd9; Memory[26] <= -8'd10; Memory[27] <= -8'd11;
			Memory[28] <= -8'd12; Memory[29] <= -8'd13; Memory[30] <= -8'd14; Memory[31] <= -8'd15;
		end
		else if(MemWrite) begin
			Memory[Address] <= WriteData;
		end
	end
	
endmodule
