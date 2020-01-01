`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    20:26:57 12/09/2019 
// Design Name: 
// Module Name:    RegisterFile 
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
module RegisterFile(
    input [1:0] Rs1, Rs2,
	input RegWrite, CLK, reset,
	input [7:0] WriteRegister,
	input [7:0] RegWriteData,
	output [7:0] Rs1Data, Rs2Data
    );
	 
	 reg [7:0] Registers [3:0]; //4 registers
	 assign Rs1Data = Registers[Rs1];
	 assign Rs2Data = Registers[Rs2];
	 
	 always @(posedge CLK or posedge reset) begin
		if(reset) begin
			Registers[0] <= 8'd0; Registers[1] <= 8'd0; 
			Registers[2] <= 8'd0; Registers[3] <= 8'd0;
		end
		else if(RegWrite) begin 
			Registers[WriteRegister[1:0]] <= RegWriteData;
		end
	end
	
endmodule
