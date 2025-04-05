package ap.exercises.ex3;

public class Main_EX3_LM_1_1 {

    static class Book {
        private String name;
        private String author;
        private int publishYear;
        private int pages;

        public Book (String name, String author, int publishYear, int pages) {
            this.name = name;
            this.author = author;
            this.publishYear = publishYear;
            this.pages = pages;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getAuthor() {
            return author;
        }
        public void setAuthor(String author) {
            this.author = author;
        }
        public int getPublishYear() {
            return publishYear;
        }
        public void setPublishYear(int publishYear) {
            this.publishYear = publishYear;
        }
        public int getPages() {
            return pages;
        }
        public void setPages(int pages) {
            this.pages = pages;
        }
    }
    static class Student{
        private String firstName;
        private String lastName;
        private int stdNumber;
        private String major;

        public Student(String firstName, String lastName, int stdNumber, String major) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.stdNumber = stdNumber;
            this.major = major;
        }
        public String getFirstName() {
            return firstName;
        }
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
        public String getLastName() {
            return lastName;
        }
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
        public int getStdNumber() {
            return stdNumber;
        }
        public void setStdNumber(int stdNumber) {
            this.stdNumber = stdNumber;
        }
        public String getMajor() {
            return major;
        }
        public void setMajor(String major) {
            this.major = major;
        }
    }
    public static void main(String[] args) {
        Book book1 = new Book("Big Java" , "Cay Horstmann" ,
                                    2019 , 1370);
        Book book2 = new Book("Discrete Mathematics" , " Kenneth H Rosen" ,
                                    2019 , 1118);

        Student student1 = new Student("Neda","Rashtchi",
                                        140301, "Computer Engineering");
        Student student2 = new Student("Zahra","Rashtchi" ,
                                        140302, " Architecture");

        System.out.println(book1.name + " " + book1.author + " " + book1.publishYear + " " + book1.pages);
        System.out.println(book2.name + " " + book2.author + " " + book2.publishYear + " " + book2.pages);
        System.out.println(student1.firstName + " " + student1.lastName + " " + student1.stdNumber+ " " + student1.major);
        System.out.println(student2.firstName + " " + student2.lastName + " " + student2.stdNumber+ " " + student2.major);
    }
}
