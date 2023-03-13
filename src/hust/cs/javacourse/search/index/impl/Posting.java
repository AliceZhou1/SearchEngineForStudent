package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractPosting;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

public class Posting extends AbstractPosting {

    /**
     * 缺省构造函数
     */
    public Posting() {
    }

    /**
     * 构造函数
     *
     * @param docId     ：包含单词的文档id
     * @param freq      ：单词在文档里出现的次数
     * @param positions ：单词在文档里出现的位置
     */
    public Posting(int docId, int freq, List<Integer> positions) {
        super(docId, freq, positions);
    }

    public Posting(int docId, int freq) {
        super(docId, freq);
    }

    /**
     * 返回包含单词的文档id
     *
     * @return ：文档id
     */
    @Override
    public int getDocId() {
        return super.docId;
    }

    /**
     * 设置包含单词的文档id
     *
     * @param docId ：包含单词的文档id
     */
    @Override
    public void setDocId(int docId) {
        super.docId = docId;
    }

    /**
     * 返回单词在文档里出现的次数
     *
     * @return ：出现次数
     */
    @Override
    public int getFreq() {
        return super.freq;
    }

    /**
     * 设置单词在文档里出现的次数
     *
     * @param freq :单词在文档里出现的次数
     */
    @Override
    public void setFreq(int freq) {
        super.freq = freq;
    }

    /**
     * 返回单词在文档里出现的位置列表
     *
     * @return ：位置列表
     */
    @Override
    public List<Integer> getPositions() {
        return super.positions;
    }

    /**
     * 设置单词在文档里出现的位置列表
     *
     * @param positions ：单词在文档里出现的位置列表
     */
    @Override
    public void setPositions(List<Integer> positions) {
        super.positions = positions;
    }

    /**
     * 判断二个Posting内容是否相同
     *
     * @param obj ：要比较的另外一个Posting
     * @return 如果内容相等返回true，否则返回false
     * position内容相同视为equals
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Posting that = (Posting) obj;
        return getDocId() == that.getDocId()
                && getFreq() == that.getFreq() //下面position内容相同就行，不要求顺序
                &&new LinkedHashSet<>(getPositions()).equals(new LinkedHashSet<>(that.getPositions()));
    }

    /**
     * Postinglist使用Set去重需要重写HashCode
     * 需要使用SET类型使得position中元素无序。
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(docId,freq,new LinkedHashSet(positions));
    }

    @Override
    public String toString() {
        return "AbstractPosting{" +
                "docId=" + docId +
                ", freq=" + freq +
                ", positions=" + positions +
                '}';
    }

    @Override
    public int compareTo(AbstractPosting o) {
        return Integer.valueOf(getDocId()).compareTo(o.getDocId());
    }

    @Override
    public void sort() {
        super.positions.sort(Comparator.naturalOrder());
    }

    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(super.docId);
            out.writeObject(super.freq);
            out.writeObject(super.positions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readObject(ObjectInputStream in) {
        try {
            super.docId= (int) in.readObject();
            super.freq= (int) in.readObject();
            super.positions= (List<Integer>) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
