package inhatc.spring.shop.dto;

import inhatc.spring.shop.entity.ItemImg;
import lombok.*;
import org.modelmapper.ModelMapper;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemImgDto {
    private Long id;                //상품 이미지 ID
    private String imgName;         //이미지 파일명
    private String oriImgName;      //이미지 원본 파일명
    private String imgUrl;          //이미지 경로
    private String repImgYn;        //대표 이미지 여부

    public static ModelMapper modelMapper = new ModelMapper();
    public static ItemImgDto entityToDto(ItemImg itemImg){
        ItemImgDto dto = modelMapper.map(itemImg, ItemImgDto.class);
        return dto;
    }
}
