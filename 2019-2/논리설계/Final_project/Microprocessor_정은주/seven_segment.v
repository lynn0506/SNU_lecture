`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    20:19:16 12/09/2019 
// Design Name: 
// Module Name:    seven_segment 
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
module seven_segment(
   input [3:0] bcd, 
	input reset, DC,
	output reg [6:0] seg
    );
	 
 always @(*) begin
	 if(reset)
		seg <= 7'b1000000; 
	if(DC) begin
		seg <= 7'b1000000; 
	end
		
	else begin
			case(bcd)
				8'd0: seg <= 7'b0111111;
				8'd1: seg <= 7'b0000110;
				8'd2: seg <= 7'b1011011;
				8'd3: seg <= 7'b1001111;
				8'd4: seg <= 7'b1100110;
				8'd5: seg <= 7'b1101101;
				8'd6: seg <= 7'b1111101;
				8'd7: seg <= 7'b0000111;
				8'd8: seg <= 7'b1111111;
				8'd9: seg <= 7'b1101111;
				8'd10: seg <= 7'b1110111;
				8'd11: seg <= 7'b1111100;
				8'd12: seg <= 7'b0111001;
				8'd13: seg <= 7'b1011110;
				8'd14: seg <= 7'b1111001;
				8'd15: seg <= 7'b1110001;
				default : seg <= 7'b0000000;
			endcase
		end 
		end
endmodule
