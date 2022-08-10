package riscv.execute

import chisel3._
import chisel3.util._
import riscv.common.AluOp
import chisel3.internal.firrtl.Component

class AluBundle(width: Int) extends Bundle {
  val aluOp = Input(AluOp())
  val a     = Input(UInt(width.W))
  val b     = Input(UInt(width.W))
  val out   = Output(UInt(width.W))
}

class Alu(width: Int) extends Module {
  val io = IO(new AluBundle(width))

  when(io.aluOp === AluOp.ADD) {
    io.out := io.a + io.b
  }.elsewhen(io.aluOp === AluOp.SUB) {
    io.out := io.a - io.b
  }.elsewhen(io.aluOp === AluOp.AND) {
    io.out := io.a & io.b
  }.elsewhen(io.aluOp === AluOp.OR) {
    io.out := io.a | io.b
  }.elsewhen(io.aluOp === AluOp.XOR) {
    io.out := io.a ^ io.b
  }.elsewhen(io.aluOp === AluOp.SLL) {
    io.out := io.a << io.b.apply(log2Ceil(width) - 1, 0)
  }.elsewhen(io.aluOp === AluOp.SRL) {
    io.out := io.a >> io.b.apply(log2Ceil(width) - 1, 0)
  }.elsewhen(io.aluOp === AluOp.SRA) {
    io.out := (io.a.asSInt >> io.b.apply(log2Ceil(width) - 1, 0)).asUInt
  }.elsewhen(io.aluOp === AluOp.SLT) {
    io.out := (io.a.asSInt < io.b.asSInt).asUInt
  }.elsewhen(io.aluOp === AluOp.SLTU) {
    io.out := io.a < io.b
  }.otherwise {
    io.out := 0.U
  }
}
