package ap.Project;

import java.time.LocalDate;

public abstract class Action {
    protected Book book;
    protected Student student;
    protected LocalDate actionDate;

    public Action(Book book, Student student, LocalDate actionDate) {
        this.book = book;
        this.student = student;
        this.actionDate = actionDate;
    }

    public Book getBook() {
        return book;
    }
    public Student getStudent() {
        return student;
    }

    public LocalDate getActionDate() {
        return actionDate;
    }
}