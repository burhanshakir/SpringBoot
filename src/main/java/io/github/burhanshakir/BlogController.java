package io.github.burhanshakir;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class BlogController {

    @Autowired
    BlogRepository blogRepository;

    @GetMapping("/blog")
    public List<Blog> index(){

        return blogRepository.findAll();

    }

    @GetMapping("/blog/{id}")
    public Blog show(@PathVariable String id){

        int blogId = Integer.parseInt(id);
        return blogRepository.findOne(blogId);
    }

    @PostMapping ("/blog/search")
    public List<Blog> search(@RequestParam Map<String,String> body){

        String searchText = body.get("text");

        return blogRepository.findByTitleContainingOrContentContaining(searchText, searchText);

    }

    @PostMapping("/blog")
    public Blog create(@RequestBody Map<String, String> body){
        int id = Integer.parseInt(body.get("id"));
        String title = body.get("title");
        String content = body.get("content");
        return blogRepository.save(new Blog(id, title, content));
    }

    @PutMapping("/blog/{id}")
    public Blog update(@PathVariable String id, @RequestBody Map<String, String> body){
        int blogId = Integer.parseInt(id);
        // getting blog
        Blog blog = blogRepository.findOne(blogId);
        blog.setTitle(body.get("title"));
        blog.setContent(body.get("content"));
        return blogRepository.save(blog);
    }

    @DeleteMapping("blog/{id}")
    public boolean delete(@PathVariable String id){
        int blogId = Integer.parseInt(id);
        blogRepository.delete(blogId);
        return true;
    }

}
