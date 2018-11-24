package com.hoken;

import java.util.ArrayList;
import java.util.List;

public class Team<T extends Player> implements Comparable<Team<T>>{
    private String name;
    private List<T> playerList;
    private int won = 0;
    private int lost = 0;
    private int tied = 0;

    public Team(String name) {
        this.name = name;
        this.playerList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public boolean addMember(T player) {
        if (this.playerList.contains(player)) {
            System.out.println(player.getName()+" is already on team " +this.name);
            return false;
        } else {
            this.playerList.add(player);
            System.out.println(player.getName()+" is added to team "+this.name);
            return true;
        }
    }

    public void listMembers() {
        if (this.playerList.isEmpty()) {
            System.out.println(this.name+" has no playerList yet.");
            return;
        }
        System.out.println(this.name+"'s member list:");

        for (T member: playerList) {
            System.out.println(member.getName());
        }
    }

    public void matchResult(Team<T> opponent, int ourScore, int theirScore) {
        String msg;
        if (ourScore > theirScore) {
            won++;
            msg = " beat ";
        } else if (ourScore < theirScore) {
            lost++;
            msg = " lost to ";
        } else {
            tied++;
            msg = " tied with ";
        }
        if (opponent != null) {
            // why do we care about the update of another team?? i think this is so we only have to call 1 statement but not correct way
            System.out.println(this.name+msg+opponent.name);
            opponent.matchResult(null, theirScore, ourScore);
        }
    }

    public int ranking() {
        return (won * 2) + tied - lost;
    }

    @Override
    public int compareTo(Team<T> opponent) {
        if (this.ranking() > opponent.ranking()) {
            return -1; // we are higher in rank, so in the list we should be 1st in the list so -1
        } else if (this.ranking() > opponent.ranking()) {
            return 1;
        }
        return 0;
    }
}
