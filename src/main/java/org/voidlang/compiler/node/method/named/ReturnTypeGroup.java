package org.voidlang.compiler.node.method.named;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.voidlang.compiler.node.method.type.Type;
import org.voidlang.compiler.node.method.type.TypeGroup;

import java.util.List;

/**
 * Represents a group of nested named type entries.
 * The purpose of this class is to be able to hold named type groups <strong>recursively</strong>.
 * <pre> {@code
 *     (bool success, (string token, string msg)) login()
 * } </pre>
 * Here {@code (bool, string)} is a {@link TypeGroup} of two {@link Type} elements: {@code bool} and {@code string}.
 */
@AllArgsConstructor
@Getter
public class ReturnTypeGroup implements ReturnTypeEntry {
    /**
     * The list of the held nested named type entries.
     */
    private final List<ReturnTypeEntry> entries;
}
