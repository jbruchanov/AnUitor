package com.scurab.android.anuitor.extract2

abstract class BaseExtractor {

    open fun fillValues(item: Any, context: ExtractingContext): MutableMap<String, Any> {
        onFillValues(item, context)
        return context.data
    }

    protected open fun onFillValues(item: Any, context: ExtractingContext) {
        context.put("Inheritance", 0, item) { inheritance() }
        context.put("Type", 0, item) { item.javaClass.name }
        context.put("StringValue", 0, item) { this.toString() }
    }

    protected fun Any?.extract(context: ExtractingContext): Any {
        if (this == null) {
            return "null"
        }
        return (DetailExtractor.findExtractor(this::class.java)
                ?: ReflectionExtractor(true))
                .fillValues(this, context)
    }
}