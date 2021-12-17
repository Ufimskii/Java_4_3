package ru.netology.manager;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.netology.domain.Issue;
import ru.netology.repository.IssueRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IssueManagerTest {
    private IssueRepository repository = new IssueRepository();
    private IssueManager manager = new IssueManager(repository);

    private Issue issue1 = new Issue(1, true, "John", 3, new HashSet<>(Arrays.asList("label1", "label2", "label3")), new HashSet<>(Arrays.asList("john", "mari", "peter")));
    private Issue issue2 = new Issue(2, false, "John", 10, new HashSet<>(Arrays.asList("label1", "label2", "label5")), new HashSet<>(Arrays.asList("john", "alex", "peter")));
    private Issue issue3 = new Issue(3, true, "Mike", 1, new HashSet<>(Arrays.asList("label4", "label5", "label6")), new HashSet<>(Arrays.asList("bob", "lara", "sophi")));
    private Issue issue4 = new Issue(4, false, "Lena", 25, new HashSet<>(Arrays.asList("label3", "label1", "label4")), new HashSet<>(Arrays.asList("mari", "lara", "tony")));

    @Nested
    public class Empty {

        @Test
        void mustReturnEmptyIfNothingToSortByNew() {
            List<Issue> expected = new ArrayList<>();
            List<Issue> actual = manager.sortByNewest();
            assertEquals(expected, actual);
        }

        @Test
        void mustReturnEmptyIfNothingToSortByOld() {
            List<Issue> expected = new ArrayList<>();
            List<Issue> actual = manager.sortByOldest();
            assertEquals(expected, actual);
        }

        @Test
        void mustReturnEmptyWhenFindByAuthor() {
            Collection<Issue> expected = new ArrayList<>();
            Collection<Issue> actual = manager.findByAuthor("John");
            assertEquals(expected, actual);
        }

        @Test
        void mustReturnEmptyWhenFindByLabel() {
            List<Issue> expected = new ArrayList<>();
            List<Issue> actual = manager.findByLabel(new HashSet<String>(Arrays.asList("label1")));
            assertEquals(expected, actual);
        }

        @Test
        void mustReturnEmptyWhenFindByAssignee() {
            Collection<Issue> expected = new ArrayList<>();
            Collection<Issue> actual = manager.findByAssignee(new HashSet<String>(Arrays.asList("john")));
            assertEquals(expected, actual);
        }

    }

    @Nested
    public class SingleItem {

        @Test
        void mustReturnOneToSortByNew() {
            manager.issueAdd(issue1);
            List<Issue> expected = new ArrayList<>(List.of(issue1));
            List<Issue> actual = manager.sortByNewest();
            assertEquals(expected, actual);
        }

        @Test
        void mustReturnOneToSortByOld() {
            manager.issueAdd(issue1);
            List<Issue> expected = new ArrayList<>(List.of(issue1));
            List<Issue> actual = manager.sortByOldest();
            assertEquals(expected, actual);
        }

        @Test
        void mustFindByAuthor() {
            manager.issueAdd(issue1);
            Collection<Issue> expected = new ArrayList<>(List.of(issue1));
            Collection<Issue> actual = manager.findByAuthor("John");
            assertEquals(expected, actual);
        }

        @Test
        void mustReturnEmptyIfNotAuthor() {
            manager.issueAdd(issue1);
            Collection<Issue> expected = new ArrayList<>();
            Collection<Issue> actual = manager.findByAuthor("Jack");
            assertEquals(expected, actual);
        }

        @Test
        void mustFindByLabel() {
            manager.issueAdd(issue3);
            Collection<Issue> expected = new ArrayList<>(List.of(issue3));
            Collection<Issue> actual = manager.findByLabel(new HashSet<String>(Arrays.asList("label4")));
            assertEquals(expected, actual);
        }

        @Test
        void mustReturnEmptyIfNoLabel() {
            manager.issueAdd(issue1);
            Collection<Issue> expected = new ArrayList<>();
            Collection<Issue> actual = manager.findByLabel(new HashSet<String>(Arrays.asList("label25")));
            assertEquals(expected, actual);
        }

        @Test
        void mustFindByAssignee() {
            manager.issueAdd(issue1);
            Collection<Issue> expected = new ArrayList<>(List.of(issue1));
            Collection<Issue> actual = manager.findByAssignee(new HashSet<String>(Arrays.asList("john")));
            assertEquals(expected, actual);
        }

        @Test
        void mustReturnEmptyWhenFindByAssignee() {
            manager.issueAdd(issue1);
            Collection<Issue> expected = new ArrayList<>();
            Collection<Issue> actual = manager.findByAssignee(new HashSet<String>(Arrays.asList("anna")));
            assertEquals(expected, actual);
        }
    }

    @Nested
    public class MultipleItems {

        @Test
        void mustAddByOne() {
            manager.issueAdd(issue1);
            manager.issueAdd(issue2);
            List<Issue> expected = new ArrayList<>(List.of(issue1, issue2));
            List<Issue> actual = manager.getAll();
            assertEquals(expected, actual);
        }

        @Test
        void mustSortByNew() {
            manager.addAll(List.of(issue1, issue2, issue3));
            List<Issue> expected = new ArrayList<>(List.of(issue3, issue1, issue2));
            List<Issue> actual = manager.sortByNewest();
            assertEquals(expected, actual);
        }

        @Test
        void mustSortByOld() {
            manager.addAll(List.of(issue1, issue2, issue3));
            List<Issue> expected = new ArrayList<>(List.of(issue2, issue1, issue3));
            List<Issue> actual = manager.sortByOldest();
            assertEquals(expected, actual);
        }

        @Test
        void mustFindByAuthor() {
            manager.addAll(List.of(issue1, issue2, issue3, issue4));
            List<Issue> expected = new ArrayList<>(List.of(issue1, issue2));
            List<Issue> actual = manager.findByAuthor("John");
            assertEquals(expected, actual);
        }

        @Test
        void mustReturnEmptyIfNoAuthor() {
            manager.addAll(List.of(issue1, issue2, issue3, issue4));
            List<Issue> expected = new ArrayList<>();
            List<Issue> actual = manager.findByAuthor("Jack");
            assertEquals(expected, actual);
        }

        @Test
        void mustFindByLabel() {
            manager.addAll(List.of(issue1, issue2, issue3, issue4));
            List<Issue> expected = new ArrayList<>(List.of(issue1, issue4));
            List<Issue> actual = manager.findByLabel(new HashSet<String>(Arrays.asList("label3")));
            assertEquals(expected, actual);
        }

        @Test
        void mustReturnEmptyIfNoLabel() {
            manager.addAll(List.of(issue1, issue2, issue3, issue4));
            List<Issue> expected = new ArrayList<>();
            List<Issue> actual = manager.findByLabel(new HashSet<String>(Arrays.asList("label25")));
            assertEquals(expected, actual);
        }

        @Test
        void mustFindByAssignee() {
            manager.addAll(List.of(issue1, issue2, issue3, issue4));
            List<Issue> expected = new ArrayList<>(List.of(issue3, issue4));
            List<Issue> actual = manager.findByAssignee(new HashSet<String>(Arrays.asList("lara")));
            assertEquals(expected, actual);
        }


        @Test
        void mustReturnEmptyWhenFindByAssignee() {
            manager.addAll(List.of(issue1, issue2, issue3, issue4));
            List<Issue> expected = new ArrayList<>();
            List<Issue> actual = manager.findByAssignee(new HashSet<String>(Arrays.asList("Anna")));
            assertEquals(expected, actual);
        }

    }

}