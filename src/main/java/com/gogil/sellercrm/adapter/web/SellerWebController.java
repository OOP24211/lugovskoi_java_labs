package com.gogil.sellercrm.adapter.web;

import com.gogil.sellercrm.adapter.dto.CreateSellerRequest;
import com.gogil.sellercrm.adapter.dto.SellerResponse;
import com.gogil.sellercrm.adapter.dto.TransactionResponse;
import com.gogil.sellercrm.adapter.dto.UpdateSellerRequest;
import com.gogil.sellercrm.usecase.seller.CreateSeller;
import com.gogil.sellercrm.usecase.seller.DeleteSeller;
import com.gogil.sellercrm.usecase.seller.GetAllSellers;
import com.gogil.sellercrm.usecase.seller.GetSeller;
import com.gogil.sellercrm.usecase.seller.UpdateSeller;
import com.gogil.sellercrm.usecase.transaction.GetAllTransactions;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/sellers")
public class SellerWebController {

    private final GetAllSellers getAllSellers;
    private final GetSeller getSeller;
    private final CreateSeller createSeller;
    private final UpdateSeller updateSeller;
    private final DeleteSeller deleteSeller;
    private final GetAllTransactions getAllTransactions;

    public SellerWebController(GetAllSellers getAllSellers,
                               GetSeller getSeller,
                               CreateSeller createSeller,
                               UpdateSeller updateSeller,
                               DeleteSeller deleteSeller,
                               GetAllTransactions getAllTransactions) {
        this.getAllSellers = getAllSellers;
        this.getSeller = getSeller;
        this.createSeller = createSeller;
        this.updateSeller = updateSeller;
        this.deleteSeller = deleteSeller;
        this.getAllTransactions = getAllTransactions;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String search, Model model) {
        List<SellerResponse> sellers = getAllSellers.execute();

        if (search != null && !search.isBlank()) {
            sellers = sellers.stream()
                    .filter(s -> s.getName().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }

        Map<Long, BigDecimal> revenueMap = getAllTransactions.execute().stream()
                .collect(Collectors.groupingBy(
                        TransactionResponse::getSellerId,
                        Collectors.reducing(BigDecimal.ZERO, TransactionResponse::getAmount, BigDecimal::add)
                ));

        model.addAttribute("sellers", sellers);
        model.addAttribute("revenueMap", revenueMap);
        model.addAttribute("search", search);
        return "sellers/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("request", new CreateSellerRequest());
        model.addAttribute("isEdit", false);
        return "sellers/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("request") CreateSellerRequest request,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "sellers/form";
        }
        createSeller.execute(request);
        return "redirect:/sellers";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        SellerResponse seller = getSeller.execute(id);
        UpdateSellerRequest request = new UpdateSellerRequest();
        request.setName(seller.getName());
        request.setContactInfo(seller.getContactInfo());
        model.addAttribute("request", request);
        model.addAttribute("sellerId", id);
        model.addAttribute("isEdit", true);
        return "sellers/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("request") UpdateSellerRequest request,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("sellerId", id);
            model.addAttribute("isEdit", true);
            return "sellers/form";
        }
        updateSeller.execute(id, request);
        return "redirect:/sellers";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        deleteSeller.execute(id);
        return "redirect:/sellers";
    }
}
