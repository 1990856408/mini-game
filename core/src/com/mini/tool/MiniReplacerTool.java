package com.mini.tool;

public class MiniReplacerTool<T extends MiniReplacer> {

    T tryReplace(T originator, T target) {
        if (originator == null) {
            return target;
        }
        if (target == null) {
            return originator;
        }
        if (target.isStatic()) {
            return target;
        }
        return (originator.getPriority() - target.getPriority()) >= 0 ? originator : target;
    }
}