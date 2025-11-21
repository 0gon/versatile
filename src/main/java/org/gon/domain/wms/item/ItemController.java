package org.gon.domain.wms.item;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.gon.domain.member.entity.RoleType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    // 재고 생성
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Item createItem(Item item) {
        return this.itemService.createItem(item);
    }


    // 재고 조회
    @PermitAll
    @GetMapping("/{productId}")
    public Item getStock(Long productId) {
        return itemService.getStock(productId);
    }

    // 전체 재고 조회
    @PermitAll
    @GetMapping
    public List<Item> getAllItem() {
        return itemService.getAll();
    }

    // 입고
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{productId}/addStock")
    public Item addStock(@PathVariable Long productId, @RequestParam int quantity) {
        String string = RoleType.ADMIN.toString();
        return itemService.addStock(productId, quantity);
    }

    // 출고
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{productId}/removeStock")
    public Item removeStock(@PathVariable Long productId, @RequestParam int quantity) {
        return itemService.removeStock(productId, quantity);
    }
}
