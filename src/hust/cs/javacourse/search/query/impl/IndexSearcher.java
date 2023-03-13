package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractPostingList;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.index.impl.Index;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.AbstractIndexSearcher;
import hust.cs.javacourse.search.query.Sort;
import hust.cs.javacourse.search.util.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static hust.cs.javacourse.search.query.AbstractIndexSearcher.LogicalCombination.AND;
import static hust.cs.javacourse.search.query.AbstractIndexSearcher.LogicalCombination.OR;

public class IndexSearcher extends AbstractIndexSearcher {
    /**
     * 从指定索引文件打开索引，加载到index对象里. 一定要先打开索引，才能执行search方法
     *
     * @param indexFile ：指定索引文件
     */
    @Override
    public void open(String indexFile) {
        super.index.load(new File(indexFile));
    }

    /**
     * 根据单个检索词进行搜索
     *
     * @param queryTerm ：检索词
     * @param sorter    ：排序器
     * @return ：命中结果数组
     */
    @Override
    public AbstractHit[] search(AbstractTerm queryTerm, Sort sorter) {
        if (Config.IGNORE_CASE)
            queryTerm.setContent(queryTerm.getContent().toLowerCase(Locale.ROOT));
        AbstractPostingList postingList = index.search(queryTerm);
        if (postingList == null)
            return new Hit[0];
        List<AbstractHit> hitlist = new ArrayList<>();
        for (int i = 0; i < postingList.size(); i++) {
            AbstractPosting posting = postingList.get(i);
            int id = posting.getDocId();
            String docpath = index.getDocName(id);
            Hit hit = new Hit(id, docpath);
            hit.getTermPostingMapping().put(queryTerm, posting);
            hit.setScore(sorter.score(hit));
            hitlist.add(hit);
        }
        sorter.sort(hitlist);
        return hitlist.toArray(new AbstractHit[0]);
    }

    /**
     * 根据二个检索词进行搜索
     *
     * @param queryTerm1 ：第1个检索词
     * @param queryTerm2 ：第2个检索词
     * @param sorter     ：    排序器
     * @param combine    ：   多个检索词的逻辑组合方式
     * @return ：命中结果数组
     */
    @Override
    public AbstractHit[] search(AbstractTerm queryTerm1, AbstractTerm queryTerm2, Sort sorter, LogicalCombination combine) {
        List<AbstractHit> list1 = new ArrayList<>(Arrays.asList(search(queryTerm1, sorter)));//aslist返回对象只读
        List<AbstractHit> list2 = new ArrayList<>(Arrays.asList(search(queryTerm2, sorter)));//aslist返回对象只读
        List<AbstractHit> list = new ArrayList<>();
        for (AbstractHit u : list1) {
            for (AbstractHit v : list2)
                if (u.getDocId() == v.getDocId()) {
                    u.getTermPostingMapping().putAll(v.getTermPostingMapping());//合并hit
                    u.setScore(sorter.score(u));
                    if (combine == AND)
                        list.add(u);
                    list2.remove(v);
                    break;
                }
        }
        if(combine == OR) {
            list.addAll(list1);
            list.addAll(list2);
        }
        sorter.sort(list);
        return list.toArray(new AbstractHit[0]);
    }
}
