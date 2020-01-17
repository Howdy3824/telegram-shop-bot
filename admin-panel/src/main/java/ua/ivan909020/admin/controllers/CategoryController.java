package ua.ivan909020.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.ivan909020.admin.domain.Category;
import ua.ivan909020.admin.services.CategoryService;
import ua.ivan909020.admin.utils.ControllerUtils;

import javax.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String showAllCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "categories/all";
    }

    @GetMapping("/add")
    public String showAddCategory() {
        return "categories/add";
    }

    @GetMapping("/edit/{category}")
    public String showEditCategory(Model model, @PathVariable Category category) {
        model.addAttribute("category", category);
        return "categories/edit";
    }

    @PostMapping("/create")
    public String createCategory(@Valid Category category, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.mergeAttributes(ControllerUtils.findErrors(bindingResult));
            model.addAttribute("category", category);
            return "categories/add";
        }

        categoryService.save(category);
        return "redirect:/categories";
    }

    @PostMapping("/update")
    public String updateCategory(@Valid Category category, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.mergeAttributes(ControllerUtils.findErrors(bindingResult));
            model.addAttribute("category", category);
            return "categories/edit";
        }

        categoryService.update(category);
        return "redirect:/categories/edit/" + category.getId();
    }

    @PostMapping("/delete")
    public String deleteCategory(@RequestParam Integer id) {
        categoryService.deleteById(id);
        return "redirect:/categories";
    }

}
