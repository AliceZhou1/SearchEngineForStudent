package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractPostingList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class PostingList extends AbstractPostingList {
    public PostingList() {
    }

    /**
     * 添加Posting,要求不能有内容重复的posting
     *
     * @param posting ：Posting对象
     */
    @Override
    public void add(AbstractPosting posting) {
        if(!contains(posting))
        super.list.add(posting);
    }

    /**
     * 获得PosingList的字符串表示
     *
     * @return ： PosingList的字符串表示
     */
    @Override
    public String toString() {
        String s="";
        for(AbstractPosting v:list)
            s+=v;
         return s;
    }

    /**
     * 添加Posting列表,,要求不能有内容重复的posting
     *
     * @param postings ：Posting列表
     */
    @Override
    public void add(List<AbstractPosting> postings) {
        super.list.addAll(postings);//使用HashSet去重必须重写hashcode方法和equals方法
        HashSet<AbstractPosting> set = new HashSet<AbstractPosting>(super.list);
        super.list=new ArrayList<AbstractPosting>(set);
    }

    /**
     * 返回指定下标位置的Posting
     *
     * @param index ：下标
     * @return： 指定下标位置的Posting
     */
    @Override
    public AbstractPosting get(int index) {
        return super.list.get(index);
    }

    /**
     * 返回指定Posting对象的下标
     *
     * @param posting ：指定的Posting对象
     * @return ：如果找到返回对应下标；否则返回-1
     */
    @Override
    public int indexOf(AbstractPosting posting) {
        return super.list.indexOf(posting);
    }

    /**
     * 返回指定文档id的Posting对象的下标
     *
     * @param docId ：文档id
     * @return ：如果找到返回对应下标；否则返回-1
     */
    @Override
    public int indexOf(int docId) {
        for(int i=0;i<super.list.size();i++){
            if(super.list.get(i).getDocId()==docId)
                return i;
        }
        return -1;
    }

    /**
     * 是否包含指定Posting对象
     *
     * @param posting ： 指定的Posting对象
     * @return : 如果包含返回true，否则返回false
     */
    @Override
    public boolean contains(AbstractPosting posting) {
        return super.list.contains(posting);
    }

    /**
     * 删除指定下标的Posting对象
     *
     * @param index ：指定的下标
     */
    @Override
    public void remove(int index) {
        super.list.remove(index);
    }

    /**
     * 删除指定的Posting对象
     *
     * @param posting ：定的Posting对象
     */
    @Override
    public void remove(AbstractPosting posting) {
        super.list.remove(posting);
    }

    /**
     * 返回PostingList的大小，即包含的Posting的个数
     *
     * @return ：PostingList的大小
     */
    @Override
    public int size() {
        return super.list.size();
    }

    /**
     * 清除PostingList
     */
    @Override
    public void clear() {
        super.list.clear();
    }

    /**
     * PostingList是否为空
     *
     * @return 为空返回true;否则返回false
     */
    @Override
    public boolean isEmpty() {
        return super.list.isEmpty();
    }

    /**
     * 根据文档id的大小对PostingList进行从小到大的排序
     */
    @Override
    public void sort() {
        super.list.sort(Comparator.naturalOrder());
    }

    /**
     * 写到二进制文件
     *
     * @param out :输出流对象
     */
    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(super.list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从二进制文件读
     *
     * @param in ：输入流对象
     */
    @Override
    public void readObject(ObjectInputStream in) {
        try {
            super.list= (List<AbstractPosting>) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
