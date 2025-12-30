package com.example.voting_sys;

public class Candidate {

    private String id;
    private String name;
    private String presentation;
    private String program;
    private int votesCount;

    public Candidate() {} // ❗ ضروري لـ Realtime DB

    public String getId() { return id; }
    public String getName() { return name; }
    public String getPresentation() { return presentation; }
    public String getProgram() { return program; }
    public int getVotesCount() { return votesCount; }
}
