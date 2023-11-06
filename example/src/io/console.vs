package console

extern int GetStdHandle(int kind)

extern int WriteConsoleA(int handle, ref byte buffer, int length, ref int written, int reserved)

extern int strlen(ref byte buffer)

void printSizedBuffer(ref byte buffer, int length) {
    let handle = GetStdHandle(-11)
    let written = 0
    WriteConsoleA(handle, buffer, length, ref written, 0)
    return
}

void print(ref byte buffer) {
    let len = strlen(buffer)
    printSizedBuffer(buffer, len)
    return
}

void println(ref byte buffer) {
    print(buffer)
    printSizedBuffer("\r\n", 2)
    return
}

void println() {
    printSizedBuffer("\r\n", 2)
    return
}
