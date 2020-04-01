package teamcode.test.AdvancedOdometry.LinearAlgebraInterpretation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class Pair<A, B> {
    private final A left;
    private final B right;

    public Pair(A left, B right) {
        this.left = left;
        this.right = right;
    }

    public A left() { return left; }
    public B right() { return right; }

    public String toString() {
        return "{" + left + "," + right + "}";
    }

    public static <A, B> List<Pair<A, B>> zip(List<A> as, List<B> bs) {
        Iterator<A> it1 = as.iterator();
        Iterator<B> it2 = bs.iterator();
        List<Pair<A, B>> result = new ArrayList<>();
        while (it1.hasNext() && it2.hasNext()) {
            result.add(new Pair<A, B>(it1.next(), it2.next()));
        }
        return result;
    }
}