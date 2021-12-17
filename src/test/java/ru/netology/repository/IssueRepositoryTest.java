package ru.netology.repository;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.netology.domain.Issue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IssueRepositoryTest {

    private IssueRepository repository = new IssueRepository();

    private Issue issue1 = new Issue(1, true, "John", 3, new HashSet<>(Arrays.asList("label1", "label2", "label3")), new HashSet<>(Arrays.asList("john", "mari", "peter")));
    private Issue issue2 = new Issue(2, false, "John", 10, new HashSet<>(Arrays.asList("label1", "label2", "label5")), new HashSet<>(Arrays.asList("john", "alex", "peter")));
    private Issue issue3 = new Issue(3, true, "Mike", 1, new HashSet<>(Arrays.asList("label4", "label5", "label6")), new HashSet<>(Arrays.asList("bob", "lisa", "sophi")));
    private Issue issue4 = new Issue(4, false, "Lena", 25, new HashSet<>(Arrays.asList("label3", "label1", "label4")), new HashSet<>(Arrays.asList("mari", "lara", "tony")));

    @Nested
    public class EmptyRepository {
        @Test
        void mustReturnEmptyWhenFindByOpen() {
            List<Issue> expected = new ArrayList<>();
            List<Issue> actual = repository.findOpen();
            assertEquals(expected, actual);
        }

        @Test
        void mustReturnEmptyWhenFindByClosed() {
            List<Issue> expected = new ArrayList<>();
            List<Issue> actual = repository.findClosed();
            assertEquals(expected, actual);
        }

        @Test
        void mustReturnEmptyIfClose() {
            repository.closeById(1);
            List<Issue> expected = new ArrayList<>();
            List<Issue> actual = repository.findOpen();
            assertEquals(expected, actual);
        }

        @Test
        void mustReturnEmptyIfOpen() {
            repository.openById(2);
            List<Issue> expected = new ArrayList<>();
            List<Issue> actual = repository.findOpen();
            assertEquals(expected, actual);
        }

    }

    @Nested
    public class SingleItem {
        @Test
        void mustFindOpenIfOpen() {
            repository.save(issue1);
            List<Issue> expected = new ArrayList<>(List.of(issue1));
            List<Issue> actual = repository.findOpen();
            assertEquals(expected, actual);
        }

        @Test
        void mustReturnEmptyIfNoOpen() {
            repository.save(issue2);
            List<Issue> expected = new ArrayList<>();
            List<Issue> actual = repository.findOpen();
            assertEquals(expected, actual);
        }

        @Test
        void mustFindClosedIfClosed() {
            repository.save(issue2);
            List<Issue> expected = new ArrayList<>(List.of(issue2));
            List<Issue> actual = repository.findClosed();
            assertEquals(expected, actual);
        }

        @Test
        void mustReturnEmptyIfNoClosed() {
            repository.save(issue3);
            List<Issue> expected = new ArrayList<>();
            List<Issue> actual = repository.findClosed();
            assertEquals(expected, actual);
        }

        @Test
        void mustClose() {
            repository.save(issue1);
            repository.closeById(1);
            List<Issue> expected = new ArrayList<>(List.of(issue1));
            List<Issue> actual = repository.findClosed();
            assertEquals(expected, actual);
        }

        @Test
        void mustDoNothingIfWrongIdWhenClose() {
            repository.save(issue1);
            repository.closeById(3);
            List<Issue> expected = new ArrayList<>();
            List<Issue> actual = repository.findClosed();
            assertEquals(expected, actual);
        }

        @Test
        void mustDoNothingIfNothingToClose() {
            repository.save(issue2);
            repository.closeById(2);
            List<Issue> expected = new ArrayList<>(List.of(issue2));
            List<Issue> actual = repository.findClosed();
            assertEquals(expected, actual);
        }

        @Test
        void mustOpen() {
            repository.save(issue2);
            repository.openById(2);
            List<Issue> expected = new ArrayList<>(List.of(issue2));
            List<Issue> actual = repository.findOpen();
            assertEquals(expected, actual);
        }

        @Test
        void mustDoNothingIfWrongIdWhenOpen() {
            repository.save(issue1);
            repository.openById(5);
            List<Issue> expected = new ArrayList<>(List.of(issue1));
            List<Issue> actual = repository.findOpen();
            assertEquals(expected, actual);
        }

        @Test
        void mustDoNothingIfNothingToOpen() {
            repository.save(issue1);
            repository.openById(1);
            List<Issue> expected = new ArrayList<>(List.of(issue1));
            List<Issue> actual = repository.findOpen();
            assertEquals(expected, actual);
        }
    }

    @Nested
    public class MultipleItems {
        @Test
        void mustFindOpenIfOpen() {
            repository.addAll(List.of(issue1, issue2, issue3, issue4));
            repository.remove(issue2);
            List<Issue> expected = new ArrayList<>(List.of(issue1, issue3));
            List<Issue> actual = repository.findOpen();
            assertEquals(expected, actual);
        }

        @Test
        void mustReturnEmptyIfNoOpen() {
            repository.addAll(List.of(issue2, issue4));
            List<Issue> expected = new ArrayList<>();
            List<Issue> actual = repository.findOpen();
            assertEquals(expected, actual);
        }

        @Test
        void mustFindClosedIfClosed() {
            repository.addAll(List.of(issue1, issue2, issue3, issue4));
            List<Issue> expected = new ArrayList<>(List.of(issue2, issue4));
            List<Issue> actual = repository.findClosed();
            assertEquals(expected, actual);
        }

        @Test
        void mustReturnEmptyIfNoClosed() {
            repository.addAll(List.of(issue1, issue3));
            List<Issue> expected = new ArrayList<>();
            List<Issue> actual = repository.findClosed();
            assertEquals(expected, actual);
        }

        @Test
        void mustClose() {
            repository.addAll(List.of(issue1, issue2, issue3, issue4));
            repository.closeById(1);
            List<Issue> expected = new ArrayList<>(List.of(issue1, issue2, issue4));
            List<Issue> actual = repository.findClosed();
            assertEquals(expected, actual);
        }

        @Test
        void mustDoNothingIfWrongIdWhenClose() {
            repository.addAll(List.of(issue1, issue2, issue3, issue4));
            repository.closeById(5);
            List<Issue> expected = new ArrayList<>(List.of(issue2, issue4));
            List<Issue> actual = repository.findClosed();
            assertEquals(expected, actual);
        }

        @Test
        void mustDoNothingIfNothingToClose() {
            repository.addAll(List.of(issue2, issue4));
            repository.closeById(4);
            List<Issue> expected = new ArrayList<>(List.of(issue2, issue4));
            List<Issue> actual = repository.findClosed();
            assertEquals(expected, actual);
        }

        @Test
        void mustOpen() {
            repository.addAll(List.of(issue2, issue4));
            repository.openById(4);
            List<Issue> expected = new ArrayList<>(List.of(issue4));
            List<Issue> actual = repository.findOpen();
            assertEquals(expected, actual);
        }

        @Test
        void mustDoNothingIfWrongIdWhenOpen() {
            repository.addAll(List.of(issue1, issue2, issue3, issue4));
            repository.openById(5);
            List<Issue> expected = new ArrayList<>(List.of(issue1, issue3));
            List<Issue> actual = repository.findOpen();
            assertEquals(expected, actual);
        }

        @Test
        void mustDoNothingIfNothingToOpen() {
            repository.addAll(List.of(issue1, issue3));
            repository.openById(1);
            List<Issue> expected = new ArrayList<>(List.of(issue1, issue3));
            List<Issue> actual = repository.findOpen();
            assertEquals(expected, actual);
        }
    }
}