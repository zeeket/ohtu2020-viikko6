package statistics;

import statistics.matcher.*;

public class Main {
    public static void main(String[] args) {
        String url = "https://nhlstatisticsforohtu.herokuapp.com/players.txt";

        Statistics stats = new Statistics(new PlayerReaderImpl(url));

        Matcher m = new And( new HasAtLeast(5, "goals"),
                new HasAtLeast(5, "assists"),
                new PlaysIn("PHI")
                );

        for (Player player : stats.matches(m)) {
            System.out.println(player);
        }

        System.out.println("----------------------------mun koodin testi-------------");

        Matcher mun = new And( 
                new Not( new HasAtLeast(1, "goals") ), 
                new PlaysIn("NYR")
                );

        for (Player player : stats.matches(mun)) {
            System.out.println(player);
        }
        System.out.println("----------------------------nyt pitäis tulla sama-------------");
        Matcher mun2 = new And( 
                new HasFewerThan(1, "goals"), 
                new PlaysIn("NYR")
                );
        for (Player player : stats.matches(mun2)) {
            System.out.println(player);
        }
        System.out.println("----------------------------nyt pitäis tulostuu 964-------------");
        System.out.println(stats.matches(new All()).size());

        System.out.println("----------------------------or testi-------------");
        Matcher mun3 = new Or( new HasAtLeast(40, "goals"),
                new HasAtLeast(60, "assists")
                );  
        for (Player player : stats.matches(mun3)) {
            System.out.println(player);
        }
        System.out.println("----------------------------or testi2-------------");
        Matcher mun4 = new And(
                new HasAtLeast(50, "points"),
                new Or( 
                    new PlaysIn("NYR"),
                    new PlaysIn("NYI"),
                    new PlaysIn("BOS")
                    )
                ); 
        for (Player player : stats.matches(mun4)) {
            System.out.println(player);
        }
        System.out.println("----------------------------querybuilder testi-------------");
        QueryBuilder query = new QueryBuilder();

        Matcher qbm = query.playsIn("NYR")
            .hasAtLeast(5, "goals")
            .hasFewerThan(10, "goals").build();

        for (Player player : stats.matches(qbm)) {
            System.out.println( player );
        }
        System.out.println("----------------------------querybuilder testi2-------------");

        Matcher qbm2 = query.oneOf(
                query.playsIn("PHI")
                .hasAtLeast(10, "assists")
                .hasFewerThan(5, "goals").build(),

                query.playsIn("EDM")
                .hasAtLeast(40, "points").build()
                ).build();
        for (Player player : stats.matches(qbm2)) {
            System.out.println( player );
        }
        
    }
}
