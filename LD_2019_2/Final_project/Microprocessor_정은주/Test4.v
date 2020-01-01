`timescale 1ns / 1ps

////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer:
//
// Create Date:   20:27:50 12/09/2019
// Design Name:   microprocessor
// Module Name:   /csehome/lynn0506/microprocessor_FINAL/Test4.v
// Project Name:  microprocessor_FINAL
// Target Device:  
// Tool versions:  
// Description: 
//
// Verilog Test Fixture created by ISE for module: microprocessor
//
// Dependencies:
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
////////////////////////////////////////////////////////////////////////////////

module Test4;
	// Inputs
	reg CLK;
	reg reset;

	// Outputs
	wire [6:0] RegWriteR;
	wire [6:0] RegWriteL;
	wire [6:0] MemWriteR;
	wire [6:0] MemWriteL;

	// Instantiate the Unit Under Test (UUT)
	Microprocessor_Test uut (
		.CLK(CLK), 
		.reset(reset), 
		.RegWriteR(RegWriteR), 
		.RegWriteL(RegWriteL), 
		.MemWriteR(MemWriteR), 
		.MemWriteL(MemWriteL)
	);
	
	initial begin
		// Initialize Inputs
		CLK = 0;
		reset = 1;

		// Wait 100 ns for global reset to finish
		#25;
		CLK = 1;
		reset = 0;
		#400;
        
		// Add stimulus here

	end
      always #25 CLK = ~CLK;
endmodule
