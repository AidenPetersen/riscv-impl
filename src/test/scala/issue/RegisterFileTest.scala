package riscv.issue

import chisel3._
import chiseltest._

import org.scalatest.flatspec.AnyFlatSpec
import riscv.issue.RegisterFile

class RegisterFileTest extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "RegisterFile"

  it should "write registers" in {
    test(new RegisterFile(5, 32, 1, 1, true))
      .withAnnotations(Seq(WriteVcdAnnotation)) { c =>
        c.io.writePorts(0).addr.poke(1.U)
        c.io.writePorts(0).data.poke(42.U)
        c.io.writePorts(0).enabled.poke(true.B)
        c.clock.step(1)
        c.io.readPorts(0).addr.poke(1.U)
        c.io.writePorts(0).addr.poke(2.U)
        c.io.writePorts(0).data.poke(42.U)
        c.clock.step(1)
        c.io.writePorts(0).addr.poke(2.U)
        c.io.writePorts(0).data.poke(42.U)
        c.io.readPorts(0).data.expect(42.U)
        c.clock.step(1)
        c.io.readPorts(0).addr.poke(2.U)
        c.clock.step(1)
        c.io.readPorts(0).data.expect(42.U)
      }
  }

  it should "ignores zero" in {
    test(new RegisterFile(5, 32, 1, 1, true))
      .withAnnotations(Seq(WriteVcdAnnotation)) { c =>
        c.io.writePorts(0).addr.poke(0.U)
        c.io.writePorts(0).data.poke(42.U)
        c.io.writePorts(0).enabled.poke(true.B)
        c.clock.step(1)
        c.io.readPorts(0).addr.poke(0.U)
        c.clock.step(1)
        c.io.readPorts(0).data.expect(0.U)
      }
  }

  it should "write to zero" in {
    test(new RegisterFile(5, 32, 1, 1, false))
      .withAnnotations(Seq(WriteVcdAnnotation)) { c =>
        c.io.writePorts(0).addr.poke(0.U)
        c.io.writePorts(0).data.poke(42.U)
        c.io.writePorts(0).enabled.poke(true.B)
        c.clock.step(1)
        c.io.readPorts(0).addr.poke(0.U)
        c.clock.step(1)
        c.io.readPorts(0).data.expect(42.U)
      }
  }

  it should "multi write and read" in {
    test(new RegisterFile(5, 32, 2, 2, true))
      .withAnnotations(Seq(WriteVcdAnnotation)) { c =>
        c.io.writePorts(0).addr.poke(1.U)
        c.io.writePorts(0).data.poke(42.U)
        c.io.writePorts(0).enabled.poke(true.B)

        c.io.writePorts(1).addr.poke(2.U)
        c.io.writePorts(1).data.poke(43.U)
        c.io.writePorts(1).enabled.poke(true.B)

        c.clock.step(1)
        c.io.writePorts(1).enabled.poke(false.B)
        c.io.writePorts(0).enabled.poke(false.B)

        c.io.readPorts(0).addr.poke(1.U)
        c.io.readPorts(1).addr.poke(2.U)

        c.clock.step(1)

        c.io.readPorts(0).data.expect(42.U)
        c.io.readPorts(1).data.expect(43.U)

        c.clock.step(1)
        c.io.readPorts(1).addr.poke(1.U)

        c.clock.step(1)

        c.io.readPorts(0).data.expect(42.U)
        c.io.readPorts(1).data.expect(42.U)
      }
  }
}
