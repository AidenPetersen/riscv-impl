package riscv.issue

import chisel3._
import chisel3.util._

object utils {
  def addrToNumRegs(addrWidth: Int): Int = {
    Math.pow(2, addrWidth).intValue()
  }
}

class RegisterFileReadPort(addrWidth: Int, dataWidth: Int) extends Bundle {
  val addr = Input(UInt(addrWidth.W))
  val data = Output(UInt(dataWidth.W))
}

class RegisterFileWritePort(addrWidth: Int, dataWidth: Int) extends Bundle {
  val addr    = Input(UInt(addrWidth.W))
  val data    = Input(UInt(dataWidth.W))
  val enabled = Input(Bool())
}

class RegisterFile(
    addrWidth: Int,
    dataWidth: Int,
    numReadPorts: Int,
    numWritePorts: Int,
    hasZero: Boolean
) extends Module {
  val io = IO(new Bundle {
    val readPorts =
      Vec(numReadPorts, new RegisterFileReadPort(addrWidth, dataWidth))
    val writePorts =
      Vec(numWritePorts, new RegisterFileWritePort(addrWidth, dataWidth))
  })

  val registers = Reg(Vec(utils.addrToNumRegs(addrWidth), UInt(dataWidth.W)))

  // Get read values
  for (rp <- io.readPorts) {
    val readData =
      if (hasZero) Mux(rp.addr === 0.U, 0.U, registers(rp.addr))
      else registers(rp.addr)
    rp.data := RegNext(readData)
  }

  // Write values
  for (wp <- io.writePorts) {
    when(wp.enabled) {
      registers(wp.addr) := wp.data
    }
  }
}
