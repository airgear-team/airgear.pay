package com.airgear.airgearpay.controller;

import com.airgear.airgearpay.service.LiqPayService;
import com.airgear.dto.CheckoutDto;
import com.airgear.dto.GoodsDto;
import com.airgear.service.GoodsService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payment")
@AllArgsConstructor
public class PaymentController {

    private final LiqPayService liqPayService;
    private final GoodsService goodsService;

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @GetMapping("/pay/{goodsId}")
    public String redirectToPaymentPage(@PathVariable Long goodsId, Authentication auth) throws IllegalAccessException {
        GoodsDto good = goodsService.getGoodsById(goodsId);
        CheckoutDto checkoutDTO = liqPayService.createCheckoutDtoPay(good, auth);
        String paymentLink = liqPayService.generatePaymentLink(checkoutDTO);
        return "redirect:" + paymentLink;
    }
}