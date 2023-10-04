package org.voidlang.compiler.node.operator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.voidlang.compiler.node.Generator;
import org.voidlang.compiler.node.Node;
import org.voidlang.compiler.node.NodeInfo;
import org.voidlang.compiler.node.NodeType;
import org.voidlang.compiler.node.type.core.Type;
import org.voidlang.compiler.node.value.Value;
import org.voidlang.llvm.element.IRBuilder;
import org.voidlang.llvm.element.IRContext;
import org.voidlang.llvm.element.IRType;
import org.voidlang.llvm.element.IRValue;

@AllArgsConstructor
@Getter
@Setter
@NodeInfo(type = NodeType.CASTING)
public class Casting extends Value {
    /**
     * The operand value to be cast to the target type.
     */
    @NonNull
    private Value operand;

    /**
     * The target type to cast the operand to.
     */
    private Type type;

    @Override
    public IRValue generate(Generator generator) {
        IRBuilder builder = generator.getBuilder();
        IRContext context = generator.getContext();

        IRValue operand = getOperand().generateAndLoad(generator);
        Type operandType = getOperand().getValueType();

        if (type.equals(operandType))
            throw new IllegalStateException("Trying to cast a value to the same type");

        if (operandType.equals(Type.DOUBLE) || operandType.equals(Type.FLOAT)) {
            if (type.equals(Type.INT))
                return builder.floatToSignedInt(operand, IRType.int32(context));
        }

        else if (operandType.equals(Type.INT)) {
            if (type.equals(Type.DOUBLE))
                return builder.signedIntToFloat(operand, IRType.doubleType(context));
            if (type.equals(Type.FLOAT))
                return builder.signedIntToFloat(operand, IRType.floatType(context));
        }

        else if (operandType.equals(Type.BOOL)) {
            if (type.equals(Type.INT))
                return builder.intCast(operand, IRType.int32(context));
        }

        throw new IllegalStateException("Cannot cast from " + operandType + " to " + type);
    }

    /**
     * Initialize all the child nodes for the overriding node.
     *
     * @param parent parent node of the overriding node
     */
    @Override
    public void preProcess(Node parent) {
        this.parent = parent;
        operand.preProcess(this);
    }

    /**
     * Initialize all type declarations for the overriding node.
     *
     * @param generator LLVM code generator
     */
    @Override
    public void postProcessType(Generator generator) {
        operand.postProcessType(generator);
    }

    /**
     * Initialize all class member declarations for the overriding node.
     *
     * @param generator LLVM code generator
     */
    @Override
    public void postProcessMember(Generator generator) {
        operand.postProcessMember(generator);
    }

    /**
     * Initialize all type uses for the overriding node.
     *
     * @param generator LLVM code generator
     */
    @Override
    public void postProcessUse(Generator generator) {
        operand.postProcessUse(generator);
    }

    /**
     * Get the wrapped type of this value.
     *
     * @return wrapped value type
     */
    @Override
    public Type getValueType() {
        return type;
    }
}
