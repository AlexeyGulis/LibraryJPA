package by.gulis.library.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "full_name")
    @NotEmpty(message = "ФИО не должно быть пустым")
    @Pattern(regexp = "[A-ZА-Я](?U)\\w* [A-ZА-Я](?U)\\w*( [A-ZА-Я](?U)\\w*){0,1}",
            message = "Поле Автор должно быть формата(Фамилия Имя Отчество,где Отчество не обязательно")
    @Size(max = 200, message = "ФИО не должно быть больше чем 200 символов")
    private String fullName;

    @OneToMany(mappedBy = "owner")
    private List<Book> bookList;

    @Column(name = "year_of_birth")
    @Min(value = 1900)
    private int dateOfBirth;

    public Person() {
    }

    public Person(String fullName, int dateOfBirth) {
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public int getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(int dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return fullName +
                ", " + dateOfBirth;
    }
}
