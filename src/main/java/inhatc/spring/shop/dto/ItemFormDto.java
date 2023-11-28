package inhatc.spring.shop.dto;

import inhatc.spring.shop.constant.ItemSellStatus;
import inhatc.spring.shop.entity.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
//@Builder
public class ItemFormDto {

    private Long id;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemNm;          //상품명

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private int price;              //가격

    @NotNull(message = "재고 수량은 필수 입력 값입니다.")
    private int stockNumber;        //재고 수량

    @NotBlank(message = "상품 상세 정보는 필수 입력 값입니다.")
    private String itemDetail;      //상품 상세 설명

    private ItemSellStatus itemSellStatus;      //상품 판매 상태

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();            //상품 이미지 리스트

    private List<Long> itemImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Item dtoToEntity(){
        return modelMapper.map(this, Item.class);
    }

    public static ItemFormDto entityToDto(Item item){
        return modelMapper.map(item, ItemFormDto.class);
    }
}
