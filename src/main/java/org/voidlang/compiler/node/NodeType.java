package org.voidlang.compiler.node;

/**
 * Represents a registry of the parsable node types.
 */
public enum NodeType {
    ROOT,
    MODIFIER_LIST,
    MODIFIER_BLOCK,
    METHOD,
    FIELD,
    MULTI_FIELD,
    PACKAGE,
    IMPORT,
    CLASS,
    STRUCT,
    TUPLE_STRUCT,
    ENUM,
    INTERFACE,
    ANNOTATION,
    LOCAL_DECLARE,
    MULTI_LOCAL_DECLARE,
    IMMUTABLE_LOCAL_DECLARE_ASSIGN,
    MUTABLE_LOCAL_DECLARE_ASSIGN,
    REFERENCE_LOCAL_DECLARE_ASSIGN,
    LOCAL_DECLARE_DESTRUCTURE_TUPLE,
    LOCAL_DECLARE_DESTRUCTURE_STRUCT,
    LOCAL_ASSIGN,
    VALUE,
    SIZEOF_TYPE,
    SIZEOF_VALUE,
    DEFAULT,
    ACCESSOR,
    REFERENCED_ACCESSOR,
    DEREFERENCED_ACCESSOR,
    SELECTION,
    IMMUTABLE_PARAMETER_INDEXER,
    MUTABLE_PARAMETER_INDEXER,
    REFERENCE_PARAMETER_INDEXER,
    NEW,
    MALLOC,
    FREE,
    NAME,
    COMPOUND_NAME,
    INITIALIZATOR,
    CASTING,
    OPERATION,
    JOIN_OPERATION,
    SIDE_OPERATION,
    METHOD_CALL,
    GROUP,
    TEMPLATE,
    LAMBDA,
    FIELD_ASSIGN,
    INDEX_FETCH,
    INDEX_ASSIGN,
    RETURN,
    DEFER,
    TUPLE,
    ARRAY,
    ARRAY_LOAD,
    ARRAY_STORE,
    IF,
    ELSE_IF,
    ELSE,
    WHILE,
    DO_WHILE,
    FOR,
    FOR_EACH,
    EMPTY,
    ERROR,
    FINISH
}
