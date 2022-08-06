package riscv.common

import chisel3._
import chisel3.util._
import chisel3.experimental.ChiselEnum

object AluOp extends ChiselEnum {
  val ADD  = Value(0x00.U) // add  -> 0000
  val SUB  = Value(0x01.U) // sub  -> 0001
  val AND  = Value(0x02.U) // and  -> 0010
  val OR   = Value(0x03.U) // or   -> 0011
  val XOR  = Value(0x04.U) // xor  -> 0100
  val SLL  = Value(0x05.U) // sll  -> 0101
  val SRL  = Value(0x06.U) // srl  -> 0110
  val SRA  = Value(0x07.U) // sra  -> 0111
  val SLT  = Value(0x08.U) // slt  -> 1000
  val SLTU = Value(0x09.U) // sltu -> 1001
}
