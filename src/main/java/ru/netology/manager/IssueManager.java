package ru.netology.manager;

import ru.netology.domain.Issue;
import ru.netology.repository.IssueRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class IssueManager {
    private IssueRepository repository;

    public IssueManager(IssueRepository repository) {
        this.repository = repository;
    }

    public void issueAdd(Issue item) {
        repository.save(item);
    }

    public List<Issue> getAll() {
        return repository.findAll();
    }

    public boolean addAll(List<? extends Issue> items) {
        return repository.addAll(items);
    }

    public List<Issue> sortByNewest() {
        Comparator byNewest = Comparator.naturalOrder();
        ArrayList<Issue> issues = new ArrayList<>(repository.findAll());
        issues.sort(byNewest);
        return issues;
    }

    public List<Issue> sortByOldest() {
        Comparator byNewest = Comparator.reverseOrder();
        ArrayList<Issue> issues = new ArrayList<>(repository.findAll());
        issues.sort(byNewest);
        return issues;
    }

    public List<Issue> findByAuthor(String author) {
        Predicate<String> byAuthor = t -> t.equals(author);
        List<Issue> issues = new ArrayList<>();
        for (Issue item : repository.findAll())
            if (byAuthor.test(item.getAuthor())) {
                issues.add(item);
            }
        return issues;
    }

    public List<Issue> findByLabel(Set<String> label) {
        Predicate<Set<String>> byLabel = t -> t.containsAll(label);
        List<Issue> issues = new ArrayList<>();
        for (Issue item : repository.findAll())
            if (byLabel.test(item.getIssueLabels())) {
                issues.add(item);
            }
        return issues;
    }

    public List<Issue> findByAssignee(Set<String> assignee) {
        Predicate<Set<String>> byLabel = t -> t.containsAll(assignee);
        List<Issue> issues = new ArrayList<>();
        for (Issue item : repository.findAll())
            if (byLabel.test(item.getIssueAssignee())) {
                issues.add(item);
            }
        return issues;
    }
}
