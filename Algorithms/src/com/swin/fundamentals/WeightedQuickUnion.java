package com.swin.fundamentals;

import com.swin.utils.Constant;
import com.swin.utils.Utils;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Dynamic Connectivity(动态连通性问题)
 * 每个触点对应的id[]元素都是同一个分量中的另一个触点的名称，这种联系称为链接
 * 森林的表示：用节点表示触点，用一个节点到另一个节点的箭头表示链接，在这样一个
 * 森林中无论我们从任何触点所对应的节点开始跟随链接，最终都会到达含有该节点的树
 * 的根节点
 * 加权quick union是对quick union的改进版本，相对于quick union可能会将大树连
 * 接到小树上面，加权quick union总是将小树连接到大树上面
 * 加权quick union最坏的情况下是要被归并的树的大小总是相等的（且总是等于2幂）。
 * 结论：加权quick union的性能是对数级别的
 * Created by Swin on 2016/12/2.
 */
public class WeightedQuickUnion {
    private int[] id;//父链接数组（由触点索引）
    private int[] sz;//各个根节点所对应的分量大小（由触点索引）
    private int count;//分量数量

    public WeightedQuickUnion(int n) {
        this.count = n;
        this.id = new int[n];
        this.sz = new int[n];

        for (int i = 0; i < n; i++) {//初始化分量，
            id[i] = i;
        }

        for (int j = 0; j < n; j++) {
            sz[j] = 1;//初始化有n个分量，n个根节点，每个根节点包含一个分量
        }
    }

    public int count() {
        return count;
    }

    private int find(int p) {
        while (p != id[p])
            p = id[p];
        return p;
    }

    public boolean connect(int p, int q) {
        return find(p) == find(q);
    }

    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        if (pRoot == qRoot)
            return;
        //将小树连接到大树上面
        if (sz[pRoot] > sz[qRoot]) {
            id[qRoot] = pRoot;
            sz[pRoot] += sz[qRoot];
        } else {
            id[pRoot] = qRoot;
            sz[qRoot] += sz[pRoot];
        }
        count--;
    }

    public static void main(String[] args){
        String fn = Constant.DATA_DIR+"tinyUF.txt";
        StdIn.setScanner(Utils.getScanner(fn));
        int n = StdIn.readInt();
        WeightedQuickUnion wqu = new WeightedQuickUnion(n);
        while (!StdIn.isEmpty()){
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if(wqu.connect(p,q))
                continue;
            wqu.union(p,q);
            StdOut.println(p+" "+q);
        }
        StdOut.println("count:"+wqu.count());
    }

}
