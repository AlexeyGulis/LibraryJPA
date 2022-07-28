package by.gulis.library.controllers;

import by.gulis.library.models.Book;
import by.gulis.library.models.Person;
import by.gulis.library.services.BooksService;
import by.gulis.library.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {

    private BooksService booksService;

    private PeopleService peopleService;


    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(@RequestParam(name = "page",required = false) Integer page,
                        @RequestParam(name = "books_per_page",required = false) Integer booksPerPage,
                        @RequestParam(name = "sort_by_year",required = false) String sortByYear,
                        Model model) {
        model.addAttribute("books", booksService.findAll(page,booksPerPage,sortByYear));
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        Book book = booksService.findOne(id);
        model.addAttribute("book", book);
        if(book.getOwner() != null){
            model.addAttribute("owner", book.getOwner());
        }else{
            model.addAttribute("people", peopleService.findAll());
        }
        return "books/show";
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "search",required = false) String search, Model model){
        if(search == null || "".equals(search)){
            model.addAttribute("books",null);
        }else{
            model.addAttribute("books",booksService.findByNameStartingWith(search));
        }
        return "books/search";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/books/new";
        }
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) return "/books/edit";
        booksService.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/give")
    public String giveBook(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        booksService.giveBook(id, person);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/take")
    public String takeBook(@PathVariable("id") int id) {
        booksService.takeBook(id);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }
}
