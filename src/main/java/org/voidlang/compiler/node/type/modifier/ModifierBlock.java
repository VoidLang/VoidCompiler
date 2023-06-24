package org.voidlang.compiler.node.type.modifier;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.voidlang.compiler.node.Generator;
import org.voidlang.compiler.node.Node;
import org.voidlang.compiler.node.NodeInfo;
import org.voidlang.compiler.node.NodeType;
import org.voidlang.llvm.element.IRBuilder;
import org.voidlang.llvm.element.IRValue;

import java.util.List;

/**
 * Represents a block of modifiers that are applied for the following type nodes in the scope.
 */
@RequiredArgsConstructor
@Getter
@NodeInfo(type = NodeType.MODIFIER_BLOCK)
public class ModifierBlock extends Node {
    /**
     * The list of the access modifiers in this block.
     */
    private final List<String> modifiers;

    /**
     * Initialize all the child nodes for the overriding node.
     * @param parent parent node of the overriding node
     */
    @Override
    public void preProcess(Node parent) {
        this.parent = parent;
    }

    /**
     * Initialize all type declarations for the overriding node.
     * @param generator LLVM code generator
     */
    @Override
    public void postProcessType(Generator generator) {
    }

    /**
     * Initialize all type uses for the overriding node.
     * @param generator LLVM code generator
     */
    @Override
    public void postProcessUse(Generator generator) {
    }

    /**
     * Generate an LLVM instruction for this node
     * @param generator LLVM instruction generation context
     */
    @Override
    public IRValue generate(Generator generator) {
        return null;
    }
}
