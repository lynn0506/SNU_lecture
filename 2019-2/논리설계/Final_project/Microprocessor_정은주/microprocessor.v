`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    20:14:01 12/09/2019 
// Design Name: 
// Module Name:    microprocessor 
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
module microprocessor(
    input [7:0] Instruction,
		input CLK, reset, 
		output [6:0] RegWriteR, RegWriteL, MemWriteR, MemWriteL,
		output [7:0] MemAddress
    );
	
	wire CLK_div;
	wire Branch, MemtoReg, MemRead, MemWrite, ALUop, ALUsrc, RegWrite, RegDst;
	wire [7:0] WriteRegister;
   wire [7:0] WriteToRegisterData, ReadData, Address;
	wire [7:0] Rs1Data, Rs2Data, SelectedRs2Data, SignExtOutput;
	wire [7:0] CurrentPC, NextPC, OneAddedPC, BranchCounter;
	wire [7:0] One;
	wire DC_R, DC_L; // don't care control signal
	
	assign MemAddress = CurrentPC;
	assign One = 8'd1;
	
	//CLK_divider clockDivider(.reset(reset), .CLK(CLK), .clkout(CLK_div)); 
	ControlUnit controller(.Instruction(Instruction[7:6]), .Branch(Branch), .MemtoReg(MemtoReg), .MemRead(MemRead), 
										.MemWrite(MemWrite), .ALUop(ALUop), .ALUsrc(ALUsrc), .RegWrite(RegWrite), .RegDst(RegDst), .DC_R(DC_R), .DC_L(DC_L));
										
	Adder oneAddPC(.data1(CurrentPC), .data2(One), .sum(OneAddedPC));
	Adder branchPC(.data1(OneAddedPC), .data2(SignExtOutput), .sum(BranchCounter));		
	PC setPC(.CLK(CLK), .reset(reset), .NextPC(NextPC), .CurrentPC(CurrentPC));

	Mux1 writeReg(.controlSignal(RegDst), .data1(Instruction[3:2]), .data2(Instruction[1:0]), .MuxOutput(WriteRegister)); // Write Register
	Mux1 pcSelect(.controlSignal(Branch), .data1(OneAddedPC), .data2(BranchCounter), .MuxOutput(NextPC)); // PC counter
	Mux1 selectRS2(.controlSignal(ALUsrc), .data1(Rs2Data), .data2(SignExtOutput), .MuxOutput(SelectedRs2Data)); // for ALU 
	Mux1 writeBackData(.controlSignal(MemtoReg), .data1(Address), .data2(ReadData), .MuxOutput(WriteToRegisterData));

	SignExtend signExtention(.Instruction(Instruction[1:0]), .SignExtOutput(SignExtOutput));
	ALU addressCount(.ALUop(ALUop), .ALUout(Address), .data1(Rs1Data), .data2(SelectedRs2Data));
	
	RegisterFile registers(.CLK(CLK), .reset(reset), .Rs1(Instruction[5:4]), .Rs2(Instruction[3:2]), .WriteRegister(WriteRegister),
																		.RegWrite(RegWrite), .Rs1Data(Rs1Data), .Rs2Data(Rs2Data), .RegWriteData(WriteToRegisterData));
   DataMemory memory(.CLK(CLK), .reset(reset), .MemWrite(MemWrite), .MemRead(MemRead), .Address(Address), 
														.WriteData(Rs2Data), .ReadData(ReadData));
	
	seven_segment MemoryOutput1(.reset(reset), .DC(DC_R), .bcd(Rs2Data[3:0]), .seg(MemWriteR));
	seven_segment MemoryOutput2(.reset(reset), .DC(DC_R),  .bcd(Rs2Data[7:4]), .seg(MemWriteL));
	seven_segment registerOutput1(.reset(reset), .DC(DC_L), .bcd(WriteToRegisterData[3:0]), .seg(RegWriteR));
	seven_segment registerOutput2(.reset(reset), .DC(DC_L), .bcd(WriteToRegisterData[7:4]), .seg(RegWriteL));

endmodule
