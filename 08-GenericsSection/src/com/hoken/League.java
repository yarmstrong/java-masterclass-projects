package com.hoken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class League<T extends Team> {
    private String name;
    private List<T> memberList = new ArrayList<>();

    public League(String name) {
        this.name = name;
    }

    public boolean addTeam(T team) {
        if (this.memberList.contains(team)) {
            System.out.println(team.getName()+" is already part of the league.");
            return false;
        }
        this.memberList.add(team);
        return true;
    }

    public void showLeagueTable() {
        List<T> list = new ArrayList<>(this.memberList); // preserve original order
        Collections.sort(list);
        for (T team : list) {
            System.out.println(team.getName()+": "+team.ranking());
        }
    }

    public void showLeagueMembers() {
        for (T team : this.memberList) {
            System.out.println(team.getName());
        }
    }
}
