package org.gon.domain.wms.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;

    // 재고 생성
    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    // 재고 조회
    @Transactional(readOnly = true)
    public Item getStock(Long productId) {
        return itemRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
    }

    // 전체 재고 조회
    @Transactional(readOnly = true)
    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    // 입고
    public Item addStock(Long productId, int quantity) {
        var product = itemRepository.findByIdWithPessimisticLock(productId).orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
        product.addStock(quantity);
        return itemRepository.save(product);
    }

    // 출고
    public Item removeStock(Long productId, int quantity) {
        var product = itemRepository.findByIdWithPessimisticLock(productId).orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
        product.removeStock(quantity);
        return itemRepository.save(product);
    }

}
