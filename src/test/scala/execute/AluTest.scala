package riscv.execute

import chisel3._
import chiseltest._

import org.scalatest.flatspec.AnyFlatSpec
import riscv.execute.Alu
import riscv.common.AluOp

class AluTest extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "Alu"
  it should "execute all ops" in {
    test(new Alu(32))
      .withAnnotations(Seq(WriteVcdAnnotation)) { c =>
        c.io.a.poke(1.U)
        c.io.b.poke(1.U)
        c.io.aluOp.poke(AluOp.ADD)

        c.clock.step(1)
        c.io.out.expect(2.U)
        c.clock.step(1)

        c.io.aluOp.poke(AluOp.SUB)

        c.clock.step(1)
        c.io.out.expect(0.U)
        c.clock.step(1)

        c.io.aluOp.poke(AluOp.AND)

        c.clock.step(1)
        c.io.out.expect(1.U)
        c.clock.step(1)

        c.io.aluOp.poke(AluOp.OR)

        c.clock.step(1)
        c.io.out.expect(1.U)
        c.clock.step(1)

        c.io.aluOp.poke(AluOp.XOR)

        c.clock.step(1)
        c.io.out.expect(0.U)
        c.clock.step(1)

        c.io.aluOp.poke(AluOp.SLL)

        c.clock.step(1)
        c.io.out.expect(2.U)
        c.clock.step(1)

        c.io.aluOp.poke(AluOp.SRL)

        c.clock.step(1)
        c.io.out.expect(0.U)
        c.clock.step(1)

        c.io.aluOp.poke(AluOp.SRA)

        c.clock.step(1)
        c.io.out.expect(0.U)
        c.clock.step(1)

        c.io.aluOp.poke(AluOp.SLT)

        c.clock.step(1)
        c.io.out.expect(0.U)
        c.clock.step(1)

        c.io.aluOp.poke(AluOp.SLTU)

        c.clock.step(1)
        c.io.out.expect(0.U)
      }
  }
  it should "all shifts" in {
    test(new Alu(32))
      .withAnnotations(Seq(WriteVcdAnnotation)) { c =>
        c.io.a.poke(6.U)
        c.io.b.poke(6.U)
        c.io.aluOp.poke(AluOp.SLL)

        c.clock.step(1)
        c.io.out.expect(384.U)
        c.clock.step(1)

        c.io.a.poke(123.U)
        c.io.b.poke(30.U)

        c.clock.step(1)
        c.io.out.expect(3221225472L.U)
        c.clock.step(1)

        c.io.a.poke(7.U)
        c.io.b.poke(2.U)
        c.io.aluOp.poke(AluOp.SRL)

        c.clock.step(1)
        c.io.out.expect(1.U)
        c.clock.step(1)

        c.io.a.poke(4278190080L.U)
        c.io.b.poke(30.U)
        c.io.aluOp.poke(AluOp.SRL)

        c.clock.step(1)
        c.io.out.expect(3.U)
        c.clock.step(1)

        c.io.a.poke(4278190080L.U)
        c.io.b.poke(16.U)
        c.io.aluOp.poke(AluOp.SRA)

        c.clock.step(1)
        c.io.out.expect(4294967040L.U)
        c.clock.step(1)

        c.io.a.poke(6.U)
        c.io.b.poke(2.U)
        c.io.aluOp.poke(AluOp.SRA)

        c.clock.step(1)
        c.io.out.expect(1.U)
      }
  }
  it should "add and sub" in {
    test(new Alu(32))
      .withAnnotations(Seq(WriteVcdAnnotation)) { c =>
        c.io.a.poke(0x7fffffff.U)
        c.io.b.poke(0x7fffffff.U)
        c.io.aluOp.poke(AluOp.ADD)

        c.clock.step(1)
        c.io.out.expect(4294967294L.U)
        c.clock.step(1)

        c.io.a.poke(4294967295L.U)
        c.io.b.poke(1.U)
        c.io.aluOp.poke(AluOp.SUB)

        c.clock.step(1)
        c.io.out.expect(4294967294L.U)
      }
  }
  it should "slt and sltu" in {
    test(new Alu(32))
      .withAnnotations(Seq(WriteVcdAnnotation)) { c =>
        c.io.a.poke(4294967295L.U)
        c.io.b.poke(0.U)
        c.io.aluOp.poke(AluOp.SLT)

        c.clock.step(1)
        c.io.out.expect(1.U)
        c.clock.step(1)

        c.io.aluOp.poke(AluOp.SLTU)

        c.clock.step(1)
        c.io.out.expect(0.U)
      }
  }
}
