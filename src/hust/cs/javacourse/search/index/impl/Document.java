package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractTermTuple;

import java.util.List;

public class Document extends AbstractDocument {

    public Document() {
    }

    public Document(int docId, String docPath) {
        super(docId, docPath);
    }

    public Document(int docId, String docPath, List<AbstractTermTuple> tuples) {
        super(docId, docPath, tuples);
    }

    @Override
    public int getDocId() {
        return super.docId;
    }

    @Override
    public void setDocId(int docId) {
        super.docId = docId;
    }

    @Override
    public String getDocPath() {
        return super.docPath;
    }

    @Override
    public void setDocPath(String docPath) {
        super.docPath = docPath;
    }

    @Override
    public List<AbstractTermTuple> getTuples() {
        return super.tuples;
    }

    @Override
    public void addTuple(AbstractTermTuple tuple) {
        if (!super.tuples.contains(tuple))
            super.tuples.add(tuple);
    }

    @Override
    public boolean contains(AbstractTermTuple tuple) {
        return super.tuples.contains(tuple);
    }

    @Override
    public AbstractTermTuple getTuple(int index) {
        return super.tuples.get(index);
    }

    @Override
    public int getTupleSize() {
        return super.tuples.size();
    }

    @Override
    public String toString() {
        return "docId:" + docId + " docPath:" + docPath + " tuples" + tuples;
    }
}
