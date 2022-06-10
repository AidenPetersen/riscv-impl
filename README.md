# My RISC-V Implementation
The goal for this project is to create a RISC-V simulator and assembler, then createan implementation for the RISC-V ISA. Currently I am planning on only implementing the RISC-V base 32 bit instruction set without extensions, but ideally, if I want to add an extension, the code should be modular enough for that.

## TODO
- [ ] Create simulator
- [ ] Create assembler
- [ ] Create implementation
  - [ ] Create a detailed schematic
  - [ ] Plan out instruction control signals
  - [ ] Create the base processor (Including multiscalar)
    - [ ] Create registers
    - [ ] Create ALU
    - [ ] Create control units
    - [ ] Create basic Memory
  - [ ] Create a cache
  - [ ] Create branch prediction

## Simulator & Assembler
The simulator and assembler will be written in Go. This should be pretty easy to make. They won't be particularly advanced, the simulator will just execute the instructions and output the processor state. I want to do this so I have a better understanding of how these softwares work.The simulator will be a CLI. The main goal for these is to make it easier to test the implementation. These will be in the `AidenPetersen/riscv-tools` github project.

### Testing
The main idea  for making these two is to make a testing pipeline so that I can compare the expected simulator output to the assembler output

```
ASM -> Simulator -> Output --------------\
                                          -> Compare
ASM -> Assembler -> Processor -> Output -/
```

## Implementation
It will be a classic 5 stage RISC pipeline, but there will be some improvements:
- Two way multiscalar
- 2 layer cache
- Simple branch prediction
It will be written in Scala's Chisel 3.
