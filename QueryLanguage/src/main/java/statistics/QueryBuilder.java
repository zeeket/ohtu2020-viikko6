package statistics.matcher;

import java.lang.reflect.Method;
import statistics.Player;

public class QueryBuilder {
    Matcher matcher;

    public QueryBuilder() {
        matcher = new All();
    }

    public Matcher build() {
        return matcher;
    }

    public QueryBuilder playsIn(String team) {
        this.matcher = new And(matcher, new PlaysIn(team));
        return this;
    }

    public QueryBuilder hasFewerThan(int value, String category) {
        this.matcher = new And(matcher, new HasFewerThan(value, category));
        return this;
    }

    public QueryBuilder hasAtLeast(int value, String category) {
        this.matcher = new And(matcher, new HasAtLeast(value, category));
        return this;
    }

    public QueryBuilder oneOf(Matcher... matchers) {
        System.out.println("we got "+matchers.length + " matchers and they are:");
        for(Matcher m : matchers){
            System.out.println(m);
        }
        this.matcher = new Or(matchers);
        return this;
    }
}
