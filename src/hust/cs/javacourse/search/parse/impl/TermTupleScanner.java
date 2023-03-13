package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.index.impl.TermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleScanner;
import hust.cs.javacourse.search.util.Config;
import hust.cs.javacourse.search.util.StringSplitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TermTupleScanner extends AbstractTermTupleScanner {
    private int pos = 0;//存储当前文档position
    private Queue<AbstractTermTuple> linetuplelist = new LinkedList<AbstractTermTuple>();//保存读取行三元组

    /**
     * 构造函数
     *
     * @param input ：指定输入流对象，应该关联到一个文本文件
     */
    public TermTupleScanner(BufferedReader input) {
        super(input);
    }

    /**
     * 获得下一个三元组
     *
     * @return: 下一个三元组；如果到了流的末尾，返回null
     */
    @Override
    public AbstractTermTuple next() {
        if (linetuplelist.isEmpty()) {
            try {
                String sread = input.readLine();
                if (sread == null)
                    return null;

                StringSplitter splitter = new StringSplitter();
                splitter.setSplitRegex(Config.STRING_SPLITTER_REGEX);
                List<String> listterm = splitter.splitByRegex(sread);
                for (String content : listterm) {
                    AbstractTermTuple termTuple = new TermTuple();
                    if (Config.IGNORE_CASE)
                        content = content.toLowerCase();
                    termTuple.term.setContent(content);
                    termTuple.curPos = pos;
                    linetuplelist.add(termTuple);
                    pos++;
                }
                return next();//需要递归重新判断不为空时才能输出。
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return linetuplelist.poll();
    }
}
