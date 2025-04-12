package ap.exercises.ex3;

public class EX3_LM_BORROW {
    private String bookName;
    private int studentNumber;
    private String borrowDate;
    private String returnDate;

    public EX3_LM_BORROW(String bookName, int studentNumber, String borrowDate, String returnDate) {
        this.bookName = bookName;
        this.studentNumber = studentNumber;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }
    String getBookName(){
        return bookName;
    }
    int getStudentNumber(){
        return studentNumber;
    }
    String getBorrowDate(){
        return borrowDate;
    }
    String getReturnDate(){
        return returnDate;
    }
}
