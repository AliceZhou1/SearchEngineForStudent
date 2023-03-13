package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;

public class TermTuple extends AbstractTermTuple {
    /**
     * 判断二个三元组内容是否相同
     *
     * @param o ：要比较的另外一个三元组
     * @return 如果内容相等（三个属性内容都相等）返回true，否则返回false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TermTuple that = (TermTuple) o;
        return freq == that.freq && curPos == that.curPos && term.equals(that.term);
    }

    /**
     * 获得三元组的字符串表示
     *
     * @return ： 三元组的字符串表示
     */
    @Override
    public String toString() {
        return "term:"+super.term+" freq:"+super.freq+" curPos:"+super.curPos;
    }
}
