package com.verbatoria.infrastructure.conversion.exception

/**
 * @author n.remnev
 */

class ConvertException(fromClass: Class<*>, toClass: Class<*>, cause: String)
    : Exception("Cannot convert $fromClass to $toClass: $cause")