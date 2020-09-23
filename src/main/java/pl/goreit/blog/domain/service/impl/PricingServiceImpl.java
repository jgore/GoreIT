package pl.goreit.blog.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.goreit.api.generated.OrderResponse;
import pl.goreit.api.generated.OrderlineView;
import pl.goreit.blog.domain.model.Account;
import pl.goreit.blog.domain.service.AccountService;
import pl.goreit.blog.domain.service.PricingService;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PricingServiceImpl implements PricingService {

    @Value("${pricing.commission}")
    private String commission;

    @Autowired
    private AccountService accountService;

    @Override
    public boolean commissionSettlement(OrderResponse orderResponse) {
        List<OrderlineView> orderlineViews = orderResponse.getOrderlineViews();

        BigDecimal sum = orderlineViews.stream()
                .map(orderlineView -> {
                    return orderlineView.getPrice().multiply(BigDecimal.valueOf(orderlineView.getAmount()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal commissionValue = sum.multiply(new BigDecimal(commission));

        for (OrderlineView orderlineView : orderResponse.getOrderlineViews()) {
            String sellerId = orderlineView.getSellerId();
            Account seller = accountService.findByUserId(sellerId);
            seller.decreaseBalance(commissionValue);
            accountService.save(seller);
        }

        return true;

    }
}
