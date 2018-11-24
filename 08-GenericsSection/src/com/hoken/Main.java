package com.hoken;

public class Main {
    public static void main(String[] args) {
        Player p1 = new Player("p1");
        System.out.println(p1.getName()+" is a "+p1.getClass());

        BaseballPlayer eijun = new BaseballPlayer("Eijun");
        FootballPlayer beckham = new FootballPlayer("Beckham");
        BasketballPlayer kobe = new BasketballPlayer("Kobe");

        System.out.println(eijun.getName()+" is a "+eijun.getClassName());
        System.out.println(beckham.getName()+" is a "+beckham.getClassName());
        System.out.println(kobe.getName()+" is a "+kobe.getClassName());

        Team<BaseballPlayer> seidou = new Team<>("Seidou");
        seidou.addMember(eijun);
        seidou.addMember(new BaseballPlayer("Miyuki"));
        seidou.listMembers();
        // teamBaseball.addMember(kobe); // GENERICS IN ACTION: kobe cant be added to a team restricted to baseball players

        Team<BaseballPlayer> inajitsu = new Team<>("Inajitsu");
        inajitsu.addMember(new BaseballPlayer("Mei"));
        seidou.matchResult(inajitsu, 5, 3);

        Team<BasketballPlayer> basket = new Team<>("Basket");
        basket.addMember(kobe);

        League<Team<BaseballPlayer>> eastTokyo = new League<>("East Tokyo");
        eastTokyo.addTeam(inajitsu);
        // eastTokyo.addTeam(basket); // GENERICS IN ACTION: basket team cant be added to league restricted to baseball team only
        eastTokyo.addTeam(seidou);

        eastTokyo.showLeagueTable();
        eastTokyo.showLeagueMembers();


        /******* RAW UNCLEAR TYPES : GENERICS TO CATCH THE ERROR THATS WHY WE DEFINE GENERICS **********/
        Team rawTeam = new Team("Raw Team");
        rawTeam.addMember(eijun); // is this a warning??
        rawTeam.addMember(kobe); // no error

        League<Team> rawLeague = new League<>("Raw League"); // will accept any teams
        rawLeague.addTeam(seidou); // adding baseball, still no warning
        rawLeague.addTeam(basket); // adding basket, still no warning

        League reallyRaw = new League("Super raw");
        reallyRaw.addTeam(seidou); // baseball team
        reallyRaw.addTeam(rawTeam); // team with mixed players

    }
}
