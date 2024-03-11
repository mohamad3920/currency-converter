package com.assignment.currencyconverter.web;

import com.assignment.currencyconverter.model.CurrencyConverterRequestDto;
import com.assignment.currencyconverter.service.CurrencyConverterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class CurrencyConverterController {
    private final CurrencyConverterService currencyConverterService;

    @GetMapping("/converter")
    public String greetingForm(@ModelAttribute CurrencyConverterRequestDto currencyConverterRequestDto) {
        return "index";
    }

    @PostMapping("/converter")
    public String greetingSubmit(@ModelAttribute @Valid CurrencyConverterRequestDto currencyConverterRequestDto, Model model) {
        var result = currencyConverterService.exchange(currencyConverterRequestDto);
        model.addAttribute("amount", result.amount());
        model.addAttribute("rate", result.rate());
        return "index";
    }
}
