package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.*;

import java.io.*;
import java.util.*;

/**
 * AbstractIndex的具体实现类
 */
public class Index extends AbstractIndex {
    /**
     * 返回索引的字符串表示
     *
     * @return 索引的字符串表示
     */
    @Override
    public String toString() {
//        return docIdToDocPathMapping + " " + termToPostingListMapping;
        StringBuilder builder = new StringBuilder();
        builder.append("docId-----docPath mapping\n");
        for (Map.Entry<Integer, String> entry : docIdToDocPathMapping.entrySet()) {
            builder.append(entry.getKey());
            builder.append("\t---->\t");
            builder.append(entry.getValue());
            builder.append("\n");
        }
        builder.append("PostingList: \n");
        for (Map.Entry<AbstractTerm, AbstractPostingList> entry : termToPostingListMapping.entrySet()) {
            builder.append(entry.getKey().toString());//获得所有posting
            builder.append("\t---->\t");
            builder.append(entry.getValue().toString());
            builder.append("\n");
        }
        return builder.toString();
    }


    /**
     * 添加文档到索引，更新索引内部的HashMap
     *
     * @param document ：文档的AbstractDocument子类型表示
     */
    @Override
    public void addDocument(AbstractDocument document) {
        int id = document.getDocId();
        String docpath = document.getDocPath();
        docIdToDocPathMapping.put(id, docpath);
        for (AbstractTermTuple v : document.getTuples()) {
            if (termToPostingListMapping.containsKey(v.term)) {
                AbstractPostingList postingList = termToPostingListMapping.get(v.term);
                int subscript = postingList.indexOf(id);
                if (subscript != -1) {
                    postingList.get(subscript).getPositions().add(v.curPos);
                    postingList.get(subscript).setFreq(postingList.get(subscript).getFreq() + 1);
                } else {
                    AbstractPosting posting = new Posting(id, 1);
                    posting.getPositions().add(v.curPos);
                    postingList.add(posting);
                }
            } else {
                Posting posting = new Posting(id, 1);
                posting.getPositions().add(v.curPos);
                PostingList postingList = new PostingList();
                postingList.add(posting);
                termToPostingListMapping.put(v.term, postingList);
            }

        }
    }

    /**
     * <pre>
     * 从索引文件里加载已经构建好的索引.内部调用FileSerializable接口方法readObject即可
     * @param file ：索引文件
     * </pre>
     */
    @Override
    public void load(File file) {
        if (file == null)
            return;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            readObject(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <pre>
     * 将在内存里构建好的索引写入到文件. 内部调用FileSerializable接口方法writeObject即可
     * @param file ：写入的目标索引文件
     * </pre>
     */
    @Override
    public void save(File file) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            writeObject(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回指定单词的PostingList
     *
     * @param term : 指定的单词
     * @return ：指定单词的PostingList;如果索引字典没有该单词，则返回null
     */
    @Override
    public AbstractPostingList search(AbstractTerm term) {
        return termToPostingListMapping.get(term);
    }

    /**
     * 返回索引的字典.字典为索引里所有单词的并集
     *
     * @return ：索引中Term列表
     */
    @Override
    public Set<AbstractTerm> getDictionary() {
        return termToPostingListMapping.keySet();
    }

    /**
     * <pre>
     * 对索引进行优化，包括：
     *      对索引里每个单词的PostingList按docId从小到大排序
     *      同时对每个Posting里的positions从小到大排序
     * 在内存中把索引构建完后执行该方法
     * </pre>
     */
    @Override
    public void optimize() {
        for (AbstractPostingList v : termToPostingListMapping.values()) {
            v.sort();
            for (int i = 0; i < v.size(); i++) {
                v.get(i).sort();
            }
        }
    }

    /**
     * 根据docId获得对应文档的完全路径名
     *
     * @param docId ：文档id
     * @return : 对应文档的完全路径名
     */
    @Override
    public String getDocName(int docId) {
        return docIdToDocPathMapping.get(docId);
    }

    /**
     * 写到二进制文件
     *
     * @param out :输出流对象
     */
    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(docIdToDocPathMapping);
            out.writeObject(termToPostingListMapping);
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
            super.docIdToDocPathMapping = (Map<Integer, String>) in.readObject();
            super.termToPostingListMapping = (Map<AbstractTerm, AbstractPostingList>) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
