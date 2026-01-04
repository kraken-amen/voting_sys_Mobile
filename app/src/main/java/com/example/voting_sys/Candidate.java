package com.example.voting_sys;

public class Candidate {

    private String id;
    private String name;
    private String presentation;
    private String program;
    private int votesCount;

    public Candidate() {} // Realtime DB
    public Candidate(String id, String name, String presentation, String program, int votesCount) {
        this.id = id;
        this.name = name;
        this.presentation = presentation;
        this.program = program;
        this.votesCount = votesCount;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getPresentation() { return presentation; }
    public String getProgram() { return program; }
    public int getVotesCount() { return votesCount; }
}
