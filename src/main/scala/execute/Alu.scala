package riscv.execute

import chisel3._
import chisel3.util._
import riscv.common.AluOp

class AluBundle(width: Int) extends Bundle {
  val aluOp = Input(AluOp())
  val a     = Input(UInt(width.W))
  val b     = Input(UInt(width.W))
  val out   = Output(UInt(width.W))
}
