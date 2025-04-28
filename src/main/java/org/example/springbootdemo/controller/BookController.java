package org.example.springbootdemo.controller;

import lombok.Data;
import org.example.springbootdemo.common.BaseResponse;
import org.example.springbootdemo.common.PageResponse;
import org.example.springbootdemo.utils.ResponseUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

  @GetMapping("/list")
  public BaseResponse<List<Book>> getList(
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "10") Integer size) {
    // 模拟数据
    List<Book> books = new ArrayList<>();
    books.add(new Book("三体", "刘慈欣", "科幻"));
    books.add(new Book("活着", "余华", "文学"));

    return ResponseUtils.success(books);
  }

  @GetMapping("/page")
  public PageResponse<Book> getPage(
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "10") Integer size) {
    // 模拟分页数据
    List<Book> books = new ArrayList<>();
    books.add(new Book("三体", "刘慈欣", "科幻"));
    books.add(new Book("活着", "余华", "文学"));
    books.add(new Book("围城", "钱钟书", "文学"));
    books.add(new Book("白鹿原", "陈忠实", "文学"));

    Page<Book> bookPage = new PageImpl<>(
        books.subList((page - 1) * size, Math.min(page * size, books.size())),
        PageRequest.of(page - 1, size),
        books.size());

    return ResponseUtils.success(bookPage);
  }

  // 内部类，实际项目中应该放在entity包下
  @Data
  private static class Book {
    private String name;
    private String author;
    private String category;

    public Book(String name, String author, String category) {
      this.name = name;
      this.author = author;
      this.category = category;
    }
  }
}
