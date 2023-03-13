package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTerm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Term extends AbstractTerm {
    public Term(){

    }

    /**
     * 构造函数
     *
     * @param content ：Term内容
     */
    public Term(String content) {
        super(content);
    }

    /**
     * 判断二个Term内容是否相同
     *
     * @param obj ：要比较的另外一个Term
     * @return 如果内容相等返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Term term = (Term) obj;
        return getContent().equals(term.getContent());
    }

    @Override
    public String toString() {
        return super.content;
    }

    @Override
    public String getContent() {
        return super.content;
    }

    @Override
    public void setContent(String content) {
        super.content=content;
    }

    @Override
    public int compareTo(AbstractTerm o) {
        return super.content.compareTo(o.getContent());
    }

    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(super.content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readObject(ObjectInputStream in) {
        try {
            super.content= (String) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
