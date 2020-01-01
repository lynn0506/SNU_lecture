`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    20:15:41 12/09/2019 
// Design Name: 
// Module Name:    CLK_divider 
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
module CLK_divider(
		input CLK, reset,
		output reg clkout
    );
	 
	 reg [31:0] cnt;
	 
	 always @(posedge CLK or posedge reset) begin
		if(reset) begin
			cnt <= 32'd0;
			clkout <= ~1'd0;
		end
		else if(cnt == 32'd25000000) begin
			cnt <= 32'd0;
			clkout <= ~clkout;
		end
		else begin
			cnt <= cnt+1;
		end
	end

endmodule
