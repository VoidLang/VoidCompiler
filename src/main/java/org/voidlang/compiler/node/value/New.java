package org.voidlang.compiler.node.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.voidlang.compiler.node.Generator;
import org.voidlang.compiler.node.Node;
import org.voidlang.compiler.node.NodeInfo;
import org.voidlang.compiler.node.NodeType;
import org.voidlang.compiler.node.element.Class;
import org.voidlang.compiler.node.element.Field;
import org.voidlang.compiler.node.local.Allocator;
import org.voidlang.compiler.node.local.PointerOwner;
import org.voidlang.compiler.node.type.QualifiedName;
import org.voidlang.compiler.node.type.core.Type;
import org.voidlang.llvm.element.*;

import java.util.List;

@RequiredArgsConstructor
@Getter
@NodeInfo(type = NodeType.NEW)
public class New extends Value implements PointerOwner, Allocator {
    private final QualifiedName name;

    private final List<Value> arguments;

    private final Initializator initializator;

    private Type type;

    private IRStruct pointerType;
    private IRValue pointer;

    /**
     * Generate an LLVM instruction for this node
     * @param generator LLVM instruction generation context
     */
    @Override
    public IRValue generate(Generator generator) {
        // create an unnamed allocation if the result is not meant to be handled.
        // e.g. if the constructor is called in a method call
        // greet(new Person("John"))
        //       ^^^^^^ here is an anonymous value of Person, which isn't meant to be mutated
        return allocate(generator, "");
    }

    @Override
    public IRValue allocate(Generator generator, String name) {
        IRContext context = generator.getContext();
        IRBuilder builder = generator.getBuilder();

        pointerType = (IRStruct) type.generateType(context);
        pointer = builder.alloc(pointerType, name);

        if (type instanceof Class clazz) {
            for (Field field : clazz.getFields().values()) {
                Type fieldType = field.getType();
                Node fieldValue = field.getValue();

                IRValue fieldPointer = builder.structMemberPointer(pointerType, pointer,
                    field.getFieldIndex(), "init " + field.getName());

                IRValue value;
                if (fieldValue != null)
                    value = fieldValue.generateAndLoad(generator);
                else
                    value = fieldType.defaultValue(generator);

                builder.store(value, fieldPointer);
            }
        }

        return pointer;
    }

    /**
     * Initialize all the child nodes for the overriding node.
     * @param parent parent node of the overriding node
     */
    @Override
    public void preProcess(Node parent) {
        this.parent = parent;
        for (Node node : arguments)
            node.preProcess(this);
        if (initializator != null)
            initializator.preProcess(this);
    }

    /**
     * Initialize all type declarations for the overriding node.
     * @param generator LLVM code generator
     */
    @Override
    public void postProcessType(Generator generator) {
        for (Node node : arguments)
            node.postProcessType(generator);
        if (initializator != null)
            initializator.postProcessType(generator);
    }

    /**
     * Initialize all class member declarations for the overriding node.
     * @param generator LLVM code generator
     */
    @Override
    public void postProcessMember(Generator generator) {
        for (Node node : arguments)
            node.postProcessMember(generator);
        if (initializator != null)
            initializator.postProcessMember(generator);
    }

    /**
     * Initialize all type uses for the overriding node.
     * @param generator LLVM code generator
     */
    @Override
    public void postProcessUse(Generator generator) {
        for (Node node : arguments)
            node.postProcessUse(generator);
        if (initializator != null)
            initializator.postProcessUse(generator);

        type = resolveType(name.getDirect());
        if (type == null)
            throw new IllegalStateException("Unable to fetch type for New: " + name.getDirect());
    }

    @Override
    public IRValue getPointer() {
        return pointer;
    }

    @Override
    public IRType getPointerType() {
        return pointerType;
    }

    /**
     * Get the wrapped type of this value.
     * @return wrapped value type
     */
    @Override
    public Type getValueType() {
        return type;
    }
}