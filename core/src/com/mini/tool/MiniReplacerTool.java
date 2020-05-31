package com.mini.tool;

public class MiniReplacerTool<T extends MiniReplacer> {

    /**
     * 尝试替换，
     * 当${source}==null，返回${target}；
     * 当${source.category}==${target.category}，返回${target}；
     * 当${target.categoryReplace}包含${source.category}，返回${target}；
     *
     * @param source 源值
     * @param target 目标，不可以为空
     * @return 比较结果
     */
    public T tryReplace(T source, T target) {
        if (target == null) {
            throw new NullPointerException();
        }
        if (source == null) {
            return target;
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