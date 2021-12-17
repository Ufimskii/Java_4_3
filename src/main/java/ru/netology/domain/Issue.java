package ru.netology.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Issue implements Comparable<Issue> {
    private int id;
    private boolean isOpen;
    private String author;
    private int openedDaysAgo;
    private Set<String> issueLabels = new HashSet<>();
    private Set<String> issueAssignee = new HashSet<>();

    @Override
    public int compareTo(Issue o) {
        return openedDaysAgo - o.openedDaysAgo;
    }
}
