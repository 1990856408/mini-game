package com.mini.tool;

public class MiniReplacerTool<T extends MiniReplacer> {

    public T tryReplace(T source, T target) {
        if (source == null) {
            return target;
        }
        if (target == null) {
            return source;
        }
        if (target.getCategory() == source.getCategory()) {
            return target;
        }
        if ((target.getCategoryReplace() & source.getCategory()) == source.getCategory()) {
            return target;
        }

        return source;
    }
}