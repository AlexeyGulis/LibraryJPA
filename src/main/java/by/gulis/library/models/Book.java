package by.gulis.library.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    @NotEmpty(message = "Поле Название Книги не должно быть пустым")
    @Size(max = 200,
            message = "Поле Название Книги не должно быть больше чем 200 символов")
    private String name;
    @Column(name = "author")
    @NotEmpty(message = "Поле Автор не должно быть пустым")
    @Pattern(regexp = "[A-ZА-Я](?U)\\w* [A-ZА-Я](?U)\\w*( [A-ZА-Я](?U)\\w*){0,1}",
            message = "Поле Автор должно быть формата(Фамилия Имя Отчество,где Отчество не обязательно")
    @Size(max = 200, message = "Поле Автор не должно быть больше чем 200 символов")
    private String author;
    @Column(name = "year_of_production")
    @Min(value = 1600)
    private int yearOfProduction;
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @Column(name = "last_time_taken")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastTimeTaken;

    @Transient
    private Boolean overdue;

    public Book() {
    }

    public Book(String name, String author, int yearOfProduction) {
        this.name = name;
        this.author = author;
        this.yearOfProduction = yearOfProduction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getLastTimeTaken() {
        return lastTimeTaken;
    }

    public void setLastTimeTaken(Date lastTimeTaken) {
        this.lastTimeTaken = lastTimeTaken;
    }

    public Boolean getOverdue() {
        return overdue;
    }

    public void setOverdue(Boolean overdue) {
        this.overdue = overdue;
    }
}
