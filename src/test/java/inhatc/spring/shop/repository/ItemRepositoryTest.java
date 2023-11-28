package inhatc.spring.shop.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import inhatc.spring.shop.constant.ItemSellStatus;
import inhatc.spring.shop.entity.Item;
import inhatc.spring.shop.entity.QItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import org.hibernate.metamodel.mapping.EntityIdentifierMapping;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static inhatc.spring.shop.entity.QItem.item;


@SpringBootTest
@Transactional
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @PersistenceContext
    private EntityManager em;


    // 재고량 -> 150개 이상 이면서 상품명에 8이 들어 있는 아이들을 추출
    // 쿼리 메소드 (재고량과 이름으로 검색)
    // JPQL을 이용해서 위에 조건
    // Native로 위에 조건
    // querydsl로 위에 조건
    public void createItemList(){
        for (int i = 1; i <= 10; i++) {
            Item item = Item.builder()
                    .itemNm("테스트 상품" + i)
                    .price(10000 + i)
                    .stockNumber(100 + i)
                    .itemDetail("테스트 상품 대한 상세 설명" + i)
                    .itemSellStatus(ItemSellStatus.SELL)
                    .build();
            itemRepository.save(item);
        }
    }


    @Test
    @DisplayName("Querydsl 테스트")
    public void queryDslTest(){
        createItemList();
        JPAQueryFactory query = new JPAQueryFactory(em);
        QItem qItem = QItem.item;

        List<Item> itemList = query.selectFrom(QItem.item)
                .where(QItem.item.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(QItem.item.itemDetail.like("%" + "1" + "%"))
                .orderBy(QItem.item.price.desc())
                .fetch();

        itemList.forEach(item1 -> System.out.println(item));
    }

    @Test
    @DisplayName("JPQL 테스트")
    public void findByDetailTest(){
        createItemList();
        itemRepository.findByDetail("1")
                .forEach((item) -> {
                    System.out.println(item);
                });
    }

    @Test
    @DisplayName("Native 테스트")
    public void findByDetailNativeTest(){
        createItemList();
        itemRepository.findByDetailNative("1")
                .forEach((item) ->{
                    System.out.println(item);
                });
    }

    @Test
    @DisplayName("Orderby 테스트")
    public void findByPriceLessThanOrderByPriceDescTest(){
        createItemList();
        itemRepository.findByPriceLessThanOrderByPriceDesc(10005)
                //.forEach(System.out::println);
                .forEach((item) -> {
                    System.out.println(item);
                });
    }

    @Test
    @DisplayName("OR 테스트")
    public void findByItemNmOrItemDetailTest(){
        createItemList();
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품2", "테스트 상품 대한 상세 설명8");
        itemList.forEach((item) -> {
            System.out.println(item);
        });
    }


    @Test
    @DisplayName("상품 이름 검색 테스트")
    public void findByItemNmTest(){
        createItemList();
        itemRepository.findByItemNm("테스트 상품1").forEach(item -> {
            System.out.println(item);
        });
    }

    @Test
    @DisplayName("상품 생성 테스트")
    public void createItemTest(){
        Item item = Item.builder()
                .itemNm("테스트 상품")
                .price(10000)
                .stockNumber(100)
                .itemDetail("테스트 상품에 대한 상세설명")
                .itemSellStatus(ItemSellStatus.SELL)
                .stockNumber(100)
                .build();
        System.out.println("======================= item : " + item);
        Item savedItems = itemRepository.save(item);
        System.out.println("======================= savedItems : " + savedItems);
    }
}